# Slab-Based Billing System

This project is a slab-based billing system for generating monthly electricity bills. It provides REST APIs for registering customers, defining price slabs (tariffs), recording monthly electricity readings, and generating monthly bills based on consumption and tariffs.

## Technologies Used

- **Programming Language:** Java 21
- **Framework:** Spring Boot 3.2.4
- **Database:** Postgresql (can be configured to use other databases)

## Features

- **Customer Registration:** Register new customers.
- **Price Slab Management:** Define and manage price slabs for different periods.
- **Electricity Reading:** Record monthly electricity consumption readings for customers.
- **Bill Calculation:** Generate monthly bills based on consumption and tariff rates.

## API Documentation
The API documentation is done it in the swagger ui, you need to run your application in local to get the API documentation details.
The API documentation is available at [API Documentation](http://localhost:8088/swagger-ui/index.html).

## Getting Started

### Prerequisites

- Java 21 installed
- Postgresql database installed and running

### Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/sojess-work/electric-bill.git

2.Update the application.yml file with your database configuration:

    spring:
      datasource:
        url: jdbc:postgresql://localhost:5432/postgres
        username: your_username
        password: your_password

### Usage

- Use POSTman or any API testing tool to access the APIs.
- Refer to the API Documentation for detailed API usage instructions.

