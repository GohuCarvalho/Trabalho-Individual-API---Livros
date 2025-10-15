# 📚 API de Gestão de Livros (Trabalho Individual)

Este é um projeto de API REST em Spring Boot para gerenciar livros e suas informações de publicação.

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos
- Java 17+ (JDK)
- Maven

### Passos:
1.  **Clone o Repositório:**
    ```bash
    git clone [https://github.com/GohuCarvalho/Trabalho-Individual-API---Livros.git](https://github.com/GohuCarvalho/Trabalho-Individual-API---Livros.git)
    ```
2.  **Execute a Aplicação:**
    ```bash
    cd Trabalho-Individual-API---Livros
    ./mvnw spring-boot:run
    ```
A API será iniciada na porta `8080`.

---

## 🛠 Tecnologias Principais

* **Spring Boot** (Core e Web)
* **Spring Data JPA** (Persistência)
* **H2 Database** (Banco de dados em memória)
* **Jakarta Validation** (Validação de campos)

---

## 🔗 Endpoints da API

O recurso principal é `/livros`.

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| **GET** | `/livros` | Lista todos os livros. |
| **GET** | `/livros/{id}` | Busca livro por ID. |
| **POST** | `/livros` | Cadastra um novo livro. |
| **PUT** | `/livros/{id}` | Atualiza um livro existente. |
| **DELETE** | `/livros/{id}` | Remove um livro por ID. |

### 📥 Modelo de Dados (POST / PUT)

Os dados devem ser enviados com a seguinte estrutura.

```json
{
  "titulo": "Nome do Livro (Máx: 40)",
  "qtdPagina": 150,
  "publicacao": {
    "autor": "Nome do Autor (Máx: 25)",
    "dataPublicacao": "yyyy-MM-dd",
    "editora": "Nome da Editora"
  }
}
