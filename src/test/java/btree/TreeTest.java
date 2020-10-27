package btree;

import com.ybin.btree.AvlTree;
import org.junit.Test;

/**
 * @author yuebing
 * @version 1.0
 * @Date 2018/5/11
 * @category
 */
public class TreeTest {

    @Test
    public void avlTesrAdd() {
        AvlTree tree = new AvlTree();
//        tree.add(3);
////        tree.add(2);
////        tree.add(5);
////        tree.add(7);
////        tree.add(4);
////        tree.add(8);
////        tree.add(6);
////        tree.add(9);
////        tree.add(10);
////
////        tree.remove(5);

        tree.add(5);
        tree.add(3);
        tree.add(2);
    }
}
