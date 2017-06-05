package com.javarush.task.task26.task2611;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by DmRG on 04.06.2017.
 */
public class Producer implements Runnable {

    private ConcurrentHashMap<String, String> map;
    private Integer count = 1;

    public Producer(ConcurrentHashMap<String, String> map) {
        this.map = map;
    }

    @Override
    public void run() {

        try {
            while (true) {
                map.put(count.toString(), "Some text for " + count.toString());
                count++;
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "  thread was terminated");
        }

    }
}
