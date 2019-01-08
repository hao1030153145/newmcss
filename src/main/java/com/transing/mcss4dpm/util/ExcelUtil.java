package com.transing.mcss4dpm.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * 包: com.transing.crawl.util
 * 源文件:ExcelUtil.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2017年06月30日
 */
public class ExcelUtil {

    public static List read2003Data(List<HSSFRow> list) {
        List<String> resultList = new ArrayList<String>();
        for (int rows = 0; rows < list.size(); rows++) {
            HSSFRow row = list.get(rows);
            HSSFCell keywordCell = row.getCell(0);
            String keyword = ExcelUtil.getHssfValue(keywordCell);
            resultList.add(keyword);
        }
        return resultList;
    }

    public static List read2007Data(List<XSSFRow> list) {
        List<String> resultList = new ArrayList<String>();
        for (int rows = 0; rows < list.size(); rows++) {
            XSSFRow xssfRow = list.get(rows);
            XSSFCell keywordXSSCell = xssfRow.getCell(0);
            String keyword = ExcelUtil.getXssfValue(keywordXSSCell);
            resultList.add(keyword);
        }
        return resultList;
    }

    public static List readExcelInfos(File file)
            throws Exception {
        InputStream is = null;
        Workbook workbook = null;

        try {
            is = new FileInputStream(file);
            workbook = new XSSFWorkbook(is);//07版excel
        } catch (Exception e) {
            is = new FileInputStream(file);//is流已经被关闭，需要重新打开
            workbook = new HSSFWorkbook(is);//03版excel
        }
        try {
            if (workbook instanceof XSSFWorkbook) {
                return batchInsert2007Data(workbook);
            } else {
                return batchInsert2003Data(workbook);
            }
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (!(is == null)) {
                is.close();
            }
            file.delete();
        }
    }

    private static List batchInsert2007Data(Workbook workbook) {
        XSSFWorkbook xssfWorkbook = (XSSFWorkbook) workbook;
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

        List<XSSFRow> xssfRows = new ArrayList<XSSFRow>();
        for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
            XSSFRow xssfRow = xssfSheet.getRow(rowNum);
            if (xssfRow != null) {
                xssfRows.add(xssfRow);
            }
        }
        return read2007Data(xssfRows);
    }

    private static List batchInsert2003Data(Workbook workbook) {
        HSSFWorkbook hssfWorkbook = (HSSFWorkbook) workbook;
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

        List<HSSFRow> hssfRows = new ArrayList<HSSFRow>();
        for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
            HSSFRow hssfRow = hssfSheet.getRow(rowNum);
            if (hssfRow != null) {
                hssfRows.add(hssfRow);
            }
        }
        return read2003Data(hssfRows);
    }

    public static String getXssfValue(XSSFCell xssfRow) {
        if (xssfRow == null) {
            return null;
        }
        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue()).trim();
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
            String result = String.valueOf(xssfRow.getNumericCellValue()).trim();
            if (result.endsWith(".0")) {
                return result.substring(0, result.length() - 2);
            } else if (result.indexOf(".") > 0 && result.indexOf("E") > 0) {
                BigDecimal bd = new BigDecimal(result);
                result = bd.toPlainString();
            }
            return result;
        } else {
            return String.valueOf(xssfRow.getStringCellValue()).trim();
        }
    }

    public static String getHssfValue(HSSFCell hssfRow) {
        if (hssfRow == null) {
            return null;
        }
        if (hssfRow.getCellType() == hssfRow.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfRow.getBooleanCellValue()).trim();
        } else if (hssfRow.getCellType() == hssfRow.CELL_TYPE_NUMERIC) {
            String result = String.valueOf(hssfRow.getNumericCellValue()).trim();
            if (result.endsWith(".0")) {
                return result.substring(0, result.length() - 2);
            } else if (result.indexOf(".") > 0 && result.indexOf("E") > 0) {
                BigDecimal bd = new BigDecimal(result);
                result = bd.toPlainString();
            }
            return result;
        } else {
            return String.valueOf(hssfRow.getStringCellValue()).trim();
        }
    }


    public static File getTemplateFile(String fileName) {
        String path = ExcelUtil.class.getClassLoader().getResource("").getPath() + "template" + File.separator
                + fileName;
        return new File(path);
    }


}
