package link;

import com.ybin.stack.ArrayStack;
import com.ybin.stack.LinkStack;
import org.junit.Test;

import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author yuebing
 * @version 1.0 2017/9/4
 * @Description
 */
public class StackTest {

    @Test
    public void stack() {
        Stack<String> stack = new Stack<>();
        stack.push("123");
        stack.size();
        stack.capacity();

        LinkStack<Integer> linkStack = new LinkStack<>();
        linkStack.push(1);
        linkStack.push(2);
        linkStack.push(3);

        linkStack.peek();
        linkStack.pop();
        linkStack.pop();
    }

    @Test
    public void testArrayStack() {
        ArrayStack<Integer> stack = new ArrayStack<>(2);
        stack.add(1);
        stack.add(2);
        stack.add(3);
        stack.pop();

        ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
    }
}
