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

    @Test
    public void testTriePrint() {
        twentyNovember.treePrint(new String[]{"b\\se", "d\\f\\g", "a\\j\\k"});
    }

    @Test
    public void sub() {
        int result = twentyNovember.lengthOfLIS(new int[]{3,4,2,5,1,6,7});
        System.out.println(result);
    }

    @Test
    public void subStringAdjustTest() {
        twentyNovember.subStringAdjust(new String("abc"), new String("adc"), 1, 2, 3);
    }
}
