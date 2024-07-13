# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.3.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.3.1/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#using.devtools)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#web)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.




###Holidays Calendar
It is a RESTful API with the ability to Add, Update and List the Federal Holidays associate with a country.
Please check Swager API for testing. 

Technologies

Built using maven, java 17, spring-boot 3.3.1, spring-boot-devtools, H2 db, swagger 2, Modelmapper, and lombok. Tests with junit.

How to start the project

mvn spring-boot:run

How to access the project (Added PostMan Collection, please import them and test).
Note: Add county then add holiday for testing after running the applicaiton as Memorey DB is used.

•POST http://localhost:9090/api/v1/countries
•Get  http://localhost:9090/api/v1/countries
•PUT  http://localhost:9090/api/v1/countries/{countryCode}
•POST http://localhost:9090/api/v1/holidays/{countryCode}
•GET  http://localhost:9090/api/v1/holidays/usa
•PUT  http://localhost:9090/api/v1/holidays/{countryCode}/{holidayId}

API RESTful

Using Swagger UI 2 to document the RESTFul API accessing http://localhost:9090/swagger-ui.html

DB (H2 Memory)
•H2 console is available at http://localhost:9090/h2-console
• Connection inf for H2 url=jdbc:h2:mem:testdb user=SA
