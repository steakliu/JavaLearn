package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO
 *
 * @author 刘牌
 * @version 1.0
 * @date 2021/9/5 0005 0:18
 */
public class ThreadLocalTest {
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private static ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        executorService.submit(() -> {
            threadLocal.set("the first thread");
            System.out.println(threadLocal.get());
        });

        executorService.submit(() -> {
            threadLocal.set("the second thread");
            System.out.println(threadLocal.get());
        });
    }
}
