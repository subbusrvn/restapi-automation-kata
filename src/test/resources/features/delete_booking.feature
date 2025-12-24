Feature: Delete Hotel Room Booking
  This feature validates the behavior of the Hotel Room Booking system when deleting bookings.
  It ensures that bookings can be deleted successfully, unauthorized deletions are blocked, and error handling works correctly.
  It covers functional correctness, negative validation, and response verification of the DELETE /booking/{id} endpoint.

#----------------------------------------------------------------------------------------------------
# Booking Created and Deleted
#----------------------------------------------------------------------------------------------------
  @delete
  Scenario Outline: Create and then delete a booking
    Given the booking system is available for user access
    When the user logs in with "<userType>" credentials
    Then access should be <loginResult>
    And the user creates a booking with "<dataset>"
    Then the booking is successfully created
    When the user deletes the booking using the stored booking ID
    Then the deletion should be successful with status code 200
    And retrieving the same booking ID should return 404 not found

    Examples:
      | userType | loginResult | dataset       |
      | valid    | granted     | BOOKING_VALID |

#----------------------------------------------------------------------------------------------------
# Booking Delete Scenario for invalid booking Id
#----------------------------------------------------------------------------------------------------
  @delete
  Scenarios: Delete booking with an invalid booking ID

    Given the user logs in with "<userType>" credentials
    And an invalid booking ID is set
    When the user deletes the booking using the stored booking ID
    Then the response status code should be 404
    And the response body should indicate booking not found

    Examples:
      | userType | loginResult | dataset       |
      | valid    | granted     | BOOKING_VALID |

#----------------------------------------------------------------------------------------------------
# Booking Delete Scenario without token
#----------------------------------------------------------------------------------------------------
  @delete
  Scenario: Delete booking without auth token
    Given the booking system is available for user access
    And a valid booking ID exists
    When the user sends a DELETE request without the auth token
    Then the response status code should be 401
    And the response body should indicate authorization error

#----------------------------------------------------------------------------------------------------
# Delete booking with expired / invalid token
#----------------------------------------------------------------------------------------------------
  @delete @security
    Scenario: Attempt to delete booking with an invalid or expired token
    Given the booking system is available for user access
    And the user logs in with "valid" credentials
    Then access should be granted
    And the user creates a booking with "BOOKING_VALID"
    Then the booking is successfully created
    Given the user has an invalid or expired token
    When the user deletes the booking using the stored booking ID
    Then the deletion should fail with status code 401
    And the error message should indicate unauthorized access
