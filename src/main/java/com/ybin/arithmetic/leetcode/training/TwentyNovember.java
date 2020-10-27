package com.ybin.arithmetic.leetcode.training;

import com.ybin.btree.BtreeLinked;
import com.ybin.link.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

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

    /**
     * 给定链表 1>2>3>4>5>6
     * 转换后:1>6>2>5>3>4
     *
     * @param node
     * @return
     */
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

    /**
     * 给定一个数组,假定数组元素值代表高度,宽度是1,则可以组成一个矩形列,如果i位置的左边与右边均比i高,则i可以放入水,
     * Math.min(maxLeft, MaxRight) - i求能放入多少水
     *
     * 双指针法
     * 6 7 2 7 8 5 9
     * @param arr
     * @return
     */
    public int water(int[] arr) {
        if (arr == null) {
            return 0;
        }
        int leftMax = arr[0];
        int rightMax = arr[arr.length - 1];
        int left = 1;
        int right = arr.length - 2;
        int water = 0;
        while (left <= right) {
            if (leftMax <= rightMax) {
                water += Math.max(0, arr[left] - leftMax);
                leftMax = Math.max(leftMax, arr[left++]);
            } else {
                water += Math.max(0, arr[right] - rightMax);
                rightMax = Math.max(rightMax, arr[right--]);
            }
        }
        return water;
    }

    private class ArrayNode {
        private Integer value;
        private int row;
        private int col;

        public ArrayNode(Integer value, int row, int col) {
            this.value = value;
            this.row = row;
            this.col = col;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getCol() {
            return col;
        }

        public void setCol(int col) {
            this.col = col;
        }
    }

    // com.ybin.arithmetic.leetcode.training.TwentyNovember.water 的变种
    public int water1(int[][] arr) {
        if (arr == null) {
            return 0;
        }
        // 1.四边放入小根堆,和已放入标识boolean数组;
        PriorityQueue<ArrayNode> priorityQueue = new PriorityQueue<>();
        boolean[][] isEn = new boolean[arr.length][arr[0].length];
        // 第一行
        for (int i = 0; i < arr[0].length; i++) {
            priorityQueue.add(new ArrayNode(arr[0][i], 0, i));
            isEn[0][i] = true;
        }
        // 最后一列
        for (int i = 0; i < arr.length; i++) {
            priorityQueue.add(new ArrayNode(arr[i][arr.length - 1], i, arr.length - 1));
            isEn[i][arr.length - 1] = true;
        }
        // 最后一行
        for (int i = 0; i < arr[arr.length - 1].length; i++) {
            priorityQueue.add(new ArrayNode(arr[arr.length - 1][i], arr.length - 1, i));
            isEn[arr.length - 1][i] = true;
        }
        for (int i = 0; i < arr.length; i++) {
            priorityQueue.add(new ArrayNode(arr[i][0], i, 0));
            isEn[i][0] = true;
        }
        int water = 0;
        int curMin = 0;
        // 2.从小顶堆弹出堆顶,从弹出的元素位置出发,计算上下左右,如果在boolean数组存在则不计算,
        // 利用优先级队列,每次让人的元素,栈顶最小,依次弹出,求其上下左右能不能把水留出去,每次都是最小值向外扩
        while (!priorityQueue.isEmpty()) {
            ArrayNode node = priorityQueue.poll();
            curMin = Math.max(node.value, curMin);
            int row = node.getRow();
            int col = node.getCol();
            // 如果不存在则将该点放入,并一次向上下左右扩散,直到队列为空;
            // 上
            if (row + 1 < arr.length && !isEn[row + 1][col]) {
                // 3.每次放入堆的时候结算,更新最大值,并用最大值-相邻元素
                water += Math.max(0, curMin - arr[row + 1][col]);
                isEn[row + 1][col] = true;
                priorityQueue.add(new ArrayNode(arr[row + 1][col], row + 1, col));
            }
            // 下
            if (row - 1 >= 0 && !isEn[row - 1][col]) {
                water += Math.max(0, curMin - arr[row - 1][col]);
                isEn[row - 1][col] = true;
                priorityQueue.add(new ArrayNode(arr[row - 1][col], row - 1, col));
            }
            // 左
            if (col - 1 >= 0 && !isEn[row][col - 1]) {
                water += Math.max(0, curMin - arr[row][col - 1]);
                isEn[row][col  - 1] = true;
                priorityQueue.add(new ArrayNode(arr[row][col  - 1], row, col - 1));
            }
            // 右
            if (col + 1 <= arr[0].length && !isEn[row][col + 1]) {
                water += Math.max(0, curMin - arr[row][col + 1]);
                isEn[row][col  + 1] = true;
                priorityQueue.add(new ArrayNode(arr[row][col + 1], row, col + 1));
            }

        }
        return water;
    }

    /**
     * 有序数组arr,目标值target,求的结果集满足 两数之和 = target
     *
     * 双指针
     *
     * @param arr
     * @param target
     * @return
     */
    public List<List<Integer>> sumEqualTarget(int[] arr, int target) {
        if (arr == null) {
            return null;
        }
        List<List<Integer>> result = new ArrayList<>();
        // 双指针
        int left = 0;
        int right = arr.length - 1;
        for (;left < right;) {
            if ((left - 1 > 0 && arr[left - 1] == arr[left]
            || (right + 1 < arr.length - 1 && arr[right + 1] == arr[right]))) {
                continue;
            }
            if (arr[left] + arr[right] > target) {
                right--;
            } else if (arr[left] + arr[right] < target) {
                left++;
            } else {
                List<Integer> r = new ArrayList<>(2);
                r.add(left);
                r.add(right);
                result.add(r);
            }
        }
        return result;
    }

    /**
     * com.ybin.arithmetic.leetcode.training.TwentyNovember#sumEqualTarget 的变种
     * 求三数之和满足target
     *
     * @param arr
     * @param target
     * @return
     */
    public List<List<Integer>> sumEqualTarget1(int[] arr, int target) {
        if (arr == null) {
            return null;
        }
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < arr.length; i ++) {
            int tar = target - arr[i];
            List<List<Integer>> re = this.sumEqualTarget(arr, tar);
            if (re != null) {
                for (int j = 0; j < re.size(); j++) {
                    re.get(j).add(i);
                    result.add(re.get(j));
                }
            }
        }
        return result;
    }
}

