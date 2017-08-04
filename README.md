![](http://imgur.com/t3teAxi.png)
### :handbag: A simple RESTful API for Purchases and Products

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) [![Github All Releases](https://img.shields.io/github/downloads/sant0ro/eCommerce/total.svg)]() [![GitHub release](https://img.shields.io/github/release/sant0ro/eCommerce.svg)]() [![Build Status](https://travis-ci.com/sant0ro/eCommerce.svg?token=vG4zszqWCsJMsJhycUru&branch=master)](https://travis-ci.com/sant0ro/eCommerce) [![Codecov](https://img.shields.io/codecov/c/github/sant0ro/eCommerce.svg)]()

## Features

<b>Products Features</b>

| Feature  |  Coded?       | Description  |
|----------|:-------------:|:-------------|
| Add a Product | &#10008; | Ability of Add a Product on the System |
| List Products | &#10008; | Ability of List Products |
| Edit a Product | &#10008; | Ability of Edit a Product |
| Delete a Product | &#10008; | Ability of Delete a Product |
| Stock | &#10008; | Ability of Update the Stock |
| Stock History | &#10008; | Ability to see the Stock History |

<b>Purchase Features</b>

| Feature  |  Coded?       | Description  |
|----------|:-------------:|:-------------|
| Create a Cart | &#10008; | Ability of Create a new Cart |
| See Cart | &#10008; | Ability to see the Cart and it items |
| Remove a Cart | &#10008; | Ability of Remove a Cart |
| Add Item | &#10008; | Ability of add a new Item on the Cart |
| Remove a Item | &#10008; | Ability of Remove a Item from the Cart |
| Checkout | &#10008; | Ability to Checkout |

## Documentation

**eCommerce** has a full API documentation made with [Swagger](https://swagger.io), you can check it by accessing [this](http://santoro.pw/eCommerce) link.

If you have any **Issue** or bug you can submit a new Issue by accessing [this](issues/) link.

If you want to **Contribute** you can submit a Pull Request, remember to READ the [Contributing Guide](CONTRIBUTING.md)

## Installation

**eCommerce** it's splitted into two standalone RESTful API's, so you can run it on whatever port you want. Installing **eCommerce** it's easy, the tutorial above will explain to you.

You can run **eCommerce** in different ways. You can go to the [Releases Page](releases/) and download the source code of the latest release, or a bundled .war or a standalone java application (.jar).

**It's recommend see the notes on [this](#notes) section.**

### Development

You can attach the .war in WebServers like **Tomcat** using the Management Interface.

If you want run the standalone `.jar` just download it, and open your CMD/Terminal and write:

**If you want RUN the Products API**

`java -jar ecommerce-products-release-XXX.jar`

**If you want RUN the Purchases API**

`java -jar ecommerce-purchase-release-XXX.jar`

You also can build from the sources by running the **Grails Console**, just went to one of the API's folder `purchase-api` or `products-api` and write on your CMD/Terminal the following:

`grailsw assemble`

If you want to run it in development scenario, you can also do it by **building** the sources. You have two manner to do it. You can Gradle or directly Grails. Both `products-api` and `purchase-api` comes with Groovy, Grails and Gradle standalone packages. So you can run it without the need of installing they.

**Option #1 - Run by Gradle**
`gradlew bootRun`

**Option #2 - Run bt Grailsw**
`grailsw run-app`

### Production

Production Environments are focused in being ready. That means, you just need execute the Jar File.

In the Production Environment all eCommerce API's are configured to work with **MySQL** in a Database called **productsAPI** and to work with a default **username and password** combination:

* **Username:** commerce
* **Password:** commerce-api
* **Database:** productsAPI & purchaseAPI
* **Port:** 3306

You can change those credentials in the `application.yaml` file. In production environments **you need import the database schema** before running the software. Both `products-api` and `purchase-api` DDL files are available on [this](sql/) folder.

### Notes

**Note.:** By default `products-api` runs on port 8090 and `purchase-api` on port 8091.

**Note.:** In all Development and Test Scenarios, eCommerce uses **H2** in-memory Database.

**Note.:** You can change Database Credentials, Server Running Port for each API on it's `application.yaml` file, available inside the `grails-app/conf` folder of each API.

**Note.:** You also can clean the sources and rebuild the sources by running `grailsw clean`

### Running Test Cases

You can easily run the **Test Cases** using the standalone Grails package built-in with both the API's. Just went to the home folder of one of them (`products-api` or `purchase-api`). And write on your CMD/Terminal:

`grailsw test-app`

### Credits

This development/educational scenario was coded and created by [Claudio Santoro](http://santoro.pw) unde the **MIT License**. The objective of this repository it's as practical test of RESTful API's with Java + Groovy.
