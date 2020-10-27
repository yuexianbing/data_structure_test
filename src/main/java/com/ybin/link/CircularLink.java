package com.ybin.link;

/**
 * @author yuebing
 * @version 1.0 2017/9/4
 * @Description 循环链表基础操作
 */
public class CircularLink<T> {

    Node head;//头节点
    public CircularLink() {
        head = new Node();
        head.next = null;
    }

    /**
     * 循环链表，添加节点
     * @param date
     */
    public void add(T date) {
        if (head.next == null) {
            Node firstNode = new Node(date);
            head.next = firstNode;
            firstNode.next = head.next;
            return;
        }
        Node p = head.next;
        while (p.next != head.next) {
            p = p.next;
        }
        Node node = new Node(date);
        p.next = node;
        node.next = head.next;
    }

    /**
     * 删除节点
     * @param data
     */
    public void dealNode(String data) {
        Node p = head.next;
        //删除第一个节点
        if (p.data.equals(data)) {
            head.next = p.next;
            Node p1 = head.next;
            while (p != p1.next) {
                p1 = p1.next;
            }
            p1.next = head.next;
            return;
        }
        while (p.next != head.next) {
            if (p.next.data.equals(data)) {
                p.next = p.next.next;

                break;
            } else {
                p = p.next;
            }
        }
    }

    /**
     * 向循环链表中的某一节点前插入指定数据
     * @param preData
     * @param data
     */
    public void insertNode(String preData, String data) {
        Node p = head.next;
        //在第一个节点前添加数据
        if (p.data.equals(preData)) {
            Node newNode = new Node(data);
            head.next = newNode;
            newNode.next = p;
            while (p.next != newNode.next) {
                p = p.next;
            }
            p.next = newNode;
            return;
        }
        while (p.next != head.next) {
            if (p.next.data.equals(preData)) {
                Node newNode= new Node(data);
                newNode.next = p.next;
                p.next = newNode;
                break;
            } else {
                p = p.next;
            }
        }
    }

    /**
     * 显示循环链表所有节点
     * @param circularLink
     */
    public void showAllNode(CircularLink circularLink) {
        Node p = head.next;
        while (p.next != head.next) {
            System.out.println("节点数据值为:" + p.data);
            p = p.next;
        }
        System.out.println("节点数据值为:" + p.data);
    }
}
