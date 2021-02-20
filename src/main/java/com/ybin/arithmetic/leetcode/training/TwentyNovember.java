package com.ybin.arithmetic.leetcode.training;

import com.ybin.btree.BtreeLinked;
import com.ybin.link.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

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
     * 2 3 4
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
     * 6 7 2 7 8 5 4
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
        int right = arr.length - 1;
        int water = 0;
        while (left <= right) {
            if (leftMax <= rightMax) {
                water += Math.max(0, leftMax - arr[left]);
                leftMax = Math.max(leftMax, arr[left++]);
            } else {
                water += Math.max(0, rightMax - arr[right]);
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

    /**
     *
     * @see com.ybin.arithmetic.leetcode.training.TwentyNovember.water
     *
     * 二维矩阵幸成洼地,求最大蓄水量
     *
     * @param arr
     * @return
     */
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
        if (arr.length == 0) {
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
        if (arr.length == 0) {
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

    /**
     * 实现获取下一个排列的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。
     *
     * 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
     *
     * 必须原地修改，只允许使用额外常数空间。
     *
     * 以下是一些例子，输入位于左侧列，其相应输出位于右侧列。
     * 1,2,3 → 1,3,2
     * 3,2,1 → 1,2,3
     * 1,1,5 → 1,5,1
     *
     */
    public void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1);
    }

    private void reverse(int[] nums, int start) {
        int i = start, j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 单词TopK
     *
     * @param str
     * @param k
     * @return
     */
    public List<String> topK(String[] str, int k) {
        if (k == 0) {
            return null;
        }
        if (str == null || str.length == 0) {
            return null;
        }
        Map<String, Integer> map = new HashMap<>();
        for (String s : str) {
            Integer count = map.get(s);
            if (count == null) {
                map.put(s, 1);
            } else {
                count++;
                map.put(s, count);
            }
        }
        PriorityQueue<String> queue = new PriorityQueue<>(k);
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (queue.size() >= k) {
                if (map.get(queue.peek()) > entry.getValue()) {
                    queue.poll();
                    queue.add(entry.getKey());
                }
            } else {
                queue.add(entry.getKey());
            }
        }
        return new ArrayList<>(queue);
    }

    private class TrieNode {

        private String path;

        private TreeMap<String, TrieNode> treeMap;

        public TrieNode(String path) {
            this.path = path;
            treeMap = new TreeMap<>();
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public TreeMap<String, TrieNode> getTreeMap() {
            return treeMap;
        }

        public void setTreeMap(TreeMap<String, TrieNode> treeMap) {
            this.treeMap = treeMap;
        }
    }

    /**
     * 字符串 b\se, d\f\g, a\j\k
     * 按目录树打印
     *
     * 1.构建前缀树
     * 2.深度优先遍历
     *
     * @param str
     */
    public void treePrint(String[] str) {
        if (str == null) {
            return;
        }
        TrieNode head = new TrieNode("");
        for (String s : str) {
            String[] sts = s.split("\\\\");
            TrieNode cur = head;
            for (String st : sts) {
                if (!cur.treeMap.containsKey(st)) {
                    cur.treeMap.put(st, new TrieNode(st));
                }
                cur = cur.treeMap.get(st);
            }
        }
        print(head, "");

    }

    private void print(TrieNode head, String pre) {
        if (head == null) {
            return;
        }
        TreeMap<String, TrieNode> map = head.getTreeMap();
        System.out.println(pre + head.path);

        Set<String> keys = map.keySet();
        for (String key : keys){
            TrieNode trieNode = map.get(key);
            print(trieNode, pre + " ");
        }

    }

    /**
     * 求数组的最长递增子序列的最大长度
     * 3 4 1 2 0 6
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];

        int resultMax = 0;
        for (int i = 0; i < nums.length; i++) {
            int max = 0;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    max = Math.max(max, dp[j]);
                }
            }
             dp[i] = max + 1;
            resultMax = Math.max(resultMax, dp[i]);
        }
        return resultMax;
    }

    public int[] sub1(int[] arr) {
        if (arr.length == 0) {
            return new int[]{0};
        }
        int[] dp = new int[]{arr.length};
        dp[0] = 1;
        // 装当前长度的结尾的最小数值
        int[] ends = new int[]{arr.length};
        ends[0] = arr[0];
        int right = 0;
        int l;
        int r;
        for (int i = 1; i < arr.length; i++) {
            l = 0;
            r = right;
            while (l <= r) {
                int mid = (l + r) / 2;
                if (arr[i] > ends[mid]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            right = Math.max(l, right);
            ends[l] = arr[i];
            dp[i] = l + 1;
        }
        return dp;
    }

    /**
     * 数组arr，求子数组最大累加的子串;
     * 如果全为负数,成立
     * 如果有正有负数：
     * 假定 数组中最大子数组的位置在i,j,则k-i-1的和一定小于0,k>i 且<j 则不会存在i-k <0 ,不然最大子数组,i到j不成立
     * 所以到i时，cur<0，cur重新记数
     *
     * @param arr
     * @return
     */
    public int sumSub(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        int cur = 0;
        for (int i = 0; i < arr.length; i++) {
            cur = arr[i] + cur;
            max = Math.max(max, cur);
            if (cur < 0) {
                cur = 0;
            }
        }
        return max;
    }

    /**
     * 二维数组求子数组的最大累加和
     *
     * pre 是前几行每行相同列累加后的值,交替替换,每次从i换行时都从新初始化pre
     * 即最后是将每行相同列累加压缩后成为一维数组,求累加和,类似于上面求和
     * O(n2*K)
     * @param arr
     * @return
     */
    public int sumSub(int[][] arr) {
        if (arr.length == 0 || arr[0].length == 0) {
            return 0;
        }

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int[] pre = new int[arr[i].length];
            for (int j = i + 1; j < arr.length; j++) {
                int cur = 0;
                for (int k = 0; k < arr.length; k++) {
                    pre[k] = pre[k] + arr[j][k];
                    cur += pre[k];
                    max = Math.max(max, cur);
                    if (cur < 0) {
                        cur = 0;
                    }
                }
            }
        }
        return max;
    }

    /**
     * 给定 前序树及中序树,求后续树
     *
     * @param t1
     * @param t2
     * @return
     */
    public int[] postTree(int[] t1, int[] t2) {
        if (t1.length == 0 || t2.length == 0 || t1.length != t2.length) {
            return new int[]{0};
        }
        int[] result = new int[t1.length];
        return this.postTree(t1, 0, t1.length, t2, 0, t2.length, result, 0, result.length);
    }

    private int[] postTree(int[] t1, int startT1, int endT1,
                           int[] t2, int startT2, int endT2,
                           int[] t3, int startT3, int endT3) {
        if (startT1 == endT1) {
            t3[endT3] = t1[startT1];
            return t3;
        }
        if (startT1 == 0) {
            t3[endT3] = t1[startT1];
            return t3;
        }
        int rootIndex = 0;
        for (int i = startT2; i <= endT2; i++) {
            if (t1[startT1] == t2[i]) {
                rootIndex = i;
                break;
            }
        }
        int leftLength = rootIndex - startT2;
        int rightLength = endT2 - rootIndex;
        if (leftLength > 0) {
            postTree(t1, startT1 + 1, startT1 + leftLength, t2, startT2, startT2 + leftLength, t3, startT3, startT3 + leftLength);
        }

        if (rightLength > 0) {
            postTree(t1, startT1 + leftLength + 1, endT1, t2, rootIndex + 1, endT2, t3, startT3 + leftLength + 1, endT3);

        }
        return t3;
    }


    /**
     * 给定字符串s1,s2，将s1调整为s2,插入一个字符的代价为ic,删除一个字符为dc,替换一个字符为rc
     * 求最后返回的调整后字符串的最小代价
     *
     * @param s1
     * @param s2
     * @param ic
     * @param dc
     * @param rc
     * @return
     */
    public int subStringAdjust(String s1, String s2, int ic, int dc, int rc) {
        if (s1 == null || s2 == null) {
            return 0;
        }
        int n = s1.length() + 1;
        int m = s2.length() + 1;
        char[] sc1 = s1.toCharArray();
        char[] sc2 = s2.toCharArray();
        int[][] dp = new int[n][m];
        // s2做列,s1变成s2,s1的首字符空字符串,空字符串变为以col 结尾的字符串则需要ic * col个代价
        for (int col = 0; col < m; col++) {
            dp[0][col] = ic * col;
        }
        // s1做行,s1变成s2,s2的首字符空字符串,s1以cow结尾变为空字符串组需要删除 需要dc * row个代价
        for (int row = 0; row < n; row++) {
            dp[row][0] = dc * row;
        }
        for (int row = 1; row < n; row++) {
            for (int col = 1; col < m; col++) {
                // 存在的可能性
                // 1.以s1的row长度结尾,row对应到col, 如果相同则是复制,代价为0
                // 2.以s1的row长度结尾,row对应到col, 如果不相同则是替换,代价为rc
                // 3.不以s1的row长度结尾,row比col大, 代价为row - 1  后面的做删除
                // 4.不以s1的row长度结尾,row比col小, 代价为col - 1  后面的做插入

                if (sc1[row - 1] == sc2[col - 1]) {
                    dp[row][col] = dp[row - 1][col - 1];
                } else {
                    dp[row][col] = dp[row - 1][col - 1] + rc;
                }
                dp[row][col] = Math.min(dp[row][col], dp[row - 1][col] + dc);
                dp[row][col] = Math.min(dp[row][col], dp[row][col - 1] + ic);
            }
        }
        return dp[n - 1][m - 1];
    }


    /**
     *
     * 字符传start->to,每次都只能变化一个字符串,每次变化的字符串的结果都在str字符集合中,求start->to的所有路径
     *
     * @param start
     * @param to
     * @param str
     * @return
     */
    public List<List<String>> minPath(String start, String to, List<String> str) {
        if (start == null || to == null || start.length() != to.length() || str == null || str.size() == 0) {
            return null;
        }

        str.add(start);
        // 将每个字符串的邻居放入该map,构建图
        Map<String, List<String>> nextMap = new HashMap<>();

        for (int i = 0; i < str.size(); i++) {
            nextMap.put(str.get(i), new ArrayList<>());
            char[] c = str.get(i).toCharArray();
            for (int k = 0; k < c.length; k++) {
                for (int j = 'a'; j <= 'z'; j++) {
                    if (c[k] != j) {
                        char temp = c[k];
                        c[k] = (char) j;
                        String re = String.valueOf(c);
                        if (!str.contains(re)) {
                            nextMap.get(str.get(i)).add(String.valueOf(c));
                        }
                        c[k] = temp;
                    }
                }
            }
        }

        // 各节点到 start 的距离 宽度优先遍历
        Map<String, Integer> distinanceMap = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        distinanceMap.put(start, 0);
        while (queue.size() > 0) {
            String cur = queue.poll();
            List<String> nextList = nextMap.get(cur);
            if (nextList == null) {
                continue;
            }
            for (int i = 0; i < nextList.size(); i++) {
                if (distinanceMap.get(nextList.get(i)) == null) {
                    distinanceMap.put(nextList.get(i), distinanceMap.get(cur) + 1);
                    queue.offer(nextList.get(i));
                }
            }
        }

        // 求所有的能到达的路径
        List<List<String>> result = new ArrayList<>();
        this.shortPath(start, to, new ArrayList<>(), nextMap, distinanceMap, result);
        // 最短路径及取其中最小的
        return result;
    }

    private List<List<String>> shortPath(String start, String to,
                                         List<String> path,
                                         Map<String, List<String>> nextMap,
                                         Map<String, Integer> distinanceMap,
                                         List<List<String>> result) {
        if (start.equals(to)) {
            result.add(path);
        }
        List<String> next = nextMap.get(start);
        if (next == null) {
            return null;
        }
        int cur = distinanceMap.get(start);
        for (String n : next) {
            if (cur + 1 == distinanceMap.get(n)) {
                path.add(n);
                shortPath(n, to, path, nextMap, distinanceMap, result);
            }

        }
        return result;
    }

    /**
     * 求给定数组的子数组最大异或和
     * a^b=c 则 c^a=b,c^b=a
     *
     * @param arr
     * @return
     */
    public int xorArrMax(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0];
        }
        // 数组预处理
        int[] xor = new int[arr.length];
        xor[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            xor[i] = arr[i] ^ xor[i - 1];
        }
        int max = xor[0];
        // 求已j结尾位置的最大子数组异或和
        for (int j = 1; j < arr.length; j++) {
            for (int i = 0; i < j; j++) {
                max = Math.max(max, xor[j] ^ xor[i]);
            }
        }
        return max;
    }

    /**
     * 两个有序数组,分别取两个数累加,求累加和的top个
     *
     * @param arr1
     * @param arr2
     * @param top
     * @return
     */
    public int[] getTopkSum(int[] arr1, int[] arr2, int top) {
        if (arr1.length == 0 && arr2.length == 0) {
            return new int[]{0};
        }
        top = Math.min(top, arr1.length * arr2.length);
        int[] topk = new int[top];
        // 两个数组中取值,都可以用一个样本做行一个样本做列
        int n = arr1.length;
        int m = arr2.length;
        int[][] dp = new int[n][m];

        boolean[][] exit = new boolean[n][m];

        PriorityQueue<TopkSumNode> queue = new PriorityQueue<>((o1, o2) -> o2.getSum() - o2.getSum());
        // 最大的数一定为右下角的数,及两个数组最后的位置相加
        queue.offer(new TopkSumNode(n - 1, m - 1, dp[n - 1][m - 1]));
        while (top > 0 && queue.size() > 0) {
            TopkSumNode max = queue.poll();
            topk[top - 1] = arr1[max.getRow()] + arr2[max.getCol()];
            int row = max.getRow() - 1;
            if (row >= 0 && !exit[row][max.getCol()]) {
                queue.offer(new TopkSumNode(row, max.getCol(), arr1[row] + arr2[max.getCol()]));
                exit[row][max.getCol()] = true;
            }
            int col = max.getCol() - 1;
            if (col >= 0 && !exit[max.getRow()][col]) {
                queue.offer(new TopkSumNode(max.getRow(), col, arr1[max.getRow()] + arr2[col]));
                exit[row][max.getCol()] = true;
            }
            top--;
        }
        return topk;
    }

    private class TopkSumNode {
        private int row;
        private int col;
        private int sum;

        public TopkSumNode(int row, int col, int sum) {
            this.row = row;
            this.col = col;
            this.sum = sum;
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

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }
    }

    public int oil(int[] gas, int[] cost) {
        if (gas.length == 0 || cost.length == 0 || gas.length != cost.length) {
            return 0;
        }
        int curCost = 0;
        int total = 0;
        int start = 0;
        for (int i = 0; i < gas.length; i++) {
            curCost += gas[i] - cost[i];
            total += gas[i] - cost[i];
            if (curCost < 0) {
                curCost = 0;
                start = i + 1;
            }
        }
        return total < 0 ? -1 : start;
    }

    /**
     *
     * 数组是正数数组,如果将数组k次切分分为k+1部分,切的地方不含,切出的数组累加和都相等,求arr是否存在这种切法
     * 3 4 2 2 5 3 1 6 8 7 -> 3 4, 2 5, 1 6, 7 切3到分成4部分
     *
     * @param
     * @return
     */
    public boolean segmentation(int[] arr) {

        if (arr.length < 5) {
            return false;
        }
        Map<Integer, Integer> sumMap = new HashMap<>(arr.length);
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sumMap.put(sum += arr[i], i);
        }

        for (int i = 1; i < arr.length - 5; i++) {
            int preSum = sumMap.get(i - 1);
            int nexSum = 2 * preSum + arr[i];
            if (sumMap.containsKey(nexSum)) {
                int second = sumMap.get(nexSum);
                nexSum = arr[second + 1] + preSum;
                if (sumMap.containsKey(nexSum)) {
                    int third = sumMap.get(nexSum);
                    nexSum = arr[third + 1] + preSum;
                    if (sumMap.containsKey(nexSum)) {
                        int forth = sumMap.get(nexSum);
                        nexSum = arr[forth + 1] + preSum;
                        if (sumMap.containsKey(nexSum)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 是否存在字符串使s1,与s2交错组成target
     *
     * @param s1
     * @param s2
     * @param target
     * @return
     */
    public boolean mergeString(String s1, String s2, String target) {
        if (target == null || (s1 == null && s2 != null && s2.length() != target.length()) || (s2 == null && s1 != null && s1.length() != target.length())) {
            return false;
        }
        int n = s1 == null ? 0 : s1.length() + 1;
        int m = s2 == null ? 0 : s2.length() +1 ;
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        char[] c3 = target.toCharArray();

        boolean[][] result = new boolean[n][m];
        result[0][0] = true;
        // 一个样本做行,一个样本做列,已s1结尾,或者已s2结尾的情况
        // 添加第一行,此时s1为空串,s2取值,等于是s1的长度为0
        for (int i = 1; i < m; i++) {
            if (result[0][i - 1]) {
                if (c2[i - 1] == c3[i - 1]) {
                    result[0][i] = true;
                }
            }
        }
        for (int i = 1; i < n; i++) {
            if (result[0][i - 1]) {
                if (c1[i - 1] == c3[i - 1]) {
                    result[0][i] = true;
                }
            }
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                result[i][j] = (c1[i - 1] == c3[i + j - 1] && result[i - 1][j]) || (c1[j - 1] == c3[i + j - 1] && result[i][j - 1]);
            }
        }
        return result[n - 1][m - 1];
    }

    /**
     * 给定两个链表n1,n2求链表从尾节点开始求和,每个节点的和放置到新链表节点,大于10的进位
     * 3->5->7 2->3->4
     * 7>5>3 4>3>2 => 1>1>8>5 => 5>8>1>1
     *
     * @param n1
     * @param n2
     * @return
     */
    public Node<Integer> sumLink(Node<Integer> n1, Node<Integer> n2) {
        if (n1 == null || n2 == null) {
            return null;
        }
        Node<Integer> cur1 = n1.next;
        Node<Integer> cur2 = n2.next;
        Node<Integer> pre = null;
        Node<Integer> newNode;

        int scale = 0;
        while (cur1 != null && cur2 != null) {
            int v1 = cur1.data;
            int v2 = cur2.data;

            int v3 = (v1 + v2) + scale;
            scale = v3 / 10;
            newNode = new Node<>(v3 % 10);
            newNode.next = pre;


            cur1 = cur1.next;
            cur2 = cur2.next;
            pre = newNode;

        }
        if (scale > 0) {
            Node<Integer> n = new Node<>(scale);
            n.next = pre;
            pre = n;
        }
        Node<Integer> cur = pre;
        Node<Integer> next;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;


        }
        return pre;
    }

    /**
     * 数组中存在一个无序的子数组,将其排序后可使数组整体有序,求使数组整体有序的最大的需要排序子串
     *
     * @param arr
     * @return
     */
    public int di(int[] arr) {
        if (arr.length <= 1) {
            return 0;
        }
        int maxIndex = 0;
        int max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (max >= arr[i]) {
                maxIndex++;
            } else {
                max = Math.max(max, arr[i]);
                maxIndex = i;
            }
        }
        int minIndex = arr.length - 1;
        int min = arr[arr.length - 1];
        for (int i = arr.length - 1; i >= 0; i--) {
            if (min >= arr[i]) {
                min--;
            } else {
                min = Math.min(max, arr[i]);
                minIndex = i;
            }
        }
        return maxIndex - minIndex + 1;
    }

    /**
     * 给定数据arr,存在子序的最小和和子序的最大和,求不满足最小和到最大和之间的不存在的数
     * 背包问题可解
     *
     * @param arr
     * @return
     */
    public int process(int[] arr) {
        if (arr.length == 0) {
            return 1;
        }
        int range = arr[0];
        Arrays.sort(arr);
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > range + 1) {
                return range + 1;
            } else {
                range += arr[i];
            }
        }
        return range + 1;
    }

    /**
     * 有序正数数组,其子串累加,求子串累加和达到aim需要填补的数
     *
     * @param arr
     * @param aim
     * @return
     */
    public int process1(int[] arr, int aim) {
        if (arr.length == 0) {
            return 1;
        }
        int process = 0;
        int range = 0;
        for (int anArr : arr) {
            while (anArr > range + 1) {
                range += range + 1;
                process++;
                if (range > aim) {
                    return process;
                }
            }
            range += anArr;
            process++;
            if (range > aim) {
                return process;
            }
        }
        while (range <= aim) {
            process++;
            range += range + 1;
        }
        return process;
    }

    /**
     * 给一个 String =”abcacdef“，找出其中不重复的最长的字串
     *
     * @param str
     * @return
     */
    public int st(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        return st1(str.toCharArray(), 0, 0, new int[26]);
    }

    public void t (Object... params) {

    }
    private int st1(char[] c, int index, int length, int[] exist) {
        if (index > c.length - 1) {
            return length;
        }
        if (exist[c[index] - 'a'] != 0) {
            return length;
        }
        int max = 0;
        for (int i = index; i < c.length; i++) {
            exist[c[index] - 'a'] = 1;
            int l = st1(c, index + 1, length + 1, exist);
            exist[c[index] - 'a'] = 0;
            length = 0;
            max = Math.max(max, l);
        }

        return max;
    }


    /**
     * 花cost的费用就能得到power能力怪兽的支持,到达i位置,怪兽的能力能>=i+1位置的能力,则可以不花钱走i+1位置过,求走完n的路径,最少的花费
     *
     * @param costs
     * @param powers
     * @return
     */
    public int minCost(int[] costs, int[] powers) {
        if ((costs.length == 0 || powers.length == 0) || costs.length != powers.length) {
            return 0;
        }
        return minCost(costs, powers, 0, 0);
    }

    private int minCost(int[] costs, int[] powers, int totalPower, int index) {
        if (index == costs.length) {
            return 0;
        }
        if (totalPower < powers[index]) {
            return costs[index] + minCost(costs, powers, totalPower + powers[index], index + 1);
        } else {
            return minCost(costs, powers, totalPower, index + 1);
        }
    }


