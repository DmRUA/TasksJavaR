package com.javarush.task.task29.task2909.human;

import java.util.ArrayList;
import java.util.List;

public class University {
    private List<Student> students = new ArrayList<>();
    private String name;
    private int age;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public University(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Student getStudentWithAverageGrade(double grade) {
        //TODO:
        for (Student student : students) {
            if (student.getAverageGrade() == grade) {
                return student;
            }
        }
        return null;
    }

    public Student getStudentWithMaxAverageGrade() {
        //TODO:
        double max = -1.0;
        Student bestStudent = null;
        for (Student student1 : students) {
            if (student1.getAverageGrade() > max) {
                bestStudent = student1;
                max = bestStudent.getAverageGrade();
            }
        }
        return bestStudent;
    }

    public Student getStudentWithMinAverageGrade() {

        Student badStudent = null;
        Double min = Double.MAX_VALUE;
        for (Student s : students) {
            if (s.getAverageGrade() < min) {
                badStudent = s;
                min = s.getAverageGrade();
            }
        }
        return badStudent;
    }


    public void expel(Student student) {
        students.remove(student);
    }
}