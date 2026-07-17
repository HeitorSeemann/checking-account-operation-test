# Automação de Testes - Operação Conta Corrente 🧪

_Leia este arquivo em outro idioma: [English](README.md)_

Este repositório contém a suíte de testes automatizados para o microsserviço **checking-account-operation**, URL: https://github.com

O projeto cobre testes funcionais de API, testes de estresse e validação de arquiteturas baseadas em eventos.

## 🚀 Funcionalidades Principais

* **Automação de API:** Testes de integração robustos e abrangentes utilizando **RestAssured**.
* **Testes de Estresse:** Validação de performance e resiliência sob alta carga usando o **K6 Engine**.
* **Validações Estruturadas:** Checagem avançada de payloads JSON, regras de otimização de cédulas e códigos de status HTTP.
* **Integração CI/CD:** Pipeline de execução fim a fim totalmente automatizado via GitHub Actions.
* **Hub Unificado de Relatórios:** Painéis HTML interativos para métricas funcionais e resumos de telemetria publicados automaticamente na web.

## 🛠️ Tecnologias Utilizadas

* **Java 25** - Linguagem de programação base para a suíte de testes funcionais.
* **JavaScript** - Linguagem utilizada para os scripts de simulação e carga de performance.
* **RestAssured** - Framework para automação e validação de APIs REST.
* **K6 Engine** - Ferramenta moderna e de código aberto para testes de carga nativos em nuvem.
* **Maven** - Gerenciador de dependências e executor do ciclo de testes funcionais.
* **Kafka & Zookeeper** - Infraestrutura de mensageria assíncrona orientada a eventos.
* **Docker** - Conteinerização para orquestração isolada das dependências de infraestrutura.
* **GitHub Actions** - Plataforma de Integração Contínua (CI).
* **Allure Report** - Framework para geração de relatórios ricos e colaborativos.

## ⚙️ Integração Contínua (CI)

Este projeto inclui um workflow do **GitHub Actions** totalmente automatizado (`ci.yml`) que é disparado a cada push e pull request para as branches `main` ou `master`. Sempre que o código é atualizado, o pipeline executa as seguintes etapas:

1. Clona este repositório de testes.
2. Clona o repositório do microsserviço alvo (`checking-account-operation`).
3. Prepara o ambiente com o Java 25.
4. Compila o processo do microsserviço alvo.
5. Sobe o **Kafka**, **Zookeeper** e a **API Spring Boot**.
6. Aguarda até que todos os serviços fiquem saudáveis e disponíveis.
7. Executa a suíte de testes funcionais do RestAssured via Maven.
8. Configura o Grafana **K6 Engine** para rodar os cenários de testes de performance em JavaScript.
9. Consolida as métricas de ambas as execuções em um espaço de trabalho isolado.
10. Gera um artefato ZIP independente e publica o **Hub de Automação** web diretamente no **GitHub Pages**.

---

## 📋 Como Executar os Testes

### Pré-requisitos

* O microsserviço alvo (`checking-account-operation`) deve estar rodando localmente ou em ambiente de teste.
* Java 25 e Maven instalados.
* K6 CLI instalado localmente (para os scripts de performance).

### Execução

1. **Clonar o repositório:**
```bash
git clone https://github.com-test.git
cd checking-account-operation-test
```

2. **Executar todos os testes funcionais automatizados:**
```bash
mvn clean test
```

3. **Executar uma classe de teste funcional específica (Opcional):**
```bash
mvn test -Dtest=NomeDaClasseTest
```

4. **Executar os testes de estresse do K6 localmente:**
```bash
cd performance-tests
k6 run nome_do_seu_script.js
```

5. **Gerar e abrir o Allure Report localmente:**
```bash
mvn allure:serve
```

---

## 📊 Visualizando os Relatórios (Hub de Dashboards no CI/CD)

Assim que a execução do pipeline automatizado terminar com sucesso, os relatórios hospedados de forma independente estarão disponíveis no seu ambiente de implantação em nuvem:

* **Link do Hub de Automação Unificado (Índice Raiz):** `https://github.io`
* **Relatórios de Testes Funcionais (Dashboard Allure):** `https://github.iofunctional/index.html`
* **Relatórios de Estresse de Performance (Log de Resumo K6):** `https://github.ioperformance/index.html`

---

## ✒️ Autor

* **Heitor Seemann** - *Mantenedor do Repositório* - [HeitorSeemann](https://github.com/HeitorSeemann)
