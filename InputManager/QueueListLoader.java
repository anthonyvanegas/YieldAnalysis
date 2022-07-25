package com.company.InputManager;

import com.company.OutputManager.WBManager;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;

public class QueueListLoader {

    LinkedList<ProductionEntry> queueList;

    public QueueListLoader(File[] listOfFiles) throws IOException{
        queueList = new LinkedList<>();
        loadList(listOfFiles);
        orderQueueList();
        writeWorkBook();
    }

    public LinkedList<ProductionEntry> getQueueList() {
        return queueList;
    }

    private void loadList(File[] listOfFiles) throws IOException {
        EntryListLoader entryListLoader;
        float i = 1;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                entryListLoader = new EntryListLoader(listOfFile.getAbsolutePath());
                System.out.print("Loading file: " + listOfFile.getAbsolutePath() + " "
                                + String.format("%3.1f", ((i/listOfFiles.length)*100))
                                + "%\n");
                addList(entryListLoader.getEntryList());
                i++;
            }
        }
    }

    private void addList(LinkedList<ProductionEntry> e){
        queueList.addAll(e);
    }

    private void orderQueueList(){
        queueList.sort((Comparator<? super ProductionEntry>) (o1, o2) -> {
            Integer x1 = o1.getItemNumber();
            Integer x2 = o2.getItemNumber();
            int itemComp = x1.compareTo(x2);

            if (itemComp != 0) {
                return itemComp;
            }

            Float x3 = o1.getInitalP();
            Float x4 = o2.getInitalP();
            return x3.compareTo(x4);
        });
    }

    private void writeWorkBook() throws IOException {
        WBManager wbManager = new WBManager("C:\\Users\\vanegasa\\IdeaProjects\\YieldManager\\src\\com\\company\\workbook.xls");
        float i = 1;
        for (ProductionEntry productionEntry : queueList) {
            wbManager.addEntry(productionEntry);
            System.out.print("Creating Entry for: " + productionEntry.getItemNumber()
                            + "\n\tFor Julian: " + productionEntry.getJulianDate()
                            + "\n\tProgress: " + String.format("%3.1f", ((i/queueList.size())*100))
                            + "%\n");
            i++;
        }
    }
}
