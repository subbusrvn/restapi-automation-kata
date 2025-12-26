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
<span style="font-size:20px;">**3. Framework Architecture**</span><br>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>REST API Automation Framework</title>
  <style>
    body {
      font-family: Arial, Helvetica, sans-serif;
      line-height: 1.6;
      color: #333;
    }
    h1, h2 {
      color: #2c3e50;
    }
    pre {
      background: #f8f9fa;
      padding: 15px;
      border-radius: 6px;
      font-size: 14px;
      overflow-x: auto;
    }
    .caption {
      text-align: center;
      font-style: italic;
      color: #555;
      margin-top: 6px;
    }
  </style>
</head>
<body>

  <h1>REST API Automation Kata</h1>

<h2>📁 Project Structure</h2>
  <pre>
restapi-automation-kata
│
├── src
│   └── test
│       ├── java
│       │   └── com.booking
│       │       ├── client
│       │       ├── config
│       │       ├── context
│       │       ├── endpoints
│       │       ├── hooks
│       │       ├── models
│       │       ├── runners
│       │       ├── services
│       │       ├── stepdefinitions
│       │       └── utils
│       └── resources
│           ├── features
│           ├── schemas
│           ├── spec
│           ├── testData
│           ├── config.properties
│           └── log4j2.xml
│
├── .assets
│   ├── architecture.png
│   └── api-flow.png
│
├── pom.xml
├── README.md
└── .gitignore
  </pre>

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

What This Command Does:
- git clone → Downloads the project source code from GitHub
- cd restapi-automation-kata → Navigates to the project root directory
------------------------------------------------------------------------------------------------
<span style="font-size:20px;">**9. Execution Mode**</span>

<span style="font-size:16px;">**9.1 All Test Execution**</span><br>

Run the below comment in the command prompt<br>
<span style="font-size:12px; color: red;"><b>mvn clean test</b></span><br>

- Cleans previous build artifacts
- Compiles the test code
- Executes all Cucumber feature files
- Runs API validations using Rest Assured

<span style="font-size:16px;">**9.2 Tag-Based Test Execution**</span>

This framework uses Cucumber tags to control test execution efficiently.
Tags help in grouping scenarios based on test intent, validation type, and execution purpose, 
enabling flexible local and CI/CD runs without code changes.

<span style="font-size:16px;">**Supported Tags: POSITIVE**</span></br>
<span style="font-size:14px;">**Purpose:**</span><br>

Covers happy-path scenarios where valid inputs are provided and the expected outcome is successful.

<span style="font-size:14px;">**Typical usage:**</span>

 - Valid booking creation
 - Successful authentication 
 - Accepted boundary values (e.g., min/max allowed length)

<span style="font-size:14px;">**Command:**</span>  
<span style="font-size:12px; color: red;"><b>mvn clean test "-Dcucumber.filter.tags=@positive"</b></span><br>

<span style="font-size:16px;">**Supported Tags: NEGATIVE**</span></br>
<span style="font-size:14px;">**Purpose:**</span><br>

Covers scenarios where invalid inputs are provided and the system is expected to reject the request gracefully.

<span style="font-size:14px;">**Typical usage:**</span>

 - Missing mandatory fields
 - Invalid credentials
 - Invalid date ranges
 - Empty or malformed input values

<span style="font-size:14px;">**Command:**</span>  
<span style="font-size:12px; color: red;"><b>mvn clean test "-Dcucumber.filter.tags=@negative"</b></span><br>

<span style="font-size:16px;">**Supported Tags: VALIDATION**</span></br>
<span style="font-size:14px;">**Purpose:**</span><br>

Groups all field-level and business-rule validations, including both positive and negative cases.

<span style="font-size:14px;">**Typical usage:**</span>

 - Boundary value analysis
 - Mandatory field validation
 - Input format and normalization checks

<span style="font-size:14px;">**Command:**</span>  
<span style="font-size:12px; color: red;"><b>mvn clean test "-Dcucumber.filter.tags=@validation"</b></span><br>

<span style="font-size:16px;">**Supported Tags: SANITY**</span></br>
<span style="font-size:14px;">**Purpose:**</span><br>

Represents a small, fast, and critical subset of tests used to quickly verify system stability after a deployment or configuration change.

<span style="font-size:14px;">**Typical usage:**</span>

 - Happy-path only
 - Minimal data setup
 - Fast execution
 - High business confidence

<span style="font-size:14px;">**Command:**</span>  
<span style="font-size:12px; color: red;"><b>mvn clean test "-Dcucumber.filter.tags=@sanity"</b></span><br>

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