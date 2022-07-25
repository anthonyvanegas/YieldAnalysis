package com.company.Analysis;

import com.company.InputManager.ProductionEntry;

import java.util.LinkedList;

public class ItemNumList {
    LinkedList<ProductionEntry> queueList;
    LinkedList<LinkedList<ProductionEntry>> itemList;

    public ItemNumList(LinkedList<ProductionEntry> queueList){
        this.queueList = queueList;
    }

    private void loadItemList(){
        int placeHolder = 0;
        int itemNumber;

        for (int i = 0; i < queueList.size(); i++) {
            itemNumber = queueList.get(i).getItemNumber();
            //Create array with item number
            if(placeHolder == itemNumber){
                
            }else{
                itemList.add(new LinkedList<ProductionEntry>());
                itemList.getLast().add(queueList.get(i));
                placeHolder = itemNumber;
            }
        }
    }
}
