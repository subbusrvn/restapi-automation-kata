# Kata API Testing in Java
## Original Specification

API Testing and Java Exercise: Setting up a Basic API Test Automation Framework.

## Objective
The objective of this exercise is to evaluate your knowledge on API testing and Java by setting up a basic API Test Automation framework using Rest-Assured and Cucumber. You will need to create a test suite that executes a few tests against one endpoint of a hotel booking website and evaluates their responses.

## Background
The application under test is a simple hotel booking website where you can book a room and also send a form with a request.

The website can be accessed at https://automationintesting.online/.

The Swagger documentation for the two endpoints you will be testing can be found at:

Booking endpoint: https://automationintesting.online/booking/swagger-ui/index.html  
Optionally, you also have the Authentican endpoint: https://automationintesting.online/auth/swagger-ui/index.html

### Swagger
This website is an external application which is not in our control.  
We noticed that the Swagger documentation is sometimes not available on the mentioned URL above.  
As a backup, you can find the Swagger documentation in this repository at [src/test/resources/spec/booking.yaml](src/test/resources/spec/booking.yaml)

The Open API Spec file is only supported in the Ultimate version of IntelliJ IDEA. But you can copy the content of the file and paste it in an online Swagger editor like https://editor.swagger.io/ to visualize the API documentation.

### Authentication
In order to authenticate yourself, the required credentials are:
* Username: `admin`
* Password: `password`

## Task
You are provided with an extremely basic API test project.

Please clone the project and create a new branch with your name. At the end, please push your branch to this project.

The project to start from, can be found here: https://github.com/freddyschoeters/API_Testing_kata

Your task is to set up an API Test Automation framework from this project using Java, Rest-Assured, and Cucumber (feel free to add more dependencies if required).

It is up to you to define the test cases. You don’t need to have a full coverage, but you need to show enough variation on the types of tests that you would need to write and execute, and what to check in the response.

This kata has the purpose to evaluate both your technical skills as well as your testing skills.

`For this task, you will use the booking endpoint.`


## Requirements
* Use Java as the programming language
* Use Rest-Assured as the API testing library
* Use Cucumber as the BDD framework
* Design your codebase using a proper Java design pattern
* Write good tests with correct checks
* Use Git for version control and push your codebase to an open GitHub repository
* Make regular commits to demonstrate your progress


## Deliverables
* Your branch pushed in the provided project.
* A comprehensive test suite covering the scenarios mentioned above
* A well-structured codebase with proper design patterns and comments
* Regular commits demonstrating your progress

## Evaluation Criteria
* Being able to successfully run the tests
* Correctness and completeness of the test suite
* Quality of the codebase (design patterns, structure, code quality, …)
* Use of Rest-Assured and Cucumber features
* Commit history and progress demonstration
------------------------------------------------------------------------------------------------
<span style="font-size:25px;">**Automation Objectives**</span>
------------------------------------------------------------------------------------------------
<span style="font-size:20px;">**1. Overview**</span>

This repository contains a scalable, maintainable API automation framework built to validate a RESTful Booking API.
The framework is designed using real-world enterprise practices, focusing on readability, modularity, and extensibility.

It demonstrates how API automation is implemented in professional QA teams, beyond simple RestAssured scripts.

------------------------------------------------------------------------------------------------
<span style="font-size:20px;">**2. Tech Stack**</span> 

* Java 17
* Rest Assured – API testing
* Cucumber (BDD) – Business-readable scenarios
* JUnit – Assertions
* Log4j2 – Logging
* Apache POI – Excel-based test data
* Maven – Build & dependency management
------------------------------------------------------------------------------------------------
<span style="font-size:20px;">**3. Framework Architecture**</span>

![Framework Architecture](docs/images/Framework-Architecture.png)

------------------------------------------------------------------------------------------------
<span style="font-size:20px;">**4. Design Principles**</span>
This framework uses:
- Rest Assured (API interaction)
- Cucumber (BDD) -first approach (business readable scenarios)
- JUnit (test execution)
- JSON Schema validation
- Test Context for data sharing
* Service abstraction (no RestAssured in step definitions)
* Single Responsibility for utilities
* Low cyclomatic complexity (≤ 5 per method)
* Data-driven testing via Excel datasets

------------------------------------------------------------------------------------------------
<span style="font-size:20px;">**5. Test Coverage**</span>
The framework currently validates:

- ✅ LOGIN AUTH (POST)
- ✅ Booking creation (POST)
- ✅ Positive and negative scenarios
- ✅ Schema validation
- ✅ Field-level response validation
- ✅ End-to-end booking lifecycle
------------------------------------------------------------------------------------------------
<span style="font-size:20px;">**6. Test Data Strategy**</span>
- Test data is externalized using Excel, simulating legacy enterprise environments where data is often maintained 
by non-technical stakeholders.

- Each test scenario refers to a dataset key, keeping scenarios clean and reusable.

- Example:
- When a guest creates a booking with "ROOMID_VALID"

------------------------------------------------------------------------------------------------
<span style="font-size:20px;">**7. How to Run Tests**</span>
- **Prerequisites**
- Java 17+
- Maven 3.8+
- Git
- Any IDE (IntelliJ IDEA / Eclipse – optional)

