package com.javarush.task.task37.task3707;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;


/**
 * Created by DmRG on 08.05.2017.
 */
public class AmigoSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E> {

    private static final Object PRESENT = new Object();
    private transient HashMap<E, Object> map;

    public AmigoSet(Collection<? extends E> collection) {
        int capacity = (int) Math.ceil(collection.size() / .75f);
        if (capacity < 16)
            capacity = 16;
        map = new HashMap<>(capacity);
        for (E e : collection)
            map.put(e, PRESENT);
    }

    public AmigoSet() {
        map = new HashMap<>();

    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public void forEach(Consumer action) {

    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean remove(Object o) {
        return map.keySet().remove(o);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        final int size = size();
        oos.writeInt(size);
        final int capacity = HashMapReflectionHelper.callHiddenMethod(map, "capacity");
        oos.writeInt(capacity);
        final float loadFactor = HashMapReflectionHelper.callHiddenMethod(map, "loadFactor");
        oos.writeFloat(loadFactor);
        for (final E e : map.keySet()) {
            oos.writeObject(e);
        }
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        final int size = ois.readInt();
        final int capacity = ois.readInt();
        final float loadFactory = ois.readFloat();
        map = new HashMap<>(capacity, loadFactory);
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked") final E e = (E) ois.readObject();
            map.put(e, PRESENT);
        }
    }

    @Override
    public Object clone() {
        try {
            AmigoSet copy = (AmigoSet) super.clone();
            copy.map = (HashMap) map.clone();
            return copy;
        } catch (Exception e) {
            throw new InternalError();
        }
    }


    @Override
    public boolean add(E e) {
        return null == map.put(e, PRESENT);
    }


    @Override
    public int size() {
        return map.size();
    }

}
