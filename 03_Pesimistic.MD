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

## Deadlocks
IF you want to update or delete entity you must get lock "For Update" on this entity. <br/>
And this will throw exceptions if there is incorrect set of locks existing on entity - please check demo with jmetter.


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
    select
      a1_0.id,
      a1_0.amount,
      a1_0.bid,
      a1_0.created_date,
      a1_0.last_modified,
      a1_0.version
    from
      account a1_0
    where
      a1_0.bid=? for share
```

```sql
    update
        account 
    set
        amount=?,
        bid=?,
        created_date=?,
        last_modified=?,
        version=? 
    where
        id=? 
        and version=?
```

- PESSIMISTIC_WRITE `FOR NO KEY UPDATE` <br/>
  we lock entity - nobody with any lock can read this entity (but You can read it without any lock).<br>
  NOBODY can change entity (until we will commit / rollback).

```sql
    select
      a1_0.id,
      a1_0.amount,
      a1_0.bid,
      a1_0.created_date,
      a1_0.last_modified,
      a1_0.version
    from
      account a1_0
    where
      a1_0.bid=? for no key update
```

```sql
    update
      account
    set
      amount=?,
      bid=?,
      created_date=?,
      last_modified=?,
      version=?
    where
      id=?
      and version=?
```

- PESSIMISTIC_FORCE_INCREMENT `FOR UPDATE NOWAIT` <br/>
  we lock entity - nobody with any lock can read this entity (but You can read it without any lock).<br>
  NOBODY can change entity (until we will commit / rollback). <br/>
  additional feature - by default this lock will increment version of entity just after query statement. <br/>
  additional feature 2 - this will fail if there is any lock on entity. (nowait component)
- This update of version will be visible only after commit. (But this is good eno+ugh for preventing lost updates for optimistic locking)
```sql
    select
      a1_0.id,
      a1_0.amount,
      a1_0.bid,
      a1_0.created_date,
      a1_0.last_modified,
      a1_0.version
    from
      account a1_0
    where
      a1_0.bid=? for no key update nowait
```
```sql
    update
        account 
    set
        version=? 
    where
        id=? 
        and version=?
```

```sql
    update
        account 
    set
        amount=?,
        bid=?,
        created_date=?,
        last_modified=?,
        version=? 
    where
        id=? 
        and version=?
```

### Scopes

- PessimisticLockScope.NORMAL
  locks only our entity
- PessimisticLockScope.EXTENDED
  locks entity and all it's internal collections











