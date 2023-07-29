# Locking and transactions

## BEFORE START

### Prepare db
use provided
- [Docker compose file](docker%2Fstandard%2Fpostgres.yaml)

OR

- any Postgress DB with provided there password and user
  - POSTGRES_USER: isolation
  - POSTGRES_PASSWORD: isolation
  - POSTGRES_DB: isolation

### Add data
Use files to create schema and fill it with data
- [Create Tables](pessimistic%2Fdata%2F01_create.sql)
- [Add test data](pessimistic%2Fdata%2F02_data.sql)
- [Set Logging Levels](pessimistic%2Fdata%2F03_set_logging.sql)


After last step you should restart database, to apply changes.

### Prepare Postman and jMeter

Use postman collections to import configured rest calls. <br>
Use provided jMetter files to test.


### Go to next steps (-;
- [Isolation levels](00_Isolation_levels.MD)
- [N+1 problems](01_n%2B1.MD)
- [Optimistic Locking](02_Optimistic.MD)
- [Pesimistic Locking](03_Pesimistic.MD)

### Additional info
https://www.monkeyuser.com/2022/small-delights/ <br>
that's all folks!