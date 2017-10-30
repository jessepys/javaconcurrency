package com.eyesee.concurrency.lock;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynchronizedDeakLock {
    private static final Logger LOG = LoggerFactory.getLogger(SynchronizedLock.class);

    public class BaseClass {
        public synchronized void doPrint() {
            LOG.info("base class print");
        }
    }

    public class ChildClass extends BaseClass {
        @Override
        public synchronized void doPrint() {
            LOG.info("child class do print");
            super.doPrint();
        }
    }

    @Test
    public void testDeadLock() {
        ChildClass childClass = new ChildClass();
        childClass.doPrint();
    }
}
