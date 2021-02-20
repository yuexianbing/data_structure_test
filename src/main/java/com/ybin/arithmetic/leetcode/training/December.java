package com.ybin.arithmetic.leetcode.training;

import com.ybin.btree.BtreeLinked;
import com.ybin.link.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

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


    /**
     * 求数组的子串中累加和小于等于target的最大累加和
     *
     * @param arr
     * @return
     */
    public int satisfyTarget(int[] arr, int target) {
        if (arr.length == 0) {
            return 0;
        }
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(0);
        int sum = 0;
        int max = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (treeSet.ceiling(sum - target) != null) {
                max = Math.max(max, treeSet.ceiling(sum - target));
            }
        }
        return max;
    }

    /**
     * 上述问题的二维数组计算
     *
     * @param arr
     * @param target
     * @return
     */
    public int satisfyTarget(int[][] arr, int target) {
        if (arr.length == 0) {
            return 0;
        }
        int[] pre;
        int max = Integer.MIN_VALUE;
        for (int r = 0; r < arr.length; r++) {
            pre = arr[r];
            for (int r1 = r + 1; r1 < arr.length; r1++) {
                for (int col = 0; col < arr[r1].length; col++) {
                    pre[col] = pre[col] + arr[r1][col];
                }
                max = Math.max(max, this.satisfyTarget(pre, target));
            }
        }
        return max;
    }

    /**
     * 求二维数组中的最长递增链
     *
     * @param arr
     * @return
     */
    public int increase(int[][] arr) {
        if (arr.length == 0) {
            return 0;
        }
        return processIncrease(arr, 0, 0, new int[arr.length][arr[0].length]);
    }

    private int processIncrease(int[][] arr, int row, int col, int[][] dp) {
        if (row >= arr.length || col >= arr[row].length) {
            return 1;
        }
        if (dp[row][col] != 0) {
            return dp[row][col];
        }
        int max = Integer.MIN_VALUE;
        // 左边满足
        if (col - 1 >= 0 && arr[row][col - 1] > arr[row][col]) {
            max = Math.max(max, processIncrease(arr, row, col - 1, dp));
        }
        // 上走
        if (row - 1 >= 0 && arr[row - 1][col] > arr[row][col]) {
            max = Math.max(max, processIncrease(arr, row - 1, col, dp));
        }
        // 右走
        if (col + 1 < arr[row].length && arr[row][col + 1] > arr[row][col]) {
            max = Math.max(max, processIncrease(arr, row, col + 1, dp));
        }
        // 下走
        if (row + 1 < arr.length && arr[row + 1][col] > arr[row][col]) {
            max = Math.max(max, processIncrease(arr, row + 1, col, dp));
        }
        int result = max <= 0 ? 0 : max + 1;
        dp[row][col] = result;
        return result;
    }

    private class TreeNode1 {

        private TreeNode1[] nodeList;

        private Integer path;

        private char value;

        private Integer end;

        public TreeNode1(char c) {
            this.nodeList = new TreeNode1[26];
            this.path = 0;
            this.end = 0;
            value = c;
        }

        public void setValue(char value) {
            this.value = value;
        }

        public boolean findNode(char c) {
            if (nodeList == null) {
                return false;
            }
            for (TreeNode1 node : nodeList) {
                if (node.nodeList[c - 'a'] != null) {
                    return true;
                }
            }
            return false;
        }

        public TreeNode1[] getNodeList() {
            return nodeList;
        }

        public void setNodeList(TreeNode1[] nodeList) {
            this.nodeList = nodeList;
        }

        public Integer getPath() {
            return path;
        }

        public void setPath(Integer path) {
            this.path = path;
        }

        public Integer getEnd() {
            return end;
        }

        public void setEnd(Integer end) {
            this.end = end;
        }
    }

    /**
     * 给定字符数组,从字符数组任意位置出发走,都能得到str中的任一得单词,不能重复,求最终能走出得单词集合
     *
     * @param param
     * @param str
     * @return
     */
    public List<String> pathString(char[][] param, String[] str) {
        if (param == null || param.length == 0 || str == null || str.length == 0) {
            return new ArrayList<>();
        }
        TreeNode1 node = this.buildTreeNode(str);
        List<String> result = new ArrayList<>();
        pathString(param, result, new LinkedList<String>(), node, 0, 0);
        return result;
    }

    private TreeNode1 buildTreeNode(String[] str) {
        TreeNode1 head = new TreeNode1('p');
        TreeNode1 node;
        Set<String> strSet = new HashSet<>();
        for (String s : str) {
            node = head;
            node.path++;
            for (char c : s.toCharArray()) {
                int index = c - 'a';
                if (!strSet.contains("" + c)) {
                    if (node.nodeList[index] == null) {
                        node.nodeList[index] = new TreeNode1(c);
                        strSet.add("" + c);
                    }
                }

                node = node.nodeList[index];
                node.path++;
            }
            node.end++;
        }
        return head;
    }

    public int pathString(char[][] param, List<String> result, LinkedList<String> path, TreeNode1 treeNode, int i, int j) {
        if (i < 0 || j < 0 || i >= param.length || j >= param.length) {
            return 0;
        }
        if (param[i][j] == 0) {
            return 0;
        }

        char c = param[i][j];
        if (treeNode.nodeList[c - 'a'] == null) {
            return 0;
        }
        if (treeNode.end == 0 || treeNode.path == 0) {
            return 0;
        }
        treeNode = treeNode.nodeList[c - 'a'];
        path.add("" + param[i][j]);
        int k = 0;
        if (treeNode.end > 0) {
            result.add(path.toString());
            k++;
        }


        param[i][j] = 0;
        // 上
        if (i > 0) {
            k += pathString(param, result, path, treeNode, i - 1, j);
        }
        // 左
        if (j > 0) {
            k += pathString(param, result, path, treeNode, i, j - 1);
        }
        // 右
        if (j > param.length) {
            k += pathString(param, result, path, treeNode, i, j + 1);
        }
        // 下
        if (i > param.length) {
            k += pathString(param, result, path, treeNode, i + 1, j);
        }
        param[i][j] = c;
        path.pollLast();
        treeNode.path -= k;
        return k;
    }

    /**
     * 字符串str1,删除任意字符得到str2得方法数
     * // 删除任意字符串,既是保留其中得的字符串
     *
     * @param str1
     * @param str2
     * @return
     */
    public int deleteToTarget(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return 0;
        }
        int[][] dp = new int[str1.length()][str2.length()];
        char[] c1 = str1.toCharArray();
        char[] c2 = str2.toCharArray();
        dp[0][0] = c1[0] == c2[0] ? 1 : 0;
        // 有多少中保留方式,可以让0 - i的字符保留后等c2[0]
        for (int i = 1; i < str1.length(); i++) {
            dp[i][0] = c1[i] == c2[0] ? dp[i - 1][0] + 1 : dp[i - 1][0];
        }
        // 任意普遍位置
        for (int i = 0; i < c1.length; i++) {
            for (int j = 0; j < c2.length; j++) {
                // 保留的位置,不包含i
                dp[i][j] = dp[i - 1][j];
                // 保留的位置一定包含i,包含i的条件是c1[i]==c2[j]
                dp[i][j] += c1[i] == c2[j] ? dp[i - 1][j - 1] : 0;
            }
        }
        return dp[c1.length - 1][c2.length - 1];
    }

    /**
     * 有一个怪兽与血瓶同时存在的方阵图,有一个英雄从最上脚走到最下角,求影响能通过的初始最小血量
     *
     * @param arr
     * @return
     */
    public int minHp(int[][] arr) {
        if (arr.length == 0) {
            return 0;
        }
        return minHp(arr, 0, 0);
    }

    private int minHp(int[][] arr, int i, int j) {
        // 如果到最后一个点,要登上最后一个点：
        if (i == arr.length - 1 && j == arr.length - 1) {
            return arr[i][j] >= 0 ? 1 : -arr[i][j] + 1;
        }
        // 在最后一行
        if (i == arr.length - 1) {
            int minRightHp = minHp(arr, i, j + 1);
            if (arr[i][j] < minRightHp) {
                return minRightHp - arr[i][j];
            } else if (arr[i][j] >= minRightHp) {
                return 1;
            }
        }
        if (j == arr[arr.length - 1].length) {
            int minDownHp = minHp(arr, i + 1, j);
            if (arr[i][j] < minDownHp) {
                return minDownHp - arr[i][j];
            } else if (arr[i][j] >= minDownHp) {
                return 1;
            }
        }
        // 普遍位置,向右的最小血量,向下的最小血量
        int minHp = Math.min(minHp(arr, i + 1, j), minHp(arr, i, j + 1));
        if (arr[i][j] < minHp) {
            return minHp - arr[i][j];
        } else if (arr[i][j] >= minHp) {
            return 1;
        }
        return 1;
    }

    public int metrix(int[][] metrix) {
        if (metrix.length == 0) {
            return 0;
        }
        return metrix(metrix, 0, 0, 0);
    }

    private int metrix(int[][] metrix, int ax, int ay, int bx) {
        if (ax == metrix.length - 1 && ay == metrix.length - 1) {
            return metrix[ax][ay];
        }
        int by = ax + ay - bx;
        // a下,b下
        // a右,b下
        // a下,b右
        // a右,b右
        int aDownBDown = Integer.MIN_VALUE;
        if (ax + 1 < metrix.length && bx + 1 < metrix.length) {
            aDownBDown = metrix(metrix, ax + 1, ay, bx + 1);
        }
        int aRightBDown = Integer.MIN_VALUE;
        if (ay + 1 < metrix.length && bx + 1 < metrix.length) {
            aRightBDown = metrix(metrix, ax, ay + 1, bx + 1);
        }
        int aDownBRight = Integer.MIN_VALUE;
        if (ax + 1 < metrix.length && by + 1 < metrix.length) {
            aDownBRight = metrix(metrix, ax + 1, ay, bx);
        }
        int aRightBRight = Integer.MIN_VALUE;
        if (ay + 1 < metrix.length && by + 1 < metrix.length) {
            aRightBRight = metrix(metrix, ax, ay + 1, bx);
        }
        int next = Math.max(aDownBDown, aRightBDown);
        next = Math.max(next, aDownBRight);
        next = Math.max(next, aRightBRight);
        if (ax == bx) {
            return metrix[ax][by] + next;
        }
        return metrix[ax][by] + next + metrix[bx][by];
    }


    public static void rotate(int[][] matrix) {
        int a = 0;
        int b = 0;
        int c = matrix.length - 1;
        int d = matrix[0].length - 1;
        while (a < c) {
            rotateEdge(matrix, a++, b++, c--, d--);
        }
    }

    public static void rotateEdge(int[][] m, int a, int b, int c, int d) {
        int tmp = 0;
        for (int i = 0; i < d - b; i++) {
            tmp = m[a][b + i];
            m[a][b + i] = m[c - i][b];
            m[c - i][b] = m[c][d - i];
            m[c][d - i] = m[a + i][d];
            m[a + i][d] = tmp;
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 求数组中排序后相邻元素的最大值,时间复杂度O(N)
     *
     * @param arr
     * @return
     */
    public int maxAdjoin(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }
        int max = arr[0];
        int min = arr[0];
        for (int index = 0; index < arr.length; index++) {
            max = Math.max(max, arr[index]);
            min = Math.min(min, arr[index]);
        }
        int[] maxBucket = new int[arr.length - 1];
        int[] minBucket = new int[arr.length - 1];
        for (int index = 0; index < arr.length; index++) {
            int bucketIndex = (arr[index] - min) * arr.length / (max - min);
            maxBucket[bucketIndex] = Math.max(maxBucket[bucketIndex], arr[index]);
            minBucket[bucketIndex] = Math.min(maxBucket[bucketIndex], arr[index]);
        }
        int res = 0;
        int lastNum = arr[0];
        for (int i = 1; i < minBucket.length; i++) {
            res = Math.max(minBucket[i] - lastNum, res);
            lastNum = maxBucket[i];
        }
        return res;
    }

    /**
     * 给定字符串str, 和match字符串数组, 求满足match能组装成str的最小方法数
     *
     * @param str
     * @param match
     * @return
     */
    public int matchString(String str, String[] match) {
        if (str == null || str.length() == 0 || match == null) {
            return 0;
        }
        HashSet<String> set = new HashSet<>(match.length);
        set.addAll(Arrays.asList(match));
        MatchTreeNode treeNode = this.add(match);
        return matchString(str, set, 0, treeNode);
    }

    private int matchString(String str, HashSet<String> set, int index, MatchTreeNode treeNode) {
        if (index == str.length()) {
            return 1;
        }
        int result = 0;
//        for (int i = index; i < str.length(); i++) {
//            String pre = str.substring(index, i);
//            if (set.contains(pre)) {
//                result += matchString(str, set, ++index);
//            }
//        }

        for (int i = index; i < str.length(); i++) {
            MatchTreeNode next = treeNode.characters[str.charAt(i) - 'a'];
            if (next != null) {
                result += matchString(str, set, ++index,  next);
            }
        }
        return result;
    }

    public MatchTreeNode add(String[] strings) {
        MatchTreeNode treeNode = new MatchTreeNode();
        for (String s : strings) {
            for (char c : s.toCharArray()) {
                int index = c - 'a';
                MatchTreeNode next = treeNode.characters[index];
                if (next == null || next.c != c) {
                    treeNode.characters[index] = new MatchTreeNode(c);
                    next = treeNode.characters[index];
                }
                treeNode = next;
            }
            treeNode.end++;
        }
        return treeNode;
    }

    private class MatchTreeNode {
        private char c;

        private MatchTreeNode[] characters;

        private int end;

        public MatchTreeNode() {
            characters = new MatchTreeNode[26];
        }

        public MatchTreeNode(char c) {
            this.c = c;
            characters = new MatchTreeNode[26];

        }

        public char getC() {
            return c;
        }

        public void setC(char c) {
            this.c = c;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

    }

    /**
     * 在n的字典序中找出第k大的元素
     *
     * @param n
     * @param k
     * @return
     */
    public int k(int n, int k) {
        int c = 1;
        while (k >= 0) {
            int num = this.tril(c, c + 1, n);
            if (k > num) {
                k -= num;
                c += 1;
            } else {
                k -= 1;
                c *= 10;
            }
        }
        return c;
    }

    private int tril(int c, int c1, int n) {
        int num = 0;
        while (c <= n) {
            num += Math.min(n + 1, c1) - c;
            c = c * 10;
            c1 = c1 * 10;
        }
        return num;
    }


    /**
     * 反转整数
     *
     * @param num
     * @return
     */
    public int resver(int num) {
        if (num == 0) {
            return 0;
        }
        int param = num;
        int res = 0;
        int m = Integer.MIN_VALUE / 10;
        int o = Integer.MIN_VALUE % 10;
        // 负数表示的范围比正数表示的范围大
        param = param > 0 ? -param : param;
        while (param != 0) {
            if (res < m ||(res == m && param % 10 < o)) {
                return 0;
            }
            res = res * 10 + param % 10;
            param = param / 10;
        }
        return num > 0 ? res : -res;
    }

    public int sumShortPath(BtreeLinked.Node node, int k) {
        return this.sumShortPath(node, 0, 0, 0, k, new HashMap<>());
    }

    private int sumShortPath(BtreeLinked.Node node, int pre, int level, int ans, int k, Map<Integer, Integer> hashMap) {
        if (node == null) {
            return ans;
        }
        int allSum = pre + node.getItem();
        if (hashMap.containsKey(allSum - k)) {
            ans = Math.max(ans, level - hashMap.get(allSum - k));
        }
        if (!hashMap.containsKey(allSum)) {
            hashMap.put(allSum, level);
        }
        sumShortPath(node.getLeftNode(), allSum, level + 1, ans, k, hashMap);
        sumShortPath(node.getLeftNode(), allSum, level + 1, ans, k, hashMap);
        if (hashMap.get(allSum) == level) {
            hashMap.remove(allSum);
        }
        return ans;
    }

    public String plalindrome(String str) {
        if (str == null || str.length() == 1) {
            return str;
        }
        boolean[][] dp = new boolean[str.length()][str.length()];
        int maxLength = 0;
        int resultStartIndex = 0;
        // 一个
        for (int i = 0; i < str.length(); i++) {
            dp[i][i] = true;
            maxLength = 1;
        }
        char[] chars = str.toCharArray();
        // 两个
        for (int i = 1; i + 1 < str.length(); i++) {
            if (chars[i] == chars[i + 1]) {
                dp[i][i + 1] = true;
                maxLength = 2;
                resultStartIndex = i;
            }
        }
        // 三个
        for (int i = 2; i < chars.length; i++) {
            for (int k = 0; k < chars.length - i + 1; k++) {
                int j = k + i - 1;
                if (dp[k + 1][j - 1] && chars[k] == chars[j]) {
                    dp[k][j] = true;
                    if (maxLength < i) {
                        maxLength = i;
                        resultStartIndex = k;
                    }
                }
            }
        }
        return str.substring(resultStartIndex, resultStartIndex + maxLength);
    }

    /**
     * 求数组中位数,中位数就是第K大的数,那实际是求第K大的数,用二分,
     * 第一个数组二分后,在第二个数组的二分中找到小于等于该数的有多少个,如果大于K,说明二分大了,数组1在左边二分,否则在右边或者就是当前数
     *
     * @param a1
     * @param a2
     * @return
     */

    public double findMedianSortedArrays1(int[] a1, int[] a2) {
        if ((a1.length + a2.length) % 2 == 1) {
            return midArr(a1, a2, (a1.length + a2.length) / 2 + 1);
        } else {
            int pre = midArr(a1, a2, (a1.length + a2.length) / 2);
            int post = midArr(a1, a2, (a1.length + a2.length) / 2 + 1);
            return (pre + post) / 2;
        }
    }
    public int midArr(int[] a1, int[] a2, int k) {


        int s = 0;
        int e = a1.length - 1;
        int res = -1;
        boolean fund = false;

        // 第K大的数在a1中
        while (s <= e) {
            int amid = s + (e - s) >>> 1;
            int target = a1[amid];
            int a1LessThan = indexLessThan(a2, target);

            if ((amid + 1) + a1LessThan == k) {
                res = target;
                fund = true;
                break;
            } else if ((amid + 1) + a1LessThan > k){
                e = amid - 1;
            } else {
                s = amid + 1;
            }
        }

        if (fund) {
            return res;
        }

        s = 0;
        e = a2.length - 1;
        res = -1;
        // 第K大的数在a1中
        while (s <= e) {
            int amid = s + (e - s) >>> 1;
            int target = a2[amid];

            int a1LessThan = indexLessThan(a1, target);

            if ((amid + 1) + a1LessThan == k) {
                res = target;
                break;
            } else if ((amid + 1) + a1LessThan > k){
                e = amid - 1;
            } else {
                s = amid + 1;
            }
        }
        return res;
    }


    private int indexLessThan(int[] arr,  int target) {
        int s1 = 0;
        int e1 = arr.length - 1;
        int res = -1;
        while (s1 < e1) {
            int mid = s1 + ((e1 - s1) >>> 1);
            if (arr[mid] <= target) {
                res = mid;
                s1 = mid + 1;
            } else if (arr[mid] > target) {
                e1 = mid - 1;
            }
        }
        return res + 1;
    }
    private int index(int[] arr, int s1, int e1, int target) {

        int index = -1;
        while (s1 <= e1) {
            int mid  = s1 + (e1 - s1) / 2;
            if (target == arr[mid]) {
                index = mid;
                break;
            } else if (target > arr[mid]) {
                s1 = mid + 1;
            } else {
                e1 = mid - 1;
            }
        }
        return index;
    }

    /**
     * 最长回文字符串
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 1) {
            return s;
        }
        String newString = mannachString(s);
        char[] chars = newString.toCharArray();
        // 当前i位置回文半径
        int[] path = new int[chars.length];
        // 中心节点
        int c = -1;
        // 能扩的最远距离
        int r = -1;
         for (int i = 0; i < chars.length; i++) {
            path[i] = r > i ? Math.min(r - i, path[2 * c - i]) : 1;

             while (i + path[i] < chars.length && i - path[i] >= 0) {
                if (chars[i + path[i]] == chars[i- path[i]]) {
                    path[i]++;
                } else {
                    break;
                }
            }
            if (path[i] > (r - c)) {
                r = path[i] + i - 1;
                c = i;
            }
        }
        int start = c - path[c] + 2;
        char[] res = new char[path[c] - 1];
        for (int i = 0; start <= r; start = start + 2, i++) {
            res[i] = chars[start];
        }
        return String.valueOf(res);
    }

    private String mannachString(String s) {
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            newString.append("#").append(s.charAt(i));
        }
        newString.append("#");
        return newString.toString();
    }

    private class MinWindow {
        private int start;

        private int end;

        public int getStart() {
            return start;
        }

        public MinWindow(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }
    }

    public String minWindow(String s, String t) {
        if (s == null || t == null) {
            return "";
        }
        if (s.length() < t.length()) {
            return "";
        }
        int[] ts = new int[256];
        for (int i = 0; i < t.length(); i++) {
            ts[t.charAt(i)]++;
        }
        int[] ss = new int[256];
        int l = 0, r = 0;
        int start = -1;
        int end = -1;
        int rLen = Integer.MAX_VALUE;
        while (r < s.length()) {
            if (ts[s.charAt(r)] > 0) {
                ss[s.charAt(r)]++;
            }
            while (this.check(ss, ts, t.length()) && l <= r) {
                if (r - l + 1 < rLen) {
                    rLen = r - l + 1;
                    start = l;
                    end = l + rLen;
                }
                ss[s.charAt(l)]--;
                l++;
            }
            r++;
        }
        return start == -1 ? "" : s.substring(start, end);
    }

    private boolean check(int[] ss, int[] ts, int tLength) {
        int a = 0;
        for (int i = 0; i < ts.length; i++) {
            if (ts[i] > 0 && ss[i] >= ts[i]) {
                a += ts[i];
            }
        }
        if (a == tLength) {
            return true;
        }
        return false;
    }

//    private int getUpMedian(int[] a, int sa, int ea, int[] b, int sb, int eb, int k) {
//        int[] longNums = a;
//        int[] shortNums = b;
//        if (a.length > b.length) {
//            longNums = a;
//            shortNums = b;
//        } else if (a.length < b.length) {
//            longNums = b;
//            shortNums = a;
//        }
//        if (0 <= k && k <= shortNums.length) {
//            return getUpMedian(shortNums, 0, k - 1, longNums, 0, k - 1);
//        } else if (k > shortNums.length && k <longNums.length) {
//            int s = k - shortNums.length - 1;
//            if (longNums[s] >= shortNums[shortNums.length - 1]) {
//                return longNums[s];
//            }
//            return getUpMedian(shortNums, 0, shortNums.length - 1, longNums, s, k - 1);
//        }
//        if (k > longNums.length && k <= a.length + b.length) {
//
//        }
//    }

    private int getUpMedian(int[] a, int sa, int ea, int[] b, int sb, int eb) {
        int mida, midb;
        while (sa < ea) {
            mida = (ea + sa) / 2;
            midb = (eb + sb) / 2;
            if (a[mida] == b[midb]) {
                return a[mida];
            }
            // 基数
            if ((ea - sa + 1) % 2 == 1) {
                if (a[mida] > b[midb]) {
                    if (b[midb] >= a[mida - 1]) {
                        return b[midb];
                    }
                    ea = mida - 1;
                    sb = midb + 1;
                } else {
                    if (a[mida] >= b[midb - 1]) {
                        return a[mida];
                    }
                    sa = mida + 1;
                    eb = midb - 1;
                }
            } else {
                // 偶数
                if (a[mida] > b[midb]) {
                    sb = midb + 1;
                    ea = mida;
                } else {
                    sa = mida + 1;
                    eb = midb;
                }
            }
        }
        return Math.min(a[sa], b[sb]);
    }

    public String longestCommonPrefix(String[] str) {
        if (str == null || str.length == 0) {
            return "";
        }
        if (str.length == 1) {
            return str[0];
        }
        int min = Integer.MAX_VALUE;
        char[] chars = str[0].toCharArray();
        for (int i = 1; i < str.length; i++) {
            char[] sChars = str[i].toCharArray();
            int index = 0;
            for ( ; index < chars.length && index < sChars.length; index++) {
                if (chars[index] != sChars[index]) {
                    break;
                }
            }
            min = Math.min(min, index);
            if (min == 0) {
                break;
            }
        }
        return min > chars.length ? "" : str[0].substring(0, min);
    }

    public int threeSumClosest(int[] nums, int target) {
        if (nums.length == 0) {
            return 0;
        }
        Arrays.sort(nums);
        int le = 1000;
        int ri = 1000;
        for (int i = 0; i < nums.length; i++) {
            int ta = target - nums[i];
            int left = i + 1; int right = nums.length - 1;
            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum == ta) {
                    return sum + nums[i];
                }
                if (sum > ta) {
                    right--;
                    ri = Math.min(ri, sum + nums[i]);
                } else {
                    left++;
                    le = Math.max(le, sum + nums[i]);
                }

            }
        }
        if (Math.abs(ri - target) > Math.abs(le - target)) {
            return le;
        } else if (Math.abs(ri - target) < Math.abs(le - target)) {
            return ri;
        } else {
            return le;
        }
    }

    /**
     * 按键输入,取每个键的组合
     *
     */
    private String[] keys = new String[]{"abc", "def", "ghi", "jkl", "mno", "qprs", "tuv", "wxyz"};
    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) {
            return new ArrayList<>(0);
        }
        char[] target = digits.toCharArray();
        char[] path = new char[target.length];
        List<String> result = new ArrayList<>();
        letterCombinations(target, path, result, 0);
        return result;
    }

    private void letterCombinations(char[] target, char[] path, List<String> result, int index) {
        if (index == target.length) {
            result.add(String.valueOf(path));
            return;
        }
        String param = keys[target[index] - '2'];
        for (int i = 0; i < param.length(); i++) {
            path[index] = param.charAt(i);
            letterCombinations(target, path, result, index + 1);
        }
    }

    /**
     * 最长递增子序列
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 1) {
            return 1;
        }
        int[] dp = new int[nums.length];
        int result = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int max = 0;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    max = Math.max(max, dp[j]);
                }
            }
            dp[i] = max + 1;
            result = Math.max(result, dp[i]);
        }
        return result;
    }


     private static class ListNode {
         int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode newNode = new ListNode(-1);
        ListNode next = newNode;
        while (l1 != null && l2 != null) {
            if (l1.val >= l2.val) {
                next.next = l2;
                l2 = l2.next;
            } else {
                next.next = l1;
                l1 = l1.next;
            }
            next = next.next;
        }

        next.next = l1 == null ? l2 : l1;

        return newNode.next;
    }

    /**
     * 删除链表的倒数第 n 个结点，并且返回链表的头结点
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head.next == null) {
            return null;
        }
        ListNode result = head;
        ListNode newNode = head;
        while (newNode != null && n >= 0) {
            n--;
            newNode = newNode.next;
        }
        if (newNode == null && n == 0) {
            return result.next;
        }
        while (newNode != null) {
            newNode = newNode.next;
            head = head.next;
        }
        head.next = head.next.next;
        return result;
    }

    /**
     *
     *
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        if (s == null) {
            return false;
        }
        if (s.length() % 2 == 1) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (stack.empty()) {
                stack.push(chars[i]);
                continue;
            }
            if (chars[i] == '[' || chars[i] == '(' || chars[i] == '{') {
                stack.push(chars[i]);
            } else {
                char c = stack.pop();
                if ((chars[i] == ']' && c != '[') || (chars[i] == ')' && c != '(') || (chars[i] == '}' && c != '{')) {
                    return false;
                }
            }
        }
        if (stack.empty()) {
            return true;
        }
        return false;
    }

    /**
     * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合
     *
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        StringBuilder builder = new StringBuilder();
        List<String> result = new ArrayList<>();
        this.generateParenthesis(n, builder, 0, result);
        return result;
    }

    private void generateParenthesis(int n,  StringBuilder path, int right, List<String> result) {
        if (n == 0 && right == 0) {
            result.add(path.toString());
            return;
        }
        if (n > 0) {
            path.append("(");
            generateParenthesis(n - 1, path, right + 1, result);
            path.deleteCharAt(path.length() - 1);
        }
        if (right > 0) {
            path.append(")");
            generateParenthesis(n, path, right - 1, result);
            path.deleteCharAt(path.length() - 1);
        }
    }

    /**
     * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
     *
     * 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
     *
     * 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/n-queens
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param n
     * @return
     */
    public List<List<String>> solveNQueens(int n) {

        List<List<String>> result = new ArrayList<>();

        String[][] path = new String[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                path[i][j] = ".";
            }
        }
        int[] dp = new int[n];
        this.solveNQueens(0, n, path, result, dp);
        return result;
    }

    private void solveNQueens(int cow, int n, String[][] path, List<List<String>> result, int[] dp) {
        if (cow == n) {
            List<String> ans = new ArrayList<>();
            StringBuilder builder;
            for (int i = 0; i < n; i++) {
                builder = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    builder.append(path[i][j]);
                }
                ans.add(builder.toString());
            }
            result.add(ans);
            return;
        }



        for (int j = 0; j < n; j++) {

            if (this.isQueens(dp, cow, j, n)){
                dp[cow] = j;
                path[cow][j] = "Q";
                solveNQueens(cow + 1, n, path, result, dp);
                path[cow][j] = ".";
                dp[cow] = 0;
            }
        }
    }

    private boolean isQueens(int[] dp, int row, int j, int n) {
        for (int i = 0; i < row; i++) {

            if (dp[i] == j
                    || Math.abs(dp[i] - j) == Math.abs(i - row)) {
                return false;
            }


        }
        return true;

    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int totalLength = nums1.length + nums2.length;
        if (totalLength % 2 == 1) {
            return (double) this.getTarget(nums1, nums2, 8);
        } else {
            return (double) (this.getTarget(nums1, nums2, totalLength / 2) + this.getTarget(nums1, nums2, totalLength / 2 + 1)) / 2;
        }
    }

    private int getTarget(int[] nums1, int[] nums2, int k) {

        int index1 = 0;
        int index2 = 0;
        while (true) {

            if (index1 == nums1.length) {
                return nums2[index2 + k - 1];
            }
            if (index2 == nums2.length) {
                return nums1[index1 + k - 1];
            }
            if (k == 1) {
                return Math.min(nums1[index1], nums2[index2]);
            }

            int mid = k / 2;
            int n1 = Math.min(index1 + mid, nums1.length) - 1;
            int n2 = Math.min(index2 + mid, nums2.length) - 1;

            int num1 = nums1[n1];
            int num2 = nums2[n2];

            if (num1 <= num2) {
                k -= n1 - index1 + 1;
                index1 = n1 + 1;
            } else {
                k -= n2 - index2 + 1;
                index2 = n2 + 1;
            }

        }
    }

