<h1 align="center">REST API Automation Framework</h1>

<p align="center" style="font-size:14px;">
  <b>Java · Rest Assured · Cucumber · Maven · GitHub Actions</b>
</p>

<hr/>

<h2>1. Overview</h2>
<p class="overview-text">
  This project is a <strong>scalable and modular API automation framework</strong> built using
  <strong>Java</strong>, <strong>Rest Assured</strong>, and <strong>Cucumber</strong>, following
  <strong>industry-standard automation practices</strong>.
</p>

<h2>2. Objectives / Goals</h2>
<p class="overview-text">
  The main goal of this project is to build a <strong>robust, scalable, and maintainable API automation framework</strong>
  following <strong>industry-standard practices</strong> and <strong>clean architecture principles</strong>.
</p>

<p class="overview-text">The framework aims to achieve the following:</p>
<ul class="section-text">
  <li><strong>Scalable and Maintainable Architecture:</strong> Supports new APIs, test scenarios, and integrations without affecting existing tests.</li>
  <li><strong>Low Cyclomatic Complexity:</strong> Code is simple and readable by minimizing conditional logic and separating responsibilities clearly.</li>
  <li><strong>Reusable and Modular Components:</strong> Core functionalities like request builders, utilities, and validators are centralized for easy reuse.</li>
  <li><strong>Clear Separation of Concerns:</strong> Test data, request building, execution, and validation are organized in distinct layers.</li>
  <li><strong>CI/CD-Ready Execution:</strong> Can run seamlessly in automated pipelines using Maven and GitHub Actions.</li>
  <li><strong>Readable and Business-Focused Tests:</strong> Cucumber BDD tests understandable to both technical and non-technical team members.</li>
</ul>

<h2>3. Tools & Technologies Stack</h2>
<p class="overview-text">
  Modern tools chosen to make API automation <strong>reliable, maintainable, and scalable</strong>:
</p>
- Java 17 – 
- Rest Assured – 
- Cucumber – BDD test scenarios
- Maven – Dependency and build management
<ul class="section-text">
  <li><strong>Java 17:</strong> Modern features, strong typing, long-term support.</li>
  <li><strong>Rest Assured:</strong> Simplified API testing Java.</li>
  <li><strong>Cucumber (BDD):</strong> Gherkin syntax for business-readable test scenarios.</li>
  <li><strong>Maven:</strong> Build and dependency management.</li>
  <li><strong>GitHub Actions:</strong> CI/CD automation.</li>
</ul>

<h2>4. Architecture / Framework Design</h2>
<p class="section-text">
  Layered modular architecture keeps codebase clean, scalable, and maintainable. Each layer has single responsibility.
</p>

<h3>4.1 Core Framework Layer</h3>
<p class="section-text">Foundational setup and shared infrastructure.</p>

<h4>client / config / endpoints</h4>
<ul>
  <li>Initializes the <strong>Rest Assured client</strong></li>
  <li>Manages environment-specific configurations</li>
  <li>Defines base URLs and API endpoint constants</li>
</ul>

<h4>context</h4>
<ul>
  <li>Stores <strong>scenario-level shared data</strong></li>
  <li>Manages authentication tokens securely</li>
  <li>Holds dynamic values such as booking IDs for reuse across steps</li>
</ul>

<h4>hooks</h4>
<ul>
  <li>Implements Cucumber lifecycle hooks (<code>@Before</code>, <code>@After</code>)</li>
  <li>Handles test setup and teardown activities</li>
  <li>Initializes and cleans up scenario context</li>
  <li>Supports logging and failure handling</li>
</ul>

<h4>models</h4>
<ul>
  <li>Defines POJOs for request and response payloads</li>
  <li>Ensures <strong>type safety</strong> in API communication</li>
  <li>Supports clean JSON serialization and deserialization</li>
</ul>

<h4>runners</h4>
<ul>
  <li>Configures Cucumber test runners</li>
  <li>Supports <strong>tag-based execution</strong></li>
  <li>Manages reporting and execution settings</li>
</ul>

<h3>4.2 Business / Service Layer</h3>
<p>
This layer encapsulates API execution logic and keeps step definitions clean and readable.
</p>

