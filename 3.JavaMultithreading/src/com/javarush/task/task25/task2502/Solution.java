package com.javarush.task.task25.task2502;

import java.util.*;

/* 
Машину на СТО не повезем!
*/
public class Solution {
    public static enum Wheel {
        FRONT_LEFT,
        FRONT_RIGHT,
        BACK_LEFT,
        BACK_RIGHT
    }

    public static class Car {
        protected List<Wheel> wheels;

        public Car() {
            //init wheels here
            wheels = new ArrayList<>();
            for (String en : loadWheelNamesFromDB()) {
                try {
                    if (!wheels.contains(Wheel.valueOf(en)))
                        wheels.add(Wheel.valueOf(en));
                    else throw new IllegalArgumentException();
                } catch (IllegalArgumentException  e) {
                    throw new IllegalArgumentException();
                }
            }
            if (wheels.size() < 4) {
                throw new IllegalArgumentException();
            }
        }

        protected String[] loadWheelNamesFromDB() {
            //this method returns mock data
            return new String[]{"FRONT_LEFT", "FRONT_RIGHT", "BACK_LEFT", "BACK_RIGHT"};
        }
    }

    public static void main(String[] args) {
    }
}
