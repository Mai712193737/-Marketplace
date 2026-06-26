# Marketplace Order Orchestrator (MOO)

A backend order orchestration system developed in **Java 17** using **Object-Oriented Programming (OOP)** and **SOLID Principles**.

This project simulates the backend operations of a multi-vendor marketplace, including product management, inventory allocation, order processing, shipping, promotions, auditing, and reporting.

---

## 📖 Project Overview

Marketplace Order Orchestrator (MOO) is designed to manage the complete lifecycle of marketplace orders while maintaining clean architecture and high code quality.

The system focuses on:

- Multi-vendor product catalog
- Inventory management
- Order lifecycle management
- Stock allocation
- Promotion engine
- Shipment routing
- Audit logging
- Analytics reports

---

## 🛠 Technologies

- Java 17+
- MySQL 8.x
- JDBC
- Maven / Gradle
- JSON Configuration
- Git & GitHub

---

## 🏛 Architecture

The project follows:

- Object-Oriented Programming (OOP)
- SOLID Principles
- Layered Architecture

```
CLI
 ↓
Service
 ↓
Repository
 ↓
Model
 ↓
MySQL
```

---

## 📂 Planned Package Structure

```
src
│
├── model
├── repository
├── repository/mysql
├── service
├── promotion
├── report
├── config
├── cli
├── db
└── Main
```

---

## ✨ Features

### Product Management

- Add products
- Update inventory
- Activate / Deactivate products

### Order Management

- Create orders
- Allocate stock
- Cancel orders
- Refund shipped orders

### Shipping

- Round Robin carrier assignment
- Tracking number generation
- JSON configuration

### Promotions

- Percentage Discount
- Fixed Threshold Discount
- Bundle Promotion

### Reporting

- Order Summary
- Inventory Status
- Revenue by Seller
- Shipment Tracking
- Audit Trail
- Low Stock Report

### Audit

Every important system action is recorded inside the Audit Log.

---

## 📚 Project Requirements

- Java 17+
- MySQL 8
- Native JDBC
- No ORM
- No Spring Boot
- ArrayList collections
- StringBuilder for reports

---

## 📄 Documentation

Software Requirements Specification (SRS)

The complete Software Requirements Specification can be found inside the repository.

---

## 🚧 Project Status

> 🟡 In Development

This repository is currently under active development.

New modules and features will be added incrementally according to the SRS document.

---

## 👥 Team

MAI , DINA , SHAHER , ZAINAB , SHEHAB ... HELWAN FCAIH 

---

## 📌 Repository Structure

```
.
├── src/
├── docs/
│   └── SRS.pdf
├── database/
├── config/
├── README.md
└── .gitignore
```

---

## 🎯 Goal

Develop a maintainable backend system that demonstrates:

- Clean Code
- SOLID Principles
- Java OOP
- JDBC
- Database Design
- Software Engineering Best Practices
