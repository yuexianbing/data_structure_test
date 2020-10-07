package com.ybin.arithmetic.leetcode;

import com.ybin.btree.BtreeLinked;
import com.ybin.link.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author : bing.yue001
 * @version : 1.0
 * @date : 2020-9-24 13:51
 * @description : 暴力递归
 */
public class ViolenceRecursion {

    /**
     * 给定一个数字,其中每一位字符能转换为具体的字母，1->A,2->B,3->C....26->Z
     * 求该字符数组最多能转换成字母拼接的字符串的数量
     *
     * @param arr
     * @param
     */
    public int process(String arr) {
        if (arr == null) {
            return 0;
        }
        return process(arr.toCharArray(), 0);

    }

    public int process(char[] c, int index) {
        if (index == c.length) {
            return 1;
        }
        if (c[index] == '0') {
            return 0;
        }

        if (c[index] == '1') {
            int p1 = process(c, index + 1);
            if (index + 2 < c.length) {
                p1 = p1 + process(c, index + 2);
            }
            return p1;
        }
        if (c[index] == '2') {
            int p2 = process(c, index + 1);
            if (index + 2 < c.length && c[index + 2] <= 6) {
                p2 = p2 + process(c, index + 2);
            }
            return p2;
        }
        return process(c, index + 1);
    }

    /**
     * 两个数组,a表示背包的重量,b表示对应背包的价值;
     * 一个人最多拿wight重量的货物,求怎么计算才能获得最大价值的货物
     *
     */
    public int backage(int[] a, int[] b, int wight) {
        if (a == null || b == null) {
            return 0;
        }
        if (wight <= 0) {
            return 0;
        }
        return process(a, b, 0, 0, wight);
    }

    private int process(int[] a, int[] b, int index, int totalWight, int wight) {
        if (totalWight > wight) {
            return -1;
        }
        if (index == a.length) {
            return 0;
        }
        int no = process(a, b, index + 1, totalWight, wight);
        int yes = process(a, b, index + 1, totalWight + a[index], wight);
        int value = -1;
        if (yes > 0) {
            value = b[index] + yes;
        }
        return Math.max(no, value);
    }

    private int process(int[] a, int[] b, int index, int wight) {
        if (wight <= 0) {
            return -1;
        }
        if (index == a.length) {
            return 0;
        }
        int no = process(a, b, index + 1, wight);
        int yes = process(a, b, index + 1, wight - a[index], wight);
        int value = -1;
        if (yes > 0) {
            value = b[index] + yes;
        }
        return Math.max(no, value);
    }

    /**
     * 给定n张排,每张牌代表一个分数,A,B两人从牌中拿取分数,A先拿,且两人必须从两端拿
     * 求A,B两人拿完牌后的最大分数
     *
     * @param arr
     */
    public int takeScore(int[] arr) {
        if (arr == null) {
            return 0;
        }
        return Math.max(takeScoreBefore(arr, 0, arr.length - 1), takeScoreAfter(arr, 0, arr.length - 1));
    }

    private int takeScoreBefore(int[] arr, int left, int right) {
        if (left == right) {
            return arr[left];
        }
        return Math.max(arr[left] + takeScoreAfter(arr, left + 1, right),
                arr[right] + takeScoreAfter(arr, left, right - 1));
    }

    private int takeScoreAfter(int[] arr, int left, int right) {
        // 后手拿,left==right的时候,后收没有拿的机会了,所以BaseCase返回0,拿不到分数
        if (left == right) {
            return 0;
        }
        // 后手拿,因为是后拿,前面的人肯定会让你拿到最小分数,所以这里取最小
        return Math.min(takeScoreBefore(arr, left + 1, right), takeScoreBefore(arr, left, right - 1));
    }

    /**
     * 斐波拉契数列求和
     * 如果有n个台阶,人每次只能跳一阶或者二阶,求能跳完的最多可能性
     *
     * n=0 > 0
     * n=1 > 1
     * n=2 > 2
     * n=3 > 3
     * n -> f(n-1) + f(n-2)
     *
     * @param args
     */
    public int count(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return count(n - 1) + count(n - 2);
    }

    public void count1(int n) {
        int pre = 0;
        int pre1 = 1;
        int cur = 0;
        for (int i = 1; i <= n; i++) {
            cur = pre + pre1;
            pre = pre1;
            pre1 = cur;

        }
        System.out.printf("cccc = "  + cur);
    }

