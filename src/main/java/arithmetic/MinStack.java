package arithmetic;

import java.util.Stack;

/**
 * @Date 2019/12/23 15:45
 * Created by Wangxuehuo
 *
 * ##最小栈##
 * 设计一个支持 push，pop，top 操作，并能在常数时间内检索到最小元素的栈。
 *
 * push(x) -- 将元素 x 推入栈中。
 * pop() -- 删除栈顶的元素。
 * top() -- 获取栈顶元素。
 * getMin() -- 检索栈中的最小元素。
 * 示例:
 *
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin();   --> 返回 -3.
 * minStack.pop();
 * minStack.top();      --> 返回 0.
 * minStack.getMin();   --> 返回 -2.
 *
 */
public class MinStack {

    // 数据栈
    private Stack<Integer> data;
    // 辅助栈
    private Stack<Integer> helper;


    public MinStack() {
        data = new Stack<Integer>();
        helper = new Stack<Integer>();
    }

    public void push(int x) {
        data.add(x);
        if (helper.isEmpty() || helper.peek() >= x){
            helper.add(x);
        }else {
            helper.add(helper.peek());
        }
    }

    public void pop() {
        if (!data.isEmpty()){
            helper.pop();
            data.pop();
        }
    }

    public int top() {
        if (!data.isEmpty()){
            return data.peek();
        }
        return 0;
    }

    public int getMin() {
        if (!helper.isEmpty()){
            return helper.peek();
        }
        return 0;
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        minStack.getMin();
        minStack.pop();
        minStack.top();
        minStack.getMin();
    }
}
