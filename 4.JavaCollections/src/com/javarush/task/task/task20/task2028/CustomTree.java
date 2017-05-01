package com.javarush.task.task.task20.task2028;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/*
Построй дерево(1)
*/
public class CustomTree
        extends AbstractList<String>
        implements List<String>, Cloneable, Serializable {

    public static void main(String[] args) {
        List<String> list = new CustomTree();
        for (int i = 1; i < 16; i++) {
            list.add(String.valueOf(i));
        }
        //System.out.println("Expected 3, actual is " + ((CustomTree) list).getParent("8"));
        list.remove("5");
        //System.out.println("Expected null, actual is " + ((CustomTree) list).getParent("11"));
    }

    public String getParent(String value) {
        for (Node<String> node = first; node != null; node = node.next) {
            if (node.item.equals(value))
                if (node.parent == null)
                    return null;
                else return node.parent.item;
        }
        return null;
    }

    private int size = 0;
    private Node<String> first;
    private Node<String> last;
    private Node<String> root = new Node<>(null, null, null);

    public CustomTree() {
    }

    private static class Node<String> implements Serializable {
        String item;
        Node<String> next;
        Node<String> prev;
        Node<String> parent;
        Node<String> leftChild;
        Node<String> rightChild;

        Node(Node<String> prev, String element, Node<String> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException("Access from index denied");
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(String s) {
        final Node<String> l = last;
        final Node<String> newNode = new Node<>(l, s, null);
        last = newNode;
        if (l == null) {
            first = newNode;
            root = newNode;
        } else if (l == first) {
            l.next = newNode;
        } else {
            l.next = newNode;
            if (root.rightChild != null)
                root = root.next;

            newNode.parent = root;

            if (root.leftChild == null)
                root.leftChild = newNode;
            else
                root.rightChild = newNode;
        }

        size++;
        return true;
    }

    String unlink(Node<String> x) {
        final String element = x.item;
        final Node<String> next = x.next;
        final Node<String> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        if (x.parent != null) {
            if (x.parent.leftChild == x)
                x.parent.leftChild = null;
            else if (x.parent.rightChild == x)
                x.parent.rightChild = null;
        }

        x.item = null;
        size--;
        return element;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (Node<String> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                }
            }
        } else {
            for (Node<String> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    if (x.leftChild != null)
                        remove(x.leftChild.item);
                    if (x.rightChild != null)
                        remove(x.rightChild.item);
                    unlink(x);
                }
            }
        }
        return false;
    }

    @Override
    public void clear() {
        for (Node<String> x = first; x != null; ) {
            Node<String> next = x.next;
            x.item = null;
            x.next = null;
            x.prev = null;
            x = next;
        }
        first = last = null;
        size = 0;
    }

    @Override
    public Iterator<String> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<String> {

        private int nextIndex = 0;
        private Node<String> lastReturned;
        private Node<String> next = first;

        private Itr() {
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public String next() {
            if (!hasNext())
                throw new NoSuchElementException();

            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.item;
        }

        @Override
        public void remove() {
            if (lastReturned == null)
                throw new IllegalStateException();

            Node<String> lastNext = lastReturned.next;
            unlink(lastReturned);
            if (next == lastReturned)
                next = lastNext;
            else
                nextIndex--;
            lastReturned = null;
        }
    }

    @Override
    protected CustomTree clone() throws CloneNotSupportedException {
        CustomTree clone = new CustomTree();
        for (Node<String> x = first; x != null; x = x.next) {
            clone.add(x.item);
        }
        return clone;
    }
}