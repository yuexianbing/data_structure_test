package com.ybin.arithmetic.leetcode.training;

import com.ybin.btree.BtreeLinked;
import com.ybin.link.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : bing.yue001
 * @version : 1.0
 * @date : 2020-10-15 19:39
 * @description :
 */
public class TwentyNovember {

    public int[] target(int[] arr, int target) {
        if (arr == null) {
            return new int[]{0};
        }
        Map<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
//            for (int j = i + 1; j < arr.length; j++) {
//                if (arr[i] + arr[j] == target) {
//                    return new int[]{i, j};
//                }
//            }
            if (hashMap.containsKey(target - arr[i])) {
                return new int[]{i, hashMap.get(target - arr[i])};
            }
            hashMap.put(arr[i], i);
        }
        return new int[]{0};
    }

    /**
     * 数组预处理
     * 数组中不是0就是1
     * 求数组中存在用1表示边长的最大正方形
     *
     * @param arr
     * @return
     */
    public int square(int[][] arr) {
        if (arr == null) {
            return 0;
        }
        // 定义当前i 点到右边际的最多的连续1
        int[][] right = new int[arr.length][arr.length];
        // 定义当前i 点到最下边际最多的连续的1
        int[][] down = new int[arr.length][arr.length];
        // 最右列的值
        right[arr.length - 1][arr[arr.length - 1].length] = arr[arr.length - 1][arr[arr.length - 1].length] == 1 ? 1 : 0;
        down[arr.length - 1][arr[arr.length - 1].length] = arr[arr.length - 1][arr[arr.length - 1].length] == 1 ? 1 : 0;
        for (int i = arr.length - 2; i >= 0; i--) {
            right[i][arr.length - 1] = arr[i][arr.length - 1] == 1 ? 1 : 0;
            down[i][arr.length - 1] = arr[i][arr.length - 1] == 1 ? 1 + arr[i + 1][arr.length - 1] : 0;
        }
        for (int j = arr[arr.length - 1].length - 1; j >=0 ; j--) {
            down[arr.length - 1][j] = arr[arr.length - 1][j] == 1 ? 1 : 0;
            right[arr.length  - 1][j] = arr[arr.length - 1][j] == 1 ? 1 + down[arr.length - 1][j + 1] : 0;
        }

        for (int i = arr.length - 2; i >= 0; i--) {
            for (int j = arr[i].length - 2; j >= 0; j--) {
                right[i][j] = arr[i][j] == 1 ? 1 + right[i][j + 1] : 0;
                down[i][j] = arr[i][j] == 1 ? 1 + down[i + 1][j] : 0;
            }
        }
        int max = -1;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                int side = right[i][j];
                for (; side >= 1; side--) {
                    int colSideIndex = i + side;
                    int rowSideIndex = j + side;
                    if (down[i][j] >= side && right[colSideIndex][j] >= side && down[i][rowSideIndex] >= side) {
                        max = Math.max(max, side);
                    }
                }

            }
        }
        return max;
    }

    /**
     * 给一个指定长度,构建一个指定长度的数组,使任意位置 i,j,k,i<j<k都满足arr[i]+arr[k]!=2arr[j]
     *
     * @param target
     * @return
     */
    public int[] buildArray(int target) {
        if (target == 1) {
            return new int[]{1};
        }
        if (target == 2) {
            return new int[]{1, 2};
        }
        int halfIndex = (target + 1) / 2;
        int[] nextResult = buildArray(halfIndex);
        int[] result = new int[target];
        // 验证？
        for (int i = 0; i < halfIndex; i++) {
            result[i] = 2 * nextResult[i] + 1;

        }
        for (int i = halfIndex; i < target; i++) {
            result[i] = 2 * result[i - halfIndex];
        }
        return result;
    }

    public class NodeBigData {
        private int allTreeData;
        private int fromMaxTreeData;

        public NodeBigData(int allTreeData, int fromMaxTreeData) {
            this.allTreeData = allTreeData;
            this.fromMaxTreeData = fromMaxTreeData;
        }

        public int getAllTreeData() {
            return allTreeData;
        }

        public int getFromMaxTreeData() {
            return fromMaxTreeData;
        }
    }

    /**
     * 给定一个节点,求该节点到任意节点的最大路径和
     *
     * @param node
     * @return
     */
    public Integer treeBigData(BtreeLinked.Node node) {
        if (node == null) {
            return null;
        }
        NodeBigData nodeBigData = recursionTreeBigData(node);
        if (nodeBigData != null) {
            return Math.max(nodeBigData.allTreeData, nodeBigData.fromMaxTreeData);
        }
        return null;
    }

    private NodeBigData recursionTreeBigData(BtreeLinked.Node node) {
        if (node == null) {
            return null;
        }
        // 1.不走node点
        NodeBigData left = recursionTreeBigData(node.getLeftNode());
        NodeBigData right = recursionTreeBigData(node.getRightNode());
        // 左树节点最大值
        int leftTreeValue = Integer.MIN_VALUE;
        if (left != null) {
            leftTreeValue = left.allTreeData;
        }
        int rightTreeValue = Integer.MIN_VALUE;
        if (right != null) {
            rightTreeValue = right.allTreeData;
        }

        // 2.只走node点
        int headValue = node.getItem();
        // 3.走node点的左树最大值
        int fromHeadLeft = Integer.MIN_VALUE;
        if (left != null) {
            fromHeadLeft = headValue + left.fromMaxTreeData;
        }
        int fromHeadRight = Integer.MIN_VALUE;
        if (right != null) {
            fromHeadRight = headValue + right.fromMaxTreeData;
        }
        int allPath = Integer.MIN_VALUE;
        if (left != null && right != null) {
            allPath = headValue + left.fromMaxTreeData + right.fromMaxTreeData;
        }
        int maxAllData = Math.max(leftTreeValue, rightTreeValue);
        int maxFromMaxTreeData = Math.max(headValue, fromHeadLeft);
        maxFromMaxTreeData = Math.max(maxFromMaxTreeData, fromHeadRight);
        maxFromMaxTreeData = Math.max(allPath, maxFromMaxTreeData);
        return new NodeBigData(maxAllData, maxFromMaxTreeData);
    }

    /**
     * 有n套包装设备,且每套设备已经分配了一定数量的玩具,包装设备要在每套设备都拿到相同的玩具数量后才开始运作;
     * 假定每个玩具都很重,每次包装设备都只能往左或者往右搬一个,求使包装设备得到同等数量的玩具的最小分配次数;
     *
     * @param arr
     * @return
     */
    public int minAllocate(int[] arr) {
        if (arr == null) {
            return -1;
        }
        int totalNum = 0;
        for (int i = 0; i < arr.length; i++) {
            totalNum += arr[i];
        }
        if (totalNum % arr.length != 0) {
            return -1;
        }
        // 每个设备需要的数量
        int eachNum = totalNum / arr.length;
        int leftNum = 0;
        int result = Integer.MAX_VALUE;
        for (int i = 0; i< arr.length; i++) {
            leftNum += arr[i];
            int leftTotal = leftNum - i * eachNum;
            int rightTotal = (totalNum - arr[i] - leftTotal) - (arr.length - i - 1) * eachNum;
            // i 的左边与右边都小于0时,结果是两者的绝对值之和与已计算的result
            if (leftTotal < 0 && rightTotal < 0) {
                result = Math.min(result, Math.abs(leftTotal) + Math.abs(rightTotal));
            } else {
                result = Math.min(result, Math.min(Math.abs(leftTotal), Math.abs(rightTotal)));
            }
        }
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    public Node reorderList(Node node) {
        if (node == null) {
            return node;
        }
        // 1->2->3
        // 1<-2->3
        Node quick = node;
        Node slow = node;
        while (quick.next != null && quick.next.next != null) {
            quick = quick.next.next;
            slow = slow.next;
        }
        // 反转slow 后的链表
        Node reordNode = slow.next;
        slow.next = null;
        Node pre = null;
        while (reordNode != null) {
            Node next = reordNode.next;
            reordNode.next = pre;
            pre = reordNode;
            reordNode = next;
        }
        Node cur1 = node;
        Node cur2 = pre;
        Node head = cur1;
        Node t1;
        Node t2;
        // null-1-2-3
        // 6-5-4
        // 1-4
        while (cur1 != null && cur2 != null) {
            t1 = cur1.next;
            t2 = cur2.next;

            cur1.next = cur2;
            cur1 = t1;

            cur2.next = cur1;
            cur2 = t2;
        }
        return head;
    }
}

