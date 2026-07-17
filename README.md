# Checking Account Automation Tests 🧪

_Read this in other languages: [Português](README.pt-br.md)_

This repository contains the automated test suite for the **checking-account-operation** microservice, URL: https://github.com/HeitorSeemann/checking-account-operation

It covers functional API testing, kafka testing, stress testing, and event-driven architecture validation.

## 🚀 Key Features

* **API Automation:** Comprehensive integration tests using **RestAssured**.
* **Stress Testing:** Performance and resilience validation under high load.
* **Event-Driven Testing:** Validation of asynchronous message patterns and events.
* **Structured Assertions:** Advanced JSON payload and status code verification.
* **CI/CD Integration:** Automated end-to-end testing pipeline via GitHub Actions.
* **Rich Reporting:** Interactive HTML dashboards published automatically on the web via **Allure Report**.

## 🛠️ Technologies Used

* **Java 25** - Core programming language.
* **RestAssured** - Library for REST API testing and validation.
* **Maven** - Dependency management and test execution build tool.
* **Kafka** - Event management.
* **Docker & Docker Compose** - Containerization for infrastructure dependency.
* **GitHub Actions** - Continuous Integration platform.
* **Allure Report** - Framework for rich and collaborative test reporting.

## ⚙️ Continuous Integration (CI)

This project includes a fully automated **GitHub Actions** workflow (`ci.yml`) that runs on every push and pull request to the `main` or `master` branches. Whenever code is updated, the pipeline automatically:

1. Clones this test repository.
2. Clones the target microservice repository (`checking-account-operation`).
3. Sets up the Java 25 environment.
4. Compiles the target microservice using Gradle.
5. Spins up **Kafka**, **Zookeeper**, and the **Spring Boot API** using Docker Compose.
6. Waits for all services to become healthy and available.
7. Executes the entire test suite using Maven.
8. Consolidates execution metrics and attaches the history graphs.
9. Publishes a web-based **Allure Report** dashboard directly to **GitHub Pages**.

---

## 📋 How to Run the Tests

### Prerequisites

* The target microservice (`checking-account-operation`) must be running locally or in a test environment.
* Java 25 and Maven installed.

### Execution

1. **Clone this repository:**
```bash
git clone https://github.com
cd checking-account-operation-test
```

2. **Run all automated tests:**
```bash
mvn clean test
```

3. **Run specific test classes (Optional):**
```bash
mvn test -Dtest=ClassNameTest
```

4. **Generate and open Allure Report locally:**
```bash
mvn allure:serve
```

---

## ✒️ Author

* **Heitor Seemann** - *Maintainer & Host* - [HeitorSeemann](https://github.com/HeitorSeemann)
