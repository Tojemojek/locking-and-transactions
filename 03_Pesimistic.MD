# Pessimistic Locking
- https://www.postgresql.org/docs/current/explicit-locking.html

| Requested Lock Mode | Current Lock Mode |           |                   |            |
|---------------------|-------------------|-----------|-------------------|------------|
|                     | FOR KEY SHARE     | FOR SHARE | FOR NO KEY UPDATE | FOR UPDATE |
| FOR KEY SHARE       |                   |           |                   | X          |
| FOR SHARE           |                   |           | X                 | X          |
| FOR NO KEY UPDATE   |                   | X         | X                 | X          |
| FOR UPDATE          | X                 | X         | X                 | X          |

## SQL examples

### For Update Lock
I will use 3 consoles - each will have separate connection to database.

All MUST HAVE manual transaction management.

This will create a new transaction AND create a for update lock.
```sql
select * from account where bid = 'ACC_0001' for update;
```
This will be locked - we will not get any results - because we have a lock on this entity. It will wait until lock is released (previous transaction is commited or rolled back).
```sql
select * from account where bid = 'ACC_0001' for update;
```
This will FAIL - we will not get any results - because we have a lock on this entity and we refuse to wait for lock.
```sql
select * from account where bid = 'ACC_0001' for update nowait;
```

SURPRISE - this works and fetches locked entity. This is because we have a lock on entity, but locks works only with other locks!
```sql
select * from account where bid = 'ACC_0001'
```

BUT 
```sql
update account set amount = 100 where bid = 'ACC_0001';
```
will not work - because we have a lock on entity and we are trying to change it. So it will have to wait.
What is maybe even worse.

#### Lost update due to locks

We create a new transaction and lock entity.
```sql
select * from account where bid = 'ACC_0001' for update;
--some logic, calculations, update value to 100.
update account set amount = 100 where bid = 'ACC_0001';
```
Above will be visible only after commit.  

But lets imagine that on same db someone else will do this:
```sql
select * from account where bid = 'ACC_0001';
--some logic, calculations, update value to 10.
update account set amount = 10 where bid = 'ACC_0001';
```
Select statement will be give result with amount from db - but before update from first transaction. <br/>
This may lead to some business logic, calculations and then update statement - this will be locked until first transaction is commited. <br/>
So we will have a situation where we will update entity with value from db - but this value will be from db before first transaction. <br/>
First transaction will be lost - completely overwritten. <br/>


## For Share Lock
I will use 3 consoles - each will have separate connection to database.

All MUST HAVE manual transaction management.

This will create a new transaction AND create a for share lock.
```sql
select * from account where bid = 'ACC_0001' for share;
```
This will create a new transaction AND create a for share lock - SHARE LOCK do not lock each other.
```sql
select * from account where bid = 'ACC_0001' for share;
```

This will be locked - we will not get any results - because we have a share lock on this entity. SHARE LOCK will also lock any update or delete on this entity.
```sql
select * from account where bid = 'ACC_0001' for update;
```

## Java approach

There are 3 types of locks

- PESSIMISTIC_READ `FOR SHARE` <br/>
  we lock entity - everybody with lower lock level (or no lock) can read entity <br/>
  NOBODY can change entity (until we will commit / rollback).

```sql
    select address0_.id            as id1_1_,
           address0_.bid           as bid2_1_,
           address0_.city          as city3_1_,
           address0_.created_date  as created_4_1_,
           address0_.last_modified as last_mod5_1_,
           address0_.street        as street6_1_,
           address0_.street_no     as street_n7_1_,
           address0_.version       as version8_1_
    from address address0_
    where address0_.bid = ? for share of address0_
```

- PESSIMISTIC_WRITE `FOR UPDATE` <br/>
  we lock entity - nobody with any lock can read this entity (but You can read it without any lock).<br>
  NOBODY can change entity (until we will commit / rollback).

```sql
select address0_.id            as id1_1_0_,
       address0_.bid           as bid2_1_0_,
       address0_.city          as city3_1_0_,
       address0_.created_date  as created_4_1_0_,
       address0_.last_modified as last_mod5_1_0_,
       address0_.street        as street6_1_0_,
       address0_.street_no     as street_n7_1_0_,
       address0_.version       as version8_1_0_
from address address0_
where address0_.id = ? for update of address0_
```

- PESSIMISTIC_FORCE_INCREMENT `FOR UPDATE NOWAIT` <br/>
  we lock entity - nobody with any lock can read this entity (but You can read it without any lock).<br>
  NOBODY can change entity (until we will commit / rollback). <br/>
  additional feature - by default this lock will increment version of entity. <br/>
  additional feature 2 - this will fail if there is any lock on entity. (nowait component)
- This update of version will be visible only after commit. (But this is good enough for preventing lost updates for optimistic locking)
```sql
select address0_.id            as id1_1_,
       address0_.bid           as bid2_1_,
       address0_.city          as city3_1_,
       address0_.created_date  as created_4_1_,
       address0_.last_modified as last_mod5_1_,
       address0_.street        as street6_1_,
       address0_.street_no     as street_n7_1_,
       address0_.version       as version8_1_
from address address0_
where address0_.bid = ? for update
    of address0_ nowait;
update
    address
set version=?
where id = ?
  and version = ?;
```

as above but works with @Version on entity.

### Scopes

- PessimisticLockScope.NORMAL
  locks only our entity
- PessimisticLockScope.EXTENDED
  locks entity and all it's internal collections











