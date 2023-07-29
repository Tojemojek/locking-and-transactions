## Optimistic Locking

TEST 1:
RESET ACC_0001 to amount = 0.
Try to add 100 times 1.00 to account ACC_0001.
Expected result ACC_0001 has amount 100 and 100 requests are success.
For tests I use hikari.maximum-pool-size= 10 - experiment with this value, results may surprise You

### Lost updates TEST 1:

In this test we are using entity without @Version annotation [AccountNoVersion.java](locking%2Fsrc%2Fmain%2Fjava%2Fpl%2Fkostrowski%2Fpessimistic%2Fmodel%2Fnotaudited%2FAccountNoVersion.java)
Try to run jMetter from file [addToAccountNoRetry_noLock.jmx](locking%2Fjmetter%2FaddToAccountNoRetry_noLock.jmx)

Result:

- 100% Success rate on requests (all 200 OK response code)
- amount = waaay smaller than we expected (in my case around 10.00)
  OMG WHAT HAVE WE DONE!

BUT WHY?

```sql
    update
        account
    set amount=?,
        bid=?
    where id = ? 
```

so basically this is overwrite. What happens on DB side? Each line when updated is locked (there is no option to change
same line in 2 threads) so our requests send and in queue and only last result of parallel requests is saved;

### Lost updates TEST 2:

@Version in class pl.kostrowski.nplusone.model.Account must be active
Try to run jMetter from file [addToAccountNoRetry_optimistic.jmx](locking%2Fjmetter%2FaddToAccountNoRetry_optimistic.jmx)./jmetter/addToAccount.jmx

Result:

- 10% Success rate on requests (10 200 OK, others 500 with OptimisticLockingException)
- amount = would be correct as only successful requests are saved
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

Try to run jMetter from file [addToAccountWithRetry_optimistic.jmx](locking%2Fjmetter%2FaddToAccountWithRetry_optimistic.jmx)
As Test 2 but method with "retry=true"

- 100% Success rate on requests (100 200 OK)
- amount = 30.00 (so valid result)
  a lot of retries done = but that's how optimistic locking should work

But this will create A LOT of noise... and we will have to retry a lot of times.

