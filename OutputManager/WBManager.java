package com.company.OutputManager;

import com.company.Analysis.AnalysisGenerator;
import com.company.InputManager.ProductionEntry;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedList;


public class WBManager {

    private final String fileName;
    private final LinkedList<String> workBookColumnList = new LinkedList<>(Arrays.asList("Julian Date", "Initial Poundage", "Final Poundage", "Percentage Yield"));
    private final LinkedList<String> analysisWBColumnList = new LinkedList<>(Arrays.asList("Item Number", "Item Description", "0-500", "500-1500", "1500-5000", "5000-10000"));
    private final Workbook wb;
    private OutputStream fileOut;

    public WBManager(String fileName) throws IOException{
        this.fileName = fileName;
        wb = new HSSFWorkbook();
        fileOut = new FileOutputStream(fileName);
        wb.write(fileOut);
    }

    public void createAnalysisSheet(AnalysisGenerator gen) throws IOException{
            Sheet sheet;
            Row row;
            Cell cell;
            fileOut = new FileOutputStream(fileName);


            sheet = wb.createSheet("Report");
            row = sheet.createRow(0);

            Font font = wb.createFont();
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);

            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyle.setFont(font);


            for(int i = 0; i < analysisWBColumnList.size(); i++){
                cell = row.createCell(i);
                cell.setCellValue(analysisWBColumnList.get(i));
                cell.setCellStyle(cellStyle);
                sheet.autoSizeColumn(i);
            }

            for(int i = 0; i < gen.getIndexList().size(); i++){
                row = sheet.createRow(i+1);

                cell = row.createCell(0);
                cell.setCellValue(gen.getIndexList().get(i));

                cell = row.createCell(1);
                cell.setCellValue(gen.getDescriptionList().get(i));

                cell = row.createCell(2);
                cell.setCellValue(gen.getAverages(AnalysisGenerator.averageOption.o500, gen.getIndexList().get(i)));

                cell = row.createCell(3);
                cell.setCellValue(gen.getAverages(AnalysisGenerator.averageOption.o1500, gen.getIndexList().get(i)));

                cell = row.createCell(4);
                cell.setCellValue(gen.getAverages(AnalysisGenerator.averageOption.o5000, gen.getIndexList().get(i)));

                cell = row.createCell(5);
                cell.setCellValue(gen.getAverages(AnalysisGenerator.averageOption.o10000, gen.getIndexList().get(i)));
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            wb.write(fileOut);
    }


    public void addEntry(ProductionEntry entry) throws IOException{
        Sheet sheet;
        Row row;
        Cell cell;
        int itemNumber = entry.getItemNumber();

        if(!sheetExist(itemNumber)){
            System.out.print("Creating Sheet: " + itemNumber);
            createItemSheet(itemNumber);
        }

        fileOut = new FileOutputStream(fileName);

        sheet = wb.getSheet(String.valueOf(itemNumber));
        row = sheet.createRow(sheet.getLastRowNum()+1);

        cell = row.createCell(0);
        cell.setCellValue(entry.getJulianDate());
        sheet.autoSizeColumn(0);

        cell = row.createCell(1);
        cell.setCellValue(entry.getInitalP());
        sheet.autoSizeColumn(1);

        cell = row.createCell(2);
        cell.setCellValue(entry.getFinalP());
        sheet.autoSizeColumn(2);

        cell = row.createCell(3);
        cell.setCellValue(entry.getYieldPercentage());
        sheet.autoSizeColumn(3);

        wb.write(fileOut);

    }

    private boolean sheetExist(int itemNumber){
        return wb.getSheet(String.valueOf(itemNumber)) != null;
    }

    private void createItemSheet(int itemNumber) throws IOException{
        Sheet sheet;
        Row row;
        Cell cell;
        fileOut = new FileOutputStream(fileName);

        wb.createSheet(String.valueOf(itemNumber));
        sheet = wb.getSheet(String.valueOf(itemNumber));
        row = sheet.createRow(0);

        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setFont(font);


        for(int i = 0; i < workBookColumnList.size(); i++){
            cell = row.createCell(i);
            cell.setCellValue(workBookColumnList.get(i));
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(i);
        }
        wb.write(fileOut);
    }
}
