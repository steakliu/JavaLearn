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
