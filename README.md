# CryptoCurrency Wallet Simulator API

The simulator allows CRUD operations of the Wallets, and allows for the transfer operations in, and between wallets.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Java 11

Maven

Lombok


### Endpoints

Create a wallet: POST /api/wallets
Example body:

```
{
  "balance": {
    "string": double,
    "string": double
  },
  "walletName": "string"
}
```

Get a wallet by ID: GET /api/wallets/{id}

Get all wallets: GET /api/wallets

Update a wallet: PUT /api/wallets/{id}

Example body:
```
{
  "balance": {
    "string": double,
    "string": double
  },
  "walletName": "string"
}

```
Buy currencies: POST /api/wallets/buy

Example body:
```
{
  "exchangeFrom": "string",
  "exchangeTo": "string",
  "quantity": Double,
  "walletID": Long
}

```

Transfer money from one wallet to another: POST /api/wallets/transfer

Example body:
```
{
  "coin": "string",
  "fromWalletID": Long,
  "quantity": Double,
  "toWalletID": Long
}

```

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management