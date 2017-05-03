package HorseDir;

import java.util.Date;
import java.util.Scanner;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public class HorseBotCommander {
    private HorseBotMessenger messenger;
    private HorseBotDatabase database;
    private HorseBotAdventurer adventurer;

    public HorseBotCommander(HorseBotMessenger messenger, HorseBotDatabase database, HorseBotAdventurer adventurer) {
        this.messenger = messenger;
        this.database = database;
        this.adventurer = adventurer;
    }

    public void command(String user, Channel channel, String body) {
        Scanner scanner = new Scanner(body);
        database.log(channel + " " + new Date().getTime() + " " + user + " " + body);
        switch (scanner.next().toLowerCase()) {
            case "!horse":
                messenger.privmsg(channel, String.format(
                        "It has been %s since %s was last killed by a horse. \uD83D\uDC0E",
                        timeDifference(database.read(HorseBotDatabase.Tracker.DEATHS, channel)),
                        channel.nick
                ));
                break;
            case "!murder":
                messenger.privmsg(channel, String.format(
                        "It has been %s since %s brutally murdered a horse. \uD83D\uDC0E",
                        timeDifference(database.read(HorseBotDatabase.Tracker.MURDERS, channel)),
                        channel.nick
                ));
                break;
            case "!panic":
                if (user.contains("glitch29") || user.contains(channel.broadcaster)) {
                    messenger.privmsg(channel, "The stream! panicBasket The stream! panicBasket The stream is on fire!");
                    messenger.privmsg(channel, "\uD83D\uDD25\uD83D\uDD25WE DON'T NEED NO WATER. LET THE MOTHERFUCKER BURN\uD83D\uDD25\uD83D\uDD25");
                }
                break;
            case "!wr":
                messenger.privmsg(channel, String.format(
                        "%s doesn't need world record to be proud of his or her accomplishments. What are you implying %s?",
                        channel.nick,
                        user
                ));
                break;
            case "!adventure":
                if (user.contains("glitch29") || user.contains("jgiga")) {
                    adventurer.beginAdventure(channel, 3);
                }
                break;
            case "!abort":
                if (user.contains("glitch29") || user.contains("jgiga")) {
                    adventurer.endAdventure(channel);
                }
                break;
            case "!commands":
                messenger.privmsg(channel, "!info, !horse, !murder, !horsefact \uD83D\uDC0E");
                break;
            case "!info":
                messenger.privmsg(channel, "HorseBotXD was created by Glitch29. Its code is available at https://github.com/Glitch29/HorseBot/. \uD83D\uDC0E");
                break;
            case "!horsefact":
                messenger.privmsg(channel, HorseFact.fact());
                break;
            case "!who's":
                messenger.privmsg(channel, "Horse.");
                break;
            case "!neigh":
                if (user.contains("glitch29") || user.contains(channel.broadcaster)) {
                   messenger.privmsg(channel, String.format(
                            "It has been %s since %s was last killed by a horse. BibleThump",
                            timeDifference(database.track(HorseBotDatabase.Tracker.DEATHS, channel)),
                            channel.nick
                    ));
                }
                break;
            case "!d:":
                if (user.contains("glitch29") || user.contains(channel.broadcaster)) {
                    messenger.privmsg(channel, String.format(
                            "It has been %s since %s brutally murdered a horse. D:",
                            timeDifference(database.track(HorseBotDatabase.Tracker.MURDERS, channel)),
                            channel.nick
                    ));
                }
                break;
            case "!\uD83D\uDC0E":
                if (user.contains("glitch29")) {
                    messenger.privmsg(channel, "\uD83D\uDC0E");
                    messenger.privmsg(channel, "\uD83D\uDC0E\uD83D\uDC0E");
                    messenger.privmsg(channel, "\uD83D\uDC0E\uD83D\uDC0E\uD83D\uDC0E");
                    messenger.privmsg(channel, "\uD83D\uDC0E\uD83D\uDC0E");
                    messenger.privmsg(channel, "\uD83D\uDC0E");
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
}
