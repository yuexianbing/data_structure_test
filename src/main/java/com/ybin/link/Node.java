package com.ybin.link;

/**
 * @author yuebing
 * @version 1.0 2017/9/4
 */
class Node {
    String date;
    Node next;
    public Node() {}
    public Node(String date) {
        this.date = date;
        this.next = null;
    }
}
