# Task Manager API

## Descrição

A **Task Manager API** é uma aplicação desenvolvida em **Spring Boot** para gerenciar tarefas e usuários. A API oferece funcionalidades de autenticação, autorização e manipulação de tarefas, permitindo que usuários criem, atualizem, excluam e visualizem tarefas.

---

## Funcionalidades

- **Autenticação e Autorização**:

  - Cadastro de usuários.
  - Login com geração de token JWT.
  - Controle de acesso baseado em papéis (ADMIN, MANAGER, USER).

- **Gerenciamento de Tarefas**:

  - Criação de tarefas associadas a usuários.
  - Atualização de tarefas (título, descrição, prioridade, status, etc.).
  - Exclusão de tarefas.
  - Listagem de todas as tarefas.
  - Busca de tarefas por ID.

- **Gerenciamento de Usuários**:
  - Listagem de usuários cadastrados.

---

## Boas Práticas Adotadas

1. **Segurança**:

   - Uso de **JWT** para autenticação e autorização.
   - Criptografia de senhas com **BCrypt**.

2. **Validação**:

   - Validação de dados de entrada com anotações do `jakarta.validation`.

3. **Tratamento de Erros**:

   - Manipulação global de exceções com o `GlobalExceptionHandler`.

4. **Camadas Bem Definidas**:

   - Separação de responsabilidades em camadas: Controller, Service, Repository e DTO.

5. **Documentação**:

   - Uso do **SpringDoc OpenAPI** para gerar documentação interativa da API.

6. **Testes**:
   - Testes de integração com o `WebTestClient`.

---

## Dependências

As principais dependências utilizadas no projeto incluem:

- **Spring Boot Starter Web**: Para criação de APIs REST.
- **Spring Boot Starter Data JPA**: Para integração com o banco de dados.
- **Spring Boot Starter Security**: Para autenticação e autorização.
- **PostgreSQL Driver**: Para conexão com o banco de dados PostgreSQL.
- **H2 Database**: Banco de dados em memória para testes.
- **Lombok**: Para reduzir o boilerplate de código.
- **SpringDoc OpenAPI**: Para documentação da API.
- **JSON Web Token (JWT)**: Para autenticação baseada em tokens.

---

## Como Rodar a API

### Pré-requisitos

- **Java 21** ou superior.
- **Maven** (ou utilize o wrapper `mvnw` incluído no projeto).
- **Docker** e **Docker Compose**.

### Passos para Rodar

1. **Configurar o Banco de Dados**:

   - Certifique-se de que o Docker está instalado e em execução.
   - Na raiz do projeto, execute o comando abaixo para subir o banco de dados PostgreSQL:
     ```bash
     docker-compose up -d
     ```

2. **Rodar a Aplicação**:

   - Com o banco de dados em execução, inicie a aplicação:
     ```bash
     ./mvnw spring-boot:run
     ```
   - Alternativamente, você pode compilar e rodar o JAR:
     ```bash
     ./mvnw clean package
     java -jar target/task_manager-0.0.1-SNAPSHOT.jar
     ```

3. **Acessar a API**:

   - A API estará disponível em: `http://localhost:8080`.

4. **Documentação da API**:
   - Acesse a documentação interativa gerada pelo **SpringDoc OpenAPI** em: `http://localhost:8080/swagger-ui.html`.

---

## Endpoints Principais

### Autenticação

- `POST /api/auth/signup`: Cadastro de usuários.
- `POST /api/auth/login`: Login e geração de token JWT.

### Tarefas

- `GET /api/tasks`: Listar todas as tarefas.
- `POST /api/tasks/create`: Criar uma nova tarefa.
- `PUT /api/tasks/edit/{id}`: Atualizar uma tarefa.
- `DELETE /api/tasks/{id}`: Excluir uma tarefa.

### Usuários

- `GET /api/tasks/users`: Listar todos os usuários.

---

## Observações

- Certifique-se de configurar corretamente as credenciais do banco de dados no arquivo [application.properties](http://_vscodecontentref_/0) caso não utilize o Docker Compose.
- Para desenvolvimento, o banco de dados H2 é utilizado nos testes.
