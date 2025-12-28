Feature: Hotel Room Booking Management System - Delete Endpoint
  This feature validates the behavior of the Hotel Room Booking system when deleting bookings.
  It ensures that bookings can be deleted successfully, unauthorized deletions are blocked, and error handling works correctly.
  It covers functional correctness, negative validation, and response verification of the DELETE /booking/{id} endpoint.

#----------------------------------------------------------------------------------------------------
# Booking Created and Deleted
#----------------------------------------------------------------------------------------------------
  @positive @delete @sanity @regression
  Scenario Outline: Create and then delete a booking id
    Given the booking system is available for user access
    When the user login with "<userType>" credentials
    Then access should be <loginResult>
    And the user creates a booking with "<dataset>"
    Then the booking is successfully created
    When the user deletes the booking using the stored booking ID
    Then the deletion should be successful
    And retrieving the same booking ID should display not found

    Examples:
      | userType | loginResult | dataset              |
      | valid    | granted     | BOOKING_VALID_DELETE1 |

#----------------------------------------------------------------------------------------------------
# Booking Delete Scenario for invalid booking Id
#----------------------------------------------------------------------------------------------------
  @negative @delete @sanity @regression
  Scenario Outline: Delete booking with an invalid booking ID
    Given the user login with "<userType>" credentials
    And an invalid booking ID is set
    When the user deletes the booking using the stored booking ID
    Then attempt to update a booking that does not exist
    And the response should state that the requested booking does not exist

    Examples:
      | userType | loginResult | dataset               |
      | valid    | granted     | BOOKING_VALID_DELETE2 |

#----------------------------------------------------------------------------------------------------
# Booking Delete Scenario without token
#----------------------------------------------------------------------------------------------------
  @negative @delete @sanity @regression
  Scenario: Delete booking without auth token
    Given the booking system is available for user access
    And a valid booking ID exists
    When the user sends a DELETE request without the auth token
    Then the API should return an unauthorized status
    And the response should clearly communicate that the request is not authorized

#----------------------------------------------------------------------------------------------------
# Delete booking with expired / invalid token
#----------------------------------------------------------------------------------------------------
  @negative @delete @sanity @regression
    Scenario: Attempt to delete booking with an invalid or expired token
    Given the booking system is available for user access
    And the user login with "valid" credentials
    Then access should be granted
    And the user creates a booking with "BOOKING_VALID_DELETE3"
    Then the booking is successfully created
    Given the user has an invalid or expired token
    When the user deletes the booking using the stored booking ID
    Then the API should return an unauthorized status
    And the error message should indicate unauthorized access
