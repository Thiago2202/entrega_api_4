# API de Alunos — Semana 04 (PUT e DELETE)

API REST com Spring Boot 3, JPA, H2 (em memória), DTO e Lombok.

## Requisitos
- Java 17 ou superior
- Maven 3.6+

## Como executar
```bash
cd api-alunos
mvn spring-boot:run
```
A API sobe em `http://localhost:8080`.

## Como executar os testes
```bash
mvn test
```

## Rotas
| Verbo  | Rota            | Corpo          | Resposta                                   |
|--------|-----------------|----------------|--------------------------------------------|
| POST   | `/alunos`       | `AlunoRequest` | 201 Created + `AlunoResponse`              |
| GET    | `/alunos`       | —              | 200 OK + lista                             |
| GET    | `/alunos/{id}`  | —              | 200 OK (404 se não existir)                |
| PUT    | `/alunos/{id}`  | `AlunoRequest` | 204 No Content (404 se não existir)        |
| DELETE | `/alunos/{id}`  | —              | 204 No Content (404 se não existir)        |

Corpo do `AlunoRequest`:
```json
{ "nome": "Maria", "email": "maria@escola.com", "idade": 20 }
```

## Exemplos
```bash
curl -X POST   localhost:8080/alunos   -H 'Content-Type: application/json' -d '{"nome":"Maria","email":"maria@escola.com","idade":20}'
curl -X PUT    localhost:8080/alunos/1 -H 'Content-Type: application/json' -d '{"nome":"Maria Silva","email":"maria@escola.com","idade":21}'
curl -X DELETE localhost:8080/alunos/1
```
# entrega_api_4
