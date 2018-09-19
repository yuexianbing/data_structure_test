package com.ybin.btree;

/**
 * @author yuebing
 * @version 1.0 2017/9/4
 * @Description 链表实现二叉树,二叉树每层最多2^(n-1)各节点，深度为k的二叉树最多有2^k-1个节点。
 */
public class BtreeLinked<T> {

    private static class Node<T> {

        /**
         * 数据域
         */
        private int item;
        /**
         * 左节点
         */
        private Node leftNode;
        /**
         * 右节点
         */
        private Node rightNode;

        Node(int item) {
            this.item = item;
        }
    }

    private Node root;

    public BtreeLinked(int t) {
        root = new Node(t);
    }

    public boolean add(int t) {
        Node r = root;
        Node newNode = new Node(t);
        for (; ;) {
            if (r.item > t) {
                if (r.leftNode == null) {
                    r.leftNode = newNode;
                    break;
                }
                r = r.leftNode;
            } else {
                if (r.rightNode == null) {
                    r.rightNode = newNode;
                    break;
                }
                r = r.rightNode;
            }
        }
        return true;
    }

    public void show() {
        this.postorderTraversal(root);
    }

    /**
     * 前序遍历
     *
     */
    public void showPreOrder(Node node) {
        if (node != null) {
            System.out.println("node = [" + node.item + "]");
            showPreOrder(node.leftNode);
            showPreOrder(node.rightNode);
        }
    }

    /**
     * 中序遍历
     * @param node
     */
    public void showFixeOrder(Node node ) {
        if (node != null) {
            showFixeOrder(node.leftNode);
            System.out.println("node = [" + node.item + "]");
            showFixeOrder(node.rightNode);
        }
    }

    /**
     * 后续遍历
     * @param node
     */
    public void postorderTraversal (Node node) {
        if (node != null) {
            postorderTraversal(node.leftNode);
            postorderTraversal(node.rightNode);
            System.out.println("node = [" + node.item + "]");
        }
    }
}
