package thread.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程工厂
 *
 * 线程工厂可以设置线程信息
 */
public class MyThreadFactory implements ThreadFactory {

    private final String groupName;

    public MyThreadFactory(String groupName) {
        this.groupName = groupName;
    }

    private final AtomicInteger nextId = new AtomicInteger();

    @Override
    public Thread newThread(Runnable r) {
        String threadName = groupName +  nextId.incrementAndGet();
        return new Thread(null,r,threadName,0);
    }
}
