package com.ybin.link;

/**
 * @author yuebing
 * @version 1.0 2017/9/4
 */
public class Node<T> {
    public T data;
    public Node next;
    public Node() {}

    public Node(T data) {
        this.data = data;
    }

    public Node(T data, Node next) {
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void print() {
        Node cur = this;
        while (cur != null) {
            System.out.println("->" + cur.data);
            cur = cur.next;
        }
    }
}
