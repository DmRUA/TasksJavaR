package com.javarush.task.task32.task3201;

import java.io.RandomAccessFile;

/*
Запись в существующий файл
*/
public class Solution {
    public static void main(String... args) {
        String fileName = args[0];
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "w");) {
            int number = Integer.parseInt(args[1]);
            String text = args[2];
             if (number > randomAccessFile.length()) {
                randomAccessFile.seek(randomAccessFile.length());
                randomAccessFile.write(text.getBytes());
             } else {
                 randomAccessFile.seek(number);
                 randomAccessFile.write(text.getBytes());
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
