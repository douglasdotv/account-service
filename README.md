# Account Service

[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/douglasdotv/account-service/blob/master/README.md)
[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](https://github.com/douglasdotv/account-service/blob/master/README.pt-br.md)

## Introduction

This is a project focused on creating a secure accounting service. The service is designed as a REST API, integrating authentication, authorization and business logic related to accounting practices.

## Features

- Accounting functionalities via API
- Role-based access control for administrators, accountants, auditors, and other employees
- Security event tracking and logging for crucial activities
- Account locking mechanism after five unsuccessful login attempts

## Endpoints

- POST /api/auth/signup: register a new user
- POST /api/auth/changepass: change the password of an authenticated user.
- POST /api/acct/payments: add a batch of payments
- PUT /api/acct/payments: update a specific payment
- GET /api/admin/user/: retrieve all app users
- DELETE /api/admin/user/{userEmail}: delete user by e-mail
- PUT /api/admin/user/role: grant/remove roles to users
- PUT /api/admin/user/access: lock/unlock user access
- GET /api/empl/payment: get a payment for an employee for a specific period (or get all payments if no period is specified)

## Permissions

|          Endpoint           | Anonymous | User | Accountant | Administrator | Auditor |
| :-------------------------: | :-------: | :--: | :--------: | :-----------: | :-----: |
|   `POST api/auth/signup`    |     +     |  +   |     +      |       +       |    +    |
| `POST api/auth/changepass`  |           |  +   |     +      |       +       |    -    |
|   `GET api/empl/payment`    |     -     |  +   |     +      |       -       |    -    |
|  `POST api/acct/payments`   |     -     |  -   |     +      |       -       |    -    |
|   `PUT api/acct/payments`   |     -     |  -   |     +      |       -       |    -    |
|    `GET api/admin/user`     |     -     |  -   |     -      |       +       |    -    |
|   `DELETE api/admin/user`   |     -     |  -   |     -      |       +       |    -    |
|  `PUT api/admin/user/role`  |     -     |  -   |     -      |       +       |    -    |
| `PUT api/admin/user/access` |     -     |  -   |     -      |       +       |    -    |
|  `GET api/security/events`  |     -     |  -   |     -      |       -       |    +    |

## Tools Used

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- H2
- Lombok
- MapStruct
