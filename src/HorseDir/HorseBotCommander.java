package HorseDir;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public class HorseBotCommander {
    private static final String DIRECTORY = "C:\\Users\\Aaron Fisher\\IdeaProjects\\HorseBot\\src\\HorseLogs\\";
    private static final String CHANNEL_FOLDER = "ChannelData\\";
    private static final String USAGE_FOLDER = "UsageLogs\\";
    private static final String SESSION_FOLDER = "SessionLogs\\";
    private Map<String, Long> LAST_HORSE_DEATH;
    private Map<String, Long> LAST_HORSE_MURDER;
    private HorseBotMessenger messenger;
    private FileWriter murderLog;
    private FileWriter deathLog;
    private FileWriter sessionLog;

    public HorseBotCommander(HorseBotMessenger messenger) {
        this.messenger = messenger;
        sessionLog = openFile(DIRECTORY,SESSION_FOLDER,getSessionName());
        deathLog = openFile(DIRECTORY,"","DeathLog.txt");
        murderLog = openFile(DIRECTORY,"","MurderLog.txt");
        LAST_HORSE_DEATH = getLastDeaths();
        LAST_HORSE_MURDER = getLastMurders();
    }

    public void command(String user, Channel channel, String body) {
        Scanner scanner = new Scanner(body);
        writeEvent(user,channel.channel,body);
        switch (scanner.next()) {
            case "!horse":
                if (LAST_HORSE_DEATH.containsKey(channel.channel)) {
                    messenger.privmsg(channel, String.format(
                            "It has been %s since %s was last killed by a horse.",
                            timeDifference(LAST_HORSE_DEATH.get(channel.channel)),
                            channel.nick
                    ));
                }
                break;
            case "!commands":
                messenger.privmsg(channel, "HorseBotXD commands: !info, !horse, !murder");
                break;
            case "!neigh":
                if (user.contains("glitch29") || user.contains(channel.name)) {
                    long newDeath = scanner.hasNextLong() ? scanner.nextLong() : new Date().getTime();
                    writeDeath(channel.channel, newDeath);
                    LAST_HORSE_DEATH.put(channel.channel, newDeath);
                    messenger.privmsg(channel, String.format(
                            "It has been %s since %s was last killed by a horse. BibleThump",
                            timeDifference(LAST_HORSE_DEATH.get(channel.channel)),
                            channel.nick
                    ));
                }
                break;
            case "!secret":
                messenger.privmsg(channel, "\uD83D\uDC0E");
                messenger.privmsg(channel, "\uD83D\uDC0E\uD83D\uDC0E");
                messenger.privmsg(channel, "\uD83D\uDC0E\uD83D\uDC0E\uD83D\uDC0E");
                messenger.privmsg(channel, "\uD83D\uDC0E\uD83D\uDC0E");
                messenger.privmsg(channel, "\uD83D\uDC0E");
                break;
            case "!murder":
                if (LAST_HORSE_MURDER.containsKey(channel.channel)) {
                    messenger.privmsg(channel, String.format(
                            "It has been %s since %s brutally murdered a horse.",
                            timeDifference(LAST_HORSE_MURDER.get(channel.channel)),
                            channel.nick
                    ));
                }
                break;
            case "!D:":
                if (user.contains("glitch29") || user.contains(channel.name)) {
                    long newDeath = scanner.hasNextLong() ? scanner.nextLong() : new Date().getTime();
                    writeMurder(channel.channel, newDeath);
                    LAST_HORSE_DEATH.put(channel.channel, newDeath);
                    messenger.privmsg(channel, String.format(
                            "It has been %s since %s brutally murdered a horse. D:",
                            timeDifference(LAST_HORSE_DEATH.get(channel.channel)),
                            channel.nick
                    ));
                }
                break;
        }
    }

    private static String timeDifference(long start) {
        long end = new Date().getTime();
        long seconds = (end - start) / 1000L;
        long minutes = seconds / 60L;
        long hours = minutes / 60L;
        long days = hours / 24L;

        return (days + " days, " + (hours % 24) + " hours, " + (minutes % 60) + " minutes, and " + (seconds % 60) + " seconds");
    }

    private static Map<String, Long> getLastDeaths() {
        File horseLog = new File("C:\\Users\\Aaron Fisher\\IdeaProjects\\HorseBot\\src\\HorseLogs\\DeathLog.txt");
        Map<String, Long> lastDeaths = new HashMap<>();
        try (Scanner fileScanner = new Scanner(horseLog)) {
            while (fileScanner.hasNext()) {
                lastDeaths.put(fileScanner.next(), fileScanner.nextLong());
            }
        } catch (FileNotFoundException e) {}
        return lastDeaths;
    }

    private static Map<String, Long> getLastMurders() {
        File horseLog = new File("C:\\Users\\Aaron Fisher\\IdeaProjects\\HorseBot\\src\\HorseLogs\\MurderLog.txt");
        Map<String, Long> lastDeaths = new HashMap<>();
        try (Scanner fileScanner = new Scanner(horseLog)){
            while (fileScanner.hasNext()) {
                lastDeaths.put(fileScanner.next(), fileScanner.nextLong());
            }
        } catch (FileNotFoundException e) {}
        return lastDeaths;
    }

    private void writeEvent(String user, String channel, String body) {
        try
        {
            sessionLog.write(channel + " " + new Date().getTime() + " " + user + " " + body + "\n");
            sessionLog.flush();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    private void writeDeath(String channel, long time) {
        try
        {
            deathLog.write(channel + " " + time + "\n");
            deathLog.flush();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    private void writeMurder(String channel, long time) {
        try
        {
            murderLog.write(channel + " " + time + "\n");
            murderLog.flush();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    private static String getSessionName() {
        return new Date().getTime() + ".txt";
    }

    private static FileWriter openFile(String directory, String folder, String file) {
        try {
            return new FileWriter(new File(directory + folder + file),true);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
