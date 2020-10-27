package com.ybin.arithmetic.leetcode;

import java.util.PriorityQueue;
import java.util.Set;

/**
 * @author : bing.yue001
 * @version : 1.0
 * @date : 2020-9-2 19:49
 * @description : 贪心算法
 */
public class Greedy {

    /**
     * 一条街有X和.标志位,X不能放灯,也不能被照亮,.能放灯也能被照亮;
     * 在一个.上放了灯,它左右的.都能被照亮,但是会被X隔断;
     * 求照亮这条街的最少灯数;
     *
     * 暴力解
     *
     *
     * @param str
     * @param index
     * @param hashSet
     * @return
     */
    public int procrss(char[] str, int index, Set<Integer> hashSet) {
        if (index  == str.length) {
            for (int i = 0; i < str.length; i++) {
                if (!"X".equals(String.valueOf(str[i]))) {
                    if (!hashSet.contains(i - 1) || !hashSet.contains(i) || hashSet.contains(i + 1)) {
                        return Integer.MAX_VALUE;
                    }
                    return hashSet.size();
                }
            }
        }
         else {
             int no = procrss(str, index + 1, hashSet);
             int yes = Integer.MAX_VALUE;
             if (".".equals(String.valueOf(str[index]))) {
                 hashSet.add(index);
                 yes = procrss(str, index + 1, hashSet);
                 hashSet.remove(index);
             }
            return Math.min(yes, no);
        }
        return 0;
    }

    /**
     * 贪心计算
     * 假设当前位置是X则调到下一个;
     * 假设下一个位置是...或者..x都只放一个灯;
     * 判断当前位置的下一个是.的话,跳到第三个,否则跳到第2个
     *
     * @param str
     * @return
     */
    public int process(String str) {
        if (str == null) {
            return 0;
        }
        char[] chars = str.toCharArray();
        int num = 0;
        int index = 0;
        while (index < chars.length) {
            if ("X".charAt(0) == chars[index]) {
                index = index + 1;
            } else {
                num++;
                if (index + 1 > chars.length) {
                    return num;
                }
                if (chars[index + 1] == ".".charAt(0)) {
                    index = index + 3;
                } else {
                    index = index + 2;
                }
            }
        }
        return num;
    }

    /**
     * 给一个金条,切分成指定数组值,金条长度为数组中元素之和,每切分一块花费同等金币,求能切分的花费最小金币和;
     *  [10,30,20] 金币长60,先切分40，20;在切分10,30;则总共划分40+20+10+30=100;
     *  先切分30，30;在切分成10,20,则共花费30+30+10+20=90;
     *
     * @param params
     * @return
     */
    public int segment(int[] params) {
        if (params.length == 1) {
            return params[0];
        }
        PriorityQueue<Integer> queue = new PriorityQueue();
        for (int param : params) {
            queue.add(param);
        }
        int num = 0;
        while (queue.size() > 1) {
            int cur = queue.poll() + queue.poll();
            num = num + cur;
            queue.offer(cur);
        }
        return num;
    }
}
