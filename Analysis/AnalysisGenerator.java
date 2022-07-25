package com.company.Analysis;

import com.company.InputManager.ProductionEntry;

import java.util.LinkedList;

public class AnalysisGenerator {
    public enum averageOption{
        o500, o1500, o5000, o10000
    }
    LinkedList<LinkedList<ProductionEntry>> itemList;
    LinkedList<Integer> indexList;
    LinkedList<String> descriptionList;

    public AnalysisGenerator(LinkedList<ProductionEntry> queueList){
        itemList = new LinkedList<>();
        indexList = new LinkedList<>();
        descriptionList = new LinkedList<>();
        formatItemList(queueList);
    }

    public LinkedList<Integer> getIndexList() {
        return indexList;
    }
    public LinkedList<String> getDescriptionList() {
        return descriptionList;
    }

    private void formatItemList(LinkedList<ProductionEntry> queueList){
        int placeHolder = 0;
        int itemNumber;

        for (int i = 0; i < queueList.size(); i++) {
            itemNumber = queueList.get(i).getItemNumber();
            if(placeHolder == itemNumber){
                itemList.getLast().add(queueList.get(i));
            }else{
                itemList.add(new LinkedList<>());
                itemList.getLast().add(queueList.get(i));
                indexList.add(itemNumber);
                descriptionList.add(queueList.get(i).getItemDescription());
                placeHolder = itemNumber;
            }
        }
    }

    public float getAverages(averageOption option, int itemNumber){
        float begSet = 0;
        float endSet = 0;
        float sum = 0;
        int count = 0;

        switch (option) {
            case o500 -> {
                begSet = 0;
                endSet = 500;
            }
            case o1500 -> {
                begSet = 500;
                endSet = 1500;
            }
            case o5000 -> {
                begSet = 1500;
                endSet = 5000;
            }
            case o10000 -> {
                begSet = 10000;
                endSet = 100000;
            }
        }

        for (ProductionEntry e:
                itemList.get(indexList.indexOf(itemNumber))) {
            if(e.getInitalP() >= begSet && e.getInitalP() <= endSet){
                sum += e.getYieldPercentage();
                count += 1;
            }
        }

        if(count == 0) return 0;
        else return sum/count;
    }

}
