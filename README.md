# 🧩 T4 — Microsserviços com Spring Boot e RestTemplate

Projeto acadêmico com dois microsserviços que se comunicam via **RestTemplate** (comunicação síncrona HTTP).

---

## 📐 Arquitetura

```
Postman / Cliente HTTP
        │
        ▼
┌───────────────────┐        RestTemplate (HTTP)       ┌──────────────────────┐
│   user-service    │  ──────────────────────────────▶ │ department-service   │
│   porta: 8081     │                                   │ porta: 8080          │
│   banco: user_db  │                                   │ banco: department_db │
└───────────────────┘                                   └──────────────────────┘
```

- **department-service** → gerencia departamentos
- **user-service** → gerencia usuários e busca o departamento de cada usuário chamando o `department-service`

---

## ✅ Pré-requisitos

Antes de rodar o projeto, instale as seguintes ferramentas:

| Ferramenta | Versão recomendada | Download |
|---|---|---|
| JDK | 17 ou superior | https://www.oracle.com/java/technologies/downloads/ |
| Maven | 3.8+ (ou use o `mvnw` incluso) | https://maven.apache.org/download.cgi |
| MySQL | 8.0+ | https://dev.mysql.com/downloads/installer/ |
| VS Code | Qualquer versão recente | https://code.visualstudio.com/ |
| Postman | Qualquer versão recente | https://www.postman.com/downloads/ |

### Extensões do VS Code necessárias

- **Extension Pack for Java** (Microsoft)
- **Spring Boot Extension Pack** (VMware)

---

## 🗄️ Configuração do Banco de Dados

Abra o MySQL e execute os dois comandos abaixo para criar os bancos:

```sql
CREATE DATABASE department_db;
CREATE DATABASE user_db;
```

> O próprio Spring Boot cria as tabelas automaticamente ao subir (`ddl-auto=update`).

---

## ⚙️ Configuração dos arquivos `application.properties`

### department-service
Arquivo em: `department-service/src/main/resources/application.properties`

```properties
server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/department_db
spring.datasource.username=root
spring.datasource.password=SUA_SENHA_AQUI

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### user-service
Arquivo em: `user-service/src/main/resources/application.properties`

```properties
server.port=8081

spring.datasource.url=jdbc:mysql://localhost:3306/user_db
spring.datasource.username=root
spring.datasource.password=SUA_SENHA_AQUI

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> ⚠️ Substitua `SUA_SENHA_AQUI` pela senha do seu MySQL local.

---

## ▶️ Como rodar o projeto

Abra **dois terminais separados** no VS Code e execute:

**Terminal 1 — department-service:**
```bash
cd department-service
./mvnw spring-boot:run
```

**Terminal 2 — user-service:**
```bash
cd user-service
./mvnw spring-boot:run
```

> Os dois serviços precisam estar rodando ao mesmo tempo. O `user-service` depende do `department-service` estar ativo para funcionar corretamente.

---

## 🧪 Testando com Postman

### 1️⃣ Criar um Departamento

```
POST http://localhost:8080/api/departments
Content-Type: application/json
```

```json
{
  "departmentName": "Tecnologia da Informação",
  "departmentDescription": "Setor de TI",
  "departmentCode": "TI-001"
}
```

**Resposta esperada:** `201 Created`

---

### 2️⃣ Criar um Usuário

```
POST http://localhost:8081/api/users
Content-Type: application/json
```

```json
{
  "firstName": "João",
  "lastName": "Silva",
  "email": "joao@email.com",
  "departmentId": 1
}
```

**Resposta esperada:** `201 Created`

---

### 3️⃣ Buscar Usuário com Departamento (comunicação entre serviços)

```
GET http://localhost:8081/api/users/1
```

**Resposta esperada:**

```json
{
  "user": {
    "id": 1,
    "firstName": "João",
    "lastName": "Silva",
    "email": "joao@email.com"
  },
  "department": {
    "id": 1,
    "departmentName": "Tecnologia da Informação",
    "departmentDescription": "Setor de TI",
    "departmentCode": "TI-001"
  }
}
```

> Este endpoint é o ponto central do projeto: o `user-service` chama o `department-service` via `RestTemplate.getForEntity()` para buscar os dados do departamento em tempo real.

---

## 📁 Estrutura do Projeto

```
T4MicroServicos/
│
├── department-service/
│   └── src/main/java/net/javaguides/department_service/
│       ├── DepartmentServiceApplication.java
│       ├── controller/DepartmentController.java
│       ├── entity/Department.java
│       ├── repository/DepartmentRepository.java
│       └── service/DepartmentService.java
│
└── user-service/
    └── src/main/java/net/javaguides/user_service/
        ├── UserServiceApplication.java
        ├── AppConfig.java
        ├── controller/UserController.java
        ├── dto/DepartmentDto.java
        ├── dto/UserDto.java
        ├── dto/ResponseDto.java
        ├── entity/User.java
        ├── repository/UserRepository.java
        └── service/UserService.java
```

---

## 📚 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Spring Web (RestTemplate)
- MySQL
- Maven

---

## 👨‍💻 Autor

Desenvolvido por **Lucas** — Trabalho T4 de Microsserviços com Spring Boot Avançado.
