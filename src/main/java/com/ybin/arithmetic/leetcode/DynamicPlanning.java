package com.ybin.arithmetic.leetcode;

import org.apache.commons.math3.analysis.function.Min;

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
     * @see com.ybin.arithmetic.leetcode.ViolenceRecursion#backage
     * 背包问题
     * 建立对应暴力递归从index出发到wight的二维表
     * basecase 是index=arr.length时返回0,所以二维表的最后一行都是0
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

    /**
     * @see ViolenceRecursion#takeScore(int[])
     * 动态规划
     *
     * @param arr
     * @return
     */
    public int takeScore(int[] arr) {
        if (arr == null) {
            return 0;
        }
        int[][] firstDp = new int[arr.length][arr.length];
        int[][] secondDp = new int[arr.length][arr.length];

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                firstDp[i][j] = -1;
            }
        }
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = 1; j < arr.length; j++) {
                firstDp[i][j] = Math.max(arr[i] + firstDp[i + 1][j], arr[j] + firstDp[i][j - 1]);
                secondDp[i][j] = Math.min(secondDp[i+1][j], secondDp[i][j-1]);
            }
        }

        return Math.max(firstDp[0][arr.length], secondDp[0][arr.length]);
    }

    /**
     * C.已参数1做行,参数2做列
     * 字符串a的长度代表有多少行,b的长度代表有多少列,每一个空格代表到该位置的最长公共子序列的长度
     * 求 a,b的最长公共子序列
     *
     * @param a
     * @param b
     * @return
     */
    public int subString(String a, String b) {
        if (a == null || b == null) {
            return 0;
        }
        int[][] dp = new int[a.length()][b.length()];
        char[] acs = a.toCharArray();
        char[] bcs = b.toCharArray();
        // 第一行因为只会出现一个acs的首字母,所以如果在bcs中找到了相同的,则从找到的位置开始,所以的值都为1
        for (int i = 0; i < bcs.length; i++) {
            if (i == 0) {
                dp[0][i] = acs[0] == bcs[i] ? 1 : 0;
            } else {
                dp[0][i] = Math.max(acs[0] == bcs[i] ? 1 : 0 , dp[0][i - 1]);
            }
        }
        for (int j = 0; j < acs.length; j++) {
            if (j == 0) {
                dp[j][0] = bcs[0] == acs[j] ? 1 : 0;
            } else {
                dp[j][0] = Math.max(bcs[0] == acs[j] ? 1 : 0 , dp[j - 1][0]);
            }
        }
        // 对于row,cl位置
        // 场景1：最长公共子串即不已row结尾也不已cl结尾
        // 场景2：最长公共子串已row结尾但不已cl结尾
        // 场景3：最长公共子串不已row结尾但已cl结尾
        // 场景3：最长公共子串已row结尾且已cl结尾,此时acs[row]=bcs[cl],且等于1+acs[row-1][cl-1]
        for (int row = 1; row < acs.length; row++) {
            for (int cl = 1; cl < bcs.length; cl++) {
                dp[row][cl] = Math.max(dp[row - 1][cl - 1], dp[row - 1][cl]);
                dp[row][cl] = Math.max(dp[row][cl], dp[row][cl - 1]);
                if (acs[row] == bcs[cl]) {
                    dp[row][cl] = Math.max(dp[row][cl], 1 + dp[row - 1][cl]);
                }
            }
        }
        return dp[acs.length - 1][bcs.length - 1];
    }

    /**
     * 数组代表每个人喝完咖啡准备刷杯子的时间,washTime代表洗咖啡机每次洗咖啡的时间,每次只能洗一杯,
     * 假定等待洗咖啡的机器的杯子不能挥发,每次自己挥发的时间是volatilizeTime
     * 求n个人喝完咖啡并洗完杯子的最少时间
     *
     *
     * @param arr
     * @param washTime
     * @param volatilizeTime
     * @return
     */
    public int coffeeLeastTime(int[] arr, int washTime, int volatilizeTime) {
        if (arr.length == 0) {
            return 0;
        }
        return coffeeLeastTime(arr, 0, washTime, volatilizeTime, 0);
    }

    private int coffeeLeastTime(int[] arr, int index, int washTime, int volatilizeTime, int washEndTime) {
        if (index == arr.length - 1) {
            return Math.min(Math.max(washEndTime, arr[index]) + washTime, arr[index] + volatilizeTime);
        }
        // 当前喝完用咖啡机洗,再决策剩下的
        int washTime1 = Math.max(washEndTime, arr[index]) + washTime;
        int nextTime1 = coffeeLeastTime(arr, index + 1, washTime1, volatilizeTime, washTime1);
        int p1 = Math.max(washTime1, nextTime1);

        // 当前喝完不用咖啡机洗,再决策剩下的
        int volatilize = arr[index] + volatilizeTime;
        int nextTime2 = coffeeLeastTime(arr, index + 1, washTime, volatilize, washEndTime);
        int p2 = Math.max(volatilize, nextTime2);

        return Math.min(p1, p2);
    }

    /**
     * 动态规划,建立二维表,
     *
     * @param arr
     * @param washTime
     * @param volatilizeTime
     * @return
     */
    public int coffeeLeastTime1(int[] arr, int washTime, int volatilizeTime) {
        if (arr.length == 0) {
            return 0;
        }
        // 小贪心,如果洗咖啡机的时间大于挥发时间,则直接让所以杯子挥发
        if (washTime > volatilizeTime) {
            return arr[arr.length - 1] + volatilizeTime;
        }
        int maxWashTime = 0;
        for (int a : arr) {
            maxWashTime = Math.max(maxWashTime, a) + washTime;
        }
        int[][] dp = new int[arr.length][maxWashTime + 1];
        for (int index = 0; index < maxWashTime + 1; index++) {
            dp[arr.length - 1][index] = Math.min(Math.max(index, arr[arr.length - 1]) + washTime, arr[arr.length - 1] + volatilizeTime);
        }
        for (int row = arr.length - 1; row >= 0; row--) {
            for (int cl = 0; cl < dp[row].length; cl++) {
                int washTime1 = Math.max(cl, arr[row]) + washTime;
                int nextTime1 = dp[row + 1][washTime1];
                int p1 = Math.max(washTime1, nextTime1);

                int volatilize = arr[row] + volatilizeTime;
                int nextTime2 = dp[row + 1][cl];
                int p2 = Math.max(volatilize, nextTime2);

                dp[row][cl] = Math.max(p1, p2);

//                // 当前喝完用咖啡机洗,再决策剩下的
//                int washTime1 = Math.max(washEndTime, arr[index]) + washTime;
//                int nextTime1 = coffeeLeastTime(arr, index + 1, washTime1, volatilizeTime, washTime1);
//                int p1 = Math.max(washTime1, nextTime1);
//
//                // 当前喝完不用咖啡机洗,再决策剩下的
//                int volatilize = arr[index] + volatilizeTime;
//                int nextTime2 = coffeeLeastTime(arr, index + 1, washTime, volatilize, washEndTime);
//                int p2 = Math.max(volatilize, nextTime2);
            }
        }
        return dp[0][0];
    }

    /**
     * 有一个排成一排的方格,方格上有红色或者蓝色涂染,
     * 其涂染顺序为squares,假定可以将红色涂成绿色,
     * 绿色涂成红色,求最少涂染方格数使G距离左侧边界最远,R最近
     * 如RGRGRRG -> RRRRRRG, 最少涂染2个G
     *
     * @param squares
     * @return
     */
    public int redAndGree(String[] squares) {
        if (squares == null) {
            return 0;
        }
        // 数组预处理,已index为分界线,右边有多少个对应元素
        int[] read = new int[squares.length];
        read[squares.length - 1] = squares[squares.length - 1].equals("R") ? 1 : 0;
        for (int index = squares.length - 2; index >= 0; index++) {
            read[index] = (squares[index].equals("R") ? 1 : 0) + read[index + 1];
        }
        int result = read[0];
        for (int index = 0; index < squares.length - 1; index ++) {
            int left = squares[index].equals("G") ? 1 : 0;
            result = Math.min(result, left + read[index + 1]);
        }
        result = Math.min(result, squares[squares.length - 1].equals("G") ? 1 : 0);
        return result;
    }
}
