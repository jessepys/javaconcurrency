## Java Concurrency
在多线程环境下，为了保证共享数据的原子和内存可见性，需要进行锁操作。在JAVA中提供了内置锁和显示锁。本文使用用例结合，来介绍以下锁的用法：
### 内置锁(synchronized) 
内置锁用来锁定代码块，在进入代码的时候获取锁定，在退出（异常退出）释放锁定。内置锁是互斥的，意味中同一时刻只能有一个线程获取该锁，其它线程只能等待或者阻塞直到锁的释放。如下面代码中，假如线程1执行addOne操作，当线程2调用getOne时，就需要等待线程1执行完成并释放锁。

#############################

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
 内置锁是可以重入的。当线程A获取锁执行某操作，如果在当前线程内，某个步骤也需要获取该锁，这个步骤是可以获取到锁的。如下例子，当ChildClass的对象执行doPrint时已经获取到了锁，内部继续调用super.doPrint，如果不能重入就会发生死锁。在同一线程内，锁可以重入。
 
########################

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