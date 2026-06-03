# Automação de Testes - Operação Conta Corrente 🧪


_Leia este arquivo em outro idioma: [English](README.md)_

Este repositório contém a suíte de testes automatizados para o microsserviço **operacao-conta-corrente**. O projeto cobre testes funcionais de API, testes de evento, testes de estresse e validação de arquiteturas baseadas em eventos.

## 🚀 Funcionalidades Principais

*   **Automação de API:** Testes de integração robustos utilizando **RestAssured**.
*   **Testes de Estresse:** Validação de performance e resiliência sob alta carga de requisições.
*   **Testes Baseados em Eventos:** Verificação de fluxos assíncronos e mensageria.
*   **Validações Estruturadas:** Checagem avançada de status HTTP e payloads JSON.

## 🛠️ Tecnologias Utilizadas

*   **Java 17+** - Linguagem de programação base.
*   **RestAssured** - Framework para automação e validação de APIs REST.
*   **Maven** - Gerenciador de dependências e executor do ciclo de testes.
*   **Kafka** - Gerenciador de eventos.

## 📋 Como Executar os Testes

### Pré-requisitos
*   O microsserviço alvo (`checking-account-operation`) deve estar rodando localmente ou em ambiente de teste.
*   Java 17+ e Maven instalados.

### Execução

1. **Clonar o repositório:**
   ```bash
   git clone https://github.com
   cd restassured-operacao-conta-corrente-automacao
   ```

2. **Executar todos os testes:**
   ```bash
   mvn clean test
   ```

3. **Executar uma classe de teste específica (Opcional):**
   ```bash
   mvn test -Dtest=NomeDaClasseTest
   ```

---

## ✒️ Autore

*   **Heitor Seemann** - *Mantenedor do Repositório* - [HeitorSeemann](https://github.com/HeitorSeemann)
