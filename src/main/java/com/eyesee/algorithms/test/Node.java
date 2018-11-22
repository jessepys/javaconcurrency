package com.eyesee.algorithms.test;

/**
 * The {@code Node} class represents .
 *
 * @author jessepi on 11/20/18
 */
public class Node {
    private Node nextNode;
    private int value;

    public Node(int value, Node nextNode) {
        this.value = value;
        this.nextNode = nextNode;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
