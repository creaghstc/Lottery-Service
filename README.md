# Lottery REST Service

A REST webservice for a lottery game with the below rules:

You have a series of lines on a ticket with 3 numbers, each of which has a value of 0, 1, or
2, 

- For each ticket if the sum of the values on a line is 2, the result for that line is 10.
- If they are all the same, the result is 5.
- Otherwise so long as both 2nd and 3rd numbers are different from the 1st, the result is 1.
- Otherwise the result is 0.

## Getting Started


### Prerequisites

In order to run this application you will need to have java installed in your machine, this project uses Java 8.
You will also need maven to build the project.

### Technologies used

- Java 
- Maven
- Spring starter web
- Spring starter JPA
- Hibernate
- Lombok
- H2 in memory database
- Swagger UI
- Intellij IDE

### Installing
To Build the application run either of the follow maven commands, they will build a jar file in the target directory.

```
mvn clean install
mvn clean package
```

Once you have the jar file you can start the application using the below java command.

```
java -jar <jar_name>.jar
```


## Using the Application

To demo the application you can use the swagger UI page to mock out the REST WS calls and see the responses, you could also use something like postman. When application is running
you can go to the following url http://localhost:8080/swagger-ui.html 

To view the database you can login to the H2 db console using the following url: http://localhost:8080/h2-console/ with settings
- Driver Class -> org.h2.Driver
- JDBC URL -> jdbc:h2:mem:lotterydb
- username -> lottoAdmin
- password -> password

Username and password can be set in the properties file.



## Running the tests

Tests can be run as part of the Maven build using:

```
mvn clean test
```


## Versioning

For any future updates please use standard SNAPSHOT versioning:
 - 1.X-SNAPSHOT for feature development 
 - 1.X.X-CR-SNAPSHOT for candidate release testing
 - 1.X.X for final releases.
 
 Repo -> https://github.com/creaghstc/Lottery-Service
## Authors

* **Conor Creagh**  - creaghstc@gmail.com


