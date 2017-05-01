package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/* 
Что внутри папки?
*/
public class Solution {
    static long countDirs = -1;
    static long countFiles = 0;
    static long countWeight = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String pathToFile = reader.readLine();
        reader.close();
        Path file = Paths.get(pathToFile);
        if (Files.isDirectory(file)) {
            Files.walkFileTree(file, new MyFleVisitor());
            System.out.println("Всего папок - " + countDirs);
            System.out.println("Всего файлов - " + countFiles);
            System.out.println("Общий размер - " + countWeight);
        } else {
            System.out.println(file.toAbsolutePath().toString() + " - не папка");
        }
    }

    static class MyFleVisitor extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            countFiles++;
            countWeight += attrs.size();
            return super.visitFile(file, attrs);

        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            countDirs++;
            return super.preVisitDirectory(dir, attrs);
        }
    }
}
