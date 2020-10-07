package com.ybin.arithmetic.leetcode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author : bing.yue001
 * @version : 1.0
 * @date : 2020-9-21 12:43
 * @description : 动态规划
 */
public class DynamicPlanning {

    /**
     * 给定一个可包含重复数字的序列，返回所有不重复的全排列
     * 输入: [1,1,2]
     * 输出:
     * [
     * [1,1,2],
     * [1,2,1],
     * [2,1,1]
     * ]
     *
     */
    public List<List<Integer>> getAll(int[] arr) {
        List<List<Integer>> result = new ArrayList<>();
        if (arr.length == 1) {
            result.add(new ArrayList<>(arr[0]));
            return result;
        }
        int[] used = new int[arr.length];
        dfs(arr, result, new LinkedList<>(), used);
        return result;
    }


    public void dfs(int arr[], List<List<Integer>> result, Deque<Integer> path, int[] used) {
        if (path.size() == arr.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < arr.length; i ++) {

            if (used[i] == 1) {
                continue;
            }
            path.addLast(arr[i]);
            used[i] = 1;
            dfs(arr, result, path, used);
            used[i] = 0;
            path.removeLast();
        }
    }

    /**
     * 逆序栈
     * 先递归到栈低,删除元素并返回
     * 在递归中处理删除的元素添加,3是栈底拿出的元素,则会放到最后添加
     *
     * @param stack
     */
    public void reversal(Stack<Integer> stack) {
        if (stack.size() == 0) {
            return;
        }
        int result = get(stack);
        reversal(stack);
        stack.push(result);
    }



    private int get(Stack<Integer> stack) {
        int result = stack.pop();
        if (stack.empty()) {
            return result;
        } else {
            int last = get(stack);
            stack.push(result);
            return last;
        }
    }


    /**
     * 求字符串能拼接的子序列,basecase = index == 数组长度
     *
     * @param s
     * @return
     */
    public List<String> process(String s) {
        List<String> result = new ArrayList<>();
        if (s == null || s.length() == 1) {
            return result;
        }
        process1(s.toCharArray(), result, "", 0);
        return result;
    }

    private void process1(char[] c, List<String> result, String path, int index) {
        if (index == c.length) {
            result.add(path);
            return;
        }

        process1(c, result, path, index + 1);

        // 要当前
        path = path + c[index];
        process1(c, result, path, index + 1);
    }

    public void verifyBracket(String bracket) {
        if (bracket == null) {
            return;
        }
        char[] c = bracket.toCharArray();
        int left = 0;
        int right = 0;
        for (char aC : c) {
            if ("(".equals(String.valueOf(aC))) {
                left++;
            } else {
                right++;
            }
        }
        int result = left - right;
        if (result < 0) {
            System.out.println("还需要左括号:" + Math.abs(result));
        } else if (result > 0) {
            System.out.println("还需要右括号:" + result);
        } else {
            System.out.println("成功匹配");
        }
    }

    /**
     * com.ybin.arithmetic.leetcode.ViolenceRecursion#process(int[], int[], int, int, int)
     * 背包问题
     *
     * @param a
     * @param b
     * @param wight
     * @return
     */
    public int backge(int[] a, int[] b, int wight) {
        if (a == null || b == null) {
            return 0;
        }
        if (wight <= 0) {
            return 0;
        }
        int[][] dpWay = new int[a.length][wight];
        for (int row = a.length - 1; row >= 0; row--) {
            for (int col = 0; col < a.length; col++) {
                int no = dpWay[row + 1][col];
                int yes = dpWay[row + 1][col];
                if (yes != -1) {
                    yes = b[col] + yes;
                }
                dpWay[row][col] = Math.max(no, yes);
            }
        }
        return dpWay[0][wight];
    }

}