<h4>services</h4>
<ul>
  <li>Executes API calls using HTTP methods (<code>GET</code>, <code>POST</code>, <code>PUT</code>, <code>PATCH</code>, <code>DELETE</code>)</li>
  <li>Accepts request objects and headers as input</li>
  <li>Returns only <strong>Response</strong> objects to callers</li>
  <li>Contains no test assertions or step-level logic</li>
</ul>

<h4>stepdefinitions</h4>
<ul>
  <li>Maps Gherkin steps to executable actions</li>
  <li>Acts as an <strong>orchestration layer</strong> between requests, services, and validators</li>
  <li>Coordinates request creation, API execution, and response handling</li>
  <li>Avoids embedding business or API execution logic</li>
</ul>

<h3>4.3 Utility & Factory Layer</h3>
<p>
This layer contains reusable helpers and factories that support data handling and request construction.
</p>

<h4>utils</h4>
<ul>
  <li>Provides common reusable utilities used across the framework</li>
  <li>Supports logging, parsing, token handling, and helper functions</li>
</ul>

<h4>excel / factory / utility</h4>
<ul>
  <li>Manages <strong>Excel-based test data</strong> reading and writing</li>
  <li>Supports <strong>dynamic request construction</strong> using factory and builder patterns</li>
  <li>Handles safe parsing of optional and nullable fields</li>
</ul>

<h4>Key Classes</h4>
<ul>
  <li><strong>TokenManager</strong> – Centralized authentication token generation and reuse</li>
  <li><strong>AuthRequestFactory</strong> – Builds authentication request payloads</li>
  <li><strong>PutBookingRequestBuilder</strong> – Constructs PUT request payloads with conditional field inclusion</li>
  <li><strong>PatchBookingRequestBuilder</strong> – Constructs PATCH request payloads with conditional field inclusion</li>
  <li><strong>SafeParser</strong> – Safely handles null and empty values</li>
  <li><strong>BookingIdExtractor</strong> – Extracts and stores booking IDs for reuse</li>
  <li><strong>LoggerUtil</strong> – Provides centralized and consistent logging support</li>
</ul>

<h3>4.4 Validation Layer</h3>
<p>
This layer centralizes all response validations to keep step definitions clean and avoid assertion duplication.
</p>
<h4>validator</h4>
<ul>
  <li>Centralizes all <strong>API response validations</strong></li>
  <li>Performs <strong>HTTP status code</strong> checks</li>
  <li>Validates <strong>response body fields</strong> and values</li>
  <li>Supports <strong>JSON schema validation</strong></li>
  <li>Prevents assertion logic from being duplicated inside step definitions</li>
</ul>

<h3>4.5 Resources Layer</h3>
<p>
Contains all external resources and configuration files required for test execution.
</p>
<ul>
  <li><strong>features</strong> – Cucumber <code>.feature</code> files written in Gherkin syntax</li>
  <li><strong>schemas</strong> – JSON schema files used for response validation</li>
  <li><strong>spec</strong> – OpenAPI / Swagger specifications for contract validation</li>
  <li><strong>testData</strong> – Excel and other input files for data-driven testing</li>
  <li><strong>config.properties</strong> – Environment-specific configuration values</li>
  <li><strong>log4j2.xml</strong> – Centralized logging configuration</li>
</ul>

<h4>High-Level Directory Overview</h4>
<pre>
restapi-automation-kata/
 └── src
     └── test
         ├── java
         │   ├── client
         │   ├── config
         │   ├── context
         │   ├── endpoints
         │   ├── hooks
         │   ├── models
         │   ├── runners
         │   ├── services
         │   ├── stepdefinitions
         │   ├── utils
         │   │    ├── excel
         │   │    │    ├── factory
         │   │    │    └── utility
         │   │    ├── AuthRequestFactory
         │   │    ├── BookingIdExtractor
         │   │    ├── LoggerUtil
         │   │    ├── PatchBookingRequestBuilder
         │   │    ├── PutBookingRequestBuilder
         │   │    ├── SafeParser
         │   │    └── TokenManager
         │   └── validator
         └── resources
             ├── config.properties     
             ├── feature
             ├── schemas
             ├── spec
             └── testData
    ├── README.md                     
    └── pom.xml                      

</pre>

<h2>5. Test Coverage</h2>
<p>
  The framework provides comprehensive <strong>functional</strong>, 
  <strong>negative</strong>, and <strong>contract-level</strong> coverage for the 
  Booking API, ensuring high confidence in API reliability and behavior.
