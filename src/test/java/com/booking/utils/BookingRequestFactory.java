package com.booking.utils;

import com.booking.models.booking.BookingDates;
import com.booking.models.booking.BookingRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ThreadLocalRandom;

public class BookingRequestFactory {

    private static final String EXCEL_PATH = "src/test/resources/testData/Booking_Creation_DataSet.xlsx";

    private static final String SHEET_NAME = "Booking";

    public static BookingRequest createFromExcel(String dataset) {

        int rowCount = ExcelUtility.getRowcount(EXCEL_PATH, SHEET_NAME);

        for (int i = 1; i <= rowCount; i++) {

            String datasetName = ExcelUtility.getCellData(EXCEL_PATH, SHEET_NAME, i, 0);

            if (datasetName.equalsIgnoreCase(dataset)) {

                BookingRequest request = new BookingRequest();

                //request.setRoomid(ExcelUtility.getCellData(EXCEL_PATH, SHEET_NAME, i, 1));

                String roomIdValue =
                        ExcelUtility.getCellData(EXCEL_PATH, SHEET_NAME, i, 1);

                if (roomIdValue == null || roomIdValue.trim().isEmpty()) {

                    long roomIdLong =
                            ((System.currentTimeMillis() % 900_000_000L) + 100_000_000L)
                                    + ThreadLocalRandom.current().nextInt(0, 100);

                    roomIdValue = String.valueOf(roomIdLong);

                    // Optional write-back (for visibility)
                    ExcelUtility.setCellData(
                            EXCEL_PATH,
                            SHEET_NAME,
                            i,
                            1,
                            roomIdValue
                    );
                }

                request.setRoomid(roomIdValue);


                request.setFirstname(ExcelUtility.getCellData(EXCEL_PATH, SHEET_NAME, i, 2));

                request.setLastname(ExcelUtility.getCellData(EXCEL_PATH, SHEET_NAME, i, 3));

                request.setDepositpaid(Boolean.parseBoolean(ExcelUtility.getCellData(EXCEL_PATH, SHEET_NAME, i, 4)));

                request.setBookingdates(new BookingDates(ExcelUtility.getCellData(EXCEL_PATH, SHEET_NAME, i, 5), ExcelUtility.getCellData(EXCEL_PATH, SHEET_NAME, i, 6)));

                request.setEmail(ExcelUtility.getCellData(EXCEL_PATH, SHEET_NAME, i, 7));

                request.setPhone(ExcelUtility.getCellData(EXCEL_PATH, SHEET_NAME, i, 8));

                return request;
            }
        }

        throw new IllegalArgumentException("Dataset '" + dataset + "' not found in Excel file: '" + EXCEL_PATH + "', sheet: '" + SHEET_NAME + "'");
    }
}
