# GdanskInternshipDynatrace
Project made for recruitment purposes using [Narodowy Bank Polski's API](http://api.nbp.pl/)

# Technologies
* Java 17
* Maven
* Spring Boot 3.0.6

# Installation
## GitHub Repository
1. Clone repository
```
git clone https://github.com/kjach/GdanskInternshipDynatrace.git
```

2. Run maven install
```
mvn clean install
```

3. Run application using maven
```
mvn spring-boot:run
```
You can also run the project using .jar file
```
java -jar target/GdanskInternshipDynatrace-0.0.1-SNAPSHOT.jar
```
Application should run at `http://localhost:8080/`
## Docker
1. Pull Docker Image
```
docker pull kjach/gid-api:latest
```
2. Run container
```
docker run -p 8080:8080 kjach/gid-api
```
Application should run at `http://localhost:8080/`

# Endpoints and examples
* `/exchangerates/{currencyCode}/{date}`

Given currency code and date in format YYYY-MM-DD this endpoint returns the average exchange rate.
For example 
```
$ curl http://localhost:8080/exchangerates/GBP/2023-01-03
```
Should return `5.2911`
* `/exchangerates/{currencyCode}/max-min-average/{numberOfQuotations}`

Given currency code and number of quotations (between 1 and 255) should return max and min average exchange rate.
For example
```
$ curl http://localhost:8080/GBP/max-min-average/10
```
Should return `Max: 5.3041 Min: 5.1958`

Notice: Results may vary depending on the date! Given results are valid for 25.04.2023
* `/exchangerates/{currencyCode}/major-difference/{numberOfQuotations}`

Given currency code and number of quotations (between 1 and 255) should return the major difference between buy and ask rates.
For example
```
$ curl http://localhost:8080/exchangerates/GBP/major-difference/10
```
Should return `0.1062`.

Notice: Results may vary depending on the date! Given results are valid for 26.04.2023
# Swagger
Swagger UI is available at `/swagger-ui.html` endpoint.

# Tests
To run tests:
```
mvn test
```
Notice: Some test may be failed due to exchange rates changes after 25.04.2023
## Author and additional info
Project created by Kacper Jach

[Trello Board](https://trello.com/b/ax94PkDU/flow)
