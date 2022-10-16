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













