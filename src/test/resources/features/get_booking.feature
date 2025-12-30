Feature: Hotel Room Booking Management System – GET Endpoints
  As a hotel booking system user
  Want to retrieve booking details using GET APIs
  So that can view booking information accurately
#-----------------------------------------------------------------------------------------------------
#Happy Path scenario : Create room booking and get the booked id with user access
#-----------------------------------------------------------------------------------------------------
  Background:
    Given rooms are available for booking

  @get @positive @sanity @smoke @regression
  Scenario Outline: Get the booked id with user access
    When a guest tries to book a room with "<dataset>"
    Then the booking reservation should be"<bookingoutcome>"

    When Update the invalid authentication token to the created bookingid
    When the guest retrieves the booking by ID
    Then the booking details should match the created booking

    Examples:
      | dataset               | bookingoutcome |
      | BOOKING_VALID_GET1    | created        |

#-----------------------------------------------------------------------------------------------------
#Negative cases : Create room booking and get the booked id without user access(No token access)
#-----------------------------------------------------------------------------------------------------
  @get @negative @regression
  Scenario Outline: Get the booked id with user access
    When a guest tries to book a room with "<dataset>"
    Then the booking reservation should be"<bookingoutcome>"
    When Set the invalid authentication token to the created bookingid
    When the guest retrieves the booking by ID
    Then the booking retrieval should be unauthorized
    #Then the booking details should match the created booking

    Examples:
      | dataset               |bookingoutcome |
      | BOOKING_VALID_GET2    | created        |
#-----------------------------------------------------------------------------------------------------
#Negative cases : Get the non existing booking id
#-----------------------------------------------------------------------------------------------------
  @get @negative @regression
  Scenario Outline: Get the non existing booked id
    When a guest tries to book a room with "<dataset>"
    Then the booking reservation should be"<bookingoutcome>"
    When Set the non-existing booking id to current booking
    When the guest retrieves the booking by ID
    Then the booking retrieval should be unauthorized

    Examples:
      | dataset               |bookingoutcome |
      | BOOKING_VALID_GET3    | created        |