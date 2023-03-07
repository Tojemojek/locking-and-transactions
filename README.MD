# Locking and transactions

## BEFORE START

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

[Isolation levels](00_Isolation_levels.MD)

[Optimistic Locking](01_Optimistic.MD)

[Pesimistic Locking](02_Pesimistic.MD)