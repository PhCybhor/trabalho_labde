# Sistema de Livraria

Trabalho desenvolvido para a disciplina de **Laboratorio de Programacao 3** (3º Semestre).

---

### Integrantes
*   **Murilo Rocha Silva**
*   **Pedro Henrique Rodrigues da Silva**
*   **Marcos Paulo Coleta Caetano**

---

### Tecnologias
*   **Java Web** (Servlets e JSP)
*   **Maven**
*   **PostgreSQL 18**

---

### Como Executar

#### 1. Banco de Dados
Certifique-se de que o **PostgreSQL** esteja rodando na porta `5432`. Verifique o usuario e a senha no arquivo:
`src/main/java/br/com/livraria/util/Conexao.java`

#### 2. Execucao via IDE
1. Abra a pasta `livraria` no **NetBeans**, **IntelliJ** ou **Eclipse**.
2. Execute o comando **Clean and Build**.
3. Inicie o servidor (Run).

#### 3. Acesso ao Sistema
Acesse o sistema pelo endereco:
**[http://localhost:8080/livraria/](http://localhost:8080/livraria/)**

- Livros: `/livros`
- Estados (Cap 5.4 - Desafio 02): `/estados`

#### 4. Tabela Estados
A tabela `estados` e criada automaticamente com:
- `nome_estado` VARCHAR(100)
- `sigla_estado` VARCHAR(2) UNIQUE

A sigla nao pode ser duplicada (validacao no frontend e no backend).