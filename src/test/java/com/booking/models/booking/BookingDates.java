package com.booking.models.booking;

public class BookingDates {

    private String checkin;
    private String checkout;

    @SuppressWarnings("unused")
    public BookingDates() {}

    public BookingDates(String checkin, String checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
    }

    public String getCheckin() {

        return checkin;
    }
    @SuppressWarnings("unused")
    public void setCheckin(String checkin) {

        this.checkin = checkin;
    }

    public String getCheckout() {

        return checkout;
    }
    @SuppressWarnings("unused")
    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }
}
