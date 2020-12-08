package com.ybin.arithmetic.leetcode.training;

import com.ybin.btree.BtreeLinked;
import com.ybin.link.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class December {

    /**
     * 一种消息接收并打印的结构:
     * 序号是1-n,如果上次出现的是n,则在n+1到来后打印后续连续的,类似视频播放
     *
     * @param seq
     * @param value
     * @return
     */
    private int waitPoint;
    private Map<Integer, Node<String>> headMap = new HashMap<>();
    private Map<Integer, Node<String>> tailMap = new HashMap<>();
    public void receive(int seq, String value) {
        if (seq < 1) {
            return;
        }
        Node<String> newNode = new Node<>(value);
        headMap.put(seq, newNode);
        tailMap.put(seq, newNode);
        if (tailMap.containsKey(seq - 1)) {
            Node<String> tail = tailMap.get(seq - 1);
            tail.next = newNode;
            tailMap.remove(seq - 1);
            headMap.remove(seq);
        }
        if (headMap.containsKey(seq + 1)) {
            newNode.next = headMap.get(seq + 1);
            tailMap.remove(seq);
            headMap.remove(seq + 1);
        }

        if (waitPoint + 1 == seq) {
            Node<String> node = headMap.get(waitPoint + 1);
            while (node != null) {
                System.out.println(node.data);
                waitPoint++;
                node = node.next;
            }
            headMap.remove(seq);
            tailMap.remove(waitPoint);
        }
    }

    /**
     * 给定两个数组,每个元素代表货币值,arr1可重复拿多个,arr2每个货币只能取一个
     * 求拿到target货币的可能数
     *
     * @param arr1
     * @param arr2
     * @param target
     * @return
     */
    public int getMoneyTarget(int[] arr1, int[] arr2, int target) {
        if (arr1.length == 0 && arr2.length == 0) {
            return 0;
        }
        int[][] deDp = getMoneyByArr1(arr1, target);
        int[][] sigDp = getMoneyByArr2(arr2, target);
        if (deDp == null) {
            return sigDp[arr2.length - 1][target];
        }
        if (sigDp == null) {
            return deDp[arr1.length - 1][target];
        }
        int res = 0;
        for (int i = 0; i <= target; i++) {
            res += deDp[arr1.length - 1][i] * sigDp[arr2.length - 1][target - i];
        }
        return res;
    }

    private int[][] getMoneyByArr1(int[] arr1, int target) {
        if (arr1.length == 0) {
            return null;
        }
        int[][] dp = new int[arr1.length][target];
        // 0列
        for (int i = 0; i < arr1.length; i++) {
            // 拿改位置的货币使总金额达到0
            dp[i][0] = 1;
        }
        for (int j = 1; arr1[0] * j <= target; j++) {
            dp[0][arr1[0] * j] = 1;
        }
        for (int i = 1; i < arr1.length; i++) {
            for (int j = 1; j < target; j++) {
                // 要达到j钱数,但是不那当前i位置的货币
                dp[i][j] = dp[i - 1][j];
                // 要达到j钱数,但是必须拿当前j位置的货币,有可能拿一张,2张,3张...n张,等于n*arr[i]+dp[i-1][j-n*arr[i]] = target
                // dp[i][j - arr1[i]] 等于 dp[i - 1][j - arr1[i]] + dp[i][j - 2arr1[i]]
                dp[i][j] += dp[i][j - arr1[i]];
            }
        }
        return dp;
    }

    private int[][] getMoneyByArr2(int[] arr2, int money) {
        if (arr2.length == 0) {
            return null;
        }
        int[][] dp = new int[arr2.length][money];
        for (int i = 0; i < arr2.length; i++) {
            // 拿改位置的货币使总金额达到0
            dp[i][0] = 1;
        }
        for (int j = 1; arr2[0] * j <= money; j++) {
            dp[0][arr2[0] * j] = 1;
        }
        for (int i = 1; i < arr2.length; i++) {
            for (int j = 1; j <= money; j++) {
                dp[i][j] = dp[i - 1][j];
                dp[i][j] = j - arr2[i] >= 0 ? dp[i - 1][j - arr2[i]] : 0;
            }
        }
        return dp;
    }

    /**
     * 给定一个无序数组,求一个最长子数组的长度,满足左右绝对值为1
     *
     * @param arr
     * @return
     */
    public int subArray(int[] arr) {
        if (arr.length <= 1) {
            return 1;
        }
        int len = 0;
        int min;
        int max;
        Set<Integer> dep;
        for (int i = 0; i < arr.length; i++) {
            min = arr[i];
            max = arr[i];
            dep = new HashSet<>();
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] - arr[j - 1] != 1) {
                    break;
                }
                if (dep.contains(arr[j])) {
                    break;
                }
                min = Math.min(min, arr[j]);
                max = Math.max(max, arr[j]);
                dep.add(arr[j]);
                if (max - min == j - i) {
                    len = Math.max(len, max - min + 1);
                }
            }
        }
        return len;
    }

    /**
     * 给定一个二叉树,求二叉树中最大连续递增节点的数量
     *
     * @param node
     * @return
     */
    public int dpTree(BtreeLinked.Node node) {
        if (node == null) {
            return 0;
        }
        DpTreeNode dpTreeNode = this.dpTree1(node);
        if (dpTreeNode == null) {
            return 1;
        } else {
            return dpTreeNode.len;
        }
    }
    private class DpTreeNode {
        private int value;
        private int len;

        public int getValue() {
            return value;
        }

        public int getLen() {
            return len;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public void setLen(int len) {
            this.len = len;
        }
    }
    private DpTreeNode dpTree1(BtreeLinked.Node node) {
        if (node == null) {
            return null;
        }

        DpTreeNode left = dpTree1(node.getLeftNode());
        DpTreeNode right = dpTree1(node.getRightNode());
        int leftLen = left != null && left.value - 1 == node.getItem() ? left.len + 1 : left.len;
        int rightLen = right != null && right.value - 1 == node.getItem() ?  right.len + 1 :  right.len;
        DpTreeNode dpTreeNode = new DpTreeNode();
        dpTreeNode.setValue(node.getItem());
        if (leftLen > rightLen) {
            dpTreeNode.setLen(leftLen);
        } else {
            dpTreeNode.setLen(rightLen);
        }
        return dpTreeNode;
    }

    public static void main(String[] args) {
        new December().subArray(new int[]{3,4,1,2,3,4,6});
    }
}
