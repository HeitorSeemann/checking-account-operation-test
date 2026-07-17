# Checking Account Automation Tests 🧪

_Read this in other languages: [Português](README.pt-br.md)_

This repository contains the automated test suite for the **checking-account-operation** microservice, URL: https://github.com/HeitorSeemann/checking-account-operation

It covers functional API testing, stress testing, and event-driven architecture validation.

## 🚀 Key Features

* **API Automation:** Comprehensive integration tests using **RestAssured**.
* **Stress Testing:** High-load performance and resilience validation using the **K6 Engine**.
* **Structured Assertions:** Advanced JSON payload, banknote optimization, and status code verification.
* **CI/CD Integration:** Automated end-to-end testing pipeline via GitHub Actions.
* **Unified Reporting Hub:** Interactive HTML dashboards for functional metrics and telemetry summaries published automatically to the web.

## 🛠️ Technologies Used

* **Java 25** - Core programming language for functional testing.
* **JavaScript** - Language used for performance testing simulation scripts.
* **RestAssured** - Library for REST API testing and validation.
* **K6 Engine** - Modern open-source tool for cloud-native performance testing.
* **Maven** - Dependency management and functional test execution build tool.
* **Kafka & Zookeeper** - Event-driven messaging backbone for asynchronous operations.
* **Docker** - Containerization for isolated service dependency orchestration.
* **GitHub Actions** - Continuous Integration platform.
* **Allure Report** - Framework for collaborative test reporting.

## ⚙️ Continuous Integration (CId)

This project includes a fully automated **GitHub Actions** workflow (`ci.yml`) that runs on every push and pull request to the `main` or `master` branches. Whenever code is updated, the pipeline automatically:

1. Clones this test repository.
2. Clones the target microservice repository (`checking-account-operation`).
3. Sets up the Java 25 environment.
4. Compiles the target microservice process.
5. Spins up **Kafka**, **Zookeeper**, and the **Spring Boot API**.
6. Waits for all background systems to become healthy and available.
7. Executes the RestAssured functional test suite using Maven.
8. Sets up the Grafana **K6 Engine** to execute the JavaScript performance test scenarios.
9. Consolidates both execution metrics into an isolated, independent workspace dashboard.
10. Generates a standalone ZIP artifact and publishes a web-based **Automation Hub** straight to **GitHub Pages**.

---

## 📋 How to Run the Tests

### Prerequisites

* The target microservice (`checking-account-operation`) must be running locally or in a test environment.
* Java 25 and Maven installed.
* K6 CLI binary installed locally (for performance scripts).

### Execution

1. **Clone this repository:**
```bash
git clone https://github.com
cd checking-account-operation-test
```

2. **Run all automated functional tests:**
```bash
mvn clean test
```

3. **Run specific functional test classes (Optional):**
```bash
mvn test -Dtest=ClassNameTest
```

4. **Run K6 performance stress tests locally:**
```bash
cd performance-tests
k6 run name_of_your_script.js
```

5. **Generate and open Allure Report locally:**
```bash
mvn allure:serve
```

---

## 📊 Viewing the Test Reports (CI/CD Dashboard Hub)

Once the automated pipeline execution completes successfully, the unbundled reports are hosted independently on your cloud deployment workspace:

* **Unified Automation Hub Link (Root Index):** `https://github.io`
* **Functional Test Reports (Allure Dashboard):** `https://github.iofunctional/index.html`
* **Performance Stress Reports (K6 Summary Log):** `https://github.ioperformance/index.html`

---

## ✒️ Author

* **Heitor Seemann** - *Maintainer & Host* - [HeitorSeemann](https://github.com/HeitorSeemann)
