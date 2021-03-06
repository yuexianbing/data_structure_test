package btree;

import com.ybin.btree.BtreeLinked;
import org.junit.Test;

/**
 * @author yuebing
 * @version 1.0 2017/9/4
 * @Description
 */
public class BtreeTest {

    /**
     * 链表二叉树添加节点,添加10,4,11,2,8,12,1,3,6,9,12,15,14
     */
    @Test
    public void testAddBtreeLiked() {
        BtreeLinked<Integer> btree = new BtreeLinked<Integer>();
//        btree.add(7);
        btree.add(3);
        btree.add(9);
        btree.add(20);
        btree.add(15);
        btree.add(7);


        btree.showFixeOrder(btree.getRoot());
    }
}
