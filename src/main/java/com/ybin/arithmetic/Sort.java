package com.ybin.arithmetic;

/**
 * @author yuebing
 * @version 1.0 2018/4/8
 * @Description 排序算法
 */
public class Sort {

    /**
     * 冒泡排序
     *
     * @param a
     */
    public static void buddingSort(int[] a) {
        if (a == null || a.length == 0) {
            return;
        }
        int tmp;
        for (int i =0; i < a.length-1; i++) {
            for (int j = i+1; j < a.length; j++) {
                if (a[i] <= a[j]) {
                    continue;
                }
                tmp = a[j];
                a[j] = a[i];
                a[i] = tmp;
            }
        }
    }

    /**
     * 鸡尾酒排序,冒泡排序的改进版
     *
     * @param a
     */
    public static void cocktailSort(int[] a) {
        if (a == null || a.length == 0) {
            return;
        }
        int left = 0 ,right = a.length -1, tmp;
        for (; ;) {
            if (left >= right)
                break;
            for (int i = left; i < right; i++) {
                if (a[i] > a[i+1]) {
                    tmp = a[i];
                    a[i] = a[i+1];
                    a[i+1] = tmp;
                }
            }
            right--;
            for (int i = right; i > left; i--) {
                if (a[i] < a[i-1]) {
                    tmp = a[i];
                    a[i] = a[i-1];
                    a[i-1] = tmp;
                }
            }
            left++;
        }
    }

    /**
     * 快速排序
     *
     * @param a
     */
    public static void quickSort(int[] a) {
        quickSort(a, 0, a.length-1);
    }

    /**
     * 快速排序
     *
     * @param a
     * @param low
     * @param hight
     */
    private static void quickSort(int[] a, int low, int hight) {
        if (a == null || a.length == 0) {
            return;
        }
        if (low >= hight) {
            return;
        }
        int left = low, right = hight, key = a[low], tmp;
        //第一次循环的时候找出数组在基准点左右侧的数据
        while (left < right) {
            while (left < right && a[right] >= key) {
                right--;
            }
            while (left < right && a[left] <= key) {
                left++;
            }
            if (left < right) {
                tmp = a[left];
                a[left] = a[right];
                a[right] = tmp;
            }
        }
        //将基准点换到数组left与right相同的位置
        a[low] = a[left];
        a[left] = key;
        //递归排序基准点左侧的数据
        quickSort(a, low, right - 1);
        //递归排序基准点右侧的数据
        quickSort(a, right + 1 , hight);
    }

    /**
     * 选择排序
     *
     * @param a
     */
    public void selectSort(int[] a) {
        if (a == null || a.length == 0) {
            return;
        }
        int min;
        for (int i =0; i < a.length - 1; i++) {
            min = i;
            for (int j = 1 ; j < a.length; j++) {
                if (a[j] < a[min]) {
                    min = j;
                }
            }
            if (min != i) {
                int x = a[i];
                a[i] = a[min];
                a[min] = x;
            }
        }
    }
}
