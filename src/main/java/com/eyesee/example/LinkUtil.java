package com.eyesee.example;

import org.junit.Test;

import java.util.Objects;
import java.util.Stack;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class LinkUtil {

    @Test
    public void testReverseLink() {
        TestObject testObject = new TestObject();
        testObject.setValue("0");

        TestObject root = testObject;

        for (int i = 1; i < 9; i ++) {
            TestObject tmp = new TestObject();
            tmp.setValue(String.valueOf(i));
            testObject.setNext(tmp);
            testObject = tmp;
        }

//        while (nonNull(root)) {
//            System.out.println(root.getValue());
//            root = root.getNext();
//        }

        TestObject result = reverseWithStack(root);
        while (nonNull(result)) {
            System.out.println(result.getValue());
            result = result.getNext();
        }
    }

    public static TestObject reverseWithStack(final TestObject testObject) {
        if (Objects.isNull(testObject)) return null;

        Stack<TestObject> stack = new Stack<>();

        TestObject tmp = testObject;
        while(nonNull(tmp)) {
            stack.push(tmp);
            tmp = tmp.getNext();
        }

        tmp = stack.pop();
        TestObject result = tmp;
        while(Objects.nonNull(tmp) && !stack.isEmpty()) {
            TestObject popObject = stack.pop();
            popObject.setNext(null);
            tmp.setNext(popObject);
            tmp = popObject;
        }

        return result;
    }


    public static TestObject reverseLink(final TestObject testObject) {
        if (isNull(testObject) || isNull(testObject.getNext())) {
            return testObject;
        }

        TestObject head = testObject;
        TestObject next = testObject.getNext();
        head.setNext(null);

        TestObject nextCopy = next;
        next = next.getNext();
        nextCopy.setNext(head);

        next = next.getNext();
        while (nonNull(next)) {
            TestObject tmp = next;
            next = next.getNext();
            tmp.setNext(nextCopy);
            nextCopy = tmp;
        }

        return nextCopy;
    }


    public static int findTheValueBySequence(int[] arrayA, int[] arrayB, int k) {
        if (arrayA == null || arrayB == null) {
            return -1;
        }

        int i = 0;
        int j = 0;
        int count = 0;

        while (count < k) {
            if (arrayA[i] < arrayB[j]) {
                i ++;
            } else {
                j ++;
            }

            count ++;
        }

        return arrayA[i] > arrayB[j] ? arrayA[i] : arrayB[j];
    }



    public static class TestObject {
        private TestObject next;
        private String value;

        public TestObject getNext() {
            return next;
        }

        public void setNext(TestObject next) {
            this.next = next;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
