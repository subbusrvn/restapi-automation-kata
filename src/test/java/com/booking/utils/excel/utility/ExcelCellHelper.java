package com.booking.utils.excel.utility;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

final class ExcelCellHelper {

    private ExcelCellHelper() {
    }

    static String read(
            XSSFWorkbook wb,
            String sheetName,
            int rownum,
            int cellnum) {

        XSSFRow row = wb.getSheet(sheetName).getRow(rownum);
        if (row == null) return "";

        XSSFCell cell = row.getCell(cellnum);
        if (cell == null) return "";

        return new DataFormatter().formatCellValue(cell);
    }

    static void write(
            XSSFWorkbook wb,
            String sheetName,
            int rownum,
            int cellnum,
            String data) {

        XSSFSheet sheet = wb.getSheet(sheetName);
        XSSFRow row = sheet.getRow(rownum);
        if (row == null) row = sheet.createRow(rownum);

        XSSFCell cell = row.getCell(cellnum);
        if (cell == null) cell = row.createCell(cellnum);

        cell.setCellValue(data);
    }
}
