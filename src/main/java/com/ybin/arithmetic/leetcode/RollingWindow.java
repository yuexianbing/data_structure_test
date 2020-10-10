package com.ybin.arithmetic.leetcode;

import java.util.LinkedList;

/**
 * @author : bing.yue001
 * @version : 1.0
 * @date : 2020-7-26 23:39
 * @description :
 */
public class RollingWindow {

    /**
     * 传入一个数组,取滑动窗口中每一个子数组的最大值
     *
     * @param arr
     * @param num
     * @return
     */
    public int[] roll1(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int[] res = new int[arr.length - num + 1];
        int index = 0;
        LinkedList<Integer> likedList = new LinkedList<>();
        for (int i = 0; i < arr.length; i++) {
            while (likedList.peekLast() != null && arr[likedList.peekLast()] < arr[i]) {
                likedList.poll();
            }
            likedList.offerLast(i);
            if (arr[i] >= num) {
                res[index ++] = arr[likedList.peekFirst()];
                if (likedList.peekFirst() == i - num + 1 ) {
                    likedList.poll();
                }
            }

        }
        return res;
    }

    /**
     * 求指定数组的子数组中,最大值-最小值<= 指定值的子数组数量
     *
     * @param arr
     * @param num
     * @return
     */
    public int roll2(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int total = 0;
        LinkedList<Integer> maxLinked = new LinkedList<>();
        LinkedList<Integer> minLinked = new LinkedList<>();
        for (int i = 0; i < arr.length; i++) {
            int r = i;
            for (; r < arr.length; r++) {
                while (maxLinked.peekLast() != null && arr[maxLinked.peekLast()] <= arr[r]) {
                    maxLinked.poll();
                }
                maxLinked.offerLast(r);
                while (minLinked.peekLast() != null && arr[minLinked.peekLast()] >= arr[r]) {
                    minLinked.poll();
                }
                minLinked.offerLast(r);
                if (arr[maxLinked.peekFirst()] - arr[minLinked.peekFirst()] > num) {
                    break;
                }
            }
            if (maxLinked.peekFirst() == i) {
                maxLinked.poll();
            }
            if (minLinked.peekFirst() == i) {
                minLinked.poll();
            }
            total = total + (r - 1 - i);

        }
        return total;
    }

    /**
     * 给定一个绳子的长度为k,有序数组arr,求k能覆盖数组的最大元素
     *
     * @param arr
     * @param k
     * @return
     */
    public int process(int[] arr, int k) {
        if (arr.length == 0 && k < 1) {
            return 0;
        }

        int result = 0;
        int left = 0;
        int right = 0;
        while (left < arr.length) {
            while (right < arr.length && (arr[right] - arr[left] <= k)) {
                right++;
            }
            result = Math.max(result, right - left);
            left++;
        }
        return result;
    }

    public static void main(String[] args) {
        int arr[] = new int[]{1,2,1,3,4,6,5,4};
        // 3 4 5 6 6
        System.out.printf("dawda" + new RollingWindow().process(arr, 3));
    }
}
