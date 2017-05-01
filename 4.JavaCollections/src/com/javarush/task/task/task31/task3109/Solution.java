package com.javarush.task.task31.task3109;

import java.io.FileInputStream;
import java.util.Properties;

/* 
Читаем конфиги
*/
public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        Properties properties = solution.getProperties("src/com/javarush/task/task31/task3109/properties.xml");
        properties.list(System.out);

        properties = solution.getProperties("src/com/javarush/task/task31/task3109/properties.txt");
        properties.list(System.out);

        properties = solution.getProperties("src/com/javarush/task/task31/task3109/notExists");
        properties.list(System.out);
    }

    public Properties getProperties(String fileName) {
        Properties prop = new Properties();
        if (fileName.endsWith(".xml")) {
            try {
                prop.loadFromXML(new FileInputStream(fileName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (fileName.endsWith(".txt")) {
            try {
                prop.load(new FileInputStream(fileName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            try {
                prop.load(new FileInputStream(fileName));
            } catch (Exception e) {}

        return prop;
    }
}
