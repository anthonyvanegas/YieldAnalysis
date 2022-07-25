package com.company.InputManager;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;

public class EntryListLoader {

    final private String fileName;
    private Sheet sheet;
    private FileInputStream fis;
    private LinkedList<ProductionEntry> entryArray;

    public EntryListLoader(String fileName) throws IOException{
        this.fileName = fileName;
        loadSheet();
        loadArray();
        fis.close();
    }

    public LinkedList<ProductionEntry> getEntryList() {
        return entryArray;
    }

    private void loadSheet() throws IOException {
        Workbook workBook;

        //Create input stream from xis file
        fis = new FileInputStream(fileName);
        //Create Workbook instance for xis file input stream
        workBook = new HSSFWorkbook(fis);
        //Get instance of cell needed and print out contents
        sheet = workBook.getSheet("printsheet");

    }

    private void loadArray() {
        int julianNumber;
        String itemDescription;
        int itemNumber;
        float initalP;
        float finalP;
        float pYield;
        ProductionEntry entry;
        entryArray = new LinkedList<>();

        //Get Julian Date
        Row row = sheet.getRow(2);
        Cell cell = row.getCell(2);
        julianNumber = Integer.parseInt(cell.getStringCellValue());

        {
            for (int i = 4; i < sheet.getLastRowNum(); i++){


                row = sheet.getRow(i);

                cell = row.getCell(1); if (cell == null) continue;
                else if (cell.getCellType() == 0)
                    itemNumber = (int)cell.getNumericCellValue();
                else if (cell.getCellType() == Cell.CELL_TYPE_STRING
                        && cell.getStringCellValue().equals("XXXX")) {
                    cell = row.getCell(5); if (cell == null) continue;
                    else if (cell.getStringCellValue().equals("==========="));      //This checked for a certain row that shared a similar product name with another
                    itemNumber = 0;
                }
                else
                    continue;
                cell = row.getCell(5); if (cell == null) continue;
                else if (cell.getCellType() == Cell.CELL_TYPE_STRING)
                    itemDescription = cell.getStringCellValue();
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

                pYield = (initalP-finalP)/initalP;

                if(!(pYield >= 0 && pYield <= 0.05))
                    continue;
                entry = new ProductionEntry(itemNumber,
                        itemDescription,
                        julianNumber,
                        initalP,
                        finalP);
                entryArray.add(entry);
            }
        }
    }
}

