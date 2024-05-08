# Via Secure Mongo

Simple Spring Boot 3.2 project connecting Spring Boot 3.2 REST API to MongoDB in order to process credentials of entitled users.

# To run it
How to build it:
mvn clean compile install package

How to run it:
mvn spring-boot:run

How to test it:
mvn test-compile test

How to read all users:
curl http://localhost:8080/users

How to find one user:
curl http://localhost:8080/users/search/findByRoleName?name=McCaine

How to add new user:
curl -i -X POST -H "Content-Type:application/json" -d "{  \"username\" : \"Tomasso\",  \"roleName\" : \"McCaine\" }" http://localhost:8080/users

# Used:

- Spring Boot 3.2
- MongoDB 7.0.2
- TestContainers
- Maven 3.9.6

# Contributors

<Tomasz Trzciński | mailto:trzcinski.tomasz.1988@gmail.com>