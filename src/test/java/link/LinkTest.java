package link;

import com.ybin.link.CircularLink;
import com.ybin.link.DoubleLink;
import com.ybin.link.SinglyLinked;
import com.ybin.queue.ArrayQueue;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author yuebing
 * @version 1.0 2017/9/4
 */
public class LinkTest {
    @Test
    public void testSinglyLink() {
        SinglyLinked singlyLinked = new SinglyLinked();
        singlyLinked.add("节点1");
        singlyLinked.add("节点2");
        singlyLinked.add("节点3");
        singlyLinked.add("节点4");
        singlyLinked.forEachNode(singlyLinked);

        singlyLinked.delNode("节点3");
        singlyLinked.forEachNode(singlyLinked);

        singlyLinked.insertNode("节点2", "新增节点5");
        singlyLinked.forEachNode(singlyLinked);
        singlyLinked.findNode("节点2");
    }

    @Test
    public void testCircularLink() {
        CircularLink circularLink = new CircularLink();
        circularLink.add("节点1");
        circularLink.add("节点2");
        circularLink.add("节点3");
        circularLink.add("节点4");
        circularLink.showAllNode(circularLink);
        System.out.println("删除节点");
        //circularLink.dealNode("节点3");
        circularLink.showAllNode(circularLink);
        System.out.println("插入节点");
        circularLink.insertNode("节点4", "节点5");
        circularLink.showAllNode(circularLink);
    }

    @Test
    public void testDoubleLink() {
        List<Long> lists = Arrays.asList(new Long[]{1L, 2L, 3L, 5L});
        DoubleLink<Long> link = new DoubleLink<>();
        link.findNodeByIndex(3);
//        link.poll();
//        link.poll();
//        link.poll();
        link.add(8L);
        link.add(10L);
        link.add(11L);
    }

}
