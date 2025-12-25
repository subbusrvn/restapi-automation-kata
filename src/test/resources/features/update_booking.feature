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
      | dataset               | bookingoutcome |roomid | firstname     | lastname     | depositpaid | checkin    | checkout   | email               |  phone      |
      | BOOKING_VALID_UPDATE1 | created        |5003   | Samyuktha    | Sarvanan     | true        | 2026-01-10 | 2026-01-15 | sam@gmail.com       |  919710288425 |

#-----------------------------------------------------------------------------------------------------
#Update With Partial Valid Data with PUT Update method
#-----------------------------------------------------------------------------------------------------
  @update @negative
  Scenario Outline: Update with partial mandatory data with PUT Update method
    Given a guest tries to book a room with "<dataset>"
    Then the booking request should be "<bookingoutcome>"
    And an existing booking is created

    When Update the booking with the following fields
      | firstname   | lastname   | depositpaid | email         | phone      | bookingdates.checkin     | bookingdates.checkout    | roomid |
      | <firstname> | <lastname> | <depositpaid> | <email>     | <phone>    | <checkin>                | <checkout>               | <roomid>                 |
    Then the booking should not be updated

    Examples:
      | dataset               | bookingoutcome |roomid | firstname     | lastname         | depositpaid | checkin    | checkout   | email            |  phone      |
      | BOOKING_VALID_UPDATE2 | created        |       |               | Sarvanan3333      | true       |            |            | sam@gmail.com    |  919710288425 |

#-----------------------------------------------------------------------------------------------------
#Update only allowed columns data with PATCH Update method
#-----------------------------------------------------------------------------------------------------
  @update @negative
  Scenario Outline: Update only allowed columns data with PATCH Update method
    Given a guest tries to book a room with "<dataset>"
    Then the booking request should be "<bookingoutcome>"
    And an existing booking is created

    When Update the booking with the following fields
      | firstname   | lastname   | depositpaid |
      | <firstname> | <lastname> | <depositpaid> |
    Then the API should return a successful update response

    Examples:
      | dataset               | bookingoutcome |  lastname            | depositpaid  | firstname         |
      | BOOKING_VALID_UPDATE3 | created        |  Sarvanan_patch      | true         | Samyuktha_patch   |
#-----------------------------------------------------------------------------------------------------
#Update With Invalid Field Values : Booking Created, Get the Created booking details and Updated the booking details
#-----------------------------------------------------------------------------------------------------
  @update @negative
  Scenario Outline: Update With Invalid Field Values
    Given a guest tries to book a room with "<dataset>"
    Then the booking request should be "<bookingoutcome>"
    And an existing booking is created

       #  Invalid date format
       #  Negative room Id
       #  Mail ID without domain

    When Update the booking with the following fields
      | firstname   | lastname   | depositpaid | email         | phone      | bookingdates.checkin     | bookingdates.checkout              | roomid                 |
      | <firstname> | <lastname> | <depositpaid> | <email>     | <phone>    | <checkin>                | <checkout>                         | <roomid>               |

    Then the booking should not be updated

    Examples:
      | dataset               | bookingoutcome |roomid  | firstname     | lastname         | depositpaid  | checkin     | checkout     | email                  |  phone               |
      | BOOKING_VALID_UPDATE4 | created        |-5003   | Samyuktha     | SarvananSubbu     | true        | 202601-10   | 2026-0115    | sam@gmailcom           |  919710288425        |

#-----------------------------------------------------------------------------------------------------
#Update Invalid  authorization
#-----------------------------------------------------------------------------------------------------
  @update @negative
  Scenario Outline: Update Invalid  authorization
    Given a guest tries to book a room with "<dataset>"
    Then the booking request should be "<bookingoutcome>"
    And an existing booking is created

    When use an invalid authentication token
    And Update the booking with the following fields
      | firstname   | lastname   | depositpaid   | email         | phone      | bookingdates.checkin     | bookingdates.checkout              | roomid                 |
      | <firstname> | <lastname> | <depositpaid> | <email>       | <phone>    | <checkin>                | <checkout>                         | <roomid>               |

    Then the system should reject the request due to invalid or missing authorization
    And The response body should indicate "Unauthorized"

    Examples:
      | dataset               | bookingoutcome |roomid  | firstname     | lastname     | depositpaid | checkin     | checkout     | email                 |  phone               |
      | BOOKING_VALID_UPDATE5 | created        |5003   | Samyuktha     | Sarvanan     | true        | 2026-01-10  | 2026-01-15   | sam@gmailcom         |  919710288425         |

