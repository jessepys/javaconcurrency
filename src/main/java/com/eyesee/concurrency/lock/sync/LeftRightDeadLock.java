package com.eyesee.concurrency.lock.sync;

/**
 * Created by jessepi on 1/14/17.
 */
public class LeftRightDeadLock {
    private Object objectA = new Object();
    private Object objectB = new Object();

    public void accessLeftRight() {
        synchronized (objectA) {
            System.out.println("access object a");
            try {
                Thread.sleep(2000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (objectB) {
                System.out.println("acess object b");
            }
        }


    }

    public void accessRightLeft() {
        synchronized (objectB) {
            System.out.println("access object b");
            try {
                Thread.sleep(2000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (objectA) {
                System.out.println("acess object a");
            }
        }
    }

    public static void main(String[] args) {
        final LeftRightDeadLock leftRightDeadLock = new LeftRightDeadLock();
        Runnable runnable = new Runnable() {
            public void run() {
                leftRightDeadLock.accessLeftRight();
            }
        };

        Runnable runnable1 = new Runnable() {
            public void run() {
                leftRightDeadLock.accessRightLeft();
            }
        };

        new Thread(runnable, "deadlock thread a").start();
        new Thread(runnable1, "deadlock thread b").start();

    }
}
