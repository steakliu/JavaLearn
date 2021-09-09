## 线程池之任务队列  
![img.png](img.png)  

#### ArrayBlockingQueue  
采用数组来实现,并采用可重入锁ReentrantLock来做并发控制，无论是添加还是读取，都先要获得锁才能进行操作
可看出进行读写操作都使用了ReentrantLock，ArrayBlockingQueue需要为其指定容量
```
    public boolean offer(E e) {
        checkNotNull(e);
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (count == items.length)
                return false;
            else {
                enqueue(e);
                return true;
            }
        } finally {
            lock.unlock();
        }
    }
    
    public void put(E e) throws InterruptedException {
        checkNotNull(e);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == items.length)
                notFull.await();
            enqueue(e);
        } finally {
            lock.unlock();
        }
    }
```
#### SynchronousQueue  
由于SynchronousQueue源码比较复杂，里面大量的Cas操作，SynchronousQueue没有容器，所以里面是装不了任务的，当一个生产者线程生产一个任务的
时候，如果没有对应的消费者消费，那么该生产者会一直阻塞，知道有消费者消费为止。  
图示：  
![img_1.png](img_1.png)
如下代码，如果我们将消费者线程注释掉执行，那么生产者哪里将会一直阻塞

```
package thread.customthreadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 测试SynchronousQueue
 */
public class SynchronousQueueTest {

    private static final SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();

    private static final ExecutorService service = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        /**
         * Provider
         */
        service.submit(() -> {
            try {
                synchronousQueue.put("liu");
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("Consumer finished spending");
        });

        /**
         * Consumer
         */
        service.submit(() ->{
            try {
                synchronousQueue.take();
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("take over");
        });
    }
}

```

#### LinkedBlockingDeque  
LinkedBlockingDeque是一个双向队列，底层使用单链表实现，任何一段都可进行元素的读写操作，在初始化LinkedBlockingDeque的时候，
我们可以指定容量，也可不指定，如果不指定，则容量为Integer.MAX_VALUE，
##### 注：Deque是双端队列，而Queue是单端队列，双端意思是两端都可以进行读写操作，而单端则只能从一端进，一端出（FIFO）
```
    public LinkedBlockingDeque() {
        this(Integer.MAX_VALUE);
    }
```

```
package thread.customthreadpool;
import java.util.concurrent.LinkedBlockingDeque;
public class LinkedBlockingDequeTest {

    private static final LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();

    public static void main(String[] args) throws InterruptedException {
        deque.put(1);
        deque.put(2);
        deque.put(3);
        deque.put(4);
        deque.put(5);
        System.out.println(deque);
        System.out.println("deque size  "+deque.size());
        deque.take();
        deque.take();
        deque.take();
        deque.take();
        deque.take();
        System.out.println(deque);
        System.out.println("deque size  "+deque.size());
    }
}

```
![img_2.png](img_2.png)  


#### LinkedBlockingQueue
底层基于单向连表实现，是一个单向队列，具有先进先出(FIFO)特点，使用了ReentrantLock来做并发控制,读写操作都上锁

```
private final ReentrantLock putLock = new ReentrantLock();
```

```
    public void put(E e) throws InterruptedException {
        if (e == null) throw new NullPointerException();
        int c = -1;
        Node<E> node = new Node<E>(e);
        final ReentrantLock putLock = this.putLock;
        final AtomicInteger count = this.count;
        putLock.lockInterruptibly();
        try {
            while (count.get() == capacity) {
                notFull.await();
            }
            enqueue(node);
            c = count.getAndIncrement();
            if (c + 1 < capacity)
                notFull.signal();
        } finally {
            putLock.unlock();
        }
        if (c == 0)
            signalNotEmpty();
    }
```

```
    public E take() throws InterruptedException {
        E x;
        int c = -1;
        final AtomicInteger count = this.count;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lockInterruptibly();
        try {
            while (count.get() == 0) {
                notEmpty.await();
            }
            x = dequeue();
            c = count.getAndDecrement();
            if (c > 1)
                notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
        if (c == capacity)
            signalNotFull();
        return x;
    }
```

#### DelayDeque  

DelayDeque是一个无界队列，添加进DelayDeque的元素会经过compareTo方法计算，然后按照时间
进行排序，排在队头的元素是最早到期的，越往后到期时间越长，DelayDeque只能接受Delayed接口类型
如图所示，队列里的元素并不是按照先进先出的规则，而是按照过期时间  
![img_3.png](img_3.png)  

##### 示例

```
package thread.customthreadpool.delayDeque;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class MyDelayed implements Delayed {

    private final String taskName ;
    private final long nowTime = System.currentTimeMillis();
    private final long expireTime ;

    public MyDelayed(String taskName,long expireTime) {
        this.taskName = taskName;
        this.expireTime = expireTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert((nowTime+expireTime) - System.currentTimeMillis(),TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        MyDelayed myDelayed = (MyDelayed) o;
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public String toString() {
        return "MyDelayed{" +
                "taskName='" + taskName + '\'' +
                ", nowTime=" + nowTime +
                ", expireTime=" + expireTime +
                '}';
    }
}
```

```
package thread.customthreadpool.delayDeque;

import java.util.concurrent.*;

public class MyDelayQueue {

    private static final DelayQueue<MyDelayed> delayQueue = new DelayQueue<>();

    private static final ExecutorService service = Executors.newCachedThreadPool();

    public static void main(String[] args) throws InterruptedException {
        service.submit(() -> {
            delayQueue.put(new MyDelayed("A-Task",5000));
            delayQueue.put(new MyDelayed("B-Task",4000));
            delayQueue.put(new MyDelayed("C-Task",3000));
            delayQueue.put(new MyDelayed("D-Task",2000));
            delayQueue.put(new MyDelayed("E-Task",1000));
        });
        while (true){
            System.out.println(delayQueue.take());
        }
    }
}

```

##### result  
![img_4.png](img_4.png)  

##### 应用场景  
1.美团外卖订单：当我们下单后没付款 ，30分钟后将自动取消订单  
2.缓存，对于某些任务，需要在特定的时间清理；  
and so on  

#### LinkedTransferQueue  
当消费线程从队列中取元素时，如果队列为空，那么生成一个为null的节点，消费者线程就一直等待，此时如果生产者线程发现队列中有一个null节点，
它就不入队了，而是将元素填充到这个null节点并唤醒消费者线程，然后消费者线程取走元素。  
LinkedTransferQueue是 SynchronousQueue 和 LinkedBlockingQueue 的整合，性能比较高，因为没有锁操作，
SynchronousQueue不能存储元素，而LinkedTransferQueue能存储元素，

#### PriorityBlockingQueue  
PriorityBlockingQueue是一个无界的阻塞队列，同时是一个支持优先级的队列，读写操作都是基于ReentrantLock,
内部使用堆算法保证每次出队都是优先级最高的元素
```
    public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        E result;
        try {
            while ( (result = dequeue()) == null)
                notEmpty.await();
        } finally {
            lock.unlock();
        }
        return result;
    }
```
