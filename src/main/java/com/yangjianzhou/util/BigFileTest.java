package com.yangjianzhou.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by yangjianzhou on 16-5-21.
 */
public class BigFileTest {

    private static final String FILE_NAME = "/home/yangjianzhou/Test/dailyIncomeFile.txt";

    private static final int TOTAL_LINE_OF_BIG_FILE = 10000000;

    private static final int MAX_LINE_PER_SUB_FILE = 1000000;

    public static void main(String[] args) throws Exception {
        buildBigFile();
        splitBigFile();
    }

    public static void buildBigFile() throws Exception {
        FileWriter fileWritter = null;
        try {

            fileWritter = new FileWriter(FILE_NAME, false);
            long startTime = System.currentTimeMillis();
            for (int lineNum = 1; lineNum <= TOTAL_LINE_OF_BIG_FILE; lineNum++) {
                fileWritter.write(buildLineContent(lineNum));
            }
            long endTime = System.currentTimeMillis();
            System.out.println("build big file time spend : " + (endTime - startTime));

        } catch (Exception exp) {

        } finally {
            if (fileWritter != null) {
                fileWritter.close();
            }
        }
    }

    public static void splitBigFile() throws Exception {
        int fileCounter = 0;
        int lineCounter = 0;
        File bigFile = new File(FILE_NAME);
        FileReader fileReader = new FileReader(bigFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        FileWriter fileWritter = new FileWriter(bigFile.getAbsolutePath() + "_" + fileCounter, false);
        String lineContent;
        long startTime = System.currentTimeMillis();
        try {
            while ((lineContent = bufferedReader.readLine()) != null) {
                lineCounter++;
                fileWritter.write(lineContent);
                fileWritter.write("\n");
                if (lineCounter % MAX_LINE_PER_SUB_FILE == 0) {
                    fileCounter++;
                    fileWritter.flush();
                    fileWritter.close();
                    fileWritter = new FileWriter(bigFile.getAbsolutePath() + "_" + fileCounter, false);
                }
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            System.out.println(exp.getCause());
        } finally {
            if (fileReader != null) {
                fileReader.close();
            }
            if (fileWritter != null) {
                fileWritter.close();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("split big file time spend : " + (endTime - startTime));
    }

    public static String buildLineContent(int lineNum) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(lineNum);
        stringBuilder.append('-');
        stringBuilder.append(lineNum+4);
        stringBuilder.append('-');
        stringBuilder.append(lineNum+0.2);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

}
