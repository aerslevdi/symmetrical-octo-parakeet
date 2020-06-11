# CryptoCurrency Wallet Simulator API

The simulator allows CRUD operations of the Wallets, and allows for the transfer operations in, and between wallets.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Java 11

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
Get a wallet by ID: GET /api/wallets/{id}
Update a wallet: PATCH /api/wallets/{id}
Example body:
```
[
    {
    "op":"replace",
    "path":"/walletName",
    "value":"new wallet name"
    }
]

```
Buy currencies: POST /api/wallets/{id}

Example body:
```
{
  "exchange": "string",
  "name": "string",
  "quantity": 0.0
}

```


## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management