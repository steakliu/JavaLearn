package thread.customthreadpool.reject;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义拒绝策略
 */
public class MyThreadRejectExecutionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        /**
         * 加入自己的处理逻辑
         */
        throw new RejectedExecutionException("请求异常");
    }
}
