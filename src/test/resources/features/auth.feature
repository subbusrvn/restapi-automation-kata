Feature: User Login Scenarios
  The below cases are used to ensure the login end point with various inputs

#----------------------------------------------------
  Valid Login Operation
# ----------------------------------------------------
  @positive @sanity
  Scenario Outline: User login access for valid Cre
    Given a user wants to access the booking system
    When the user logs in with "<userType>" credentials
    Then access should be <loginResult>

    Examples:
      | userType        | loginResult |
      | valid           | granted     |


  #----------------------------------------------------
  #Login operation check with various login credentials
  # ----------------------------------------------------
  @authVerification @negative
  Scenario Outline: User login access based on credentials
    Given a user wants to access the booking system
    When the user logs in with "<userType>" credentials
    Then access should be <loginResult>

    Examples:
      | userType        | loginResult |
      | invalidUser     | denied      |
      | invalidPassword | denied      |
      | emptyUsername   | denied      |
      | emptyPassword   | denied      |
      | missingRequest  | denied      |
      | sqlinjUsername  | denied      |
      | sqlinjPassword  | denied      |
      | caseSenUsername | denied      |
      | caseSenPassword | denied      |
      | splcharUsername | denied      |
      | splcharPassword | denied      |

  #----------------------------------------------------
  #Login operation with invalid Content-Type
  #----------------------------------------------------
  @negative
  Scenario: System rejects improperly formatted login attempts
    Given the system is available for user access
    When a client submits a login request in an unsupported format
    Then the system should reject the request
  #----------------------------------------------------
  #Access Token length, type verification
  #----------------------------------------------------
  @negative
  Scenario: Login operation with token validation
    Given the system is available for user access
    When login with valid credentials
    Then the system should return a token
    And the token format should be alphanumeric
    And the token length should be greater than 10 characters
    And Content-Type is genearted in the header

 #----------------------------------------------------
 #Unique token for every Login access
 #----------------------------------------------------
  @negative
  Scenario: Each login creates a unique user session
    When a user is logged into the booking system
    And the user logs in again with the same credentials
    Then each login should create a unique session