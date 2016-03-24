package Helpers;

/**
 * Created by sanczo on 2016-03-21.
 */
public class MyTime {

    private long minutes;

    private long seconds;


    private long milliseconds;

    private long totalMilliseconds;

    public MyTime(long milliseconds)
    {
        this.totalMilliseconds = milliseconds;
        this.minutes = milliseconds / 60000;
        this.seconds = (milliseconds % 60000)/1000;
        this.milliseconds = (milliseconds % 1000);
    }


    public long getMinutes() {
        return minutes;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public long getTotalMilliseconds() {
        return totalMilliseconds;
    }
}