    /**
     * 从中序与后序遍历序列构造二叉树
     * 根据一棵树的中序遍历与后序遍历构造二叉树。
     * ​
     * 注意:
     * 你可以假设树中没有重复的元素。
     * 例如，给出                          0  1 2 3  4  5  6  7  8  9     4  5  6  7  8  9
     * 中序遍历 inorder = [9,3,15,20,7]   [5,9,10,3,14,15,16,20,7,30]   [14,15,16,20,7,30]
     *                                                                  3  4  5 6  7  8
     * 后序遍历 postorder = [9,15,7,20,3] [5,10,9,14,16,15,30,7,20,3]   [14,16,15,30,7,20]
     *     3
     *    /\
     *  9  20
     * /\   /\
     *5 10 15 7
     *    /\   \
     *  14  16  30
     *
     * 返回如下的二叉树：
     *
     * @param inorder
     */
    public BtreeLinked.Node convert(int[] inorder, int[] postorder) {
        return this.convert(inorder, postorder, 0, inorder.length - 1, 0, inorder.length - 1);
    }

    private BtreeLinked.Node convert(int[] inorder, int[] postorder,
                                    int inorderStart, int inorderEnd,
                                    int postStart, int postEnd) {
        if (inorderStart == inorderEnd) {
            return new BtreeLinked.Node(inorder[inorderStart]);
        }
        int item = postorder[postEnd];
        BtreeLinked.Node newHead = new BtreeLinked.Node(item);


        int headIndex = -1;
        for (int index = inorderStart; index < inorderEnd; index++) {
            if (inorder[index] == item) {
                headIndex = index;
                break;
            }
        }

        int leftLength = headIndex - inorderStart;
        int rightLength = inorderEnd - headIndex;

        // 右节点
        if (rightLength > 0) {
            BtreeLinked.Node right = convert(inorder, postorder, headIndex + 1, inorderEnd, postEnd - rightLength, postEnd - 1);
            newHead.setRightNode(right);
        }
        // 左节点
        if (leftLength > 0) {
            BtreeLinked.Node left = convert(inorder, postorder, inorderStart, headIndex - 1, postStart , postEnd - rightLength - 1);
            newHead.setLeftNode(left);
        }

        return newHead;
    }

    /**
     * 有n个格子,每次只能走一个,从m格子触发走k步到达p格子
     * 求有多少种走法
     *
     * @param n
     * @param m
     * @param p
     * @param k
     * @return
     */
    public int way(int n, int m, int p, int k) {
        if (n < 0 || m > n || m < 0 || p > n || p < 0 || k < 0) {
            return -1;
        }
        int[][] dpWay = new int[n][k];
        for (int index = 0; index < n; index++) {
            for (int jndex = 0; jndex < k; jndex++) {
                dpWay[index][jndex] = -1;
            }
        }
        return way(n, m, p, k, dpWay);
    }

    private int way(int n, int cur, int target, int k, int[][] dpWay) {
        if (dpWay[cur][target] != -1) {
            return dpWay[cur][target];
        }
        if (k <= 0) {
            dpWay[cur][k] = cur == target ? 1 : 0;
            return dpWay[cur][k];
        }
        if (cur == 1) {
            dpWay[cur][k] = way(n, 2, target, k - 1, dpWay);
            return dpWay[cur][k];
        }
        if (cur == n) {
            dpWay[cur][k] = way(n, n - 1, target, k - 1, dpWay);
            return dpWay[cur][k];
        }
        dpWay[cur][k] = way(n, cur - 1, target, k -1, dpWay) + way(n, cur + 1, target, k - 1, dpWay);
        return dpWay[cur][k];
    }

    /**
     * n皇后问题
     * n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击,
     * 同一行,同一列,同一斜线的皇后会互相攻击
     *
     * @param args
     */
    public List<List<String>> queen(int n) {
        int[] record = new int[n];
        List<List<String>> result = new ArrayList<>();
        queen(n, 0, new LinkedList<>(), result, record);
        return result;
    }

    private void queen(int n, int row, Queue<String> path1, List<List<String>> result, int[] record) {
        if (row == n) {
            result.addAll(new ArrayList(path1));

            return;
        }

        for (int col = 0; col < n; col++) {
            if (this.isT(row, record, col)) {
                path1.offer(".");
                continue;
            }
            path1.offer("*");
            record[row] = col;
            queen(n, row + 1, path1, result, record);
            path1.remove();

        }
    }

    private boolean isT(int row, int[] record, int col) {
        for (int i = 0; i < row; i++) {
            if (record[i] == col || Math.abs(row - i) == Math.abs(col - record[i])) {
                return true;
            }
        }
        return false;
    }



    public static void main(String[] args) {
        new ViolenceRecursion().queen(4);
    }
}
