package com.booking.utils.excel.factory;

import com.booking.models.booking.BookingRequest;

public class BookingRequestFactory {

    private BookingRequestFactory() {
        // utility class
    }
    public static BookingRequest createFromExcel(String dataset) {
        return BookingRequestBuilder.build(dataset);
    }
}
