Feature: Hotel Room Booking Management System – Create Booking

This feature validates the behavior of the Hotel Room Booking system by ensuring that room
reservations can be created, validated, and managed correctly under various conditions.
It covers functional correctness, data validation, error handling, and contract compliance
of the booking API.

  Background:
    Given rooms are available for booking
  #----------------------------------------------------
  #Guest book a room with various room id
  #----------------------------------------------------
  @validation @create
  Scenario Outline: Guest book a room with various room id
    When a guest tries to book a room with "<dataset>"
    Then the booking request should be "<bookingoutcome>"

    Examples:
      | dataset                 | bookingoutcome |
      | ROOMID_VALID            | created        |
      | ROOMID_OUT_OF_RANGE     | rejected       |
      | ROOMID_ZERO             | rejected       |
      | ROOMID_NEGATIVE         | rejected       |
      | ROOMID_ALPHANUMERIC     | rejected       |
      | ROOMID_DECIMAL          | rejected       |
      | ROOMID_SPECIAL_CHARS    | rejected       |

  #----------------------------------------------------
  #Guest book a room with various firstname rules
  # First name length should be 3 ~ 18 charecters
  #----------------------------------------------------
  @validation @create
  Scenario Outline: Guest books a room with various firstname rules
    When a guest tries to book a room with "<dataset>"
    Then the booking request should be "<bookingoutcome>"

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

  #----------------------------------------------------
  #Guest book a room with various lastname rules
# Last name length should be 3 ~ 30 charecters
  #----------------------------------------------------
  @validation @create
  Scenario Outline: Guest book a room with various lastname rules
    When a guest tries to book a room with "<dataset>"
    Then the booking request should be "<bookingoutcome>"

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

 #----------------------------------------------------
 # Guest book a room with various email rules
 #----------------------------------------------------
  @validation @create
  Scenario Outline: Guest book a room with various email rules
    When a guest tries to book a room with "<dataset>"
    Then the booking request should be "<outcome>"

    Examples:
      | dataset                | outcome  |
      | EMAIL_VALID            | created |
      | EMAIL_EMPTY            | rejected |
      | EMAIL_INVALID_FORMAT   | rejected |
      | EMAIL_NO_DOMAIN        | rejected |
      | EMAIL_NO_USERNAME      | rejected |

 #----------------------------------------------------
 # Guest book a room based on stay dates
 #----------------------------------------------------
  @validation @create
  Scenario Outline: Guest book a room based on stay dates
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

 #----------------------------------------------------
 # Guest book a room based on deposit paid
 #----------------------------------------------------
  @validation @create
  Scenario Outline: Guest book a room based on deposit paid
    When a guest tries to book a room with "<dataset>"
    Then the booking request should be "<bookingoutcome>"

    Examples:
      | dataset             | bookingoutcome |
      | DEPOSIT_TRUE        | created        |
      | DEPOSIT_FALSE       | created        |
      | DEPOSIT_MISSING     | rejected       |
      | DEPOSIT_STRING      | rejected       |
      | DEPOSIT_NUMBER      | rejected       |
      | DEPOSIT_INVALID     | rejected       |

 #----------------------------------------------------
 # Guest book a room with various phone number
 #----------------------------------------------------
  @validation @create
  Scenario Outline: Guest book a room with various phone number
    When a guest tries to book a room with "<dataset>"
    Then the booking request should be "<bookingoutcome>"

    Examples:
      | dataset                    | bookingoutcome |
      | PHONE_VALID_MIN_LENGTH_11  | created        |
      | PHONE_VALID_MAX_LENGTH_21  | created        |
      | PHONE_TOO_SHORT            | rejected       |
      | PHONE_TOO_LONG             | rejected       |
      | PHONE_EMPTY                | rejected       |
      # | PHONE_ALPHANUMERIC       | rejected       | This case will be validated in the front end
      # | PHONE_SPECIAL_CHARS      | rejected       | This case will be validated in the front end

 # ------------------------------------------------------------------
 # Booking Creation – Success Flow - Response Data,Schema Validation
 # -------------------------------------------------------------------
@positive @sanity @create
  Scenario Outline: A guest book a room successfully and receives a valid booking schema details from system.
    When a guest tries to book a room with "<dataset>"
    Then the room reservation is confirmed
    And the booking details are returned correctly

    Examples:
      | dataset                    |
      | SCHEMA_VALIDATION          |

 # ------------------------------------------------------------------
 # Booking Creation – Validate all the response data
 # -------------------------------------------------------------------
  @positive @sanity @create
  Scenario Outline: A guest books a room successfully and receives a valid booking  details from system.
    When a guest tries to book a room with "<dataset>"
    Then the room reservation is confirmed
    And a booking reference is generated
    And the booking details returned should be correct

    Examples:
      | dataset        |
      | BOOKING_VALID  |

  #--------------------------------------------------------------------
  # Booking API - Response data structure validation with Swagger Response
  # --------------------------------------------------------------------
@positive @sanity @create
  Scenario Outline: Validate booking creation response against Swagger contract
    Given login with valid credentials
    When a guest creates a booking with "<dataset>"
    Then the response should follow the Swagger booking contract

    Examples:
      | dataset              |
      | SWAGGER_VALIDATION   |