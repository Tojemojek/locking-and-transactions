## Pessimistic Locking

- https://www.postgresql.org/docs/current/explicit-locking.html

| Requested Lock Mode | Current Lock Mode |           |                   |            |
|---------------------|-------------------|-----------|-------------------|------------|
|                     | FOR KEY SHARE     | FOR SHARE | FOR NO KEY UPDATE | FOR UPDATE |
| FOR KEY SHARE       |                   |           |                   | X          |
| FOR SHARE           |                   |           | X                 | X          |
| FOR NO KEY UPDATE   |                   | X         | X                 | X          |
| FOR UPDATE          | X                 | X         | X                 | X          |

### Types

There are 3 types of locks

- PESSIMISTIC_READ
  we lock entity - everybody can read entity, NOBODY can change entity.
  in postgress realized with `FOR SHARE` lock

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

- PESSIMISTIC_WRITE
  we lock entity - everybody can read entity, only we can change entity.
  in postgress realized with `FOR UPDATE` lock

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

- PESSIMISTIC_FORCE_INCREMENT

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











