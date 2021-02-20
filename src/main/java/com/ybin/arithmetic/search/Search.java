package com.ybin.arithmetic.search;

import java.util.Objects;

/**
 * @author yuebing
 * @version 1.0
 * @Date 2018/9/18
 * @category 二分查找
 */
public class Search<T> {

    public int binarySerach(int[] t, int target) {
        if (t == null) {
            return -1;
        }

        for (int l = 0, r = t.length -1; l <= r; ) {
            int min = l + (r - l)/2;
            if (t[min] == target) {
                return min;
            } else if (t[min] > target) {
                r = min - 1;
            } else {
                l = min + 1;
            }
        }
        return -1;
    }

    public int binarySearchRecursion(int[] num, int target) {
        if (Objects.isNull(num) || num.length <= 0) {
            return -1;
        }
        return binarySearchRecursion(num, 9, 0, num.length - 1);
    }

    private int binarySearchRecursion(int[] num, int target, int low, int height) {
        if (low > height) {
            return -1;
        }
        //取中间值
        int midValue = low + ((height - low) >> 1);
        if (target > num[midValue]) {
            return binarySearchRecursion(num, target, midValue + 1, height);
        } else if (target < num[midValue]) {
            return binarySearchRecursion(num, target, low, midValue - 1);
        } else {
            return midValue;
        }
    }

    public double binarySearchSqrt(double target) {
        if (target == 0) {
            return 0;
        }
        return binarySearchSqrt(target, 0, target, 0);
    }

    private double binarySearchSqrt(double target, double low, double height, int count) {
        double mid = low + (height - low)/2;
        if (height - low <= 0.00000001) {
            return mid;
        }
        count++;
        System.out.printf("count = " + count);
       if (target > mid * mid) {
           return binarySearchSqrt(target, mid, target, count);
       } else if (target < mid * mid) {
           return binarySearchSqrt(target, low, mid, count);
       } else  {
           return mid;
       }
    }

    public double binarySearchPower(double x, int n) {
        if (x == 0) {
            return 0;
        }
        return  binarySearchPower(x, 0, n);
    }

    private double binarySearchPower(double target, double low, double height) {
        double mid = low + (height - low)/2;
        if (height - low <= 0.00000001) {
            return mid;
        }

//        if (target > mid * mid) {
//            return binarySearchSqrt(target, mid, target);
//        } else if (target < mid * mid) {
//            return binarySearchSqrt(target, low, mid);
//        } else  {
//            return mid;
//        }
        return 0;
    }

    /**
     * 查找第一个大于给定值的数据
     *
     * @param arr
     * @return
     */
    public int binarySearchGreaterThanFirst(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        int res = -1;
        while (left <= right)//不需要加判断条件 && right>=0 && left<length
        {
            //int mid = (left + right) / 2;   这种方法会溢出！！！！
            int mid = left + (right - left) / 2;
            if (arr[mid] > target)
            {
                res = mid;
                right = mid - 1;
            }
            else// if (arr[mid] <= k)
                left = mid + 1;
        }
        return res;
    }

    public static void main(String[] args) {
        int[] num = new int[]{1, 2, 4, 7, 9, 10};
        System.out.printf("" + new Search<>().binarySearchGreaterThanFirst(num, 6));
    }

}





