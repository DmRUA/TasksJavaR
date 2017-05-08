package com.javarush.task.task34.task3411;

/* 
Ханойские башни
*/

public class Solution {
    public static void main(String[] args) {
        int count = 3;
        moveRing('A', 'B', 'C', count);
    }

    public static void moveRing(char a, char b, char c, int count) {
        //напишите тут ваш код
        if (count > 0) {
            moveRing(a, c, b, count - 1); // перенести башню из n−1 диска на 2-й штырь
            System.out.println(String.format("from %c to %c", a, b));
            moveRing(c, b, a, count - 1); // перенеси башню из n−1 диска на 3-й штырь
        }
    }
}