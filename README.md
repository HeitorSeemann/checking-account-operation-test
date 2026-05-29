# Checking Account Automation Tests 🧪

_Read this in other languages: [Português](README.pt-br.md)_

This repository contains the automated test suite for the **operacao-conta-corrente** microservice. It covers functional API testing, stress testing, and event-driven architecture validation.

## 🚀 Key Features

*   **API Automation:** Comprehensive integration tests using **RestAssured**.
*   **Stress Testing:** Performance and resilience validation under high load.
*   **Event-Driven Testing:** Validation of asynchronous message patterns and events.
*   **Structured Assertions:** Advanced JSON payload and status code verification.

## 🛠️ Technologies Used

*   **Java 17+** - Core programming language.
*   **RestAssured** - Library for REST API testing and validation.
*   **Maven** - Dependency management and test execution build tool.

## 📋 How to Run the Tests

### Prerequisites
*   The target microservice (`operacao-conta-corrente`) must be running locally or in a test environment.
*   Java 17+ and Maven installed.

### Execution

1. **Clone this repository:**
   ```bash
   git clone https://github.com
   cd restassured-operacao-conta-corrente-automacao
   ```

2. **Run all automated tests:**
   ```bash
   mvn clean test
   ```

3. **Run specific test classes (Optional):**
   ```bash
   mvn test -Dtest=ClassNameTest
   ```

---

## ✒️ Author

*   **Heitor Seemann** - *Maintainer & Host* - [HeitorSeemann](https://github.com/HeitorSeemann)
