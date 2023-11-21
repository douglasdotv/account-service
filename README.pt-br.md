# Account Service

[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/douglasdotv/account-service/blob/master/README.md)
[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](https://github.com/douglasdotv/account-service/blob/master/README.pt-br.md)

## Introdução

Este é um projeto focado na criação de um serviço de contabilidade. O serviço foi projetado como uma API REST que integra autenticação, autorização e regras de negócios relacionadas a práticas contábeis.

## Funcionalidades

- Funcionalidades contábeis disponibilizadas via API
- Controle de acesso baseado em funções para administradores, contadores, auditores e outros funcionários
- Rastreamento e registro de eventos de segurança para atividades cruciais
- Mecanismo de bloqueio de conta após cinco tentativas de login sem sucesso

## Endpoints

- POST /api/auth/signup: registrar um novo usuário
- POST /api/auth/changepass: alterar a senha de um usuário autenticado
- POST /api/acct/payments: adicionar um lote de payments
- PUT /api/acct/payments: atualizar um payment específico
- GET /api/admin/user/: listar todos os usuários
- DELETE /api/admin/user/{userEmail}: excluir usuário por e-mail
- PUT /api/admin/user/role: conceder/remover roles aos usuários
- PUT /api/admin/user/access: bloquear/desbloquear o acesso de usuários
- GET /api/empl/payment: obter um payment para um funcionário por um período específico (ou obter todos os payments se nenhum período for especificado)

## Permissões

|          Endpoint           | Anônimo | Usuário | Contador | Administrador | Auditor |
| :-------------------------: | :-----: | :-----: | :------: | :-----------: | :-----: |
|   `POST api/auth/signup`    |    +    |    +    |    +     |       +       |    +    |
| `POST api/auth/changepass`  |         |    +    |    +     |       +       |    -    |
|   `GET api/empl/payment`    |    -    |    +    |    +     |       -       |    -    |
|  `POST api/acct/payments`   |    -    |    -    |    +     |       -       |    -    |
|   `PUT api/acct/payments`   |    -    |    -    |    +     |       -       |    -    |
|    `GET api/admin/user`     |    -    |    -    |    -     |       +       |    -    |
|   `DELETE api/admin/user`   |    -    |    -    |    -     |       +       |    -    |
|  `PUT api/admin/user/role`  |    -    |    -    |    -     |       +       |    -    |
| `PUT api/admin/user/access` |    -    |    -    |    -     |       +       |    -    |
|  `GET api/security/events`  |    -    |    -    |    -     |       -       |    +    |

## Ferramentas Utilizadas

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- H2
- Lombok
- MapStruct