------------------------------------------------------------------------------------------------
<span style="font-size:20px;">**8. SetUp**</span>
- git clone https://github.com/subbusrvn/restapi-automation-kata
- cd restapi-automation-kata

**What This Command Does**

- git clone → Downloads the project source code from GitHub
- cd restapi-automation-kata → Navigates to the project root directory
- mvn clean test →
- Cleans previous build artifacts
- Compiles the test code
- Executes all Cucumber feature files
- Runs API validations using Rest Assured

**Execution Mode**

- Tests run headlessly via command line
- No UI or browser interaction is required
- Suitable for local execution and CI/CD pipelines
------------------------------------------------------------------------------------------------
<span style="font-size:20px;">**9. Run all tests**</span>
- mvn clean test

------------------------------------------------------------------------------------------------
<span style="font-size:20px;">**10. Reports**</span>

- After execution, test reports will be available at:
- Cucumber HTML Report: target/cucumber-report.html

------------------------------------------------------------------------------------------------
<span style="font-size:20px;">**11. Logging**</span>
- Centralized logging using Log4j2
- Logs are written to console and rolling log files
- Log files are excluded from version control (.gitignore)

------------------------------------------------------------------------------------------------
<span style="font-size:20px;">**12. Why This Framework?**</span>

- This project is built to reflect how API automation is done in real teams, not just demo scripts:
- Clean separation of concerns
- Maintainable structure
- Interview-ready design
- Easy to extend with new APIs or scenarios

------------------------------------------------------------------------------------------------
<span style="font-size:20px;">**13. Open Bugs**</span>
------------------------------------------------------------------------------------------------
- #🐞 Known Issues / Bugs Identified
- During API automation and exploratory testing, the following functional issues were identified in the Booking API.
- ### BUG-01: System rejects to fail the improperly formatted login attempts
- **Endpoint:** POST /auth/login
- **Severity:** Critical
- **Priority:** High
- **Description:**  
  System allows the login operation, when the Content-Type is passed as "plain/text". 
- **Expected Result:**  
  API should reject the request with 415 Error code.
- **Actual Result:**  
  Login Operation is sucessful with 200 Status code.
- **Status:** Open

---------------------------------------------------------------------------------------------------

### BUG-02: Missing validation for past check-in dates
- **Endpoint:** POST /booking
- **Severity:** High
- **Priority:** High
- **Description:**  
  API allows booking with check-in dates in the past dates.
- **Expected Result:**  
  API should reject bookings with past dates.
- **Actual Result:**  
  Booking is accepted.
- **Status:** Open

---------------------------------------------------------------------------------------------------
### BUG-03: Response STATUS Code 201 is wrongly displayed for the booking success case, When compare the Swagger contract
- **Endpoint:** POST /booking
- **Severity:** Mediaum
- **Priority:** Medium
- **Description:**  
  Response STATUS Code 201 is wrongly displayed for the booking success case
- **Expected Result:**  
  As per Swagger It should be 200. But as per standard, 201 is fine. But we need to update the swagger.
- **Actual Result:**  
  Booking operations succefully completed with201 status code.
- **Status:** Open
---------------------------------------------------------------------------------------------------
### BUG-04: If the DEPOSIT_PAID field is empty or contains any value other than true or false, the booking operation is permitted.  
- **Endpoint:** POST /booking
- **Severity:** High
- **Priority:** High
- **Description:**  
  If the DEPOSIT_PAID field is empty or contains any value other than true or false, the booking operation is permitted.
- **Expected Result:**  
  Booking operationshould be prohibited with 400 status code.
- **Actual Result:**  
  Booking operations succefully completed with201 status code.
- **Status:** Open

---------------------------------------------------------------------------------------------------

### BUG-05: Schema Validation Failure
- **Endpoint:** POST /booking
- **Severity:** Critical
- **Priority:** High
- **Description:**  
  The booking response schema validation fails because two request fields are not returned by the API, 
- despite being accepted and persisted in the request. This is treated as a functional defect and 
- documented accordingly.
- **Expected Result:**  
  The content to match the given JSON schema.
- **Actual Result:**  
  object has missing required properties (["email","phone"])
- **Status:** Open

---------------------------------------------------------------------------------------------------

### BUG-06: Response data structure does not comply with the Swagger contract
- **Endpoint:** POST /booking
- **Severity:** Critical
- **Priority:** High
- **Description:**  
  Response data structure does not comply with the Swagger contract
- **Expected Result:**  
  {
  "bookingid": 10,
  **"booking":** {
  "roomid": 2,
  "firstname": "John",
  "lastname": "Doe",
  "depositpaid": true,
  "bookingdates": {
  "checkin": "2025-10-13",
  "checkout": "2025-10-15"
  },
  "email": "john.doe@example.com",
  "phone": "1234567890"
  }
  }
- 
- **Actual Result:**  

{
"bookingid": 34,
"roomid": 5687,
"firstname": "Samyuktha",
"lastname": "Saravanan",
"depositpaid": true,
"bookingdates": {
"checkin": "2025-12-28",
"checkout": "2025-12-31"
}
}  

- **Status:** Open

---------------------------------------------------------------------------------------------------
<span style="font-size:20px;">**14. 👤 Author**</span>
- Saravanan Subramaniyan
- Senior QA | API Automation | Framework Design