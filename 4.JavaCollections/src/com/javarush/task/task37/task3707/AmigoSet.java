package com.javarush.task.task37.task3707;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by DmRG on 08.05.2017.
 */
public class AmigoSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E> {

    private static final Object PRESENT = new Object() ;
    private transient HashMap<E, Object> map;

    public AmigoSet(Collection<? extends E> collection)
    {
        int capacity = (int)Math.ceil(collection.size()/.75f);
        if(capacity<16)
            capacity = 16;
        map = new HashMap<>(capacity);
        for(E e : collection)
            map.put(e, PRESENT);
    }

    public AmigoSet()
    {
        map = new HashMap<>();

    }


    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer action) {

    }

    @Override
    public Spliterator<E> spliterator() {
        return null;
    }

    @Override
    public Stream<E> stream() {
        return null;
    }

    @Override
    public Stream<E> parallelStream() {
        return null;
    }

    @Override
    public boolean add(E e) {
        return null == map.put(e, PRESENT);
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeIf(Predicate filter) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public int size() {
        return 0;
    }

}
