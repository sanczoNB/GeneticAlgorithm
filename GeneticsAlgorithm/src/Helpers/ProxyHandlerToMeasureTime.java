package Helpers;

import MyGraph.IGraphService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.Duration;

/**
 * Created by sanczo on 2016-04-14.
 */
public class ProxyHandlerToMeasureTime implements InvocationHandler {


    private Duration totalTime = Duration.ofNanos(0);

    private IGraphService proxied;

    private long counter = 0;

    public ProxyHandlerToMeasureTime(IGraphService proxied) {
        this.proxied = proxied;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.nanoTime();

        Object result = method.invoke(proxied, args);

        long durationTime = System.nanoTime() - startTime;

        totalTime = totalTime.plusNanos(durationTime);

        counter++;

        return result;
    }

    public Duration getTotalTimeInNanoSeconds() {
        return totalTime;
    }

    public long getCounter() {
        return counter;
    }

    public long getAveregeRunTime() {
        long totalTime = this.totalTime.toNanos();
        return totalTime / counter;
    }
}
