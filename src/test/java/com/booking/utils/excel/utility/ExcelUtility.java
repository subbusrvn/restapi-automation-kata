package com.booking.utils.excel.utility;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class ExcelUtility {

    private ExcelUtility() {
    }

    public static int getRowcount(String excelPath, String sheetName) {
        try {
            XSSFWorkbook wb = ExcelWorkbookIO.open(excelPath);
            return wb.getSheet(sheetName).getLastRowNum();
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getCellData(
            String excelPath,
            String sheetName,
            int rownum,
            int cellnum) {

        try {
            XSSFWorkbook wb = ExcelWorkbookIO.open(excelPath);
            return ExcelCellHelper.read(wb, sheetName, rownum, cellnum);
        } catch (Exception e) {
            return "";
        }
    }

    public static void setCellData(
            String excelPath,
            String sheetName,
            int rownum,
            int cellnum,
            String data) {

        try {
            XSSFWorkbook wb = ExcelWorkbookIO.open(excelPath);
            ExcelCellHelper.write(wb, sheetName, rownum, cellnum, data);
            ExcelWorkbookIO.save(wb, excelPath);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to write data to Excel file", e);
        }
    }
}
