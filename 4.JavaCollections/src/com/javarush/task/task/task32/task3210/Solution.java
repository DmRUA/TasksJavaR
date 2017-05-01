package com.javarush.task.task32.task3210;

import java.io.RandomAccessFile;

/* 
Используем RandomAccessFile
*/

public class Solution {
    public static void main(String... args) {
        String fileName = args[0];
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw");) {
            int number = Integer.parseInt(args[1]);
            String text = args[2];
            byte[] bytes = new byte[text.length()];
            randomAccessFile.seek(number);
            randomAccessFile.read(bytes,0,text.length());
            String result = convertByteToString(bytes);
            String isTrueorFalse;
            if (result.equals(text)) {
                isTrueorFalse = "true";
            } else {
                isTrueorFalse = "false";
            }
            randomAccessFile.seek(randomAccessFile.length());
            randomAccessFile.write(isTrueorFalse.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String convertByteToString(byte[] bt) {
        return new String(bt);
    }
}
