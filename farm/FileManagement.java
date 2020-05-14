package com.project.farm;

import java.io.*;
import java.util.Scanner;

public class FileManagement {

    FileManagement() {

    }

    public static int readFromFile(File file) {
        Integer highScore=0;
            Scanner sc=null;
            try {
                sc = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            highScore = sc.nextInt();
            return highScore;
    }

    public static Boolean saveTofile(File file , int highScore) {
        boolean saved = false;
            try {
                saved = true;
                FileWriter out = new FileWriter(file);
                out.write(highScore + "");
                out.close();
            } catch (Exception x) {
                System.err.println("Error: " + x.getMessage());}
        return saved;
    }
}
