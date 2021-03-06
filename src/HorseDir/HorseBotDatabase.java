package HorseDir;

import HorseDir.Channels.Channel;
import HorseLogs.Trackers.Tracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by Aaron Fisher on 4/30/2017.
 */
public class HorseBotDatabase {
    private static final String SESSION_FOLDER = "SessionLogs\\";
    private static final String TRACKER_FOLDER = "Trackers\\";
    private static final Map<Tracker,Map<String, LongWithNotes>> TRACKER_MAP = new HashMap<>();
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

    public LongWithNotes read(Tracker tracker, Channel channel) {
        if (!readTracker(tracker).containsKey(channel.channelCode())) {
            return null;
        }
        return readTracker(tracker).get(channel.channelCode());
    }

    public long track(Tracker tracker, Channel channel) {
        return track(tracker, channel, new Date().getTime());
    }

    public long track(Tracker tracker, Channel channel, Long time) {
        readTracker(tracker).put(channel.channelCode(), new LongWithNotes(time, ""));
        try (FileWriter writer = new FileWriter(new File(directory + TRACKER_FOLDER + tracker.fileName), true)) {
            writer.write(channel.channelCode() + " " + time + "\n");
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

    public String latestKey(Tracker tracker) {
        long max = Long.MIN_VALUE;
        String key = "";
        for (Map.Entry<String,LongWithNotes> entry : readTracker(tracker).entrySet()) {
            if (entry.getValue().date > max) {
                max = entry.getValue().date;
                key = entry.getKey();
            }
        }
        return key;
    }

    private Map<String, LongWithNotes> readTracker(Tracker tracker) {
        if (!TRACKER_MAP.containsKey(tracker)) {
            TRACKER_MAP.put(tracker, loadTracker(tracker));
        }
        return TRACKER_MAP.get(tracker);
    }

    private Map<String, LongWithNotes> loadTracker(Tracker tracker) {
        File file = new File(directory + TRACKER_FOLDER + tracker.fileName);
        Map<String, LongWithNotes> map = new HashMap<>();
        try (Scanner scanner = new Scanner(file)){
            while (scanner.hasNext()) {
                map.put(scanner.next(), new LongWithNotes(scanner.nextLong(), scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static class LongWithNotes {
        long date;
        String notes;

        public LongWithNotes(long date, String notes) {
            this.date = date;
            this.notes = notes;
        }
    }
}
