# CryptoCurrency Wallet Simulator API

The simulator allows CRUD operations of the Wallets, and allows for the transfer operations in, and between wallets.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. It runs on port 8080.

### Prerequisites

Java 11

Maven

Lombok

### Swagger

Swagger is integrated into the project for ease of access of endpoints:

[Endpoints in swagger](http://localhost:8080/swagger-ui.html)

## Postman collection

A postman collection is provided with available endpoints. 
File:
```
CryptoWallet.postman_collection.json
```

### Endpoints
* Get all cryptocoins with default price in USD:

  GET /api/cryptocoins

* Get all cryptocoins with query to get price in different currency (E.g. Euros):

  GET /api/cryptocoins?convert=EUR

* Create a wallet: 

  POST /api/wallets

Example body:

```
{
  "balance": {
    "String": Double,
    "String": Double
  },
  "name": "String"
}
```

* Get a wallet by ID: 

  GET /api/wallets/{id}

* Get all wallets: 

  GET /api/wallets

* Update a wallet: 

  PUT /api/wallets/{id}

Example body:
```
{
  "balance": {
    "string": Double,
    "string": Double
  },
  "name": "String"
}

```
* Buy currencies: 

  POST /api/wallets/buy

Example body:
```
{
  "exchangeFrom": "String",
  "exchangeTo": "String",
  "quantity": Double,
  "walletID": Long
}

```

* Transfer money from one wallet to another: 

  POST /api/wallets/transfer

Example body:
```
{
  "coin": "String",
  "fromWalletID": Long,
  "quantity": Double,
  "toWalletID": Long
}

```

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management