</p>

<h3>5.1 Authentication Coverage</h3>
<ul>
  <li>Valid authentication token generation</li>
  <li>Invalid credentials handling</li>
  <li>Missing or malformed authentication payload validation</li>
  <li>Token reuse across scenarios via centralized token management</li>
  <li>Authorization failure scenarios (expired or missing token cases)</li>
</ul>
<p>
  <strong>✔ Ensures secure access control and prevents unauthorized API usage.</strong>
</p>

<h3>5.2 Booking API – Full Lifecycle Coverage</h3>
<p>
  Covers the entire <strong>CRUD lifecycle</strong> of a booking:
</p>

<p>CRUD workflow:</p>
<h4>5.2.1 Create Booking</h4>
<ul>
  <li>Valid booking creation with complete payload</li>
  <li>Mandatory field validations</li>
  <li>Boundary value checks (dates, price, names)</li>
</ul>

<h4>5.2.2 Read Booking</h4>
<ul>
  <li>Fetch booking by valid ID</li>
  <li>Fetch booking with invalid or non-existent ID</li>
  <li>Verify response consistency after create or update</li>
</ul>

<h4>5.2.3 Update Booking</h4>
<ul>
  <li>Full update (PUT) with all fields</li>
  <li>Partial update (PATCH) with selective fields</li>
  <li>Data integrity verification after update</li>
</ul>

<h4>5.2.4 Delete Booking</h4>
<ul>
  <li>Successful deletion with valid authorization</li>
  <li>Verify booking is no longer accessible after deletion</li>
  <li>Negative scenarios for invalid or missing authorization</li>
</ul>
<p>
  <strong>✔ Validates real-world booking workflows end-to-end.</strong>
</p>
<h3>5.3 Negative & Edge Case Scenarios</h3>
<ul>
  <li>Invalid request payloads</li>
  <li>Missing mandatory fields</li>
  <li>Incorrect data types</li>
  <li>Invalid path parameters</li>
  <li>Unauthorized/forbidden access</li>
  <li>Handling of non-existent resources</li>
</ul>
<p>
  <strong>✔ Ensures API fails gracefully with correct status codes and error messages.</strong>
</p>
<h3>5.4 Schema & Field-Level Validation</h3>
<ul>
  <li>JSON schema validation</li>
  <li>Mandatory vs optional field verification</li>
  <li>Data type and format checks (dates, numbers, booleans)</li>
  <li>Contract validation aligned with Swagger / OpenAPI specifications</li>
</ul>

<h3>5.5 Data & State Validation</h3>
<ul>
  <li>Dynamic data handling using scenario context</li>
  <li>Safe sharing of booking IDs and tokens across steps</li>
  <li>Validation of API state after every mutation (create, update, delete)</li>
  <li>✔ Guarantees test reliability and independence.</li>

</ul>

<h3>5.6 Execution Characteristics</h3>
<ul>
  <li>Independent & repeatable scenarios</li>
  <li>No hard-coded IDs/tokens</li>
  <li>Environment-agnostic</li>
  <li>CI/CD-ready execution</li>
</ul>

<h3>5.7 Coverage Summary</h3>
<p>
  This test suite ensures:
</p>
<ul>
  <li>Functional correctness</li>
  <li>RobustError handling</li>
  <li>Contract compliance</li>
  <li>High confidence regression safety</li>
</ul>


<h2>6. Test Data Strategy</h2>

<p>
Test data is managed efficiently to support <strong>scalable, maintainable, and flexible testing</strong>.
</p>

<h3>Test Data Location</h3>
<ul>
  <li>Stored in Excel files under <code>src/test/resources/testData/</code></li>
  <li>Each row represents an independent test scenario</li>
  <li>Columns correspond to request fields and optional parameters</li>
</ul>

<h3>Data-Driven Execution</h3>
<ul>
  <li>The <strong>Factory layer</strong> reads Excel rows to dynamically build API request payloads</li>
  <li>For PATCH requests, only non-empty fields from Excel are included in the request</li>
  <li>Allows multiple scenarios to be tested without hardcoding payloads</li>
</ul>

<h3>Benefits</h3>
<ul>
  <li>Improves test coverage with minimal code changes</li>
  <li>Simplifies adding new test scenarios</li>
  <li>Maintains readability and low cyclomatic complexity</li>
  <li>Supports clean separation of data and logic</li>
