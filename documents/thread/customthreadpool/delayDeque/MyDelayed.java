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
