package com.javarush.task.task35.task3512;

public class Generator<T> {
    private Class<T> clas;

    public Generator(Class<T> clas) {
        this.clas = clas;
    }

    T newInstance() throws IllegalAccessException, InstantiationException {
        return (T) clas.newInstance();
    }
}
