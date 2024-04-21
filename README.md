# Slab-Based Billing System

This project is a slab-based billing system for generating monthly electricity bills. It provides REST APIs for registering customers, defining price slabs (tariffs), recording monthly electricity readings, and generating monthly bills based on consumption and tariffs.

## Technologies Used

- **Programming Language:** Java 11+
- **Framework:** Spring Boot
- **Database:** Postgresql (can be configured to use other databases)

## Features

- **Customer Registration:** Register new customers.
- **Price Slab Management:** Define and manage price slabs for different periods.
- **Electricity Reading:** Record monthly electricity consumption readings for customers.
- **Bill Calculation:** Generate monthly bills based on consumption and tariff rates.

## API Documentation

The API documentation is available at [API Documentation](http://localhost:8088/swagger-ui/index.html) (Replace # with actual API documentation URL).

## Getting Started

### Prerequisites

- Java 11+ installed
- Postgresql database installed and running

### Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/your-username/slab-based-billing-system.git

2.Update the application.yml file with your database configuration:

    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/billing_system
        username: your_username
        password: your_password

### Usage

- Use POSTman or any API testing tool to access the APIs.
- Refer to the API Documentation for detailed API usage instructions.

