# TEST TASK #

This is the simple RESTfull Spring Boot Application, with some integration tests. All acceptance done according to task list, if you want i can add frontend part in Angular.

### What is this repository for? ###

* This repository is for test spring boot app and author skills
* Version 1.0
* Simple documentation [Swagger URL](http://localhost:8080/swagger-ui/)
* Testing via [postman](https://www.getpostman.com/collections/d5ac6951c92cac4d5a35)

### How to set up?(example) ###
* MySQL is using
* mvn clean install
* mvn spring-boot:run

### Docker (DB also dockerized) ###

* docker build -t test-app:1.0 .
* docker-compose up