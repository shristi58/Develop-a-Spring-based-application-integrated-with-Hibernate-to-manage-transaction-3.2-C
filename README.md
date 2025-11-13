# Banking Transaction Demo (Spring + Hibernate)

## Overview
Simple Spring Boot application demonstrating transaction management using Spring Data JPA / Hibernate.
It provides an `AccountService.transfer()` method that moves money between accounts within a `@Transactional` boundary so failures cause rollback.

## Tech
- Spring Boot
- Spring Data JPA (Hibernate)
- MySQL
- Java 17

## Setup
1. Create DB using `src/main/resources/bank_schema.sql` or let JPA create it (`ddl-auto=update`).
2. Update DB credentials in `application.properties`.
3. Build and run:
