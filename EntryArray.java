package com.company;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.IOException;

public class EntryArray {

    private String fileName = "";
    private Sheet sheet;

    public EntryArray(String fileName) throws IOException{
        this.fileName = fileName;
        System.out.println(fileName);
        loadSheet();
        loadArray();
    }

    private void loadSheet() {

        Workbook workBook;

        try {
            //Create input stream from xis file
            FileInputStream fis = new FileInputStream(fileName);

            //Create Workbook instance for xis file input stream
            workBook = new HSSFWorkbook(fis);

            //Get instance of cell needed and print out contents
            sheet = workBook.getSheet("printsheet");

            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadArray() throws IOException{
        int julianNumber;
        String itemNumber = "";
        float initalP = 0;
        float finalP = 0;
        ProductionEntry entry;

        //Get Julian Date
        Row row = sheet.getRow(2);
        Cell cell = row.getCell(2);
        julianNumber = Integer.parseInt(cell.getStringCellValue());
        if(julianNumber < 0 || julianNumber > 365) //Verify Date
            throw new IOException("Julian Number Outside of Limits");

        {
            for (int i = 4; i < sheet.getLastRowNum(); i++){
                row = sheet.getRow(i);

                cell = row.getCell(1); if (cell == null) continue;
                else if (cell.getCellType() == 0)
                    itemNumber = Integer.toString((int)cell.getNumericCellValue());
                else
                    continue;

                cell = row.getCell(2); if (cell == null) continue;
                else if (cell.getCellType() == 1)
                    itemNumber += cell.getStringCellValue();
                else
                    continue;

                cell = row.getCell(30); if (cell == null) continue;
                else if (cell.getCellType() == 2 && cell.getNumericCellValue() != 0)
                    initalP = (float)cell.getNumericCellValue();
                else
                    continue;

                cell = row.getCell(28); if (cell == null) continue;
                else if (cell.getCellType() == 2 && cell.getNumericCellValue() != 0)
                    finalP = (float)cell.getNumericCellValue();
                else
                    continue;

                entry = new ProductionEntry(itemNumber, julianNumber,
                                            initalP, finalP);
                System.out.println(entry.toString() + "\n Percentage Yield: " + (1-entry.getYieldPercentage())*100 + "%");

                itemNumber = "";
                initalP = 0;
                finalP = 0;
            }
        }

    }

}
