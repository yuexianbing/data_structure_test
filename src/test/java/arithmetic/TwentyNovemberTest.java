package arithmetic;

import com.ybin.arithmetic.leetcode.training.TwentyNovember;
import org.junit.Test;

/**
 * @author : bing.yue001
 * @version : 1.0
 * @date : 2020-10-19 14:13
 * @description :
 */
public class TwentyNovemberTest {

    TwentyNovember twentyNovember = new TwentyNovember();

    @Test
    public void buildArrayTest() {

        int[] result = twentyNovember.buildArray(7);
        for (int r : result) {
            System.out.println("输出结果:" + r);
        }

    }
}
