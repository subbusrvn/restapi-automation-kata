<h1 align="center">REST API Automation Framework</h1>

<p align="center" style="font-size:14px;">
  <b>Java · Rest Assured · Cucumber · Maven · GitHub Actions</b>
</p>

<hr/>

<style>
  .overview-text {
    font-size: 14px;
    line-height: 1.6;
  }
</style>

<p class="overview-text">
  This project is a <strong>scalable and modular API automation framework</strong> built using
  <strong>Java</strong>, <strong>Rest Assured</strong>, and <strong>Cucumber</strong>, following
  <strong>industry-standard automation practices</strong>.
</p>

<section style="font-size:14px; line-height:1.7; color:#333;">
  <h3 style="color:#2c3e50;">3. Objectives / Goals</h3>

  <p>
    The main goal of this project is to build a <strong>robust, scalable, and maintainable API automation framework</strong>
    following <strong>industry-standard practices</strong> and <strong>clean architecture principles</strong>.
  </p>

  <p>The framework aims to achieve the following:</p>

  <ul>
    <li><strong>Scalable and Maintainable Architecture:</strong> The framework can easily support new APIs, test scenarios, and integrations without affecting existing tests.</li>
    <li><strong>Low Cyclomatic Complexity:</strong> The code is kept simple and readable by minimizing conditional logic and separating responsibilities clearly.</li>
    <li><strong>Reusable and Modular Components:</strong> Core functionalities like request builders, utilities, and validators are centralized for easy reuse.</li>
    <li><strong>Clear Separation of Concerns:</strong> Test data, request building, execution, and validation are organized in distinct layers for clarity and maintainability.</li>
    <li><strong>CI/CD-Ready Execution:</strong> The framework can run seamlessly in automated pipelines using Maven and GitHub Actions for fast feedback.</li>
    <li><strong>Readable and Business-Focused Tests:</strong> Using Cucumber BDD, the tests are understandable to both technical and non-technical team members.</li>
  </ul>
</section>


<section style="font-size:14px; line-height:1.7; color:#333;">
  <h3>4. Tech Stack</h3>

  <p>
    The framework uses a set of modern, widely adopted tools to make API automation <strong>reliable, maintainable, and scalable</strong>. 
    Each tool in the stack was chosen to make development and testing easier while keeping the framework robust for enterprise use.
  </p>

  <ul>
    <li><strong>Java 17:</strong> The main programming language for the framework. It provides modern features, strong typing, and long-term support.</li>
    <li><strong>Rest Assured:</strong> A Java library for API testing that makes sending requests and validating responses straightforward.</li>
    <li><strong>Cucumber (BDD):</strong> Lets us write test scenarios in plain, readable language (Gherkin), so both technical and non-technical team members can understand the tests.</li>
    <li><strong>Maven:</strong> Handles project builds and dependencies, ensuring consistent builds and making it easy to run tests automatically.</li>
    <li><strong>GitHub Actions:</strong> Automates test execution and reporting in CI/CD pipelines, providing quick feedback on the health of the APIs.</li>
  </ul>

  <p>
    Using this stack, the framework is not only easy to maintain but also ready for integration into continuous delivery pipelines, following best practices for enterprise API testing.
  </p>
</section>






<p class="overview-text">
  It is designed to support <strong>clean architecture</strong>,
  <strong>low cyclomatic complexity</strong>,
  <strong>reusable components</strong>, and
  <strong>CI/CD-ready execution</strong>,
  making it suitable for <strong>enterprise-level API testing</strong>.
</p>


<h2>🏗️ Framework Architecture</h2>

<ul>
  <li><b>Factory Layer</b> – Request builders & data providers</li>
  <li><b>Utility Layer</b> – Token management, parsing, helpers</li>
  <li><b>Validators</b> – Response & Swagger validation</li>
  <li><b>Step Definitions</b> – Thin & readable steps</li>
</ul>

<h2>🔄 CI/CD Integration</h2>

<p>
Tests are executed automatically using <b>GitHub Actions</b> on:
</p>

<ul>
  <li>Push to <code>main</code></li>
  <li>Manual trigger</li>
  <li>Hourly health checks</li>
</ul>