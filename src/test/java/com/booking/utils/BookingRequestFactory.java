package com.booking.utils;

import com.booking.models.booking.BookingDates;
import com.booking.models.booking.BookingRequest;

import java.io.IOException;

public class BookingRequestFactory {

    private static final String EXCEL_PATH = "src/test/resources/testData/Booking_Creation_DataSet.xlsx";

    private static final String SHEET_NAME = "Booking";

    public static BookingRequest createFromExcel(String dataset) {

        try {
            ExcelUtility excel = new ExcelUtility(EXCEL_PATH);

            int rowCount = ExcelUtility.getRowcount(SHEET_NAME);

            for (int i = 1; i <= rowCount; i++) {

                String datasetName =
                        ExcelUtility.getCellData(SHEET_NAME, i, 0);

                if (datasetName.equalsIgnoreCase(dataset)) {

                    BookingRequest request = new BookingRequest();

                    String roomIdValue = ExcelUtility.getCellData(SHEET_NAME, i, 1);
                    request.setRoomid(roomIdValue);

                    request.setFirstname(
                            ExcelUtility.getCellData(SHEET_NAME, i, 2));

                    request.setLastname(
                            ExcelUtility.getCellData(SHEET_NAME, i, 3));

                    request.setDepositpaid(
                            Boolean.parseBoolean(
                                    ExcelUtility.getCellData(SHEET_NAME, i, 4)));

                    request.setBookingdates(
                            new BookingDates(
                                    ExcelUtility.getCellData(SHEET_NAME, i, 5),
                                    ExcelUtility.getCellData(SHEET_NAME, i, 6)
                            )
                    );

                    request.setEmail(
                            ExcelUtility.getCellData(SHEET_NAME, i, 7));

                    request.setPhone(
                            ExcelUtility.getCellData(SHEET_NAME, i, 8));

                    return request;
                }
            }

            throw new IllegalArgumentException(
                    "Dataset not found in Excel: " + dataset);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to read booking data from Excel", e);
        }
    }
}
