package com.javarush.task.task25.task2510;

/**
 * Created by DmRG on 31.05.2017.
 */
public class HandlerException implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (e instanceof Error) System.out.println("Нельзя дальше работать");
        else if (e instanceof Exception) System.out.println("Надо обработать");
        else if (e instanceof Throwable) System.out.println("ХЗ");
    }
}
