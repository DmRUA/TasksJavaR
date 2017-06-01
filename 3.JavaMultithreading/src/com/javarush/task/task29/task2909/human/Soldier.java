package com.javarush.task.task29.task2909.human;

/**
 * Created by DmRG on 01.06.2017.
 */
public class Soldier extends Human {
    public Soldier(String name, int age) {
        super(name, age);
    }

    @Override
    public void live() {
            fight();
    }

    public void fight() {
    }
}
