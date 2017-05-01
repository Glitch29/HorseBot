package HorseDir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Aaron Fisher on 4/30/2017.
 */
public class HorseBotDatabase {
    private static final String SESSION_FOLDER = "SessionLogs\\";
    private static final Map<Tracker,Map<String, Long>> TRACKER_MAP = new HashMap<>();
    private final String directory;
    private FileWriter sessionLog;

    public HorseBotDatabase(String directory) {
        this.directory = directory;
        try {
            sessionLog = new FileWriter(new File(directory + SESSION_FOLDER + new Date().getTime() + ".txt"), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Long read(Tracker tracker, Channel channel) {
        if (!readTracker(tracker).containsKey(channel.channel)) {
            return 0L;
        }
        return readTracker(tracker).get(channel.channel);
    }

    public long track(Tracker tracker, Channel channel) {
        return track(tracker, channel, new Date().getTime());
    }

    public long track(Tracker tracker, Channel channel, Long time) {
        readTracker(tracker).put(channel.channel, time);
        try (FileWriter writer = new FileWriter(new File(directory + tracker.fileName), true)) {
            writer.write(channel.channel + " " + time + "\n");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public void log(String entry) {
        try {
            sessionLog.write(entry + "\n");
            sessionLog.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Long> readTracker(Tracker tracker) {
        if (!TRACKER_MAP.containsKey(tracker)) {
            TRACKER_MAP.put(tracker, loadTracker(tracker));
        }
        return TRACKER_MAP.get(tracker);
    }

    private Map<String, Long> loadTracker(Tracker tracker) {
        File file = new File(directory + tracker.fileName);
        Map<String, Long> map = new HashMap<>();
        try (Scanner scanner = new Scanner(file)){
            while (scanner.hasNext()) {
                map.put(scanner.next(), scanner.nextLong());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    public enum Tracker {
        DEATHS ("DeathLog.txt"),
        MURDERS ("MurderLog.txt");

        String fileName;
        Tracker(String fileName) {
            this.fileName = fileName;
        }
    }
}