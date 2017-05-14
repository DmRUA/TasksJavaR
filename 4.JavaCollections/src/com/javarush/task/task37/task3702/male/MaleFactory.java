package com.javarush.task.task37.task3702.male;

import com.javarush.task.task37.task3702.AbstractFactory;
import com.javarush.task.task37.task3702.Human;

/**
 * Created by DmRG on 14.05.2017.
 */
public class MaleFactory implements AbstractFactory {
    public Human getPerson(int age){
        if (age <= 12) return new KidBoy();
        if (age > 12 && age <= 19) return new TeenBoy();
        if (age > 19) return new Man();
        return null;
    }
}
