# Checking Account Automation Tests 🧪

_Leia isso em outros idiomas: [English](README.md)_

Este repositório contém a suíte de testes automatizados para o microsserviço **checking-account-operation**, URL: https://github.com
Ele cobre testes funcionais de API, testes com Kafka, testes de estresse e validação de arquitetura orientada a eventos.

## 🚀 Principais Funcionalidades

*   **Automação de API:** Testes de integração abrangentes utilizando **RestAssured**.
*   **Testes de Estresse:** Validação de performance e resiliência sob alta carga.
*   **Testes Orientados a Eventos:** Validação de padrões de mensagens assíncronas e eventos.
*   **Asserções Estruturadas:** Verificação avançada de payloads JSON e códigos de status HTTP.
*   **Integração CI/CD:** Pipeline de testes end-to-end automatizada via GitHub Actions.

## 🛠️ Tecnologias Utilizadas

*   **Java 11** - Linguagem de programação principal.
*   **RestAssured** - Biblioteca para testes e validação de APIs REST.
*   **Maven** - Ferramenta de gerenciamento de dependências e build.
*   **Kafka** - Gerenciamento de eventos.
*   **Docker & Docker Compose** - Containerização para dependências de infraestrutura.
*   **GitHub Actions** - Plataforma de Integração Contínua (CI).

## ⚙️ Integração Contínua (CI)

Este projeto inclui um workflow completo do **GitHub Actions** (`ci.yml`) que é executado a cada push e pull request nos branches `main` ou `master`.

Sempre que o código é atualizado, a pipeline executa automaticamente os seguintes passos:
1. Faz o clone deste repositório de testes.
2. Faz o clone do repositório do microsserviço principal (`checking-account-operation`).
3. Configura o ambiente com Java 11.
4. Compila e empacota o microsserviço principal utilizando o Gradle.
5. Sobe os containers do **Kafka**, **Zookeeper** e a **API Spring Boot** via Docker Compose.
6. Aguarda até que todos os serviços estejam saudáveis e disponíveis.
7. Executa toda a suíte de testes Cucumber/RestAssured utilizando o Maven.

---

## 📋 Como Executar os Testes

### Pré-requisitos
*   O microsserviço principal (`checking-account-operation`) deve estar rodando localmente ou em um ambiente de testes.
*   Java 11 e Maven instalados.

### Execução

1. **Clone este repositório:**
   ```bash
   git clone https://github.com-test.git
   cd checking-account-operation-test
   ```

2. **Execute todos os testes automatizados:**
   ```bash
   mvn clean test
   ```

3. **Execute classes de teste específicas (Opcional):**
   ```bash
   mvn test -Dtest=NomeDaClasseTest
   ```

---

## ✒️ Autor

*   **Heitor Seemann** - *Criador & Mantenedor* - [HeitorSeemann](https://github.com)