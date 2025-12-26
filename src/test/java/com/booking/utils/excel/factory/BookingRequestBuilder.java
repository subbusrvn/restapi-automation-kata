package com.booking.utils.excel.factory;

import com.booking.models.booking.BookingDates;
import com.booking.models.booking.BookingRequest;

public class BookingRequestBuilder {

    private BookingRequestBuilder() {
    }

    public static BookingRequest build(String dataset) {
        int row = ExcelDatasetLocator.findDatasetRow(dataset);

        BookingRequest request = new BookingRequest();
        request.setRoomid(RoomIdResolver.resolve(row));
        request.setFirstname(ExcelDatasetLocator.getCell(row, 2));
        request.setLastname(ExcelDatasetLocator.getCell(row, 3));
        request.setDepositpaid(
                Boolean.parseBoolean(ExcelDatasetLocator.getCell(row, 4))
        );
        request.setBookingdates(
                new BookingDates(
                        ExcelDatasetLocator.getCell(row, 5),
                        ExcelDatasetLocator.getCell(row, 6)
                )
        );
        request.setEmail(ExcelDatasetLocator.getCell(row, 7));
        request.setPhone(ExcelDatasetLocator.getCell(row, 8));

        return request;
    }
}
