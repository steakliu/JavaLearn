## ThreadLocal
ThreadLocal字面意思本地线程,ThreadLocal使每个线程之间是隔离的，数据是独立的，我们使用过session都知道
session是一个会话，我们可以用它来存储一些用户的基本信息，这样每个用户在服务端都能取到，ThreadLocal也可以做到，
ThreadLocal将相应的信息存储在当前的线程中，只有当前线程能够访问，其他线程不能访问，其实ThreadLocal
可以说是一个定制化的Map。

下面我们通过一个示例来演示怎么使用ThreadLocal,我们用ThreadLocal来存储用户对象，然后取出用户对象，移除
用户对象
threadLocal.set(threadLocalUser)存储用户的信息
threadLocal.get()获取当前线程存储的信息
threadLocal.remove()移除当前线程储存的信息

如果我们在executorService.shutdown()关闭线程池后再去get()，那么将会返回null，因为线程池已经关闭
不知道是从那个线程中取，所以返回null。
```
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
            threadLocalUser.setUserId("131420");
            threadLocalUser.setRoleId("2");
            threadLocal.set(threadLocalUser);
            getThreadLocalUser();
            removeThreadLocalUser();
        });

        executorService.shutdown();
    }
}

```

