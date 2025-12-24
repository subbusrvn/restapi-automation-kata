package com.booking.utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;


public class ExcelUtility {

    private ExcelUtility() {
    }

    public static int getRowcount(String excelPath, String sheetName) {
        try (FileInputStream fi = new FileInputStream(excelPath);
             XSSFWorkbook wb = new XSSFWorkbook(fi)) {

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

        try (FileInputStream fi = new FileInputStream(excelPath);
             XSSFWorkbook wb = new XSSFWorkbook(fi)) {

            XSSFRow row = wb.getSheet(sheetName).getRow(rownum);
            if (row == null) return "";

            XSSFCell cell = row.getCell(cellnum);
            if (cell == null) return "";

            return new DataFormatter().formatCellValue(cell);

        } catch (Exception e) {
            return "";
        }
    }
}
