Feature: Hotel Room Booking Management System – End to End Booking

  As a user
  I want to manage hotel bookings
  So that I can create, retrieve, update, and delete bookings securely

  # ------------------------------------------------------------------
  # Booking API - End-to-End Flow --> Create - Read - Update - Delete
  # -------------------------------------------------------------------
  @E2E @sanity @smoke @regression
  Scenario Outline: Guest performs full booking lifecycle
  Given login with valid credentials
  #When a guest creates a booking with "<dataset>"
  #Then the room reservation is confirmed
    When a guest tries to book a room with "<dataset>"
    Then the booking reservation should be"<bookingoutcome>"
  And a booking reference is generated
  And the booking details returned should be correct

  And a booking exists
  When the guest retrieves the booking by ID
  When the guest updates the booking with "<updateDataset>"
  Then the booking update should be successful

  When the guest retrieves the booking by ID
  Then the booking details should reflect the updates

  When the guest deletes the booking
  Then the booking should be successfully deleted

  Examples:
  | dataset        | bookingoutcome | updateDataset  |
  | BOOKING_E2E    | created        | BOOKING_UPDATE |