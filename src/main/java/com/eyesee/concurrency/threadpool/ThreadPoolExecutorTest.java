package com.eyesee.concurrency.threadpool;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ThreadPoolExecutorTest {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadPoolExecutorTest.class);

    /**
     * The thread pool has dependency thread,  cause the dead lock.
     */
    @Test
    public void testThreadPoolThreadDependency() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<String> stringRunnable = () -> {
            return "test";
        };
        Callable<String> runnable = () -> {
            Future<String> result = executor.submit(stringRunnable);
            try {
                return result.get();
            } catch (InterruptedException e) {
                return null;
            } catch (ExecutionException e) {
                return null;
            }
        };
        try {
            LOG.info(executor.submit(runnable).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testThreadPoolRun() {
        Runnable runnable = () -> {
            LOG.info("the runner method");
        };

        TimingThreadPoolExecutor executor = new TimingThreadPoolExecutor(4, 10,
                100, TimeUnit.MINUTES, new LinkedBlockingDeque<>()
                );

        IntStream.range(1, 6).forEach(s -> {
            executor.submit(runnable);
        });


    }

    public static class TimingThreadPoolExecutor extends ThreadPoolExecutor {
        private ThreadLocal<Long> startTime = new ThreadLocal<>();

        public TimingThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                        TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        protected void beforeExecute(Thread t, Runnable r) {
            try {
                startTime.set(System.nanoTime());
            } finally {
                super.beforeExecute(t, r);
            }
        }

        protected void afterExecute(Runnable r, Throwable t) {
            try {
                Long endTime = System.nanoTime();
                LOG.info("the cost time: {}, thread name: {}", endTime - startTime.get(), Thread.currentThread().getName());
            } finally {
                super.afterExecute(r, t);
            }
        }

        protected void terminated() {

        }

    }
}
