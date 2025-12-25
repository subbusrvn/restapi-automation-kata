Feature: Update Existing Booking Details

  As an authenticated hotel staff user
  Wanted to update existing booking details
  So that guest and stay information remains accurate
#-----------------------------------------------------------------------------------------------------
#Happy Path scenario : Booking Created, Get the Created booking details and Updated the booking details
#-----------------------------------------------------------------------------------------------------
  Background:
    Given login with valid credentials

  @update @positive
  Scenario Outline: Successfully update all booking fields
    Given a guest tries to book a room with "<dataset>"
    Then the booking request should be "<bookingoutcome>"
    And an existing booking is created

    When Update the booking with the following fields
     | firstname   | lastname   | depositpaid | email         | phone      | bookingdates.checkin     | bookingdates.checkout    | roomid |
     | <firstname> | <lastname> | <depositpaid> | <email>     | <phone>    | <checkin>                | <checkout>               | <roomid>                 |
    Then the API should return a successful update response
    And the updated booking should reflect all the new values

    Examples:
      | dataset       | bookingoutcome |roomid | firstname     | lastname     | depositpaid | checkin    | checkout   | email               |  phone      |
      | BOOKING_VALID | created        |5003   | Samyuktha    | Sarvanan     | true        | 2026-01-10 | 2026-01-15 | sam@gmail.com       |  919710288425 |
