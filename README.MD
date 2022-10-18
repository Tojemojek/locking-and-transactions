# Locking and transactions

## Isolation levels

I use *Read Committed* default isolation level for postgress.

More info: <br>
[fauna.com](https://fauna.com/blog/introduction-to-transaction-isolation-levels) <br>
[geeksforgeeks.org](https://www.geeksforgeeks.org/transaction-isolation-levels-dbms/) <br>
[postgresql.org](https://www.postgresql.org/docs/current/transaction-iso.html)

**!!! WARNING !!!** <br>

Postgres bahaves a bit different than most of SQL DB systems. From Postgress specs<br>

> In PostgreSQL, you can request any of the four standard transaction isolation levels, but internally only three
> distinct isolation levels are implemented, i.e., PostgreSQL's Read Uncommitted mode behaves like Read Committed. This
> is
> because it is the only sensible way to map the standard isolation levels to PostgreSQL's multiversion concurrency
> control architecture.

> The table also shows that PostgreSQL's Repeatable Read implementation does not allow phantom reads. This is acceptable
> under the SQL standard because the standard specifies which anomalies must not occur at certain isolation levels;
> higher
> guarantees are acceptable. The behavior of the available isolation levels is detailed in the following subsections.

### BEFORE START

Prepare db - use provided

- docker/custom-logging/docker-compose.yaml (it logs a lot) <br>
  or
- docker/standard/docker-compose.yaml <br>
  or
- any Postgress DB with provided there password and user

Use files to create schema and fill it with data

- data/01_create.sql
- data/02_data.sql

Use postman collections to import configured rest calls. <br>
Use provided jMetter files to test.

## N+1 again...
### Using paging
First page:
```sql
    select
        person0_.id as id1_2_,
        person0_.bid as bid2_2_,
        person0_.created_date as created_3_2_,
        person0_.last_modified as last_mod4_2_,
        person0_.name as name5_2_,
        person0_.primary_address_id as primary_7_2_,
        person0_.version as version6_2_ 
    from
        person person0_ 
    order by
        person0_.bid asc limit ?
```
Next Page:
```sql
    select
        person0_.id as id1_2_,
        person0_.bid as bid2_2_,
        person0_.created_date as created_3_2_,
        person0_.last_modified as last_mod4_2_,
        person0_.name as name5_2_,
        person0_.primary_address_id as primary_7_2_,
        person0_.version as version6_2_ 
    from
        person person0_ 
    order by
        person0_.bid asc limit ? offset ?
```

BUT beware! When fetching "bags" or "sets" paging... does not work properly: <br>
```sql
select
        person0_.id as id1_2_0_,
        account2_.id as id1_0_1_,
        person0_.bid as bid2_2_0_,
        person0_.created_date as created_3_2_0_,
        person0_.last_modified as last_mod4_2_0_,
        person0_.name as name5_2_0_,
        person0_.primary_address_id as primary_7_2_0_,
        person0_.version as version6_2_0_,
        account2_.amount as amount2_0_1_,
        account2_.bid as bid3_0_1_,
        account2_.created_date as created_4_0_1_,
        account2_.last_modified as last_mod5_0_1_,
        account2_.version as version6_0_1_,
        accounts1_.person_id as person_i1_3_0__,
        accounts1_.accounts_id as accounts2_3_0__ 
    from
        person person0_ 
    left outer join
        person_accounts accounts1_ 
            on person0_.id=accounts1_.person_id 
    left outer join
        account account2_ 
            on accounts1_.accounts_id=account2_.id 
    order by
        person0_.bid asc
```

WARNING!
> firstResult/maxResults specified with collection fetch; applying in memory!

### Fetching "bags"
You can specify max one bag
You can "chain" queries and allow hibernate cache L1 to do it's job


### Fetchin "sets"
You can specify as many as You want in Graph: <br>
`@EntityGraph(type= EntityGraph.EntityGraphType.FETCH, attributePaths={"accounts", "addresses"} )`
```sql
select
  person0_.id as id1_2_0_,
  account2_.id as id1_0_1_,
  address4_.id as id1_1_2_,
  person0_.bid as bid2_2_0_,
  person0_.created_date as created_3_2_0_,
  person0_.last_modified as last_mod4_2_0_,
  person0_.name as name5_2_0_,
  person0_.primary_address_id as primary_7_2_0_,
  person0_.version as version6_2_0_,
  account2_.amount as amount2_0_1_,
  account2_.bid as bid3_0_1_,
  account2_.created_date as created_4_0_1_,
  account2_.last_modified as last_mod5_0_1_,
  account2_.version as version6_0_1_,
  accounts1_.person_id as person_i1_3_0__,
  accounts1_.accounts_id as accounts2_3_0__,
  address4_.bid as bid2_1_2_,
  address4_.city as city3_1_2_,
  address4_.created_date as created_4_1_2_,
  address4_.last_modified as last_mod5_1_2_,
  address4_.street as street6_1_2_,
  address4_.street_no as street_n7_1_2_,
  address4_.version as version8_1_2_,
  addresses3_.person_id as person_i1_4_1__,
  addresses3_.addresses_id as addresse2_4_1__
from
  person person0_
    left outer join
  person_accounts accounts1_
  on person0_.id=accounts1_.person_id
    left outer join
  account account2_
  on accounts1_.accounts_id=account2_.id
    left outer join
  person_addresses addresses3_
  on person0_.id=addresses3_.person_id
    left outer join
  address address4_
  on addresses3_.addresses_id=address4_.id
```


## Optimistic Locking

TEST 1:
RESET ACC_0001 to amount = 0.
Try to add 100 times 1.00 to account ACC_0001.
Expected result ACC_0001 has amount 100 and 100 requests are success.
For tests I use hikari.maximum-pool-size= 10 - experiment with this value, results may surprise You

### Lost updates TEST 1:

Comment out @Version in class pl.kostrowski.locking.model.Account
Try to run jMetter from file ./jmetter/addToAccount.jmx

Result:

- 100% Success rate on requests (all 200 OK response code)
- amount = 10.00 (90% of failures)
  OMG WHAT HAVE WE DONE!

BUT WHY?

```sql
    update
        account
    set amount=?,
        bid=?,
        created_date=?,
        last_modified=?,
        version=?
    where id = ? 
```

so basically this is overwrite. What happens on DB side? Each line when updated is locked (there is no option to change
same line in 2 threads) so our requests send and in queue and only last result of parallel requests is saved;

### Lost updates TEST 2:

@Version in class pl.kostrowski.locking.model.Account must be active
Try to run jMetter from file ./jmetter/addToAccount.jmx

Result:

- 10% Success rate on requests (10 200 OK, others 500 with OptimisticLockingException)
- amount = 10.00 (BUT 90% of failures - so valid result)
  NOT THE BEST but at least we ACK only good transactions.

BUT WHY?

```sql
    update
        account
    set amount=?,
        bid=?,
        created_date=?,
        last_modified=?,
        version=?
    where id = ?
      and version = ?
```

so version must match on DB what we have in java = in this update statement we are also changing version. As single line
is always locked on update in DB, there is no risk of lost updates.

### Lost updates TEST 3:

Try to run jMetter from file ./jmetter/addToAccountWithRetry.jmx
As Test 2 but method with "retry=true"

- 100% Success rate on requests (100 200 OK)
- amount = 100.00 (so valid result)
  a lot of retries done = but that's how optimistic locking should work

## Pessimistic Locking

- https://www.postgresql.org/docs/current/explicit-locking.html

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











