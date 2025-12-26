package com.booking.utils.excel.factory;

import com.booking.config.ConfigManager;
import com.booking.utils.excel.utility.ExcelUtility;

public class RoomIdResolver {

    private static final String EXCEL_PATH = ConfigManager.getProperty("testdata");
    private static final String SHEET_NAME = ConfigManager.getProperty("sheetname");


    private RoomIdResolver() {
    }

    public static Object resolve(int row) {
        String roomIdStr =
                ExcelUtility.getCellData(EXCEL_PATH, SHEET_NAME, row, 1);

        if (roomIdStr == null || roomIdStr.trim().isEmpty()) {
            return generateAndWriteRoomId(row);
        }
        return roomIdStr.trim();
    }

    private static int generateAndWriteRoomId(int row) {
        int generatedId =
                (int) ((System.currentTimeMillis() % 900_000_000L) + 100_000_000L);

        ExcelUtility.setCellData(
                EXCEL_PATH,
                SHEET_NAME,
                row,
                11,
                String.valueOf(generatedId)
        );
        return generatedId;
    }
}
