package com.javarush.task.task25.task2508;

public class TaskManipulator implements Runnable, CustomThreadManipulator {
    private Thread thread;

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

    @Override
    public void start(String threadName) {
        this.thread = new Thread(this);
        this.thread.setName(threadName);
        this.thread.start();
    }

    @Override
    public void stop() {
        thread.interrupt();
    }
}