#-----------------------------------------------------------------------------------------------------
#Update operation with expired token
#-----------------------------------------------------------------------------------------------------
  @update @negative
  Scenario Outline: Update operation with exired authorization
    Given a guest tries to book a room with "<dataset>"
    Then the booking request should be "<bookingoutcome>"
    And an existing booking is created

    When use an expired authentication token
    And Update the booking with the following fields
      | firstname   | lastname   | depositpaid   | email         | phone      | bookingdates.checkin     | bookingdates.checkout              | roomid                 |
      | <firstname> | <lastname> | <depositpaid> | <email>       | <phone>    | <checkin>                | <checkout>                         | <roomid>               |

    Then the system should reject the request due to invalid or missing authorization
    And The response body should indicate "Unauthorized"

    Examples:
      | dataset               | bookingoutcome |roomid  | firstname     | lastname     | depositpaid | checkin     | checkout     | email                 |  phone              |
      | BOOKING_VALID_UPDATE6 | created        |5003   | Samyuktha     | Sarvanan     | true        | 2026-01-10  | 2026-01-15   | sam@gmailcom         |  919710288425         |

#-----------------------------------------------------------------------------------------------------
#Update operation with no token
#-----------------------------------------------------------------------------------------------------
  @update @negative
  Scenario Outline: Update operation with no token
    Given a guest tries to book a room with "<dataset>"
    Then the booking request should be "<bookingoutcome>"
    And an existing booking is created

    When use without authentication token
    And Update the booking with the following fields
      | firstname   | lastname   | depositpaid   | email         | phone      | bookingdates.checkin     | bookingdates.checkout              | roomid                 |
      | <firstname> | <lastname> | <depositpaid> | <email>       | <phone>    | <checkin>                | <checkout>                         | <roomid>               |

    Then the system should reject the request due to invalid or missing authorization
    And The response body should indicate "Unauthorized"

    Examples:
      | dataset               | bookingoutcome |roomid  | firstname     | lastname     | depositpaid | checkin     | checkout     | email                 |  phone              |
      | BOOKING_VALID_UPDATE7 | created        |5003   | Samyuktha     | Sarvanan     | true        | 2026-01-10  | 2026-01-15   | sam@gmailcom         |  919710288425         |

#-----------------------------------------------------------------------------------------------------
#Update operation for non-existing booking
#-----------------------------------------------------------------------------------------------------
  @update @negative
  Scenario Outline: Ensure update fails gracefully when booking ID does not exist
    Given a guest tries to book a room with "<dataset>"
    Then the booking request should be "<bookingoutcome>"
    And an existing booking is created

    When Set the non-existing booking id to current booking
    And Update the booking with the following fields
      | firstname   | lastname   | depositpaid   | email         | phone      | bookingdates.checkin     | bookingdates.checkout              | roomid                 |
      | <firstname> | <lastname> | <depositpaid> | <email>       | <phone>    | <checkin>                | <checkout>                         | <roomid>               |

    Then Ensure update fails gracefully when booking ID does not exist

    Examples:
      | dataset               | bookingoutcome |roomid  | firstname     | lastname     | depositpaid | checkin     | checkout     | email                 |  phone              |
      | BOOKING_VALID_UPDATE8 | created        |5003   | Samyuktha     | Sarvanan     | true        | 2026-01-10  | 2026-01-15   | sam@gmailcom         |  919710288425         |
    #-----------------------------------------------------------------------------------------------------