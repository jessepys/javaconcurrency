package com.eyesee.algorithms.link;

import java.util.Stack;

/**
 * The {@code Cqueue} class represents .
 *
 * @author jessepi on 11/30/18
 */
public class Cqueue<T> {
    private Stack<T> stack1 = new Stack<>();
    private Stack<T> stack2 = new Stack<>();

    //stack: 1 2 3 4 5
    //two: 5 4 3 2 1
    public void appendTail(T node) {
        stack1.push(node);
    }

    public T deleteHead() {
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
        T result = stack2.pop();

        while(!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }

        return result;
    }

    public Stack<T> getStack1() {
        return stack1;
    }

    public void setStack1(Stack<T> stack1) {
        this.stack1 = stack1;
    }

    public Stack<T> getStack2() {
        return stack2;
    }

    public void setStack2(Stack<T> stack2) {
        this.stack2 = stack2;
    }
}
