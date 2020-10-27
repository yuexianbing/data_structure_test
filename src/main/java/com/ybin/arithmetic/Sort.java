package com.ybin.arithmetic;

import java.util.Collections;

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
     * @param low
     * @param height
     */
    public static void quickSort(int[] a, int low, int height) {
        if (a == null || a.length == 0) {
            return;
        }
        if (low >= height) {
            return;
        }
        int left = low, right = height, key = a[low], tmp;
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
        quickSort(a, right + 1 , height);
    }

    public void quick(int[] a, int low, int height) {
        if (a == null || a.length == 0) {
            return;
        }
        if (low >= height) {
            return;
        }
        int key = a[low], left = low, right = height, tmp;
        for (; left < right;) {
            while (left <= right && a[right] >= key) {
                right--;
            }
            while (left <= right && a[left] <= key) {
                left++;
            }
            if (left < right) {
                tmp = a[left];
                a[left] = a[right];
                a[right] = tmp;
            }
        }

        a[low] = a[left];
        a[left] = key;

        quick(a, low, right - 1);
        quick(a, right + 1, height);
    }

    /**
     * 选择排序, O(n^2)，最好，最坏，平均O(n^2)
     *
     * @param a
     */
    public static void chooseSort(int[] a) {
        if (a == null || a.length < 2) {
            return;
        }
        int tmp, min;
        for (int i = 0; i < a.length - 1; i++) {
            min = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[min]) {
                    min = j;
                }
            }
            if (min == i) {
                continue;
            }
            tmp = a[min];
            a[min] = a[i];
            a[i] = tmp;
        }
    }

    /**
     * 插入排序, O(n^2)
     *
     * @param arr
     */
    public static void insertSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int pre , cur;
        for (int i = 0; i < arr.length; i++) {
            pre = i - 1;
            cur = arr[i];
            while (pre >=0 && arr[pre] > cur) {
                arr[pre + 1] = arr[pre];
                pre--;
            }
            arr[pre + 1] = cur;
        }
    }

    public static void shellSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int temp, j;
        for (int increment = arr.length / 2; increment > 0; increment /= 2) {
            for (int i = 0; i < arr.length; i++) {
                temp = arr[i];
                for (j = i - increment; j >= 0; j -= increment) {
                    if (temp < arr[j]) {
                        arr[j + increment] = arr[j];
                    } else {
                        break;
                    }
                }
                arr[j + increment] = temp;
            }
        }
    }

    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int[] temp = new int[arr.length];
        sort(arr, 0, arr.length - 1, temp);
    }

    private static void sort(int[] arr, int left, int right, int[] temp) {
        if (left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        sort(arr, left, mid, temp);
        sort(arr, mid + 1, right, temp);
        merge(arr, left, right, mid);
    }

    private static void merge(int[] arr, int left, int right, int mid) {
        int i = left, j = mid + 1, t = 0;
        int[] temp = new int[right - left + 1];
        while (i <= mid && j <= right) {
            if (arr[i] > arr[j]) {
                temp[t++] = arr[j++];
            } else {
                temp[t++] = arr[i++];
            }
        }

        int start = i, end = mid;
        if (j <= right) {
            start = j;
            end = right;
        }
        while (start <= end) {
            temp[t++] = arr[start++];
        }

        t =0;
        while (t < temp.length) {
            arr[left++] = temp[t++];
        }
    }
}
