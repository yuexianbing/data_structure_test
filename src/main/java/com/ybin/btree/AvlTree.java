package com.ybin.btree;

/**
 * @author yuebing
 * @version 1.0
 * @Date 2018/5/11
 * @category AVL树操作
 */
public class AvlTree<E extends Comparable> {

    /**
     * 根节点
     */
    private Node root;
    /**
     * AVL树高度
     */
    private int hight;

    private static final class Node<E> {
        /**
         * 元素值
         */
        E element;
        /**
         * 左节点
         */
        Node left;
        /**
         * 右节点
         */
        Node right;
        /**
         * 节点高度
         */
        int nodeHight;

        public Node(E element) {
            this(element, null, null);
        }
        public Node(E element, Node left, Node right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }
    }

    public void add(E e) {
        if (e != null)
            this.root = insert(e, root);
    }

    private Node insert (E e, Node node) {
        if (node == null) {
            return new Node(e);
        }
        int compare = e.compareTo(node.element);
        if (compare < 0) {
            node.left = insert(e, node.left);
        }else if (compare > 0) {
            node.right = insert(e, node.right);
        } else {

        }
        return banlance(node);
    }

    private Node banlance(Node node) {
        if (node == null) {
            return node;
        }
        if (hight(node.left) - hight(node.right) > 1) {
            if (hight(node.left.left) >= hight(node.left.right)) {
                node = this.rotateWithLeftChild(node);
            } else {
                node = this.doubleRotateWithLeftChild(node);
            }
        } else if (hight(node.right) - hight(node.left) > 1) {
            if (hight(node.right.right) >= hight(node.right.left)) {
                node = this.rotateWithRightChild(node);
            } else {
                node = this.doubleRotateWithRightChild(node);
            }

        }
        node.nodeHight = Math.max(hight(node.left), hight(node.right)) + 1;
        return node;
    }

    private Node doubleRotateWithLeftChild(Node node) {
        node.left = this.rotateWithRightChild(node.left);
       return rotateWithLeftChild(node);
    }

    private Node doubleRotateWithRightChild(Node node) {
        node.right = this.rotateWithLeftChild(node.right);
        return rotateWithRightChild(node);
    }

    private Node rotateWithRightChild(Node oldNode) {
        Node  newNode = oldNode.right;
        oldNode.right = newNode.left;
        newNode.left = oldNode;
        oldNode.nodeHight = Math.max(hight(oldNode.left), hight(oldNode.right)) + 1;
        newNode.nodeHight = Math.max(hight(newNode.right), oldNode.nodeHight) + 1;
        return newNode;
    }

    private Node rotateWithLeftChild(Node oldNode) {
        Node newNode = oldNode.left;
        oldNode.left = newNode.right;
        newNode.right = oldNode;
        oldNode.nodeHight = Math.max(hight(oldNode.left), hight(oldNode.right)) + 1;
        newNode.nodeHight = Math.max(hight(newNode.left), oldNode.nodeHight) + 1;
        return newNode;
    }
    private int hight(Node node) {
        return node == null ? -1 : node.nodeHight;
    }
}
