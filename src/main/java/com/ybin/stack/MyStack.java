package com.ybin.stack;

/**
 * @author yuebing
 * @version 1.0 2017/9/4
 * @Description 链表实现堆栈
 */
public class MyStack<E> {

    transient private Node first;

    transient private Node last;

    int size = 0;

    int modCount = 0;

    public MyStack() {
    }

    /**
     * 添加元素
     * @param e
     */
    public void push(E e) {
        Node f = first;
        Node n = new Node(e, null, f);
        first = n;
        if (f == null) {
            last = f;
        } else {
            f.prev = first;
        }
        size++;
        modCount++;
    }

    public E peek() {
        if (size == 0) {
            return null;
        }
        return (E) first.item;
    }

    public E pop() {
        if (size == 0) {
            return null;
        }
        E e = (E) first.item;
        Node next = first.next;
        if (next == null) {
            first = null;
            last = null;
        } else {
            first.next = null;
            first = next;
            first.prev = null;
        }
        size--;
        modCount++;
        return e;
    }

    private static class Node<E> {
        E item;
        Node prev;
        Node next;

        public Node(E item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
