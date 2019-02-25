package example;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sun.text.normalizer.UCharacter;

public class getExcelData {

    private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook;
    private static XSSFCell Cell;
    private static XSSFRow Row;


    public static void main(String [] args){
        //System.out.println("Could not read the Excel sheet");
        String FilePath = "C:\\Alex\\AddUserTCs.xlsx";
        String SheetName = "Admin";
        excelTest(FilePath, SheetName);
        SheetName = "User";
        excelTest(FilePath, SheetName);
        System.out.println("\n Finish test !!! ");
    }

    public static void  excelTest(String FilePath, String SheetName )   {
        //String FilePath = "C:\\Alex\\AddUserTCs.xlsx";
        //String SheetName = "Sheet1";
        String[][] tabArray = null;
        try {
            File excelFile = new File(FilePath);
            FileInputStream fis = new FileInputStream(excelFile);
            // Access the required test data sheet
            ExcelWBook = new XSSFWorkbook(fis);

            ExcelWSheet = ExcelWBook.getSheet(SheetName);
            int startRow = 1;
            int startCol = 0;
            int ci, cj;
            int totalRows = ExcelWSheet.getLastRowNum();
            int totalCols = ExcelWSheet.getRow(0).getLastCellNum();
            // you can write a function as well to get Column count
            //int totalCols = 8;
            tabArray = new String[totalRows][totalCols];
            System.out.printf("Grösse des tabArray %d %d \n ", totalRows, totalCols);
            ci = 0;
            for (int i = startRow; i <= totalRows; i++, ci++) {
                cj = 0;
               // System.out.println();
                for (int j = startCol; j < totalCols; j++, cj++) {
                    Cell = ExcelWSheet.getRow(i).getCell(j);
                    tabArray[ci][cj] = getCellData(i,j);
                    System.out.printf("Writeout Tablearray(%d)(%d) %s \t \n",ci,cj, tabArray[ci][cj]);
                }
            }
            System.out.printf("\nFinished to read Exceldata \n\n ");
        } catch (FileNotFoundException e) {
            System.out.println("Could not read the Excel sheet");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Could not read the Excel sheet");
            e.printStackTrace();
        }

    }

    public static String getCellData(int RowNum, int ColNum)
    {
        try {
            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);

            if (Cell.getCellType() == CellType.BOOLEAN) {
                String s = Boolean.toString(Cell.getBooleanCellValue());
                //System.out.printf("Boolean Cell Type: %s Cell value: %s \n", Cell.getCellType(), Cell.getBooleanCellValue() );
                return Boolean.toString(Cell.getBooleanCellValue());
            }

            else if (Cell.getCellType() == CellType.NUMERIC) {
                //System.out.printf("Boolean Cell Type: %s Cell value: %s \n", Cell.getCellType(), Cell.getBooleanCellValue() );
                return String.valueOf(Cell.getNumericCellValue());

            }
            else {
                String CellData = Cell.getStringCellValue();
                //System.out.printf("Cell Type: %s Cell value %s \n", Cell.getCellType(), CellData);
                return CellData;
            }
        }

        catch(Exception e){
            System.out.println(e.getMessage());
            throw (e);
        }

    }



}
