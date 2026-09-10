# 🚗 Turbo.az Monolithic Backend Clone

A production-ready, feature-complete monolithic backend API cloning the core functionality of the **Turbo.az** marketplace. Built with **Spring Boot 3**, **Spring AI**, and a modern data infrastructure including **Milvus Vector DB**, **Google Gemini**, **PostgreSQL 17**, **Redis**, and **MinIO**.

---

## 🌟 Core Features

* **🔐 Authentication & Security**: JWT-based authentication with access/refresh tokens, Redis token invalidation, and role-based authorization.
* **🚗 Car & Catalog Management**: CRUD management for vehicle listings, category schemas, and specification filtering.
* **🤖 AI & Semantic Search Engine**:
    * Integrated **Google Gemini AI** (`gemini-3.6-flash`) for listing generation and smart car data processing.
    * Vector embeddings via `gemini-embedding-001` stored in **Milvus Vector Database** for high-performance semantic search.
* **💾 Storage Management**: Dedicated file/image storage service backed by **MinIO Object Storage**.
* **📧 Email & Notifications**: Asynchronous email delivery system configured with SSL/TLS (Gmail SMTP).
* **🔄 Database Migrations**: Automated database versioning and change-sets using **Liquibase**.
* **⚡ Caching & Events**: Redis caching with enabled keyspace event notifications for session and state management.

---

## 🛠️ Tech Stack

* **Core**: Java 17+, Spring Boot 3.x, Spring AI
* **Database**: PostgreSQL 17
* **Vector Store**: Milvus Standalone v2.3.21 (with Attu Web UI)
* **Caching & Keyspace**: Redis 7 (Alpine)
* **Object Storage**: MinIO
* **Coordination**: etcd v3.5.5
* **Migration**: Liquibase
* **Security**: Spring Security, JWT (JSON Web Tokens)
* **Mail Service**: JavaMailSender (SMTP / SSL)
* **Containerization**: Docker, Docker Compose

---

## 📁 Package Architecture

The project follows a clean, module-based domain architecture under `az.ingress.turbo.az_clone`:

```text
az.ingress.turbo.az_clone
├── common          # Shared utilities, exceptions, and base DTOs
├── config          # Application-level configuration classes
└── module          # Domain modules
    ├── ai          # Google Gemini AI services & prompt handling
    ├── auth        # Registration, login, JWT issuance & security filters
    ├── car         # Vehicle inventory, listings, and specifications
    ├── notification# Email service & notification logic
    ├── search      # Milvus vector search integration
    ├── storage     # MinIO file management & upload integration
    └── user        # User profile management and role configurations