package com.eyesee.designpattern.activeobject.strategy;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrategyTest {
    private static final Logger LOG = LoggerFactory.getLogger(StrategyTest.class);

    @Test
    public void testRun() {
        Runner runner = new Runner(new Detail1());
        runner.run();

        runner = new Runner(new Detail2());
        runner.run();
    }

    public static class Runner {
        private Operation operation;

        public Runner(Operation operation) {
            this.operation = operation;
        }

        public void run() {
            operation.doStep1();
            operation.doStep2();
        }
    }


    public static class Detail1 implements Operation {
        @Override
        public void doStep1() {
            LOG.info("detail 1 step 1");
        }

        @Override
        public void doStep2() {
            LOG.info("detail 1 step 2");
        }
    }

    public static class Detail2 implements Operation {
        @Override
        public void doStep1() {
            LOG.info("detail 2 step 1");
        }

        @Override
        public void doStep2() {
            LOG.info("detail 2 step 2");
        }
    }

    public interface Operation {
        public void doStep1();
        public void doStep2();
    }
}
