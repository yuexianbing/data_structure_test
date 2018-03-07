package link;

import com.ybin.link.CircularLink;
import com.ybin.link.SinglyLinked;
import org.junit.Test;

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
}
