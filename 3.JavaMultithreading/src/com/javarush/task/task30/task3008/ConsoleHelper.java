package com.javarush.task.task30.task3008;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by DmRG on 04.06.2017.
 */
public class ConsoleHelper {
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() {
        String string = "";
        boolean isRead = false;
        do {
            try {
                string = bufferedReader.readLine();
                isRead = true;
            } catch (IOException e) {
                System.out.println("Произошла ошибка при попытке ввода текста. Попробуйте еще раз.");
            }
        } while (!isRead);
        return string;
    }

    public static int readInt() {
        Integer integer = 0;
        boolean isRead = false;
        do {
            try {
                integer = Integer.parseInt(readString());
                isRead = true;
            } catch (NumberFormatException e) {
                System.out.println("Произошла ошибка при попытке ввода числа. Попробуйте еще раз.");
            }
        } while (!isRead);
        return integer;
    }
}
