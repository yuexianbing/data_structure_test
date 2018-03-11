package link;

import com.ybin.stack.MyStack;
import org.junit.Test;

import java.util.Stack;

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

        MyStack<Integer> myStack = new MyStack<>();
        myStack.push(1);
        myStack.push(2);
        myStack.push(3);

        myStack.peek();
        myStack.pop();
        myStack.pop();
    }
}
