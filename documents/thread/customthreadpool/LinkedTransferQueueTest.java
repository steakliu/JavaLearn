package thread.customthreadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueTest {

    private static final ExecutorService service = Executors.newCachedThreadPool();

    private static final LinkedTransferQueue<Integer>  linkQueue = new LinkedTransferQueue<>();

    public static void main(String[] args) throws InterruptedException {
//        service.submit(() ->{
//            linkQueue.put(1);
//            linkQueue.put(2);
//            linkQueue.put(3);
//            linkQueue.put(4);
//            linkQueue.put(5);
//        });
        service.submit(() -> {
            try {
                linkQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        service.shutdown();
    }
}
