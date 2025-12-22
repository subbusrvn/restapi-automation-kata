Feature: Hotel Room Booking Management

  Scenario Outline: Validate booking with firstname rules
    Given rooms are available for booking
    When a guest submits booking request using "<dataset>"
    Then booking should be "<bookingoutcome>"
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
    When a guest submits booking request using "<dataset>"
    Then booking should be "<bookingoutcome>"

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