</ul>
<p class="section-text">
  Describes how <strong>Excel files</strong> simulate 
  <strong>enterprise-level test data</strong>, with scenarios referring to 
  <strong>dataset keys</strong> to keep Gherkin feature files clean and readable.
</p>
<h2>7. How to Run Tests</h2>
<h3>7.1 Prerequisites</h3>
<ul>
  <li>Java 17+</li>
  <li>Maven 3.8+ or higher</li>
  <li>Git</li>
  <li>Any IDE (IntelliJ IDEA, Eclipse) Optional</li>
</ul>

<h2>8. Setup</h2>
<p>
  Follow these simple steps to set up the project on your local machine:
</p>
## How to Run

<pre>
1. Clone repo: git clone https://github.com/subbusrvn/restapi-automation-kata
cd restapi-automation-kata
</pre>
<p>
  Once cloned, the project is ready to run without any additional configuration.
</p>

<h2>9. Execution Modes</h2>
<p>
  The framework supports multiple ways to execute tests based on your needs:
</p>
<ul>
  <li><strong>Run all tests:</strong> <pre>mvn clean test</pre></li>
  <li><strong>Tag-based execution:</strong>
    <pre>mvn clean test -Dcucumber.filter.tags="@sanity"</pre>
    <ul>
      <li>@positive – functional– Valid functional scenarios</li>
      <li>@negative – error/failure– Error and failure scenarios</li>
      <li>@validation – schema/contract– Schema and contract validations</li>
      <li>@sanity – critical APIs– Quick checks for critical APIs</li>
    </ul>
  </li>
</ul>

<h2>10. Reports</h2>
<h3> 10.1.Cucumber Report</h3>
<p>
  After test execution, a Cucumber HTML report is generated automatically.
</p>
<p><strong>Report Location:</strong></p>
<pre>target/cucumber-report.html</pre>
<p>
  This report provides detailed execution results, step-level status, and failure information.
</p>

<h3> 10.2.ChainTest Report(Extent Report) (Enhanced Cucumber Reporting)</h3>

<p>
  We’ve integrated <strong>ChainTest Report</strong> into this project to generate a rich HTML test report on top of the standard Cucumber output. ChainTest is a powerful reporting framework that produces both static HTML dashboards and email-friendly summary reports that help visualize test execution results in a clear, actionable format.
</p>

<h3> What It Includes</h3>
<ul>
  <li>
    <strong>Static HTML Dashboard</strong> (e.g., <code>Index.html</code>) with detailed pass/fail statistics
  </li>
  <li>
    <strong>Email Report</strong> (e.g., <code>Email.html</code>) suitable for CI email notifications
  </li>
</ul>

<p>
  Reports are generated automatically when the test suite runs with the appropriate listener configured.
</p>
<pre>
target/chaintest/Index.html
target/chaintest/Email.html
</pre>
<h2>11. Logging</h2>
<p>
  Logging is handled using <strong>Log4j2</strong>.
</p>
<ul>
  <li>Logs are printed to the console during execution</li>
  <li>Rolling log files are generated for deeper analysis</li>
  <li>Log files are excluded from version control</li>
</ul>

<h2>12. Why This Framework?</h2>
<p>
  This framework is designed to reflect real-world API automation practices, not demo or toy scripts.
</p>

<ul>
  <li>Professional-grade API automation architecture</li>
  <li>Clean separation of concerns across layers</li>
  <li>Highly maintainable and scalable design</li>
  <li>Easy to extend for additional APIs and services</li>
</ul>


<h2>13. Open Bugs</h2>
<p>
  The following known issues were identified during testing and are currently open:
</p>
<ul>
  <li>Missing validations</li>
  <li>Schema mismatches</li>
  <li>Inconsistent error messages</li>

<a href="https://docs.google.com/spreadsheets/d/1ZBe-pOIDCGirSx6NcQij8g251fds4ogIofQyB94mGCQ/edit?gid=0#range=A2">
  Kata Hotel Room Booking Endpoints - Open Bug List
</a>
</ul>
<p>
  These issues highlight gaps in the API and demonstrate the effectiveness of the test coverage.
</p>
<h2>14. Author</h2>
<p><strong>Saravanan Subramaniyan</strong><br/>
Senior QA | API Automation | Framework Designing
</p>
