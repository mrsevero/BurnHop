# Burnhop Back-end

[![Build Status](https://travis-ci.com/wandsu/burnhop-server.svg?branch=dev)](https://travis-ci.com/wandsu/burnhop-server)

## Configuração

Para este projeto, é necessário as versões das seguintes bibliotecas:

    postgres 12.4
    openjdk 11
    apache maven 3.6.3
    
Com as dependências instaladas, é possível configurar o projeto.

### Postgres

Antes de tudo, é necessário criar o banco de dados e as tabelas do **Burnhop** no Postgres. 

##### 1. Criar novo banco de dados no postgres chamado burnhop.

    CREATE DATABASE burnhop;

##### 2. Realizar a recuperação do arquivo dump para criar as tabelas conforme o arquivo database/burnhop.sql;

    psql burnhop < burnhop.sql

Com isso, o Postgres já está configurado para o **Burnhop**.

## Deploy

Para gerar o arquivo **jar** do projeto, é necessário entrar na pasta do back-end e executar o comando:

    mvn clean install
    
Ao finalizar, será criada uma nova pasta chamada **target**, dentro dela estará o arquivo **jar** referente ao **Burnhop**.

Para executá-lo, basta utilizar o comando:

    java -jar burnhop-{version}.jar
    
## Recursos

O servidor por padrão está configurado na porta **9000**. 
Os **resources** serão descritos logo abaixo.

### Users Resource

Todos as requisições relacionadas aos usuários tem como caminho raiz **/users**

#### Create User

Este recurso é responsável por criar um novo usuário no banco de dados.
Para esta requisição, é necessário definir as seguintes configurações:

    HTTP Method: POST
    Content-type: x-www-form-urlencoded
    URL: http://ip:9000/users/
    Body: "name=name&username=username&email=email&data_nasc=2020-10-10&pwd=senha"

Existem 2 possíveis respostas para esta requisição.

    200 OK. Usuário criado com sucesso
    409 CONFLICT. Usuário já existente com o e-mail fornecido.

#### Login

Este recurso é responsável pela autenticação do usuário na aplicação.
Para esta requisição, é necessário definir as seguintes configurações:

    HTTP Method: POST
    Content-type: x-www-form-urlencoded
    URL: http://ip:9000/users/login
    Header -> email: email
    Header -> password: senha

Existem 3 possíveis respostas para esta requisição.

    200 OK. Usuário autorizado.
    401 UNAUTHORIZED. Senha informada é incorreta para um usuário cadastrado.
    500 INTERNAL_SERVER_ERROR. Credenciais do usuário não encontradas no banco de dados.


#### Get User

Este recurso é responsável pelo retorno de um usuário, obtendo um email como parâmetro.
Para esta requisição, é necessário definir as seguintes configurações:

    HTTP Method: GET
    URL: http://ip:9000/users/{email}

Existem 2 possíveis respostas para esta requisição.

    200 OK. Usuário retornado.
    404 NOT_FOUND. Email fornecido não localizado no banco de dados.
	
