package com.javarush.task.task29.task2909.human;

/**
 * Created by DmRG on 02.06.2017.
 */
public class UniversityPerson extends Human {
    private University university;
    protected int age;
    protected String name;

    public UniversityPerson(String name, int age) {
        super(name, age);
        this.name = name;
        this.age = age;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
