
# Public Health Nonprofit Program Funding

A MySQL database & API developed in under two weeks using Spring Boot

## Brief Description  

This repository contains all process documents, code, and documentation for my final project as part of [Promineo Tech's](http://www.promineotech.com) Feb. 2023 Backend Developer Cohort. The goal of the project was to create a MySQL database and corresponding API endpoints with fully implemented CRUD operations. The database architecture is meant to reflect organizational tracking of funding sources for a fictional nonprofit focusing on public health.  

For more information on the project details, please refer to the proposal document located [here](http://www.github.com/ProjectGrantwood/public-health-nonprofit-program-funding/src/main/resources/static/designdocs/FINAL_PROJECT_PROPOSAL.pdf).

*I came up with this idea in consultation with my endlessly loving, hard-working, beautiful wife Liz, who is a program manager for a public health nonprofit (...when she's not working overnight as a bedside nurse, or being called "Mom"). This project, not to mention my career as a developer, would not exist without her support.*

---

## Technologies used

- [Java 17](https://www.oracle.com/java/)
- [Gradle](https://gradle.org/)
- [Spring Boot 3](https://spring.io/projects/spring-boot)
- [Swagger 3](https://swagger.io/)
- [DBeaver](https://dbeaver.io/)
- [Advanced REST Client](https://www.advancedrestclient.com/home)
- [MySQL Workbench](https://www.mysql.com/products/workbench/)

---

## ERD

![Entity Relationship Diagram](https://github.com/ProjectGrantwood/public-health-nonprofit-program-funding/blob/main/src/main/resources/static/designdocs/PublicHealthNonprofitProgramFundingERD_final.png)

## Where the final project and the proposal differ

### *```Grant``` is now ```FinancialGrant```*

The proposal lists a "grant" table, as well as an associated endpoint. Seeing as ```GRANT``` is a keyword in MySQL, actually using this as a name for a table resulted in major errors. I changed the word to ```FinancialGrant``` or any number of appropriate variations throughout the project.

### *Requests cannot expect extra JSON fields*  

```PUT``` requests to both the ```FinancialGrant``` and ```Donation``` endpoints were both proposed to accept an extra optional JSON field called ```programAllotments```, which was intended to allow the user to specify how much of the grant or donation was to be allocated to each program. This would have been represented as extra columns in the ```program_financial_grant``` and ```program_donation``` tables. I discovered during this project's development that this is not very straightforward to implement, though I am confident that it is possible and would certainly speak to what a solution would look like, if asked. I decided to postpone implementing this feature from the project in favor of ensuring I had a working CRUD API with a fully functional database.


### *Required query parameters added for ```POST```*

When sending a ```POST``` request that creates a new Donation or new Grant, there is now a required query parameter specifying a Donor ID or Granting Organization ID, respectively. Otherwise there was no behavior in any of the existing requests that would actually create this association.  

### *Optional query parameters added for ```PUT```*  

When including a Grant ID or Donation ID (but not both) as a query string parameter in ```PUT``` requests to ```/program/programId```, the associated Grant or Donation (if it exists) is associated with that program in the database.  

### *Documentation completed with Swagger*

The proposal document listed full RAML documentation as a stretch goal. I was able to complete this goal, however, I used Swagger to produce OpenAPI 3.0.0 documentation instead of RAML. If running this project on your local machine, I configured the application.properties file to redirect you directly to the Swagger UI at ```http://localhost:8080```.  

### *Added some non-trivial test cases*

I added a few unit test cases to the ```ProgramControllerUnitTests``` class. Adding test cases was listed as "encouraged but not required" in the project description, but my understanding is that testing is immensely important to the maintainability of any codebase, and I felt that I needed to showcase some ability to write robust test cases. The included tests are far from exhaustive, but they do demonstrate that I have the ability to write tests that would be appropriate in a real-world application.
