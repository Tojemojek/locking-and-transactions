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


