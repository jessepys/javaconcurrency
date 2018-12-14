package com.eyesee.algorithms.link;

import com.eyesee.algorithms.annotation.Solution;

import java.util.Stack;

/**
 * The {@code Link} class represents .
 *
 * @author jessepi on 11/29/18
 */
public class Link {


    public void printNode(Node node) {
        if (null == node) {
            return;
        }

        if (null == node.getNext()) {
            System.out.println(node.getValue());
        } else {
            printNode(node.getNext());
        }
        System.out.println(node.getValue());
    }

    @Solution
    public void printReverseLink(Node head) {
        if (null == head) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(head);
        Node next = head.getNext();
        while(null != next) {
            stack.push(next);
            next = next.getNext();
        }

        Node temp = null;
        while((temp = stack.pop()) != null) {
            System.out.println(temp.getValue());
        }
    }


    public void removeNode(Node head, int value) {
        if (null == head) {
            return;
        }

        if (head.getValue() == value) {
            head = head.getNext();
            return;
        }

        Node node = head;
        while (null != node.getNext()) {
            Node next = node.getNext();
            if (next.getValue() == value) {
                node.setNext(next.getNext());
                break;
            }
            node = next;
        }
    }

    public void addToTail(Node head, int value) {
        Node node = new Node();
        node.setValue(value);

        if (null == head) {
            head = node;
        } else {
            Node next = head;
            while(null != next.getNext()) {
                next = next.getNext();
            }

            next.setNext(node);
        }
    }

    public static class Node {
        private int value;
        private Node next;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
