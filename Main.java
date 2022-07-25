package com.company;

import com.company.Analysis.AnalysisGenerator;
import com.company.InputManager.QueueListLoader;
import com.company.OutputManager.WBManager;

import java.io.*;

public class Main {


    public static void main(String[] args) throws IOException {
        QueueListLoader queueListLoader;

        File folder = new File("REMOVED");
        File[] listOfFiles = folder.listFiles();

        queueListLoader = new QueueListLoader(listOfFiles);
        AnalysisGenerator numList = new AnalysisGenerator(queueListLoader.getQueueList());

        WBManager wbManager = new WBManager("REMOVED");
        wbManager.createAnalysisSheet(numList);
    }
}




