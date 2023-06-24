# public-health-nonprofit-program-funding
Final Project for the Promineo Tech Backend Bootcamp

## Brief Description:

This repository contains all process documents, code, and documentation for my final project as part of Promineo Tech's Feb. 2023 Backend Developer cohort. The goal of the project was to create a MySQL database and corresponding API endpoints with fully implemented CRUD operations. It is coded primarily in Java using the Spring Boot framework, and I used Gradle as the build-tool.

The database architecture is meant to reflect organizational tracking of funding sources for a fictional nonprofit focusing on Public Health. I came up with this idea in consultation with my endlessly loving, hard-working, beautiful wife Liz, who is a program manager for a public health nonprofit (...when she's not working overnight as a bedside nurse). This project, not to mention my career as a developer, would not exist without her support.

**For more information on the project details, please refer to the proposal document located in the** _src/main/resources/static_ **folder!**

## Where the project and the proposal differ:

The proposal document was written before I had a chance to really dig into the code and the database design. As a result, there are a few differences between the proposal and the final product. I will list them here:

1. The proposal lists a "grant" table, as well as an associated endpoint. Of course, ```GRANT``` is a keyword in MySQL, so this was a no-go. I changed the word to ```FinancialGrant``` or any number of appropriate variations throughout the code as well as the database.
2. PUT requests to both the ```FinancialGrant``` and ```Donation``` endpoints were both proposed to accept an extra optional JSON field called "programAllotments", which was intended to allow the user to specify how much of the grant or donation was to be allocated to each program. I realized this would require writing a custom deserializer, which is much easier to do when using the Spring Framework by itself, while this project was created with Spring Boot. I decided to leave this feature out for now, and removed the two extra columns from the ```FinancialGrant``` and ```Donation``` tables. I look forward to future database projects using Spring Framework, where it would be easier to implement custom behaviors for features like this.