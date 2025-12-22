Feature: Hotel Room Booking Management

  Scenario Outline: Validate booking with firstname rules
    Given rooms are available for booking
    When a guest submits booking request using "<dataset>"
    Then booking should be "<bookingoutcome>"

    Examples:
      | dataset               | bookingoutcome   |
      | FIRSTNAME_VALID       | created          |
      | FIRSTNAME_LT_MIN      | rejected         |
      | FIRSTNAME_EQ_MIN      | created          |
      | FIRSTNAME_GT_MAX      | rejected         |
      | FIRSTNAME_EQ_MAX      | created         |


