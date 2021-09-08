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
