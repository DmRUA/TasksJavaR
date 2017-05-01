package com.javarush.task.task32.task3211;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;

/* 
Целостность информации
*/

public class Solution {
    public static void main(String... args) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(new String("test string"));
        oos.flush();
        System.out.println(compareMD5(bos, "5a47d12a2e3f9fecf2d9ba1fd98152eb")); //true

    }

    public static boolean compareMD5(ByteArrayOutputStream byteArrayOutputStream, String md5) throws Exception {
        // вызываем конструктор для MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
        // Сбрасывает обзор для дальнейшего использования
        md.reset();
        //Обновите обзор, используя указанный ByteBuffer.
        md.update(byteArrayOutputStream.toByteArray());
        //Завершает вычисление хеша, выполняя заключительные операции, такие как дополнение.
        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
//        http://www.rgagnon.com/javadetails/java-0596.html Convert a byte array to a Hex string
        for (Byte array : byteData){
            String temp = Integer.toString((array & 0xff) + 0x100, 16);
            temp = temp.substring(1);
            sb.append(temp);
        }
        String inMD5 = sb.toString();
        return inMD5.equals(md5);
    }
}
