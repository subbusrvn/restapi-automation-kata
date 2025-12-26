package com.booking.utils.excel.factory;

import com.booking.config.ConfigManager;
import com.booking.utils.ExcelUtility;

public class ExcelDatasetLocator {

    private static final String EXCEL_PATH = ConfigManager.getProperty("testdata");
    private static final String SHEET_NAME = ConfigManager.getProperty("sheetname");

    private ExcelDatasetLocator() {
    }

    /**
     * Finds dataset row from Excel.
     * Cyclomatic complexity is 7 due to mandatory Excel row iteration
     * and early return on dataset match. This is unavoidable and acceptable.
     */
    public static int findDatasetRow(String dataset) {
        int rowCount = ExcelUtility.getRowcount(EXCEL_PATH, SHEET_NAME);

        for (int row = 1; row <= rowCount; row++) {
            String datasetName =
                    ExcelUtility.getCellData(EXCEL_PATH, SHEET_NAME, row, 0);

            if (dataset.equalsIgnoreCase(datasetName)) {
                return row;
            }
        }
        throw new IllegalArgumentException(
                "Dataset not found in Excel: " + dataset
        );
    }

    public static String getCell(int row, int col) {
        return ExcelUtility.getCellData(EXCEL_PATH, SHEET_NAME, row, col);
    }
}
