package com.ybin.link;

/**
 * @author yuebing
 * @version 1.0 2017/9/4
 */
public class SinglyLinked<T> {
    Node head;
    int size = 1;
    public SinglyLinked() {
        head = new Node();
        head.next = null;
    }
    public void add(T date) {
        if (head == null) {
            this.head = new Node();
            head.next = null;
        }
        Node p = head;
        while (p.next != null) {
            p = p.next;
        }
        p.next = new Node(date);
        size++;
    }

    public Node getHead() {
        return head;
    }

    public int size() {
        return size;
    }

    public void delNode(String date) {
        Node p = head;
        while (p.next != null) {
            if (p.next.data.equals(date)) {
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
            if (p.next.data.equals(perDate)) {
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
            if (p.next.data.equals(date)) {
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
            System.out.println("com.ybin.link.Node>>=" + p.next.data);
            p = p.next;
        }
    }

    /**
     * 反转链表 leetcode 206
     *
     */
    public void reverse() {
        if (head == null) {
            return;
        }
        Node cur = head.next;
        Node pre = null;

        while (cur != null) {
            Node next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        if (pre != null && head != null) {
            head.next = pre;
        }
    }

    /**
     * 反转某 2个位置间的链表
     *
     * @param m
     * @param n
     */
    public void reverseBetween(int m, int n) {
        assert head != null;
        assert m < n;
        int i = 1;
        Node cur = head.next;
        Node  pre = null;
        Node newNode = cur;
        Node oldTail = null;
        while (cur != null) {
            Node next = cur.next;
            if (i >= m && i <= n) {
                cur.next = pre;
                pre = cur;
                if (i == n) {
                    oldTail = next;
                }
            } else if (m > 0 && i == m -1){
                newNode = cur;
                newNode.next = null;
            }
            cur = next;
            i++;
        }
        for (; ;) {
            if (newNode.next == null) {
                newNode.next = pre;
            }
            if (pre.next == null) {
                pre.next = oldTail;
                break;
            }else {
                pre = pre.next;
            }
        }
        head.next = newNode;
    }

}


