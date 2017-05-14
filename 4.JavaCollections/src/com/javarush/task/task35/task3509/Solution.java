package com.javarush.task.task35.task3509;

import java.util.*;


/* 
Collections & Generics
*/
public class Solution{

    public static void main(String[] args) {
    }

    public static <T> ArrayList<T> newArrayList(T... elements) {
        ArrayList<T> arrayList = new ArrayList<T>();
        for (T element : elements) {
            arrayList.add(element);
        }
        //напишите тут ваш код
        return arrayList;
    }

    public static <T> HashSet<T> newHashSet(T... elements) {
        //напишите тут ваш код
        HashSet<T> hashSet = new HashSet<T>();
        for (T element : elements) {
            hashSet.add(element);
        }
        return hashSet;
    }

    public static <K,V>  HashMap<K,V> newHashMap(List<? extends K> keys, List<? extends V> values) {
        //напишите тут ваш код
        HashMap<K, V> hashMap = new HashMap<K, V>();
        if (keys.size() != values.size()) throw new IllegalArgumentException("Error!!!") ;
        for (int i = 0; i < keys.size(); i++) {
            hashMap.put(keys.get(i), values.get(i));
        }
        return hashMap;
    }
}
