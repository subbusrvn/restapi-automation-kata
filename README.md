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
<ul class="section-text">
  <li><strong>Java 17:</strong> Modern features, strong typing, long-term support.</li>
  <li><strong>Rest Assured:</strong> Java library for API testing.</li>
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
  <li>Initializes <strong>Rest Assured client</strong></li>
  <li>Manages environment-specific configurations</li>
  <li>Defines base URLs and API endpoint constants</li>
</ul>

<h4>context</h4>
<ul>
  <li>Stores <strong>scenario-level shared data</strong></li>
  <li>Manages authentication tokens securely</li>
  <li>Holds dynamic values like booking IDs</li>
</ul>

<h4>hooks</h4>
<ul>
  <li>Cucumber lifecycle hooks (<code>@Before</code>, <code>@After</code>)</li>
  <li>Handles test setup and teardown</li>
  <li>Initializes and cleans up scenario context</li>
  <li>Supports logging and failure handling</li>
</ul>

<h4>models</h4>
<ul>
  <li>POJOs for request/response payloads</li>
  <li>Ensures <strong>type safety</strong></li>
  <li>Supports JSON serialization/deserialization</li>
</ul>

<h4>runners</h4>
<ul>
  <li>Configures Cucumber test runners</li>
  <li>Tag-based execution</li>
  <li>Manages reporting and execution settings</li>
</ul>

<h3>4.2 Business / Service Layer</h3>
<p class="section-text">Encapsulates API execution logic and keeps step definitions clean.</p>

<h4>services</h4>
<ul>
  <li>Executes API calls: <code>GET</code>, <code>POST</code>, <code>PUT</code>, <code>PATCH</code>, <code>DELETE</code></li>
  <li>Accepts request objects and headers</li>
  <li>Returns only <strong>Response</strong> objects</li>
  <li>No test assertions inside</li>
</ul>

<h4>stepdefinitions</h4>
<ul>
  <li>Maps Gherkin steps to executable actions</li>
  <li>Orchestration layer between requests, services, validators</li>
  <li>Coordinates request creation, API execution, response handling</li>
  <li>No business/API logic inside</li>
</ul>

<h3>4.3 Utility & Factory Layer</h3>
<p class="section-text">Reusable helpers and factories for data handling and request construction.</p>

<h4>utils</h4>
<ul>
  <li>Common utilities: logging, parsing, token handling, helper functions</li>
</ul>

<h4>excel / factory / utility</h4>
<ul>
  <li>Excel-based test data reading/writing</li>
  <li>Dynamic request construction using factory/builder patterns</li>
  <li>Safe parsing of optional and nullable fields</li>
</ul>

<h4>Key Classes</h4>
<ul>
  <li><strong>TokenManager</strong> – Centralized token management</li>
  <li><strong>AuthRequestFactory</strong> – Builds auth payloads</li>
  <li><strong>PutBookingRequestBuilder</strong> – PUT request payload builder</li>
  <li><strong>PatchBookingRequestBuilder</strong> – PATCH request payload builder</li>
  <li><strong>SafeParser</strong> – Handles null/empty values</li>
  <li><strong>BookingIdExtractor</strong> – Extracts and stores booking IDs</li>
  <li><strong>LoggerUtil</strong> – Centralized logging</li>
</ul>

<h3>4.4 Validation Layer</h3>
<p class="section-text">Centralizes all response validations.</p>
<h4>validator</h4>
<ul>
  <li>API response validations</li>
  <li>HTTP status code checks</li>
  <li>Response body field validations</li>
  <li>JSON schema validation</li>
  <li>Prevents duplication in step definitions</li>
</ul>

<h3>4.5 Resources Layer</h3>
<p class="section-text">External resources and configuration files for test execution.</p>
<ul>
  <li><strong>features</strong> – Cucumber <code>.feature</code> files written in Gherkin syntax</li>
  <li><strong>schemas</strong> – JSON schema files used for response validation</li>
  <li><strong>spec</strong> – OpenAPI / Swagger specifications for contract validation</li>
  <li><strong>testData</strong> – EExcel and other input files for data-driven testing</li>
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
<p class="section-text">
  The framework provides comprehensive <strong>functional</strong>, 
  <strong>negative</strong>, and <strong>contract-level</strong> coverage for the 
  Booking API, ensuring high confidence in API reliability and behavior.
</p>

<h3>5.1 Authentication Coverage</h3>
<ul>
  <li>Valid authentication token generation</li>
  <li>Invalid credentials handling</li>
  <li>Malformed/missing authentication payloads</li>
  <li>Token reuse via centralized token management</li>
  <li>Authorization failure scenarios</li>
</ul>
<p><strong>✔ Ensures secure access control</strong></p>

<h3>5.2 Booking API – Full Lifecycle Coverage</h3>
<p>
  Covers the entire <strong>CRUD lifecycle</strong> of a booking:
</p>

<p>CRUD workflow:</p>
<h4>5.2.1 Create Booking</h4>
<ul>
  <li>Valid booking creation with full payload</li>
  <li>Mandatory field validation</li>
  <li>Boundary checks (dates, price, names)</li>
</ul>

<h4>5.2.2 Read Booking</h4>
<ul>
  <li>Fetch booking by valid ID</li>
  <li>Fetch with invalid/non-existent ID</li>
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
  <li>Verify booking inaccessible after deletion</li>
  <li>Negative scenarios for invalid/missing authorization</li>
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
<pre>
git clone https://github.com/subbusrvn/restapi-automation-kata
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
<p>
  After test execution, a Cucumber HTML report is generated automatically.
</p>
<p><strong>Report Location:</strong></p>
<pre>target/cucumber-report.html</pre>
<p>
  This report provides detailed execution results, step-level status, and failure information.
</p>

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
</ul>
<p>
  These issues highlight gaps in the API and demonstrate the effectiveness of the test coverage.
</p>
<h2>14. Author</h2>
<p><strong>Saravanan Subramaniyan</strong><br/>
Senior QA | API Automation | Framework Design
</p>
