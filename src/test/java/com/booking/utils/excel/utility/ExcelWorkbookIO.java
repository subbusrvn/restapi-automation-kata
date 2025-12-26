package com.booking.utils.excel.utility;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;

final class ExcelWorkbookIO {

    private ExcelWorkbookIO() {
    }

    static XSSFWorkbook open(String excelPath) throws Exception {
        try (FileInputStream fi = new FileInputStream(excelPath)) {
            return new XSSFWorkbook(fi);
        }
    }

    static void save(XSSFWorkbook wb, String excelPath) throws Exception {
        try (FileOutputStream fo = new FileOutputStream(excelPath)) {
            wb.write(fo);
        }
    }
}
