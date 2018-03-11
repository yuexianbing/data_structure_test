package com.ybin.link;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * @author yuebing
 * @version 1.0 2017/9/4
 * @Description
 */
public class DoubleLink<E> {

    transient int size = 0;

    transient Node first;

    transient Node last;

    transient int modCount = 0; //记录双向链表结构变化次数。在遍历时与进入遍历方法时，记录该值，如果遍历过程中发现值变化了，抛出ConcurrentModificationException

    public DoubleLink() {

    }

    public DoubleLink(Collection<? extends E> c) {
        this();
        addAll(size, c);
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        Object[] objects = c.toArray();
        if (objects.length == 0) {
            return false;
        }
        Node pred, succ;
        if (index == this.size) {
            pred = last;
            succ = null;
        } else {
            succ = findNodeByIndex(index);
            pred = succ.prev;
        }
        for (Object o : objects) {
            Node newNode = new Node(o, null, pred);
            if (pred == null) {
                first = newNode;
            } else {
                pred.next = newNode;
            }
            pred = newNode;
        }
        if (succ == null) {
            last = pred.next;
        } else {
            pred.next= succ;
            succ.prev = pred;
        }
        modCount++;
        size += objects.length;
        return true;
    }

    /**
     * 添加节点
     * @param e
     */
    public void add(E e) {
        Node last = this.last;
        Node newNode = new Node(e, null, last);
        this.last = newNode;
        if (last == null) {
            first = newNode;
        }  else {
            last.next = newNode;
        }
        size++;
        modCount++;
    }

    /**
     * poll一个元素
     */
    public E poll() {
        return first == null ? null : unLinked(first);
    }
    private E unLinked(Node node) {
        assert node !=null;
        E e = (E) first.item;
        Node next = first.next;
        first.next = null;
        first.prev = null;//避免内存泄漏
        first = next;
        if (next == null) {
            last =null;
        } else {
            next.prev = null;
        }
        size--;
        modCount++;
        return e;
    }

    public Node findNodeByIndex(int index) {
        if (index < (size << 1) ) {
            Node node = first;
            for (int i = 0 ; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    public void addLast(E item) {
        Node newNode = new Node(item, null, this.last);
        if (last == null) {
            first = newNode;
        } else {
            this.last.next = newNode;
        }
        this.last = newNode;
        size++;
        modCount++;
    }
    private static class Node<E>{
        E item;
        Node<E> next;
        Node<E> prev;

        Node(E item, Node<E> next, Node<E> prev) {
            LinkedList linkedList = new LinkedList(new ArrayList());
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
