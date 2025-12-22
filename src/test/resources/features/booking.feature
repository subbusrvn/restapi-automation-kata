Feature: Hotel Room Booking Management

  Scenario Outline: Validate booking with firstname rules
    Given rooms are available for booking
    When a guest tries to book a room with "<dataset>"
    Then the booking request should be "<bookingoutcome>"

# First name length should be 3 ~ 18 charecters
    Examples:
      | dataset               | bookingoutcome   |
      | FIRSTNAME_VALID       | created          |
      | FIRSTNAME_LT_MIN      | rejected         |
      | FIRSTNAME_EQ_MIN      | created          |
      | FIRSTNAME_GT_MAX      | rejected         |
      | FIRSTNAME_EQ_MAX      | created         |
      | FIRSTNAME_EMPTY       | rejected         |
      | FIRSTNAME_PARAM_MISS  | rejected         |
      | FIRSTNAME_SPACES_ONLY | rejected         |
      | FIRSTNAME_NUMERIC     | created         |
      | FIRSTNAME_SPECIAL_CHARS      | created  |
      | FIRSTNAME_ALPHANUMERIC | created         |
      | FIRSTNAME_LEADING_TRAILING_SPACES |created   |

# Last name length should be 3 ~ 30 charecters

  Scenario Outline: Validate booking with lastname rules
    Given rooms are available for booking
    When a guest tries to book a room with "<dataset>"
    Then the booking request should be "<bookingoutcome>"

    # Last name length should be 3 ~ 30 charecters

    Examples:
      | dataset               | bookingoutcome   |
      | LASTNAME_VALID       | created          |
      | LASTNAME_LT_MIN      | rejected         |
      | LASTNAME_EQ_MIN      | created          |
      | LASTNAME_GT_MAX      | rejected         |
      | LASTNAME_EQ_MAX      | created          |
      | LASTNAME_EMPTY       | rejected         |
      | LASTNAME_PARAM_MISS  | rejected         |
      | LASTNAME_SPACES_ONLY | rejected         |
      | LASTNAME_NUMERIC     | created          |
      | LASTNAME_SPECIAL_CHARS  | created       |
      | LASTNAME_ALPHANUMERIC   | created       |
      | LASTNAME_LEADING_TRAILING_SPACES |created   |

  Scenario Outline: Validate booking with email rules
    Given rooms are available for booking
    When a guest tries to book a room with "<dataset>"
    Then the booking request should be "<outcome>"

    Examples:
      | dataset                | outcome  |
      | EMAIL_VALID            | created |
      | EMAIL_EMPTY            | rejected |
      | EMAIL_INVALID_FORMAT   | rejected |
      | EMAIL_NO_DOMAIN        | rejected |
      | EMAIL_NO_USERNAME      | rejected |

  Scenario Outline: Guest books a room based on stay dates
    Given rooms are available for booking
    When a guest tries to book a room with "<dataset>"
    Then the booking request should be "<bookingoutcome>"

    Examples:
      | dataset                            | bookingoutcome       |
      | DATES_VALID_RANGE                  | created              |
      | DATES_CHECKIN_AFTER_CHECKOUT       | rejected             |
      | DATES_SAME_DAY                     | rejected             |
      | DATES_CHECKIN_IN_PAST              | rejected             |
      | DATES_CHECKIN_MISSING              | rejected             |
      | DATES_CHECKOUT_MISSING             | rejected             |
      | DATES_CHECKIN_TOO_FAR_IN_FUTURE    | created              |
      | DATES_CHECKIN_NULL                 | rejected             |
      | DATES_CHECKOUT_NULL                | rejected             |
      | DATES_TOO_LONG_STAY                | created              |