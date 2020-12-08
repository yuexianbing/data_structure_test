package com.ybin.arithmetic.leetcode;

import com.ybin.link.Node;

/**
 * @author : bing.yue001
 * @version : 1.0
 * @date : 2020-8-1 16:02
 * @description :
 */
public class Linked {


    /**
     * 链表回文
     *
     * @param head
     * @return
     */
    public boolean plalind(Node head) {
        if (head == null || head.getNext() == null) {
            return false;
        }
        Node n1 = head;
        Node n2 = head;
        while (n2.getNext() != null && n2.getNext().next != null) {
            n1 = n1.getNext();
            n2 = n2.getNext().getNext();
        }
        Node n3 = n1.getNext();
        n1.setNext(null);

        Node pre;
        Node next;
        while (n3 != null) {
            next = n3.getNext();
            pre = n3;
            n3.setNext(pre);
            n3 = next;
        }

        while (head != null || n3 != null) {
            if (head.data != n3.data) {
                return false;
            }
            head = head.next;
            n3 = n3.next;
        }
        // 还原
//
//        while () {
//
//        }

        return true;

    }

    /**
     * 判断两个单项链表是否相交
     * 1.如果两链表都无环,且相交的话,其交点到最后一个节点的距离都一样,求不相交部分的插值,长的先走差值部分,最后在同时走,走到相同的点是交点
     * 2.一个有环,一个无环,则不会相交
     * 3.两个都有环, 相交的情况1：在环外相交,此时入环的节点相同,算法跟1一样,结束点即入环的节点;2:环内相交:则交点为任一一个链表的入环节点
     * @param node1
     * @param node2
     */
    public Node intersect(Node node1, Node node2) {
        Node loop1 = isLoop(node1);
        Node loop2 = isLoop(node2);
        // 两者都没环
        if (loop1 == null && loop2 == null) {
            return intersect1(node1, loop1, node2, loop2);
        }
        // 两者都有环
        if (loop1 != null && loop2 != null) {
            Node l1 = loop1;
            boolean isInter = false;
            while (l1 != null) {
                if (l1 == loop2) {
                    isInter = true;
                    break;
                }
                l1 = l1.next;
            }
            if (isInter) {
                if (loop1 == loop2) {
                    // 入环点一样的情况下,跟两者都无环一致
                    return intersect1(node1, loop1, node2, loop2);
                } else {
                    // 入环点不一样,相交点必为其中一个链表的如环点
                    return loop1;
                }
            }
        }
        return null;
    }

    private Node intersect1(Node node1, Node loop1, Node node2, Node loop2) {
        if (node1 == null || node2 == null) {
            return null;
        }
        Node n1 = node1;
        Node n2 = node2;
        int length1 = 0;
        while (n1 != loop1) {
            n1 = n1.next;
            length1 ++;
        }
        while (n2 != loop2) {
            n2 = n2.next;
            length1 --;
        }

        n1 = node1;
        n2 = node2;

        if (length1 > 0) {
            return this.getNode(n2, n1, length1);
        } else {
            length1 = Math.abs(length1);
            return this.getNode(n1, n2, length1);
        }

    }

    private Node getNode(Node n1, Node n2, int length1) {
        while (n1 != null && n2 != null) {
            n1 = n1.next;
            if (length1 == 0) {
                n2 = n2.next;
            }
            if (length1 > 0) {
                length1--;
            }
            if (n1 == n2) {
                return n1;
            }

        }
        return null;
    }

    private Node isLoop(Node node) {
        if (node == null || node.next == null) {
            return null;
        }
        Node fast = node;
        Node slow = node;
        boolean isMeet = false;
        while (fast != null && fast.next != null) {
            fast = isMeet ? node.next : fast.next.next;
            slow = slow.next;

            // 第一次相遇
            if (fast == slow) {
                fast = node;
                isMeet = true;
            }
            // 已经相遇,且第二次相遇
            if (isMeet && fast == slow) {
                return fast;
            }
        }
        return null;
    }

    public Node segmentation(Node node, int x) {
        if (node == null) {
            return node;
        }
        Node cur = node.next;
        Node pre = null;
        Node preHead = null;
        Node after = null;
        Node afterHead = null;
        Node equ = null;
        while (cur != null) {
            Node ne = new Node(cur.data);
            if ((int)cur.data > x) {

                if (after == null) {
                    after = ne;
                    afterHead = after;
                } else {
                    after.next = ne;
                    after = after.next;
                }
            } else if ((int)cur.data < x){
                if (pre == null) {
                    pre = ne;
                    preHead = pre;
                } else {
                    pre.next = ne;
                    pre = pre.next;
                }
            } else {
                equ = cur;
            }
            cur = cur.next;
        }
        Node newNode = null;
        if (pre != null) {
            if (equ != null) {
                pre.next = equ;
                equ.next = afterHead;
            } else {
                pre.next = afterHead;
            }
            newNode = preHead;
        } else {
            if (equ != null) {
                equ.next = afterHead;
                newNode = equ;
            } else {
                newNode = afterHead;
            }
        }
        newNode.print();
        return newNode;
    }
}