//    public int minCost1(int[] costs, int[] powers) {
//        if ((costs.length == 0 || powers.length == 0) || costs.length != powers.length) {
//            return 0;
//        }
//        int[][] dp = new int[costs.length][powers.length];
//        for (int i = 0; i ) {
//
//        }
//    }

    /**
     * 字符串中的最长不重复子串
     * 已i位置结尾,i的最近一个重复的字符在map[chars[i]],但要满足i-1的最远位置
     *
     * @param str
     * @return
     */
    public int maxLengthNotRepeat(String str) {
        if (str == null || str == "") {
            return 0;
        }
        char[] chars = str.toCharArray();
        int[] map = new int[127];
        for (int i = 0; i < chars.length; i++) {
            map[i] = -1;
        }
        // i - 1位置向左能到达的最大位置
        int pre = -1;
        // 最后结果
        int len = 0;
        // 当前位置到最左的最远距离
        int curLen;
        for (int i = 0; i < chars.length; i++) {
            pre = Math.max(pre, map[chars[i]]);
            curLen = i - pre;
            len = Math.max(len, curLen);
            map[chars[i]] = i;
        }
        return len;
    }


    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        if (x / 10 == 0) {
            return true;
        }
        int res = 0;
        while (x > res) {
            res = res * 10 + x % 10;
            x = x / 10;
        }
        return res == x || x == res / 10;
    }

    public int maxArea(int[] height) {
        if (height.length == 0) {
            return 0;
        }
        if (height.length == 1) {
            return 0;
        }
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;
        while (left <= right) {

            if (height[left] >= height[right]) {
                maxArea = Math.max(maxArea, (right - left) * height[right]);
                right--;
            } else {
                maxArea = Math.max(maxArea, (right - left) * height[left]);
                left++;
            }
        }
        return maxArea;
    }

    public int trap(int[] height) {
        if (height.length == 0) {
            return 0;
        }
        if (height.length == 1) {
            return 0;
        }
        int left = 0;
        int right = height.length - 1;
        int water = 0;
        int preMax = 0;
        while (left <= right) {
// 双指针, 左边小则左边后移，并用前一次的preMax-当前左边值累加,反之右边相同套路
            if (height[left] < height[right]) {
                if (preMax > height[left]) {
                    water += preMax - height[left];
                } else {
                    preMax = height[left];
                }
                left++;
            } else {
                if (preMax > height[right]) {
                    water += preMax - height[right];
                } else {
                    preMax = height[right];
                }
                right--;
            }
        }
        return water;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        if (nums.length == 0) {
            return new ArrayList<>(0);
        }
        Map<Integer, Integer> twoSum;
        int target;
        List<List<Integer>> result = new ArrayList<>(nums.length / 3 + 1);
        Set<Integer> set = new HashSet<>();
        int xr;
        for (int i = 0; i < nums.length; i++) {
            target = 0 - nums[i];
            twoSum = new HashMap<>();
            for (int j = i + 1; j < nums.length; j++) {
                xr = nums[i] ^ (target - nums[j]) ^ nums[j];
                if (!set.contains(xr) && twoSum.containsKey(target - nums[j])) {
                    result.add(Arrays.asList(nums[i], target - nums[j], nums[j]));
                    xr = nums[i] ^ target - nums[j] ^ nums[j];
                    set.add(xr);
                }
                twoSum.put(nums[j], j);
            }
        }
        return result;
    }

    public List<List<Integer>> threeSum1(int[] nums) {
        if (nums.length == 0) {
            return new ArrayList<>(0);
        }
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>(nums.length / 3 + 1);
        int target;
        int right;
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            target = -nums[i];
            for (int left = i + 1; left < nums.length; left++) {
                if (left > i + 1 && nums[left] == nums[left - 1]) {
                    continue;
                }
                right = nums.length - 1;
                while (right > left) {
                    if (nums[left] + nums[right] == target) {
                        result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                        break;
                    } else if (nums[left] + nums[right] > target) {
                        right--;
                    } else {
                        break;
                    }
                }
            }
        }
        return result;
    }


    public static void main(String[] args) {
//        new TwentyNovember().treePrint(new String[]{"b\\se", "d\\f\\g", "a\\j\\k"});

//        SinglyLinked singlyLinked = new SinglyLinked();
//        singlyLinked.add(3);
//        singlyLinked.add(5);
//        singlyLinked.add(7);
//        SinglyLinked singlyLinked1 = new SinglyLinked();
//        singlyLinked1.add(2);
//        singlyLinked1.add(3);
//        singlyLinked1.add(4);
//
//        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("");
//            }
//        }, 1, 1 , TimeUnit.MILLISECONDS);
//
//        new TwentyNovember().st("abcacdef");

//        new TwentyNovember().sag(new int[]{1,5,2,1,7});

        new TwentyNovember().nextPermutation(new int[]{1,2,4,3,5,6,7,-3,-2,0,-4,-5});
    }



}

