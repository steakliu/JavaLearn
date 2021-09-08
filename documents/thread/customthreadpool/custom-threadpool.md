## 手动创建线程池  

### 任务队列  
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

