package com.eyesee.algorithms.tree;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The {@code BinaryTree} class represents .
 *
 * @author jessepi on 11/29/18
 */
public class BinaryTree {

    private Node mockData() {
        Node node = new Node();
        node.setValue(10);

        Node leftNode = new Node();
        leftNode.setValue(6);
        node.setLeftNode(leftNode);

        Node rightNode = new Node();
        rightNode.setValue(14);
        node.setRightNode(rightNode);

        Node left11Node = new Node();
        left11Node.setValue(4);
        Node left12Node = new Node();
        left12Node.setValue(8);

        leftNode.setLeftNode(left11Node);
        leftNode.setRightNode(left12Node);

        Node right11Node = new Node();
        right11Node.setValue(12);
        Node right12Node = new Node();
        right12Node.setValue(16);

        rightNode.setLeftNode(right11Node);
        rightNode.setRightNode(right12Node);


        return node;
    }

    @Test
    public void testPreVist() {
        Node node = mockData();
        preVist(node);
    }

    @Test
    public void testMedianVist() {
        Node node = mockData();
        medianVist(node);
    }

    @Test
    public void testAfterVist() {
        Node node = mockData();
        afterVist(node);
    }

    @Test
    public void testRangeVist() {
        Node node = mockData();
        rangeVisit(node);
    }

    @Test
    public void testContructTree() {
        List<Integer> preResults = Arrays.asList(1, 2, 4, 7, 3, 5, 6, 8);
        List<Integer> medianResults = Arrays.asList(4, 7, 2, 1, 5, 3, 8, 6);
        Node node = contractTree(preResults, medianResults);
    }

    public Node contractTree(List<Integer> preResults, List<Integer> medianResults) {
        if (null == preResults || null == medianResults ||preResults.size() < 1) {
            return null;
        }

        int rootVlaue = preResults.get(0);
        int k = -1;
        while(rootVlaue != medianResults.get(++k));

        Node rootNode = new Node();
        rootNode.setValue(rootVlaue);

        if (medianResults.size() <= 1) {
            return rootNode;
        }

        if (k > 0) {
            Node node = contractTree(preResults.subList(1, k + 1), medianResults.subList(0, k));
            rootNode.setLeftNode(node);
        }

        if (k < medianResults.size() - 1) {
            Node node = contractTree(preResults.subList(k + 1, preResults.size()),
                    medianResults.subList(k + 1, medianResults.size()));
            rootNode.setRightNode(node);
        }
        return rootNode;
    }

    public void rangeVisit(Node node) {
        if (null == node) {
            return;
        }
        List<Node> results = visitChild(Arrays.asList(node));
        while (null != results && results.size() > 0) {
            results = visitChild(results);
        }
    }

    private List<Node> visitChild(List<Node> nodes) {
        if (null == nodes) {
            return null;
        }
        List<Node> results = new ArrayList<>();

        nodes.stream().forEach(node -> {
            System.out.println(node.getValue());
            if (null != node.getLeftNode()) {
                results.add(node.getLeftNode());
            }

            if (null != node.getRightNode()) {
                results.add(node.getRightNode());
            }
        });

        return results;
    }

    public void preVist(Node node) {
        if (null == node) {
            return;
        }

        System.out.println(node.getValue());
        preVist(node.getLeftNode());
        preVist(node.getRightNode());
    }

    public void medianVist(Node node) {
        if (null == node) {
            return;
        }

        medianVist(node.getLeftNode());
        System.out.println(node.getValue());
        medianVist(node.getRightNode());
    }

    public void afterVist(Node node) {
        if (null == node) {
            return;
        }
        afterVist(node.getLeftNode());
        afterVist(node.getRightNode());
        System.out.println(node.getValue());
    }

    public static class Node {
        private int value;
        private Node leftNode;
        private Node rightNode;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Node getLeftNode() {
            return leftNode;
        }

        public void setLeftNode(Node leftNode) {
            this.leftNode = leftNode;
        }

        public Node getRightNode() {
            return rightNode;
        }

        public void setRightNode(Node rightNode) {
            this.rightNode = rightNode;
        }
    }
}