//    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
//        int length1 = nums1.length, length2 = nums2.length;
//        int totalLength = length1 + length2;
//        if (totalLength % 2 == 1) {
//            int midIndex = totalLength / 2;
//            double median = getKthElement(nums1, nums2, midIndex + 1);
//            return median;
//        } else {
//            int midIndex1 = totalLength / 2 - 1, midIndex2 = totalLength / 2;
//            double median = (getKthElement(nums1, nums2, midIndex1 + 1) + getKthElement(nums1, nums2, midIndex2 + 1)) / 2.0;
//            return median;
//        }
//    }
//
//    public int getKthElement(int[] nums1, int[] nums2, int k) {
//        /* 主要思路：要找到第 k (k>1) 小的元素，那么就取 pivot1 = nums1[k/2-1] 和 pivot2 = nums2[k/2-1] 进行比较
//         * 这里的 "/" 表示整除
//         * nums1 中小于等于 pivot1 的元素有 nums1[0 .. k/2-2] 共计 k/2-1 个
//         * nums2 中小于等于 pivot2 的元素有 nums2[0 .. k/2-2] 共计 k/2-1 个
//         * 取 pivot = min(pivot1, pivot2)，两个数组中小于等于 pivot 的元素共计不会超过 (k/2-1) + (k/2-1) <= k-2 个
//         * 这样 pivot 本身最大也只能是第 k-1 小的元素
//         * 如果 pivot = pivot1，那么 nums1[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums1 数组
//         * 如果 pivot = pivot2，那么 nums2[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums2 数组
//         * 由于我们 "删除" 了一些元素（这些元素都比第 k 小的元素要小），因此需要修改 k 的值，减去删除的数的个数
//         */
//
//        int length1 = nums1.length, length2 = nums2.length;
//        int index1 = 0, index2 = 0;
//        int kthElement = 0;
//
//        while (true) {
//            // 边界情况
//            if (index1 == length1) {
//                return nums2[index2 + k - 1];
//            }
//            if (index2 == length2) {
//                return nums1[index1 + k - 1];
//            }
//            if (k == 1) {
//                return Math.min(nums1[index1], nums2[index2]);
//            }
//
//            // 正常情况
//            int half = k / 2;
//            int newIndex1 = Math.min(index1 + half, length1) - 1;
//            int newIndex2 = Math.min(index2 + half, length2) - 1;
//            int pivot1 = nums1[newIndex1], pivot2 = nums2[newIndex2];
//            if (pivot1 <= pivot2) {
//                k -= (newIndex1 - index1 + 1);
//                index1 = newIndex1 + 1;
//            } else {
//                k -= (newIndex2 - index2 + 1);
//                index2 = newIndex2 + 1;
//            }
//        }
//    }


    public int removeDuplicates(int[] nums) {
        if (nums.length < 2) {
            return nums.length;
        }
        int pre = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[pre]) {
                int temp = nums[i];
                nums[i] = nums[pre + 1];
                nums[pre + 1] = temp;
                pre++;
            }
        }
        return pre + 1;
    }

    /**
     * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
     *
     * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     *
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return head;
        }
        ListNode node = new ListNode();
        ListNode slow = head;
        ListNode quick = slow.next;

        ListNode pre = node;

        pre.next = slow;

        while (true) {

            pre.next = quick;
            slow.next = quick.next;
            quick.next = slow;

            pre = slow;

            if (pre.next == null || pre.next.next == null) {
                break;
            }

            slow = pre.next;
            quick = pre.next.next;



        }
        return node.next;
    }

    public ListNode swapPairs1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode newHead = head.next;
        head.next = swapPairs1(newHead.next);
        newHead.next = head;
        return newHead;
    }

    /**
     * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
     *
     * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
     *
     * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/remove-element
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param nums
     * @param val
     * @return
     */
    public int removeElement(int[] nums, int val) {
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0] == val ? 0 : 1;
        }
        int pre = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                int temp = nums[pre];
                nums[pre] = nums[i];
                nums[i] = temp;
                pre++;
            }
        }
        return pre;
    }

    public int maxProfit(int[] prices) {

        if (prices.length == 0) {
            return 0;
        }

        int left = 0;
        int max = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] >= prices[left]) {
                max = Math.max(max, prices[i] - prices[left]);
            } else {
                left = i;
            }
        }
        return max;
    }

    public class State {

        private int leftMin;

        private int rightMax;

        private int max;

        public State(int leftMin, int rightMax, int max) {
            this.leftMin = leftMin;
            this.rightMax = rightMax;
            this.max = max;
        }
    }

    public int maxProfit1(int[] prices) {

        if (prices.length == 0) {
            return 0;
        }

        return maxProfit1(prices, 0, prices.length - 1).max;
    }

    private State maxProfit1(int[] prices, int left, int right) {
        if (left == right) {
            int max = Math.max(prices[left], prices[right]);
            int min = Math.min(prices[left], prices[right]);
            return new State(min, max, max - min);
        }

        int mid = (right - left) / 2;

        mid = left + mid;

        int leftEnd = Math.max(mid - 1, 0);

        int rightEnd = Math.min(mid + 1, prices.length - 1);

        State leftState = this.maxProfit1(prices, left, leftEnd);
        State rightState = this.maxProfit1(prices, rightEnd, right);

        int rootMax = prices[mid];

        int max = Math.max(leftState.max, rightState.max);

        int rMax = Math.max(rootMax, rightState.rightMax);
        int rMin = Math.min(rootMax, leftState.leftMin);

        max = Math.max(max, rMax - rMin);

        return new State(rMin, rMax, max);

    }

    public int[][] merge(int[][] intervals) {

        if (intervals.length == 0 || intervals[0].length == 0) {
            return new int[0][0];
        }

        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));
        LinkedList<int[]> re = new LinkedList<>();
        re.offerLast(new int[]{intervals[0][0], intervals[0][1]});
        for (int i = 1; i < intervals.length; i++) {
            int[] pre = re.peekLast();
            if (pre[1] >=  intervals[i][0]) {
                re.pollLast();
                re.offerLast(new int[] {pre[0], Math.max(intervals[i][1], pre[1])});
            } else {
                re.offerLast(intervals[i]);
            }
        }
        return re.toArray(new int[re.size()][]);
    }

    /**
     * 返回数组全排列中的字典序排列下一个最大的组合
     * 先找第一个非降序的点,该点的右侧是降序的数据;
     * 将该点与降序组合中的第一个大于他的点交换,
     * 最后交换后续的
     *
     * @param nums
     */
    public void nextPermutation(int[] nums) {
        if (nums.length == 1) {
            return;
        }
        int i = nums.length - 2;
        int j = nums.length - 1;

        while (i >= 0 && nums[i] >= nums[i + 1]) {
                i--;
        }

        if (i >= 0) {
            while (j >= i && nums[j] <= nums[i]) {
                j--;
            }
            this.swap(nums, i, j);
        }

        resver(nums, i + 1);
    }

    private void resver(int[] nums, int i) {
        int j = nums.length - 1;
        while (i < j) {
            this.swap(nums, i, j);
            i++;
            j--;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public int climbStairs(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    private class TreeNodeMaxSun {
        private int leftMax;

        private int rightMax;
    }

      public static class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;

          TreeNode() {
          }

          TreeNode(int val) {
              this.val = val;
          }

          TreeNode(int val, TreeNode left, TreeNode right) {
              this.val = val;
              this.left = left;
              this.right = right;
          }
      }

    public int maxPathSum(TreeNode root) {
        MaxPathSum pathSum = this.maxPathSum1(root);
        return pathSum.allMaxNotRoot;
    }

    private class MaxPathSum {

        private int leftRightMax;

        private int allMaxNotRoot;

        public MaxPathSum(int leftRightMax, int allMaxNotRoot) {
            this.leftRightMax = leftRightMax;
            this.allMaxNotRoot = allMaxNotRoot;
        }
    }

    private MaxPathSum maxPathSum1(TreeNode node) {
        if (node == null) {
            return new MaxPathSum(-3000, -3000);
        }
        MaxPathSum left = maxPathSum1(node.left);
        MaxPathSum right = maxPathSum1(node.right);



        // 只根节点
        int rootMax = node.val;
        // 走根节点或者左右任一一边
        int leftRightMax = Math.max(left.leftRightMax, right.leftRightMax) + rootMax;

        int allMaxNotRoot = rootMax + left.leftRightMax + right.leftRightMax;

        int max = Math.max(leftRightMax, rootMax);

        max = Math.max(max, left.allMaxNotRoot);

        max = Math.max(max, right.allMaxNotRoot);

        max = Math.max(max, allMaxNotRoot);


        return new MaxPathSum(Math.max(leftRightMax, rootMax), max);
    }


    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null) {
            return null;
        }
        if (lists.length == 0) {
            return null;
        }

        ListNode head = new ListNode();
        ListNode cur = head;
        Queue<ListNode> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.val));
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                queue.add(lists[i]);
            }
        }
        while (!queue.isEmpty()) {
            ListNode listNode = queue.poll();
            cur.next = listNode;
            cur = cur.next;
            if (listNode.next == null) {
                continue;
            }
            queue.add(listNode.next);
        }
        return head.next;
    }

    public int search(int[] nums, int target) {
        if (nums.length == 1) {
            return nums[0] == target ? 0 :-1;
        }
        int l = 0;
        int r = nums.length - 1;
        while (l <= r) {
            int mid = (r + l) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            // 两个都不等
            if (nums[l] == nums[mid] && nums[r] == nums[mid]) {
                while (l != mid && nums[l] == nums[mid]) {
                    l++;
                }
                if (l == mid) {
                    l = mid + 1;
                    continue;
                }
            }

            if (nums[l] != nums[mid]) {
                if (nums[l] < nums[mid]) {
                    if (target >= nums[l] && target <= nums[mid]) {
                        r = mid - 1;
                    } else {
                        l = mid + 1;
                    }
                } else {
                    if (target >= nums[mid] && target <= nums[r]) {
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }
                }
            } else {
                if (nums[mid] < nums[r]) {
                    if (target >= nums[mid] && target <= nums[r]) {
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }
                } else {
                    if (target >= nums[l] && target <= nums[mid]) {
                        r = mid - 1;
                    } else {
                        l = mid + 1;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
     * 输入: [3,2,1,5,6,4] 和 k = 2
     * 输出: 5
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        int heapSize = nums.length;
        buildMaxHeap(nums, heapSize);
        for (int i = nums.length - 1; i >= nums.length - k + 1; --i) {
            swap1(nums, 0, i);
            --heapSize;
            maxHeapify(nums, 0, heapSize);
        }
        return nums[0];
    }

    public void buildMaxHeap(int[] a, int heapSize) {
        for (int i = heapSize / 2; i >= 0; --i) {
            maxHeapify(a, i, heapSize);
        }
    }

    public void maxHeapify(int[] a, int i, int heapSize) {
        int l = i * 2 + 1, r = i * 2 + 2, largest = i;
        if (l < heapSize && a[l] > a[largest]) {
            largest = l;
        }
        if (r < heapSize && a[r] > a[largest]) {
            largest = r;
        }
        if (largest != i) {
            swap1(a, i, largest);
            maxHeapify(a, largest, heapSize);
        }
    }

    public void swap1(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * 给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
     *
     * @param matrix
     * @return
     */
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }
        if (matrix.length == 1 && matrix[0].length == 1) {
            if (matrix[0][0] == 49) {
                return 1;
            }
            return 0;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] rowDp = new int[row][col];
        int[][] colDp = new int[row][col];

        rowDp[row - 1][col - 1] = matrix[row - 1][col - 1] == 49 ? 1 : 0;
        colDp[row - 1][col - 1] = matrix[row - 1][col - 1] == 49 ? 1 : 0;

        for (int i = row - 2; i >= 0; i--) {
            rowDp[i][col - 1] = matrix[i][col - 1] == 49 ? rowDp[i + 1][col - 1] + 1 : 0;
            colDp[i][col - 1] = matrix[i][col - 1] == 49 ? 1 : 0;
        }

        for (int i = col - 2; i >= 0; i--) {
            rowDp[row - 1][i] = matrix[row - 1][i] == 49 ? 1 : 0;
            colDp[row - 1][i] = matrix[row - 1][i] == 49 ? colDp[row - 1][i + 1] + 1 : 0;
        }

        for (int i = row - 2; i >= 0; i--) {
            for (int j = col - 2; j >= 0; j--) {
                rowDp[i][j] = matrix[i][j] == 49 ? rowDp[i + 1][j] + 1 : 0;
                colDp[i][j] = matrix[i][j] == 49 ? colDp[i][j + 1] + 1 : 0;
            }
        }

        int maxArea = 0;
        for (int i = row - 1; i >= 0; i--) {
            for (int j = col - 1; j >= 0; j--) {
                // 横
                int r = colDp[i][j];

                int b = rowDp[i][j];

                if (b == 0) {
                    continue;
                }

                if (b == 1) {
                    maxArea = Math.max(maxArea, b * r);
                }

                for (int ib = b; ib > 0; ib--) {
                    int xs = Math.min(colDp[i + ib - 1][j], r);
                    for (int iib = ib; iib > 0; iib--) {
                        xs = Math.min(colDp[i + iib - 1][j], xs);

                    }

                    if (xs > 0) {
                        maxArea = Math.max(maxArea, ib * xs);
                    }
                }
            }
        }
        return maxArea;
    }

    public int maximalRectangle1(char[][] matrix) {
        int m = matrix.length;
        if (m == 0) {
            return 0;
        }
        int n = matrix[0].length;
        int[][] left = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    left[i][j] = (j == 0 ? 0 : left[i][j - 1]) + 1;
                }
            }
        }

        int ret = 0;
        for (int j = 0; j < n; j++) { // 对于每一列，使用基于柱状图的方法
            int[] up = new int[m];
            int[] down = new int[m];

            Deque<Integer> stack = new LinkedList<Integer>();
            for (int i = 0; i < m; i++) {
                while (!stack.isEmpty() && left[stack.peek()][j] >= left[i][j]) {
                    stack.pop();
                }
                up[i] = stack.isEmpty() ? -1 : stack.peek();
                stack.push(i);
            }
            stack.clear();
            for (int i = m - 1; i >= 0; i--) {
                while (!stack.isEmpty() && left[stack.peek()][j] >= left[i][j]) {
                    stack.pop();
                }
                down[i] = stack.isEmpty() ? m : stack.peek();
                stack.push(i);
            }

            for (int i = 0; i < m; i++) {
                int height = down[i] - up[i] - 1;
                int area = height * left[i][j];
                ret = Math.max(ret, area);
            }
        }
        return ret;
    }

    public int searchInsert(int[] nums, int target) {
        if (nums.length == 0 || nums[0] > target) {
            return 0;
        }

        int l = 0;
        int r = nums.length - 1;
        int res = nums.length;
        while (l <= r) {
            int mid = l + (r - l) / 2;

            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < target) {
                res = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return res + 1;
    }


    /**
     *
     * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
     *
     * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
     *
     * @param heights
     * @return
     */
    public int largestRectangleArea(int[] heights) {
        if (heights.length == 0) {
            return 0;
        }
        if (heights.length == 1) {
            return heights[0];
        }
        Stack<Integer> stack = new Stack<>();
        int area = 0;
        int index;
        int pre;
        for (int i = 0; i < heights.length; i++) {

            while (!stack.empty() && heights[stack.peek()] > heights[i]) {
                index = stack.pop();
                pre = stack.empty() ? -1 : stack.peek();
                area = Math.max(heights[index] * (i - pre - 1), area);
            }
            stack.push(i);

        }

        int w = stack.peek();
        int e;
        int left;
        while (!stack.empty()) {
            e = stack.pop();
            left = stack.empty() ? -1 : stack.peek();
            area = Math.max(heights[e] * (w - left), area);

        }
        return area;
    }

    public List<List<Integer>> levelOrder(TreeNode root) {

        if (root == null) {
            return new ArrayList<>();
        }

        LinkedList<TreeNode> linkedList = new LinkedList<>();
        linkedList.offer(root);
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> c;
        while (!linkedList.isEmpty()) {
            c = new ArrayList<>();

            int size = linkedList.size();
            for (int i = 0; i < size; i++) {
                TreeNode tr = linkedList.poll();
                if (tr == null) {
                    continue;
                }
                c.add(tr.val);
                if (tr.left != null) {
                    linkedList.offer(tr.left);
                }
                if (tr.right != null) {
                    linkedList.offer(tr.right);
                }

            }
            if (c.size() > 0) {
                result.add(c);
            }

        }
        return result;
    }

    public int findKthLargest1(int[] nums, int k) {
        if (nums.length == 0) {
            return -1;
        }
        int heapSize = nums.length - 1;
        for (int i = heapSize / 2; i >= 0; i--) {
            maxHeapify1(nums, i, heapSize);
        }

        for (int i = nums.length - 1; i >= nums.length - k + 1; i--) {
            swap12(nums, 0, i);
            heapSize--;
            maxHeapify1(nums, 0, heapSize);
        }
        return nums[0];
    }

    private void maxHeapify1(int[] nums, int start, int heapSize) {
        int left = 2 * start + 1, right = 2 * start + 2, bigIndex = start;
        if (left <= heapSize && nums[bigIndex] < nums[left]) {
            bigIndex = left;
        }
        if (right <= heapSize && nums[bigIndex] < nums[right]) {
            bigIndex = right;
        }
        if (start != bigIndex) {
            swap12(nums, bigIndex, start);
            maxHeapify1(nums, bigIndex, heapSize);
        }

    }

    private void swap12(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return targetSum == 0;
        }
        return hasPathSum(root.left, targetSum - root.val)
                || hasPathSum(root.right, targetSum - root.val);

    }

    /**
     * 给定一个二维网格和一个单词，找出该单词是否存在于网格中。
     *
     * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
     *
     *
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {
        if (word == null) {
            return false;
        }
        int[][] dp = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (exist(board, word, 0, i, j, dp)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean exist(char[][] board, String word, int index, int i, int j, int[][] dp) {
        if (dp[i][j] > 0) {
            return false;
        }
        if (index == word.length() - 1) {
            return board[i][j] == word.charAt(index);
        }

        boolean exist = false;

        dp[i][j] = 1;
        if (j + 1 < board[i].length && board[i][j + 1] == word.charAt(index + 1)) {
            exist = exist(board, word, index + 1, i, j + 1, dp);
        }
        if (exist) {
            return true;
        }
        if (j - 1 >= 0 && board[i][j - 1] == word.charAt(index + 1)) {
            exist = exist(board, word, index + 1, i, j - 1, dp);
        }
        if (exist) {
            return true;
        }
        if (i - 1 >= 0 && board[i - 1][j] == word.charAt(index + 1)) {
            exist = exist(board, word, index + 1, i - 1, j, dp);
        }
        if (exist) {
            return true;
        }
        if (i + 1 < board.length && board[i + 1][j] == word.charAt(index + 1)) {
            exist = exist(board, word, index + 1, i + 1, j, dp);
        }
        if (exist) {
            return true;
        }
        dp[i][j] = 0;


        return false;
    }




    public String longestPalindromeSubseq(String s) {
        if (s == null || s.length() == 0 || s.length() == 1) {
            return s;
        }


        StringBuilder builder = new StringBuilder();

        for (char c : s.toCharArray()) {
            builder.append("#").append(String.valueOf(c));
        }
        builder.append("#");
        char[] ss = builder.toString().toCharArray();
        int[] path = new int[ss.length];
        int r = -1;
        int c = 0;
        for (int i = 0; i < ss.length; i++) {
            path[i] = r > i ? Math.min(r - i, path[2 * c - i]) : 1;

            while (i + path[i] < ss.length && i - path[i] >= 0) {
                if (ss[i + path[i]] == ss[i - path[i]]) {
                    path[i]++;
                } else {
                    break;
                }
            }

            if (path[i] > r - c) {
                c = i;
                r = i + path[i] - 1;
            }
        }

        int start = c - path[c] + 2;
        char[] re = new char[path[c] - 1];
        for (int j = 0; start <= r; start = start + 2, j++) {
            re[j] = ss[start];
        }
        return Arrays.toString(re);
    }

//    public String longestPalindrome(String s) {
//        if (s == null || s.length() < 2) {
//            return s;
//        }
//        int[][] dp = new int[s.length()][s.length()];
//        char[] chars = s.toCharArray();
//
//        for (int i) {
//
//        }
//    }

    public String longestPalindrome2(String str) {
        if (str == null || str.length() == 1) {
            return str;
        }

        int preLen = 0;
        int c = 0;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int l1 = expend(chars, i, i);
            int l2 = expend(chars, i, i + 1);
            int max = Math.max(l1, l2);

            if (max > preLen) {
                c = i;
                preLen = max;
            }
        }
        if (preLen == 1) {
            return String.valueOf(chars[c]);
        }
        return str.substring(c - (preLen - 1) / 2, c + preLen / 2 + 1);
    }

    private int expend(char[] chars, int  left, int right) {
        while (left >= 0 && right < chars.length && chars[left] == chars[right]) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    /**
     * 给你一个仅由大写英文字母组成的字符串，你可以将任意位置上的字符替换成另外的字符，总共可最多替换 k 次。在执行上述操作后，找到包含重复字母的最长子串的长度。
     *
     * @param s
     * @param k
     * @return
     */
    public int characterReplacement(String s, int k) {
       if (s == null) {
           return 0;
       }
       if (s.length() < 2) {
           return s.length();
       }
       int left = 0;
       int right = 0;
       int[] dp = new int[26];
       int preMax = 0;
       int res = 0;
       while (right < s.length()) {
           dp[s.charAt(right) - 'A']++;

           preMax = Math.max(preMax, dp[s.charAt(right) - 'A']);

           if (right - left + 1 > preMax + k) {
               dp[s.charAt(left) - 'A']--;
               left++;

           }
           res = Math.max(res, right - left);
           right++;
       }
        return res;
    }

    public int largestRectangleArea1(int[] heights) {
        if (heights.length == 0) {
            return 0;
        }
        if (heights.length == 1) {
            return heights[0];
        }
        Stack<Integer> stack = new Stack<>();
        int area = 0;
        int index;
        int pre;

        for (int i = 0; i < heights.length; i++) {
            while (!stack.empty() && heights[stack.peek()] > heights[i]) {
                index = stack.pop();
                pre = stack.empty() ? -1 : stack.peek();
                area = Math.max(heights[index] * (i - pre - 1), area);
            }
            stack.push(i);

        }

        int w = stack.peek();
        int e;
        int left;
        while (!stack.empty()) {
            e = stack.pop();
            left = stack.empty() ? -1 : stack.peek();
            area = Math.max(heights[e] * (w - left), area);

        }
        return area;
    }

    public List<Integer> rightSideView(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> result = new ArrayList<>();

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            TreeNode treeNode = queue.peek();
            result.add(treeNode.val);

            for (int i = 0; i < size; i++) {
                treeNode = queue.poll();
                if (treeNode == null) {
                    break;
                }
                if (treeNode.right != null) {
                    queue.offer(treeNode.right);
                }
                if (treeNode.left != null) {
                    queue.offer(treeNode.left);
                }
            }
        }
        return result;
    }

    private void rightSideView(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }
        result.add(root.val);
        if (root.right != null) {
            rightSideView(root.right, result);
        } else {
            rightSideView(root.left, result);
        }
    }

    public String multiply(String num1, String num2) {
        if (num1 == null || num2 == null) {
            return null;
        }
        if ((num1.length() == 1 && num1.equals("0")) || (num2.length() == 1 && num2.equals("0")) ) {
            return "0";
        }

        LinkedList<String> path = new LinkedList<>();
        multiply(num1.toCharArray(), num2.toCharArray(), num2.length() - 1, path);
        StringBuilder buffer = new StringBuilder();
        while (!path.isEmpty()) {
            buffer.append(path.pollLast());
        }
        return buffer.toString();
    }

    private void multiply(char[] num1, char[] num2, int index, LinkedList<String> path) {

        if (index < 0) {
            return;
        }

        int preS = 0;
        LinkedList<String> str = new LinkedList<>();
        for(int i = num1.length - 1; i >= 0; i--) {
            int result = Integer.valueOf(String.valueOf(num1[i])) * Integer.valueOf(String.valueOf(num2[index]));
            result += preS;
            preS = result / 10;
            int c = result % 10;
            str.offer(c + "");
        }
        if (preS > 0) {
            str.offer(preS + "");
        }

        for (int i = index; i < num2.length - 1; i++) {
            str.offerFirst("0");
        }

        preS = 0;
        int size = str.size();
        while (size > 0) {
            String s = path.pollFirst();
            int j = s == null ? 0 : Integer.parseInt(s);
            int result = Integer.valueOf(str.pollFirst()) + j;

            result += preS;
            preS = result / 10;
            int c = result % 10;

            str.offer(c + "");
            size--;
        }
        if (preS > 0) {
            str.offer(preS + "");
        }
        path.addAll(str);
        multiply(num1, num2, index - 1, path);
    }

    public int candy(int[] candys) {
        return this.getCandy(candys, 0);
    }

    private int getCandy(int[] nums, int index) {
        if (index >= nums.length) {
            return 0;
        }
        int a = nums[index] + getCandy(nums, index + 2);
        int b = getCandy(nums, index + 1);
        return Math.max(a, b);
    }

    public int candy1(int[] candys) {
        int[] dp = new int[candys.length + 2];
        dp[candys.length - 1] = candys[candys.length - 1];
        for (int i = candys.length - 2; i >= 0; i--) {
            int a = candys[i] + dp[i + 2];
            int b = dp[i + 1];
            dp[i] = Math.max(a, b);
        }
        return dp[0];
    }

    private Stack<Integer> stack1 = new Stack<>();
    private Stack<Integer> stack2 = new Stack<>();
    public void put(int a) {
        while (!stack1.empty()) {
            stack2.push(stack1.pop());
        }
        stack2.push(a);
    }

    public int poll() {
        while (!stack2.empty()) {
            stack1.push(stack2.pop());
        }
        return stack1.pop();
    }

    public int[] searchRange(int[] nums, int target) {
        if (nums.length == 0) {
            return new int[]{-1, -1};
        }
        int[] result = new int[2];
        result[0] = this.search(nums, target, true);
        result[1] = this.search(nums, target, false);
        return result;
    }

    private int search(int[] nums, int target, boolean first) {
        int l = 0;
        int r = nums.length - 1;
        int ans = -1;
        while (l <= r) {
            int mid = (r + l) / 2;
            if (nums[mid] > target) {
                r = mid - 1;
            } else if (nums[mid] < target) {
                l = mid +1;
            } else {
                ans = mid;
                if (first) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
        }
        return ans;
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums.length == 0 || nums.length < 4) {
            return new ArrayList<>();
        }
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            if (nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) {
                break;
            }
            if (nums[i] + nums[nums.length - 1] + nums[nums.length - 2] + nums[nums.length - 1] < target) {
                continue;
            }
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                if (nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target) {
                    break;
                }
                if (nums[i] + nums[j] + nums[nums.length - 2] + nums[nums.length - 1] < target) {
                    continue;
                }

                int left = j + 1;
                int right = nums.length - 1;;

                while (left < right) {
                    int sum = nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum == target) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        left++;
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        right--;
                    } else if (sum > target) {
                        right--;
                    } else {
                        left++;
                    }
                }

            }
        }
        return result;
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<>();
        LinkedList<Integer> path = new LinkedList<>();
        combinationSum(candidates, 0, target, result, path);
        return result;
    }
    public void combinationSum(int[] candidates, int index, int target, List<List<Integer>> result, LinkedList<Integer> path) {
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        if (target < 0 || index == candidates.length) {
            return;
        }
        for (; index < candidates.length; index++) {
            if (target >= candidates[index]) {
                path.offerLast(candidates[index]);
                combinationSum(candidates, index, target - candidates[index], result, path);
                path.pollLast();
            }
        }
    }

    public int oil(int[] gas, int[] cost) {

        int c = 0;
        int max = 0;
        int index = 0;
        for (int i = 0; i < gas.length; i++) {
            c += gas[i] - cost[i];
            max += gas[i] - cost[i];
            if (c < 0) {
                index = i + 1;
                c = 0;
            }
        }
        return (max < 0 || index == gas.length) ? -1 : index;
    }

    public boolean isValidSudoku(char[][] board) {
        if (board.length == 0) {
            return false;
        }

        boolean[][] row = new boolean[9][10];
        boolean[][] col = new boolean[9][10];
        boolean[][] box = new boolean[9][10];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if ('.' == board[i][j]) {
                    continue;
                }
                int boxIndex = 3 * (i / 3) + j / 3;
                int num = board[i][j] - '0';
                if (row[i][num] || col[i][num] || box[boxIndex][num]) {
                    return false;
                }
                row[i][num] = true;
                col[i][num] = true;
                box[boxIndex][num] = true;
            }
        }
        return true;
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        if (candidates.length == 0) {
            return new ArrayList<>();
        }
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<>();
        LinkedList<Integer> path = new LinkedList<>();
        List<int[]> freq = new ArrayList<>();
        freq.add(new int[]{candidates[0], 1});

        for (int i = 1; i < candidates.length; i++) {
            if (freq.get(freq.size() - 1)[0] == candidates[i]) {
                freq.get(freq.size() - 1)[1]++;
            } else {
                freq.add(new int[]{candidates[i], 1});
            }
        }

        combinationSum2(target, result, path, freq, 0);

        return result;
    }

    private void combinationSum2(int target, List<List<Integer>> result,
                                                LinkedList<Integer> path, List<int[]> freq, int index) {
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        if (index == freq.size() || target < 0) {
            return;
        }
        for (; index < freq.size(); index++) {
            int[] nums = freq.get(index);
            int num = nums[0];
            int sq = nums[1];
            int max = Math.min(sq, target / num);
            for (int i = 1; i <= max; i++) {
                path.offerLast(num);
                combinationSum2(target - num * i, result, path, freq, index + 1);
            }
            for (int i = 0; i < max; i++) {
                path.pollLast();
            }
        }
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        LinkedList<Integer> path = new LinkedList<>();
        int[] dp = new int[nums.length];
        permuteUnique(nums, result, path, 0, dp);
        return result;
    }

    private void permuteUnique(int[] nums, List<List<Integer>> result,
                               LinkedList<Integer> path, int index, int[] dp) {
        if (index == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (dp[i] > 0 || (i > 0 && nums[i] == nums[i - 1] && dp[i - 1] == 0)) {
                continue;
            }
            dp[i] = 1;
            path.offerLast(nums[i]);
            permuteUnique(nums, result, path, index + 1, dp);
            path.pollLast();
            dp[i] = 0;
        }
    }

    /**
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）,机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角
     * （在下图中标记为 “Finish” ）。问总共有多少条不同的路径？
     *
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = m - 1; i >= 0; i--) {
            dp[i][n - 1] = 1;
        }
        for (int j = n - 1; j >= 0; j--) {
            dp[m - 1][j] = 1;
        }
        for (int i = m - 2; i >= 0; i++) {
            for (int j = n - 2; j >= 0; j++) {
                dp[i][j] = dp[i + 1][j] + dp[i][j + 1];
            }
        }
        return dp[0][0];
    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 1;
        }
        int l = maxDepth1(root.left);
        int r = maxDepth1(root.right);
        return Math.max(l, r) + 1;
    }

    private int maxDepth1(TreeNode node) {
        if (node == null) {
            return 1;
        }
        int l = maxDepth1(node.left);
        int r = maxDepth1(node.right);
        return Math.max(l, r) + 1;
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode cur = head;
        ListNode pre = null;
        ListNode he = null;
        ListNode end;
        int count = 1;
        while (cur != null) {
            if (count == 1) {
                he = cur;
            }
            cur = cur.next;
            end = cur;
            if (count == k) {
                while (he.next != end) {
                    ListNode next = he.next;
                    he.next = pre;
                    pre = he;
                    he = next;
                    count--;
                }
            }
            count++;
        }
        return head;
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        LinkedList<Integer> path = new LinkedList<>();
        subsets(nums, 0, result, path);
        return result;
    }

    private void subsets(int[] nums, int index, List<List<Integer>> result, LinkedList<Integer> path) {
        if (index == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        path.offerLast(nums[index]);
        subsets(nums, index + 1, result, path);
        path.pollLast();
        subsets(nums, index + 1, result, path);
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        LinkedList<Integer> path = new LinkedList<>();
        subsetsWithDup(nums, 0, result, path, new int[nums.length + 1]);
        return result;
    }

    private void subsetsWithDup(int[] nums, int index, List<List<Integer>> result, LinkedList<Integer> path, int[] dp) {
        result.add(new ArrayList<>(path));
        for (; index < nums.length; index++) {
            if (dp[index] > 0 || (index > 0 && nums[index] == nums[index - 1] && dp[index - 1] == 0)) {
                continue;
            }
            dp[index] = 1;
            path.offerLast(nums[index]);
            subsetsWithDup(nums, index + 1, result, path, dp);
            dp[index] = 0;
            path.pollLast();
        }
    }

    public int minPathSum(int[][] grid) {
        int[][] dp = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                dp[i][j] = -1;
            }
        }
        return minPathSum(grid, 0, 0, dp);
    }

    private int minPathSum(int[][] grid, int x, int y, int[][] dp) {
        if (x == grid.length - 1 && y == grid[x].length - 1) {
            return grid[x][y];
        }
        if (x > grid.length - 1 || y > grid[x].length - 1) {
            return Integer.MAX_VALUE;
        }
        if (dp[x][y] >= 0) {
            return dp[x][y];
        }
        int xPath = minPathSum(grid, x + 1, y, dp);
        int yPath = minPathSum(grid, x, y + 1, dp);
        dp[x][y] = Math.min(xPath, yPath)+ grid[x][y];
        return dp[x][y];
    }

    private int minPathSum1(int[][] grid) {
        int l = grid.length;
        int r = grid[0].length;
        int[][] dp = new int[l][r];
        dp[l - 1][r - 1] = grid[l - 1][r - 1];
        for (int i = l - 2; i >= 0; i--) {
            dp[i][r - 1] = grid[i][r - 1] + dp[i + 1][r - 1];
        }
        for (int j = r - 2; j >= 0; j--) {
            dp[l - 1][j] = grid[l - 1][j] + dp[l - 1][j + 1];
        }
        for (int i = l - 2; i >= 0; i--) {
            for (int j = r - 2; j >= 0; j--) {
                dp[i][j] = Math.min(dp[i + 1][j], dp[i][j + 1]) + grid[i][j];
            }
        }
        return dp[0][0];
    }



    public static void main(String[] args) {
        December d = new December();
//        d.minPathSum(new int[][]{{1,2,3}, {4,5,6}});

        d.nextPermutation(new int[]{2,4,3,2,1});
//        d.fourSum(new int[]{-1,-5,-5,-3,2,5,0,4}, -7);
//        d.put(1);
//        d.put(2);
//        d.put(3);
//
//        d.poll();
//        d.put(4);
//        d.poll();


//        new December().multiply("464", "01");
//        new December().candy1(new int[]{1,5,2,5,7,3,1,3,4,5,1,1,9,8,8,1});
//        System.out.println(new December().candy1(new int[]{1,5,2,5,7,3,1,3,4,5,1,1,9,8,8,1}));
//        System.out.println(new December().candy(new int[]{1,5,2,5,7,3,1,3,4,5,1,1,9,8,8,1}));
//        new December().characterReplacement("ABAB", 2);
//        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } };
//        printMatrix(matrix);
//        rotate(matrix);
//        System.out.println("=========");
//        printMatrix(matrix);
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(3);
        l1.next.next.next = new ListNode(4);
        l1.next.next.next.next = new ListNode(5);
//
//        TreeNode node = new TreeNode(1);
//        node.left = new TreeNode(2);
//        node.right = new TreeNode(3);
//        node.right.right = new TreeNode(4);
//        node.left.right = new TreeNode(5);
        d.reverseKGroup(l1, 2);
//
//        new December().rightSideView(node);
//
////        new December().searchInsert(new int[]{1,3,5,6}, 2);
//
//        node.right = new TreeNode(20);
//        ListNode[] lists = new ListNode[]{null, new ListNode(1)};
//        new December().maximalRectangle(new char[][]{{'1','1','1','1','1','1','1','1'},{'1','1','1','1','1','1','1','0'},{'1','1','1','1','1','1','1','0'},{'1','1','1','1','1','0','0','0'},{'0','1','1','1','1','0','0','0'}});

//        new December().exist(new char[][]{{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}}, "SEE");

//        new December().findKthLargest1(new int[]{3,1,2,4}, 2);



    }
}
