/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c5;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class DatabaseReader
{
    public static final String TRAINING_FILE_NAME = "C:\\Users\\feby\\Desktop\\C5\\src\\c5\\DATASET.xlsx";
    public static final String TESTING_FILE_NAME = "C:\\Users\\feby\\Desktop\\C5\\src\\c5\\Data Testing.xlsx";
    public static final String DATA_FILE_NAME = "C:\\Users\\feby\\Desktop\\C5\\src\\c5\\DATA.xlsx";
    private static LinkedList<LinkedList<Object>> temp = new LinkedList<LinkedList<Object>>();
    
    public static Object[] getTableHeader() {
        return temp.getFirst().toArray();
    }
    
    public static Object[][] getTable(String fileName) {        
        Object[][] table = null;
        temp.clear();
        try {
            FileInputStream excelFile = new FileInputStream(
                    new File(fileName));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(1);
            Iterator<Row> iterator = sheet.iterator();
            
            while (iterator.hasNext()) {                
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                temp.add(new LinkedList<Object>());
                
                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        temp.getLast().add(currentCell.getStringCellValue());
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        temp.getLast().add(currentCell.getNumericCellValue());
                    }
                }
            }
            temp.removeLast();temp.removeLast();temp.removeLast();
            table = new Object[temp.size()-1][temp.getFirst().size()];
            for (int i = 0; i < temp.size()-1; i++)
                for (int j = 0; j < temp.getFirst().size(); j++)
                    table[i] = temp.get(i+1).toArray();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }
}
