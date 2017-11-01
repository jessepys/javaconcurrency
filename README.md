## Java Concurrency
在多线程环境下，为了保证共享数据的原子和内存可见性，需要进行锁操作。在JAVA中提供了内置锁和显示锁。本文使用用例结合，来介绍以下锁的用法：
### 内置锁(synchronized) 
* 内置锁用来锁定代码块，在进入代码的时候获取锁定，在退出（或者异常退出）释放锁定。内置锁是互斥的，意味中同一时刻只能有一个线程获取该锁，其它线程只能等待或者阻塞直到锁的释放。如下面代码中，假如线程1执行addOne操作，当线程2调用getOne时，就需要等待线程1执行完成并释放锁。

```
    public class ProductPool {
        private Integer product = new Integer(0);

        public synchronized Integer getProduct() {
            return product;
        }

        public synchronized void addOne() {
            this.product = this.product + 1;
            LOG.info("produce value: {}", this.product);
        }

        public synchronized Integer getOne() {
            Integer old = new Integer(this.product);
            this.product = this.product - 1;
            return old;
        }
    }
```   
 * 内置锁是可以重入的。当线程A获取锁执行某操作，如果在当前线程A内，某个步骤也需要获取该锁，该步骤是可以获取到锁的。如下例子，当ChildClass的对象执行doPrint时已经获取到了锁，内部继续调用super.doPrint，如果不能重入就会发生死锁。在同一线程内，锁可以重入。
 
```

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
```
  * 内置锁使用非常简单，在需要同步的方法、代码块上加入synchronized就行了，不需要显示的获取和释放锁。且内置锁是JVM内置的，它可以执行部分优化，比如在线程封闭锁对象（该对象使用了锁，但是却不是共享对象，只在某一个线程使用）的锁消除，改变锁的粒度来消除内置锁的同步等。
  * 在某些情况下，我们希望获取锁但又不想一直等待，所以我们指定获取到锁的最大时间，如果获取不到就超时。内置锁对这种细粒度的控制是不支持的，JAVA提供了一种新的锁机制：显示锁。下章，我们就对该话题进行讨论。
 
### ReentrantLock
 ReentrantLock是JAVA 5提供的细粒度的锁，作为内置锁在某些场景的补充。比如：支持线程获取锁的时间设置，支持获取锁线程对interrupt事件响应。但是在使用时必须显示的获取锁，然后在finally中释放。如果不释放，相当于在程序中放置了个定时炸弹，后期很难发现。它实现了Lock的以下API(部分例子为了达到测试效果没有unlock, 实际使用中绝对不能这样)：
 
 1 . void lock()  获取锁，一致等待直到获取。下面的例子中，在主线程中获取锁且不释放， 子线程调用lock方法来获取锁。可以看到，子线程一致处于RUNNABLE状态，即使它被interrupt。

```      
    @Test
    public void testLockWithInterrupt() throws InterruptedException {
        final Lock lock = new ReentrantLock();
        lock.lock();
        Thread childThread = new Thread(() -> {
               lock.lock();
            }, "t1 thread");
        childThread.start();
        childThread.interrupt();

        LOG.info("the child thread state: {}", childThread.getState().name());
        assertFalse(childThread.isInterrupted());
    }
```

2 . void lockInterruptibly() throws InterruptedException; 获取锁直到线程被interrupt, 线程抛出InterruptedException。下面的例子中，主线程获取锁且不释放，子线程调用lockInterruptibly方法来获取锁。首先在子线程获取不到锁的时候，会处于一直等待状态；当主线程中调用子线程interrupt时，子线程会抛出InterruptedException。

```
	@Test(expected = InterruptedException.class)
	public void testLockInterruptibly() throws Exception {
	    final Lock lock = new ReentrantLock();
	    lock.lock();
	    Thread.sleep(1000);
	    Thread mainThread = Thread.currentThread();
	    Thread t1=new Thread(new Runnable(){
	        @Override
	        public void run() {
	            try {
	                lock.lockInterruptibly();
	            } catch (InterruptedException e) {
	                LOG.error(" thread interrupted: {}", e);
	                mainThread.interrupt();
	            }
	        }
	    }, "t1 thread");
	    t1.start();
	    Thread.sleep(1000);
	    t1.interrupt();
	    Thread.sleep(1000000);
	}
```

3 . boolean tryLock() 获取锁，如果获取不到则立即返回false。

```
	@Test
	public void testTryLock() throws InterruptedException {
	    CountDownLatch countDownLatch = new CountDownLatch(1);
	    ReentrantLock reentrantLock = new ReentrantLock();
	    Runnable runnable = () -> {
	        reentrantLock.lock();
	        try {
	            Thread.sleep(2 * 1000l);
	            countDownLatch.countDown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        } finally {
	            reentrantLock.unlock();
	        }
	    };
	
	    Runnable interruptRunnable = () -> {
	        boolean result = reentrantLock.tryLock();
	        if (result) {
	            LOG.info("lock success");
	            reentrantLock.unlock();
	        } else {
	            LOG.info("lock failed");
	        }
	    };
	
	    new Thread(runnable).start();
	    new Thread(interruptRunnable).start();
	    countDownLatch.await();
	}
    
```

4 . boolean tryLock(long time, TimeUnit unit) throws InterruptedException 在指定的时间内获取锁，且返回结果。

```
@Test
public void testTryLockWithTime() throws InterruptedException, ExecutionException {
    final Lock lock = new ReentrantLock();
    lock.lock();
    CompletableFuture<Boolean> completableFuture = CompletableFuture.supplyAsync(() -> tryLock(lock));
    assertFalse(completableFuture.get().booleanValue());
}

private boolean tryLock(Lock lock) {
    try {
        boolean result = lock.tryLock(100, TimeUnit.MILLISECONDS);
        LOG.info("lock result: {}", result);
        return result;
    } catch (InterruptedException e) {
        LOG.error("interrupted: {}", e);
    }
    return false;
}
    
```

### Semaphore
信号量常常用来控制对某一资源的访问数量。例如，下面的测试中我们设置信号量的permits为5，当其中5个现在获取且没释放，其它访问线程是获取不到permit的。

```
@Test
public void testSemaphore() throws InterruptedException {
    Semaphore semaphore = new Semaphore(5);
    CountDownLatch countDownLatch = new CountDownLatch(2000);
    Executor executor = Executors.newFixedThreadPool(10);

    Runnable runnable = () -> {
        boolean isAcquired = semaphore.tryAcquire();
        if (isAcquired) {
            try {
                LOG.info("semaphore is acquired");
                TimeUnit.MICROSECONDS.sleep(2);
            } catch (InterruptedException ex) {
                LOG.error("error: {}", ex);
            } finally {
                semaphore.release();
            }
        } else {
            LOG.info("semaphore is not acquired");
        }
        countDownLatch.countDown();
    };
    IntStream.range(1, 2001).forEach(i ->
            executor.execute(runnable)
    );
    countDownLatch.await();
}
```
