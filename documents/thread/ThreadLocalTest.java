package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal
 */
public class ThreadLocalTest {

    private static ThreadLocal<ThreadLocalUser> threadLocal = new ThreadLocal<ThreadLocalUser>();

    final static ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 获取本地线程值
     */
    static void getThreadLocalUser(){
        System.out.println(Thread.currentThread().getName()+"  :  "+threadLocal.get());
    }

    /**
     * 移除本地线程值
     */
    static void removeThreadLocalUser(){
        threadLocal.remove();
        System.out.println(Thread.currentThread().getName()+"  :  "+threadLocal.get());
    }

    public static void main(String[] args) {
        executorService.submit(() ->{
            ThreadLocalUser threadLocalUser = new ThreadLocalUser();
            threadLocalUser.setUserId("123456");
            threadLocalUser.setRoleId("1");
            threadLocal.set(threadLocalUser);
            getThreadLocalUser();
            removeThreadLocalUser();
        });

        executorService.submit(() ->{
            ThreadLocalUser threadLocalUser = new ThreadLocalUser();
            threadLocalUser.setUserId("123456");
            threadLocalUser.setRoleId("1");
            threadLocal.set(threadLocalUser);
            getThreadLocalUser();
            removeThreadLocalUser();
        });

        executorService.shutdown();
    }
}
