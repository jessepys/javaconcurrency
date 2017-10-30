package com.eyesee.concurrency.deacklock;

/**
 * Created by jessepi on 1/15/17.
 */
public class TestSyncronized {

    public synchronized  String getMessage() {
        try {
            System.out.println("begin getMessage");
            Thread.sleep(40L);
            System.out.println("end get message");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "start";
    }

    public synchronized String getMessage2() {
        return "start2";
    }

    public static void main(String[] args) {

        final TestSyncronized test = new TestSyncronized();

        Runnable runnable = new Runnable() {
            public void run() {
                System.out.println(test.getMessage());
            }
        };

        final TestSyncronized testSyncronized = new TestSyncronized();

       new Thread(runnable).start();
        System.out.println(test.getMessage());



    }
}
