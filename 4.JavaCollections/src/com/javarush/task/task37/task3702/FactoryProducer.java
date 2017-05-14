package com.javarush.task.task37.task3702;

import com.javarush.task.task37.task3702.female.FemaleFactory;
import com.javarush.task.task37.task3702.male.MaleFactory;

/**
 * Created by DmRG on 14.05.2017.
 */
public class FactoryProducer {
   public enum HumanFactoryType {MALE, FEMALE}

   public static AbstractFactory getFactory(HumanFactoryType humanFactoryType) {
       if (humanFactoryType.equals(HumanFactoryType.FEMALE)) return new FemaleFactory();
        else return new MaleFactory();
   }
}
