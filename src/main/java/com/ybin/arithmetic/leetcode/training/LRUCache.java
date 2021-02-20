package com.ybin.arithmetic.leetcode.training;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author : bing.yue001
 * @version : 1.0
 * @date : 2021-1-22 9:47
 * @description :
 */
public class LRUCache {

    private int capacity;

    private ListNode head;

    private ListNode tail;

    private Map<Integer, ListNode> map;


    public static class ListNode {
        int key;
        int val;
        ListNode next;

        ListNode pre;
        ListNode(int key, int val) { this.key = key; this.val = val; }
    }

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
    }

    public int get(int key) {
        ListNode listNode = map.get(key);
        if (listNode == null) {
            return -1;
        }
        if (listNode == tail) {
            return listNode.val;
        }

        ListNode pre = listNode.pre;
        if (listNode == head) {
            head = head.next;
            head.pre = pre;

        } else {
            listNode.next.pre = pre;
            if (listNode.pre != null) {
                listNode.pre.next = listNode.next;
            }

        }


        tail.next = listNode;
        listNode.pre = tail;
        listNode.next = null;
        tail = listNode;

        return listNode.val;
    }

    public void put(int key, int value) {
        ListNode node = map.get(key);
        if (node != null) {
            if (node == tail) {
                node.val = value;
                return;
            }

            if (node == head) {
                head = head.next;
                node.pre = null;

            } else {
                node.next.pre = node.pre;
                node.pre.next = node.next;
            }

            node.val = value;

            node.pre = tail;
            node.next = null;

            tail.next = node;
            tail = node;
            return;
        }

        if (map.size() == capacity) {
            map.remove(this.head.key);
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                head.next.pre = head.pre;
                this.head = this.head.next;
            }

        }

        if (head == null) {
            head = new ListNode(key, value);
            tail = head;
            map.put(key, head);
            return;
        }

        node = new ListNode(key, value);
        node.pre = tail;
        tail.next = node;
        tail = node;
        map.put(key, node);
    }

    public static void main(String[] args) {
//        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } };
//        printMatrix(matrix);
//        rotate(matrix);
//        System.out.println("=========");
//        printMatrix(matrix);
        ListNode l1 = new ListNode(1, 1);
        l1.next = new ListNode(2, 2);
        l1.next.next = new ListNode(3, 2);
        l1.next.next.next = new ListNode(4, 3);
        LRUCache cache = new LRUCache(2);

        cache.put(2, 1);
        cache.put(1, 1);
        cache.put(2, 3);
        cache.put(4, 1);
        cache.get(1);
        cache.get(2);

    }
}
