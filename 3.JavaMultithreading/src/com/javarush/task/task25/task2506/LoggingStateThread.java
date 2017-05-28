package com.javarush.task.task25.task2506;

/**
 * Created by DmRG on 29.05.2017.
 */
public class LoggingStateThread extends Thread {
    private Thread thread;

    public LoggingStateThread(Thread target) {
        super();
        thread = target;
        setDaemon(true);
    }

    @Override
    public void run() {

        PrintStateThread(thread.getState());
        State state = thread.getState();

        while (state != State.TERMINATED) {
            if (state != thread.getState()) {
                PrintStateThread(thread.getState());
                state = thread.getState();
            }

        }
    }

    private void PrintStateThread(Thread.State state) {
        System.out.println(state);
    }
}
