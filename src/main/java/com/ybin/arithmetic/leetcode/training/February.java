package com.ybin.arithmetic.leetcode.training;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author : bing.yue001
 * @version : 1.0
 * @date : 2021-2-12 10:11
 * @description :
 */
public class February {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public void solveSudoku(char[][] board) {
        boolean[][] rowB = new boolean[10][10];
        boolean[][] colB = new boolean[10][10];
        boolean[][] bucket = new boolean[10][10];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                int bucketIndex = 3 * (i / 3) + j / 3;
                if (!String.valueOf(board[i][j]).equals(".")) {
                    int num = board[i][j] - '0';
                    rowB[i][num] = true;
                    colB[j][num] = true;
                    bucket[bucketIndex][num] = true;
                }
            }
        }
        solveSudoku(board, 0, 0, rowB, colB, bucket);
    }

    private boolean solveSudoku(char[][] board, int i, int j,
                                boolean[][] rowB, boolean[][] colB,
                                boolean[][] bucket) {
        if (i == board.length) {
            return true;
        }
        int nextI = j == board[0].length - 1 ? i + 1 : i;
        int nextY = j == board[0].length - 1 ? 0 : j + 1;
        if (!String.valueOf(board[i][j]).equals(".")) {
            return solveSudoku(board, nextI, nextY, rowB, colB, bucket);
        } else {
            int bucketIndex = 3 * (i / 3) + j / 3;
            for (int x = 1; x <= 9; x++) {
                if (!rowB[i][x] && !colB[j][x] && !bucket[bucketIndex][x]) {
                    rowB[i][x] = true;
                    colB[j][x] = true;
                    bucket[bucketIndex][x] = true;
                    board[i][j] = (char) (x + '0');
                    if (solveSudoku(board, nextI, nextY, rowB, colB, bucket)) {
                        return true;
                    }
                    board[i][j] = '.';
                    rowB[i][x] = false;
                    colB[j][x] = false;
                    bucket[bucketIndex][x] = false;
                }
            }
            return false;
        }
    }


    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k <= 0) {
            return head;
        }
        ListNode ne = new ListNode(0);
        ne.next = head;
        ListNode pre = ne;

        while (head != null) {
            ListNode tail = pre;
            for (int i = 0; i < k; i++) {
                tail = tail.next;
                if (tail == null) {
                    return ne.next;
                }
            }

            ListNode next = tail.next;
            ListNode[] resever = this.resevr(head, tail);
            head = resever[0];
            tail = resever[1];
            pre.next = head;
            tail.next = next;

            pre = tail;
            head = tail.next;
        }
        return ne.next;
    }

    private ListNode[] resevr(ListNode head, ListNode tail) {
        ListNode pre = tail.next;
        ListNode p = head;
        while (pre != tail) {
            ListNode next = p.next;
            p.next = pre;
            pre = p;
            p = next;
        }
        return new ListNode[]{tail, head};
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

    private class IsValidBST {
        private boolean isTrue;
        private long min;
        private long max;

        public IsValidBST(boolean isTrue, long min, long max) {
            this.isTrue = isTrue;
            this.min = min;
            this.max = max;
        }
    }

    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return false;
        }
        IsValidBST result = this.isValidBST1(root);
        return result.isTrue;
    }

    private IsValidBST isValidBST1(TreeNode root) {

        if (root == null) {
            return new IsValidBST(true, Long.MAX_VALUE, Long.MIN_VALUE);
        }
        long max = root.val;
        long min = root.val;
        IsValidBST left = isValidBST1(root.left);
        if (!left.isTrue) {
            return new IsValidBST(false, min, max);
        }
        IsValidBST right = isValidBST1(root.right);
        if (!right.isTrue) {
            return new IsValidBST(false, min, max);
        }
        max = Math.max(right.max, root.val);
        min = Math.min(left.min, root.val);
        return new IsValidBST(left.max < root.val && right.min > root.val, min, max);
    }

    public boolean canJump(int[] nums) {

        // [3,2,1,0,4]
        // [2,3,1,1,4]
        int step = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > step) {
                return false;
            }
            step = Math.max(step, i + nums[i]);
        }
        return true;
    }

    public int firstMissingPositive(int[] nums) {
        int l = 0;
        int r = nums.length;
        while (l < r) {
            if (nums[l] == l + 1) {
                l++;
            } else if (nums[l] > r || nums[l] <= l || nums[l] == nums[nums[l] - 1]) {
                nums[l] = nums[--r];
            } else {
                swapMissingPositive(nums, l, nums[l] - 1);
            }
        }
        return l + 1;
    }

    private void swapMissingPositive(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public boolean isMatch(String s, String p) {
        if (p.length() == 0) {
            return s.length() == 0;
        }
        return match(s.toCharArray(), p.toCharArray(), 0, 0);
    }
    private boolean match(char[] s, char[] p, int si, int pi) {
        if (pi >= p.length) {
            return si >= s.length;
        }
        boolean match = si < s.length && (s[si] == p[pi] || p[pi] == '.');
        if (pi + 1 < p.length && p[pi + 1] == '*') {
            return match(s, p, si, pi + 2) || (match && match(s, p, si + 1, pi));
        }
        return match && match(s, p, si + 1, pi + 1);
    }

    public boolean isMatch2(String s, String p) {
        if (p.length() == 0) {
            return s.length() == 0;
        }
        char[] ss = s.toCharArray();
        char[] pp = p.toCharArray();
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[s.length()][p.length()] = true;
        for (int i = s.length() - 1 ; i >= 0; i--) {
            for (int j = p.length() - 1; j >= 0; j--) {
                boolean match = i < s.length() && (ss[i] == pp[j] || pp[j] == '.');
                if (j + 1 < p.length() && pp[j + 1] == '*') {
                    dp[i][j] = (match && dp[i + 1][j]) || dp[i][j + 2];
                    continue;
                }
                dp[i][j] = match && dp[i + 1][j + 1];
            }
        }
        return dp[0][0];
    }

    private boolean isMatch(char[] s, char[] p, int si, int pi) {

        return false;
    }

    int[][] mem;
    public boolean isMatchChar(char[] s, int s1, char[] p, int p1) {
        if(p1 >= p.length)
            return s1 >= s.length;

        boolean match = s1 < s.length && ((s[s1] == p[p1]) || p[p1] == '.');
        if(p.length - p1 >= 2 && p[p1 + 1] == '*') {
            boolean t = isMatchChar(s, s1, p, p1 + 2)
                    || (match && isMatchChar(s, s1 + 1, p, p1));

            return t;
        }
        boolean t = match && isMatchChar(s, s1 + 1, p, p1 + 1);

        return t;
    }
    public boolean isMatch1(String s, String p) {
        this.mem = new int[s.length() + 1][p.length() + 1];
        char[] ss = s.toCharArray(), pp = p.toCharArray();
        return isMatchChar(ss, 0, pp, 0);
    }

    /**
     * 给定一个 没有重复 数字的序列，返回其所有可能的全排列。
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        LinkedList<Integer> path = new LinkedList<>();
        if (nums.length == 0) {
            result.add(path);
            return new ArrayList<>(result);
        }


        int[] dp = new int[nums.length];

        this.permute(nums, 0, path, result, dp);

        return result;
    }

    private void permute(int[] nums, int index, LinkedList<Integer> path, List<List<Integer>> result, int[] dp) {
        if (index == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (dp[i] > 0) {
                continue;
            }
            dp[i] = 1;
            path.offerLast(nums[i]);
            permute(nums, index + 1, path, result, dp);
            path.pollLast();
            dp[i] = 0;
        }
    }

    /**
     * https://leetcode-cn.com/problems/jump-game-ii/
     *
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return 0;
        }
        int step = 0;
        int cur = 0;
        int next = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (i > cur) {
                step++;
                cur = next;
            }
            next = Math.max(next, i + nums[i]);
        }
        return step;
    }

    /**
     * https://leetcode-cn.com/problems/jump-game-iii/
     * 这里有一个非负整数数组 arr，你最开始位于该数组的起始下标 start 处。当你位于下标 i 处时，你可以跳到 i + arr[i] 或者 i - arr[i]。
     *
     * @param arr
     * @param start
     * @return
     */
    public boolean canReach(int[] arr, int start) {
        Queue<Integer> leveQueue = new LinkedList<>();
        Map<Integer, Integer> leveMap = new HashMap<>();
        leveQueue.add(start);
        leveMap.put(start, 0);
        int leve = 0;
        while (!leveQueue.isEmpty()) {
            Integer cur = leveQueue.poll();
            if (arr[cur] == 0) {
                return true;
            } else {
                int left = cur - arr[cur];
                int right = cur + arr[cur];
                if (left >=0 && !leveMap.containsKey(left)) {
                    leveQueue.offer(left);
                    leveMap.put(left, leve + 1);
                }
                if (right < arr.length && !leveMap.containsKey(right)) {
                    leveQueue.offer(right);
                    leveMap.put(right, leve + 1);
                }
            }
        }
        return false;
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        if (obstacleGrid[m - 1][n - 1] == 1) {
            return 0;
        }
        int[][] dp = new int[m][n];
        dp[m - 1][n - 1] = 1;
        for (int i = m - 2; i >= 0 ; i--) {
            dp[i][n - 1] = dp[i + 1][n - 1] == 0 ? 0 : 1;
            if (obstacleGrid[i][n - 1] == 1) {
                dp[i][n - 1] = 0;
            }

        }
        for (int j = n - 2; j >= 0 ; j--) {
            dp[m - 1][j] = dp[m - 1][j + 1] == 0 ? 0 : 1;
            if (obstacleGrid[m - 1][j] == 1) {
                dp[m - 1][j] = 0;
            }
        }
        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                    continue;
                }
                dp[i][j] = dp[i + 1][j] + dp[i][j + 1];
            }
        }
        return dp[0][0];
    }

    /**
     * 输入: [7,1,5,3,6,4]
     * 输出: 7
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     *      随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
     * 求股票利润
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        if (prices.length == 1) {
            return 0;
        }
        int[][] dp = new int[prices.length + 1][2];
        for (int i = 0; i < prices.length; i++) {
            for (int j = 0; j < prices.length; j++) {
                dp[i][j] = -1;
            }
        }
        return maxProfit(prices, 0, 0, dp);
    }

    private int maxProfit(int[] prices, int m, int  n, int[][] dp) {
        if (n == prices.length) {
            return 0;
        }
        if (dp[m][n] > -1) {
            return dp[m][n];
        }
        int l = (prices[n] - prices[m]) + maxProfit(prices, n + 1, n + 1, dp);
        int r = maxProfit(prices, m, n + 1, dp);
        int sum = Math.max(l, r);
        dp[m][n] = sum;
        return sum;
    }

    public int maxProfit1(int[] prices) {
        if (prices.length == 1) {
            return 0;
        }
        int ans = 0;
        int left = 0;
        for (int i = 1; i < prices.length; i++) {
            ans += Math.max(ans, prices[i] - prices[i - 1]);
        }
        return ans;
    }


    public static void main(String[] args) {
        new February().isMatch2("ab", ".*");
        new February().firstMissingPositive(new int[]{2, 1});
        TreeNode node = new TreeNode(0);
        new February().maxProfit(new int[]{7,1,5,3,6,4});
//        node.left = new TreeNode(1);
        node.right = new TreeNode(1);
//        node.left.left = new TreeNode(19);
//        node.right.right = new TreeNode(56);
//        node.left.left.right = new TreeNode(27);
        new February().isValidBST(node);

        ListNode listNode = new ListNode(0);
        ListNode r = listNode;
        for (int i = 1; i < 6; i++) {
            listNode.next = new ListNode(i);
            listNode = listNode.next;
        }
        new February().reverseKGroup(r.next, 3);
//        new February().solveSudoku(new char[][]{{'5','3','.','.','7','.','.','.','.'},{'6','.','.','1','9','5','.','.','.'},{'.','9','8','.','.','.','.','6','.'},{'8','.','.','.','6','.','.','.','3'},{'4','.','.','8','.','3','.','.','1'},{'7','.','.','.','2','.','.','.','6'},{'.','6','.','.','.','.','2','8','.'},{'.','.','.','4','1','9','.','.','5'},{'.','.','.','.','8','.','.','7','9'}});
    }
}
