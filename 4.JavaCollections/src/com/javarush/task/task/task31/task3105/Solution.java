package com.javarush.task.task31.task3105;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* 
Добавление файла в архив
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        if (args.length < 2)
            return;
        Map<ZipEntry, StringBuffer> mapZipEntry = new HashMap<>();

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(args[1]));
        ) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                int counter;
                StringBuffer buffer = new StringBuffer();
                while ((counter = zipInputStream.read()) != -1) {
                    buffer.append((char) counter);
                }
                mapZipEntry.put(zipEntry, buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(args[1]));
        ) {
            File file = new File(args[0]);
            String fileName = "new/" + file.getName();
            if (file.isFile()) {
                zipOutputStream.putNextEntry(new ZipEntry(fileName));
                Files.copy(file.toPath(), zipOutputStream);
            }
            for (Map.Entry<ZipEntry, StringBuffer> pair : mapZipEntry.entrySet()) {
                if (!pair.getKey().getName().equals(file.getName())) {
                    zipOutputStream.putNextEntry(pair.getKey());
                    for (char c : pair.getValue().toString().toCharArray()) {
                        zipOutputStream.write(c);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
