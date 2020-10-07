package com.ybin.arithmetic.leetcode;

/**
 * @author : bing.yue001
 * @version : 1.0
 * @date : 2020-9-14 12:55
 * @description :
 */
public class Array {

    /**
     * 数组中的数是0-n-1的随机整数,求数组中任意重复的数字
     *
     * @param array
     * @return
     */
    public int repeat(int[] array) {
        if (array.length == 0) {
            return -1;
        }
        for (int i = 0; i < array.length; i++) {
            while (array[i] != i) {
                if (array[i] == array[array[i]]) {
                    return array[i];
                }
                int temp = array[i];
                array[i] = array[array[i]];
                array[array[i]] = temp;
            }
        }
        return -1;
    }

    /**
     * 给定数组,长度为n+1,数组中的数是1-n中的随机数,取任意重复的数
     * 利用二分法
     *
     * @param array
     * @return
     */
    public int duplication(int[] array) {
        if (array.length == 0) {
            return -1;
        }
        int start = 1;
        int end = array.length - 1;
        while (start < end) {
            int mid = (end - start) >> 1;
            int count = 0;
            int c = mid - start + 1;
            for (int i = 0; i < array.length; i++) {
                for (; start <= mid; start++) {
                    if (array[i] == start) {
                        count++;
                    }
                }
            }
            if (count > c) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
            
        }
        return 0;
    }
}
