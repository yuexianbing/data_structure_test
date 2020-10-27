package arithmetic;

import com.ybin.arithmetic.Sort;
import org.junit.Test;

/**
 * @author yuebing
 * @version 1.0 2018/4/8
 * @Description
 */
public class SortTest {

    @Test
    public void buddingSortTest() {
        int[] arr = new int[]{15, -1, 22, 10, 0, 17, 13};
//        Sort.buddingSort(arr);
//        Sort.chooseSort(arr);
        Sort.insertSort(arr);
        for (int a : arr) {
            System.out.println(a);
        }
    }

    @Test
    public void cocktailSortTest() {
        int[] arr = new int[]{15, -1, 22, 10, 0, 17, 13};
        Sort.cocktailSort(arr);
        for (int a : arr) {
            System.out.println(a);
        }
    }

    @Test
    public void quickSortTest() {
        int[] arr = new int[]{15, -1, 22, 10, 0, 17, 13};
        Sort.quickSort(arr);
        for (int a : arr) {
            System.out.println(a);
        }
    }

    @Test
    public void shellSortTest() {
        int[] arr = new int[]{15, -1, 22, 10, 0, 17, 13, 9, 10, 11, 14, 6};
        Sort.mergeSort(arr);
        for (int a : arr) {
            System.out.println(a);
        }
    }
}
