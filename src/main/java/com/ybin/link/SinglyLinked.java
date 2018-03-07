package com.ybin.link;

/**
 * @author yuebing
 * @version 1.0 2017/9/4
 */
public class SinglyLinked {
    Node head;
    public SinglyLinked() {
        head = new Node();
        head.next = null;
    }
    public void add(String date) {
        if (head == null) {
            this.head = new Node();
            head.next = null;
        }
        Node p = head;
        while (p.next != null) {
            p = p.next;
        }
        p.next = new Node(date);
    }

    public void delNode(String date) {
        Node p = head;
        while (p.next != null) {
            if (p.next.date.equals(date)) {
                p.next = p.next.next;
                break;
            } else {
                p = p.next;
            }
        }
    }

    public void insertNode(String perDate, String date) {
        Node p = head;
        while (p.next != null) {
            if (p.next.date.equals(perDate)) {
                Node insetNode = new Node(date);
                insetNode.next = p.next;
                p.next = insetNode;
                break;
            } else {
                p = p.next;
            }
        }
    }

    public Node findNode(String date) {
        Node p = head;
        while (p.next != null) {
            if (p.next.date.equals(date)) {
                return p.next;
            } else {
                p = p.next;
            }
        }
        return null;
    }

    public void forEachNode(SinglyLinked singlyLinked) {
        Node p = singlyLinked.head;
        while (p.next != null) {
            System.out.println("com.ybin.link.Node>>=" + p.next.date);
            p = p.next;
        }
    }

}


