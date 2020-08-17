package com.ybin.btree;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author yuebing
 * @version 1.0 2017/9/4
 * @Description 链表实现二叉树,二叉树每层最多2^(n-1)各节点，深度为k的二叉树最多有2^k-1个节点。
 */
@Data
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

    public Integer[] wide(Node node) {
        if (node == null || (node.leftNode == null && node.rightNode == null)) {
            return null;
        }
        Queue<Node> queue = new LinkedList<>();

        List<Integer> con = new ArrayList<>();
        Node cur = node;
        con.add(cur.item);
        for (; cur != null; cur = queue.poll()) {
            if (cur.leftNode != null) {
                queue.offer(cur.leftNode);
                con.add(cur.leftNode.item);
            } else {

                con.add(null);
            }
            if (cur.rightNode != null) {
                queue.offer(cur.rightNode);
                con.add(cur.rightNode.item);
            } else {
                con.add(null);
            }
        }
        Integer[] a =  con.toArray(new Integer[con.size()]);
        for (int index = 0; index < a.length; index++) {
            System.out.printf("结果: 下标" + index + " 值: " + a[index]);
        }
        return a;
    }

    @Data
    public class NodeInfo{

        private boolean isBalance;

        private int height;

        public NodeInfo(boolean isBalance, int height) {
            this.isBalance = isBalance;
            this.height = height;
        }
    }

    public NodeInfo balance(Node node) {
        if (node == null) {
            return new NodeInfo(true, 0);
        }
        NodeInfo left = balance(node.leftNode);
        NodeInfo right = balance(node.rightNode);
        int height = Math.max(left.height, right.height) + 1;
        boolean isBalance = false;
        if (left.isBalance && right.isBalance && Math.abs((left.height - right.height)) <= 1) {
            isBalance = true;
        }
        return new NodeInfo(isBalance, height);
    }

    @Data
    public class NodeBstInfo{

        private boolean isBst;

        private int maxSize;

        private int max;

        private int min;

        public NodeBstInfo(boolean isBst, int maxSize, int max, int min) {
            this.isBst = isBst;
            this.maxSize = maxSize;
            this.max = max;
            this.min = min;
        }
    }

    /**
     * 查找已node为头节点的子树中,满足搜索树的所有节点之和
     *
     * @param node
     * @return
     */
    public NodeBstInfo bstMaxSize(Node node) {
        if (node == null) {
            return null;
        }
        NodeBstInfo left = bstMaxSize(node.leftNode);
        NodeBstInfo right = bstMaxSize(node.leftNode);
        boolean isBst = false;
        int maxSize = 0;
        int max = node.item;
        int min = node.item;

        if (left != null) {
            maxSize = left.maxSize;
            max = Math.max(max, left.maxSize);
            min = Math.min(min, left.min);
        }
        if (right != null) {
            maxSize = Math.max(maxSize, right.maxSize);
            max = Math.max(max, right.maxSize);
            min = Math.min(min, right.min);
        }

        if (left == null ? true : (left.isBst && left.max < node.item)
            && right == null ? true : (right.isBst && right.min > node.item)
        ) {
            isBst = true;
            maxSize = (left == null ? 0 : left.maxSize) + (right == null ? 0 : right.maxSize) + 1;
            max = Math.max(max, left == null ? 0 : left.max);
            min = Math.min(min, right == null ? 0 : right.min);
        }

        return new NodeBstInfo(isBst, maxSize, max, min);
    }

    @Data
    public class NodeHappy{

        private int happy;

        private List<NodeHappy> nodeHappies;
    }

    @Data
    public class NodeHappyInfo{
        /**
         * 自己去时,下属快乐值是多少
         */
        private int yes;
        /**
         * 自己不去时,下属快乐值
         */
        private int no;

        public NodeHappyInfo(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }

    /**
     * 一个管理可以管理若干员工,当公司开年会时,管理去时,他的直接下级不能去,当管理不去时,下级可去也可不去
     * 求能去的最大人数
     *
     * @param nodeHappy
     * @return
     */
    public NodeHappyInfo happy(NodeHappy nodeHappy) {
        if (nodeHappy == null) {
            return new NodeHappyInfo(0, 0);
        }
        if (nodeHappy.getNodeHappies() == null) {
            return new NodeHappyInfo(1, 0);
        }

        int yes = 0;
        int no = 0;
        for (NodeHappy happy : nodeHappy.getNodeHappies()) {
            NodeHappyInfo nodeHappyInfo = happy(happy);
            // 当当前节点去时,其最近节点只能不去;
            yes = yes + nodeHappyInfo.no;
            // 当当前节点不去时,去子节点不去和去的节点的最大值
            no = yes + Math.max(nodeHappyInfo.yes, nodeHappyInfo.no);
        }
        return new NodeHappyInfo(yes, no);
    }

    @Data
    public class NodeCompleteInfo {

        /**
         * 是否满二叉树
         */
        private boolean isFull;
        /**
         * 是否完全二叉树
         */
        private boolean isCbt;
        /**
         * 树高
         */
        private int height;

        public NodeCompleteInfo(boolean isFull, boolean isCbt, int height) {
            this.isFull = isFull;
            this.isCbt = isCbt;
            this.height = height;
        }
    }

    /**
     * 是否完全二叉树
     * 左右节点返回是否满二叉树,是否完全二叉树,左数,右数的高度
     * 满足节点X是完全二叉树,左树,右树必须是完全二叉树
     * 当整个树是满二叉树时,必须左树满二叉树,右树是满二叉树,且左树高度必须等于右数高度
     * 当左树是满二叉树时:1.右树必须是完全二叉树且左数高度必须=右数高度+1;2.右树必须是满二叉树且左数高度必须=右数高度+1
     * 当左数是完全二叉树时：1.右树必须是满二叉树且,左数高度=右树高度+1;
     *
     *
     * @param node
     * @return
     */
    public NodeCompleteInfo isCbt(Node node) {
        if (node == null) {
            return new NodeCompleteInfo(true, true, 0);
        }
        NodeCompleteInfo left = isCbt(node.leftNode);
        NodeCompleteInfo right = isCbt(node.rightNode);
        int height = Math.max(left.getHeight(), right.height) + 1;
        boolean isFull = false;
        if ((left.isFull && right.isFull) && (left.height == right.height)) {
            isFull = true;
        }
        boolean isCbt = false;
        if (isFull) {
            isCbt = true;
        } else if (left.isCbt && right.isCbt) {
            if (left.isFull && right.isFull && left.height == right.height + 1) {
                isCbt = true;
            }
            if (right.isFull && left.height == right.height + 1) {
                isCbt = true;
            }
            if (left.isFull && left.height == right.height) {
                isCbt = true;
            }
        }
        return new NodeCompleteInfo(isFull, isCbt, height);
    }

    public class NodePublic {

        /**
         * 第一个相交的节点
         */
        private Node publicNode;
        /**
         * 是否有A
         */
        private boolean isContainA;
        /**
         * 是个有B
         */
        private boolean isContainB;

        public NodePublic(Node publicNode, boolean isContainA, boolean isContainB) {
            this.publicNode = publicNode;
            this.isContainA = isContainA;
            this.isContainB = isContainB;
        }
    }

    /**
     * 给定二叉树中一个节点,找其子树中,a,b第一个相交的节点
     *
     * @param head
     * @param a
     * @param b
     * @return
     */
    public NodePublic publicNode(Node head, Node a, Node b) {
        if (head == null) {
            return new NodePublic(null, false, false);
        }
        NodePublic left = publicNode(head.leftNode, a, b);
        NodePublic right = publicNode(head.rightNode, a, b);
        // a,b出现在左树
        if (left.isContainA && left.isContainB) {
            return new NodePublic(left.publicNode, true, true);
        }
        // a,b出现在右树
        if (right.isContainA && right.isContainB) {
            return new NodePublic(right.publicNode, true, true);
        }
        // a出现在左树,b出现在右树;a出现在右树,b出现在左树
        if ((left.isContainA && right.isContainB) || (left.isContainB && right.isContainA)) {
            return new NodePublic(head, true, true);
        }
        // 左树,右树都没有
        return new NodePublic(null, false, false);
    }
}
