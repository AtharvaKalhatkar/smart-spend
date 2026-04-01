# SmartSpend: AI-Powered Finance & Access Control Backend

## 📌 Project Overview
SmartSpend is an intelligent financial data processing backend designed to demonstrate modern backend architecture, strict role-based access control, and the integration of Artificial Intelligence in fintech. 

Unlike standard CRUD applications, SmartSpend leverages **Spring AI** and Large Language Models (LLMs) to automatically process and categorize natural language transaction descriptions. The system enforces strict security boundaries, ensuring user data isolation and role-specific dashboard analytics.

## 🚀 Technology Stack
* **Language:** Java 17
* **Framework:** Spring Boot 3.2.4
* **AI Integration:** Spring AI (0.8.1) integrating with Groq (Llama-3.3-70b-versatile)
* **Security:** Spring Security & BCrypt
* **Database:** PostgreSQL (Spring Data JPA)
* **Validation:** Jakarta Bean Validation

---

## 🎯 Alignment with Assignment Requirements

### 1. Artificial Intelligence & Data Processing (Bonus/Highlight)
To demonstrate modern fintech capabilities, the system processes financial entries intelligently. When a user submits a natural language description (e.g., *"Uber ride to the airport"*), the `ExpenseAiService` uses carefully engineered LLM prompts to automatically parse, classify, and tag the transaction into strict financial categories (e.g., `TRAVEL`).

### 2. User and Role Management
The system utilizes a relational `User` model to handle authentication and identity. Users are assigned distinct roles (e.g., `ROLE_USER`, `ROLE_ADMIN`) at creation, forming the foundation of the system's access control matrix.

### 3. Access Control Logic
Robust Role-Based Access Control (RBAC) is implemented via **Spring Security**:
* **Standard Users (`ROLE_USER`):** Can strictly only `POST` and `GET` their own isolated financial records. They cannot access system-wide data.
* **Administrators (`ROLE_ADMIN`):** Have elevated privileges to view cross-system data and aggregated global dashboard metrics.
* *Implementation:* Route-level authorization using `SecurityFilterChain` and method-level security ensuring horizontal data isolation.

### 4. Financial Records Management
The core domain revolves around the `Expense` entity, mapped relationally to specific users. 
* **Operations:** Supports Creating, Viewing, and flexible Filtering (by date range and category).
* **Immutability Principle:** Financial records are designed to be immutable ledgers. While deletion is supported for user error correction, in-place updates are restricted by design to mimic real-world financial audit trails.

### 5. Dashboard Summary APIs
The backend exposes highly flexible aggregation endpoints (`/api/expenses/stats` and `/api/expenses/filter`). These endpoints perform database-level date bounding and Java Stream API reductions to serve real-time dashboard metrics:
* Total Expenditure
* Categorical spending volume
* Top spending categories
* Dynamic date-range bounding (Weekly/Monthly)

### 6. Validation and Error Handling
The API is hardened against malformed data using Jakarta Validation. Endpoints enforce strict constraints (e.g., `@Positive` amounts, `@NotNull` references) and utilize global exception handling to return clean, standardized HTTP status codes and JSON error messages to the client.

---

## 🏗️ Architecture 
The application strictly adheres to a layered architectural pattern for separation of concerns:
* **Controllers:** Handle HTTP routing, payload validation, and HTTP response formatting.
* **Services:** Isolate core business logic, including the `ExpenseAiService` which abstracts the complexities of LLM API communication and prompt engineering.
* **Repositories:** Interface with PostgreSQL using Spring Data JPA for secure, parameterized SQL execution.
* **Security Config:** Centralized authentication and authorization routing.

---

## 🛠️ Setup and Installation

### Prerequisites
* Java 17+
* Maven
* PostgreSQL running locally or via Docker
* Groq API Key (for the AI categorization engine)

### 1. Environment Configuration
Configure your database credentials and API keys. You can set these as system environment variables or directly inside `src/main/resources/application.properties`:

```properties
# Database Configuration
#spring.datasource.url=jdbc:postgresql://localhost:5432/smartspend_db
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

# AI Configuration
spring.ai.openai.api-key=your_groq_api_key_here
