package HorseDir;

import java.util.Date;

/**
 * Created by Aaron Fisher on 5/17/2017.
 */
public class DelayedLine implements Comparable<DelayedLine> {
    long timestamp;
    String line;

    public DelayedLine(String line, long timestamp) {
        this.line = line;
        this.timestamp = timestamp;
    }

    public DelayedLine(String line) {
        this.line = line;
        timestamp = new Date().getTime();
    }

    @Override
    public int compareTo(DelayedLine other) {
        return Long.compare(timestamp, other.timestamp);
    }
}
