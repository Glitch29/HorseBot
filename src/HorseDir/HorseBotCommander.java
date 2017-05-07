package HorseDir;

import WorldRecords.Category;
import WorldRecords.RecordLookup;
import WorldRecords.Restriction;

import java.util.Date;
import java.util.Scanner;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public class HorseBotCommander {
    private static boolean PYRAMID = true;
    private static final String KONAMI_CODE = "⬆⬆⬇⬇⬅➡⬅➡\uD83C\uDD71\uD83C\uDD70";
    private static final String BANG_KONAMI_CODE = "!" + KONAMI_CODE;
    private HorseBotMessenger messenger;
    private HorseBotDatabase database;
    private HorseBotAdventurer adventurer;
    private static String SPECIAL_USER = "enderjp";

    public HorseBotCommander(HorseBotMessenger messenger, HorseBotDatabase database, HorseBotAdventurer adventurer) {
        this.messenger = messenger;
        this.database = database;
        this.adventurer = adventurer;
    }

    public void command(String user, Channel channel, String body) {
        Scanner scanner = new Scanner(body);
        database.log(channel + " " + new Date().getTime() + " " + user + " " + body);
        switch (scanner.next().toLowerCase()) {
            case "!anyhorse":
                try {
                    Channel targetChannel = Channel.get(database.latestKey(HorseBotDatabase.Tracker.DEATHS));
                    messenger.privmsg(channel, String.format(
                            "The last runner to be killed by a horse was %s, %s ago. \uD83D\uDC0E",
                            targetChannel.nick,
                            timeDifference(database.read(HorseBotDatabase.Tracker.DEATHS, targetChannel))
                    ));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            break;
            case "!translate":
                messenger.privmsg(channel, "Your Boko Shield is badly damaged. BibleThump (probably)");
                //!translate "Dein Bokschild zerbricht gleich."
                break;
            case BANG_KONAMI_CODE:
                if (PYRAMID) {
                    SPECIAL_USER = user;
                    messenger.privmsg(channel, String.format("%s has taken control of the special powers.",
                            user));
                } else {
                    messenger.privmsg(channel, "The special powers need time to recharge.");
                }
            case "!horse":
                Channel targetChannel = channel;
                try {
                    targetChannel = Channel.get(scanner.next());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                messenger.privmsg(channel, String.format(
                        "It has been %s since %s was last killed by a horse. \uD83D\uDC0E",
                        timeDifference(database.read(HorseBotDatabase.Tracker.DEATHS, targetChannel)),
                        targetChannel.nick
                ));
                break;
            case "!believe":
                try {
                    if (scanner.hasNext()) {
                        String next = scanner.next().toLowerCase();
                        System.out.println(next);
                        Channel believe = Channel.get("#" + next);
                        messenger.privmsg(believe, String.format(
                                "HorseBot believes in you, %s. You're not going to screw it up! \uD83D\uDC0E",
                                believe.nick
                        ));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "!try":
                try {
                    if (scanner.hasNext()) {
                        String next = scanner.next().toLowerCase();
                        System.out.println(next);
                        Channel believe = Channel.get("#" + next);
                        messenger.privmsg(believe, String.format(
                                "HorseBot is trying to believe in you, %s! \uD83D\uDC0E",
                                believe.nick
                        ));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "!fuckamiibos":
                messenger.privmsg(channel, "PJSalt");
                break;
            case "!murder":
                messenger.privmsg(channel, String.format(
                        "It has been %s since %s brutally murdered a horse. \uD83D\uDC0E",
                        timeDifference(database.read(HorseBotDatabase.Tracker.MURDERS, channel)),
                        channel.nick
                ));
                break;
            case "!seal":
                messenger.privmsg(channel, String.format(
                        "It has been %s since %s gratuitously abused a Sand Seal. ᶘ ᵒᴥᵒᶅ",
                        timeDifference(database.read(HorseBotDatabase.Tracker.SEALS, channel)),
                        channel.nick
                ));
                break;
            case "!panic":
                if (user.contains("glitch29") || user.contains(channel.broadcaster)) {
                    messenger.privmsg(channel, "The stream! panicBasket The stream! panicBasket The stream is on fire!");
                    messenger.privmsg(channel, "\uD83D\uDD25\uD83D\uDD25WE DON'T NEED NO WATER. LET THE MOTHERFUCKER BURN\uD83D\uDD25\uD83D\uDD25");
                }
                break;
            case "!points":
                if (user.equals("dylrocks17") && (new Date().getTime() & 30L) == 0L) {
                    messenger.privmsg(channel, "/ban DylRocks17 " + 60*60*24*7);
                }
                break;
            case "!wr":
                messenger.privmsg(channel, "!ad, !any%, !amq, !as, !ms, !ad-a, !any%-a, !amq-a, !as-a, !ms-a, !ad-a+us");
                break;
            case "!ad":
                messenger.privmsg(channel, RecordLookup.leaderboard(Category.AD));
                break;
            case "!any%":
                messenger.privmsg(channel, RecordLookup.leaderboard(Category.Any));
                break;
            case "!amq":
                messenger.privmsg(channel, RecordLookup.leaderboard(Category.AMQ));
                break;
            case "!as":
                messenger.privmsg(channel, RecordLookup.leaderboard(Category.AS));
                break;
            case "!ms":
                messenger.privmsg(channel, RecordLookup.leaderboard(Category.MS));
                break;
            case "!100%":
                messenger.privmsg(channel, RecordLookup.leaderboard(Category.HUNDO));
                break;
            case "!ad-a":
                messenger.privmsg(channel, RecordLookup.leaderboard(Category.AD, Restriction.Amiiboless));
                break;
            case "!any%-a":
                messenger.privmsg(channel, RecordLookup.leaderboard(Category.Any, Restriction.Amiiboless));
                break;
            case "!amq-a":
                messenger.privmsg(channel, RecordLookup.leaderboard(Category.AMQ, Restriction.Amiiboless));
                break;
            case "!as-a":
                messenger.privmsg(channel, RecordLookup.leaderboard(Category.AS, Restriction.Amiiboless));
                break;
            case "!ms-a":
                messenger.privmsg(channel, RecordLookup.leaderboard(Category.MS, Restriction.Amiiboless));
                break;
            case "!ad-a+us":
                messenger.privmsg(channel, RecordLookup.leaderboard(Category.AD, Restriction.Amiiboless, Restriction.USA));
                break;
            case "!pb":
                messenger.privmsg(channel, RecordLookup.getPBbyName(scanner.hasNext() ? scanner.next() : channel.broadcaster));
                break;
            case "!adventure":
                if (user.contains("glitch29") || user.contains(channel.broadcaster)) {
                    adventurer.beginAdventure(channel);
                }
                break;
            case "!abort":
                if (user.contains("glitch29") || user.contains(channel.broadcaster)) {
                    adventurer.endAdventure(channel);
                }
                break;
            case "!commands":
                messenger.privmsg(channel, "!info, !complimentstreamer, !horse, !murder, !horsefact \uD83D\uDC0E");
                break;
            case "!info":
                messenger.privmsg(channel, "HorseBotXD was created by Glitch29. Its code is available at https://github.com/Glitch29/HorseBot/. \uD83D\uDC0E");
                break;
            case "!horsefact":
                if (scanner.hasNextInt()) {
                    messenger.privmsg(channel, HorseFact.fact(scanner.nextInt()));
                } else {
                    messenger.privmsg(channel, HorseFact.fact());
                }
                break;
            case "!complimentstreamer":
                if (scanner.hasNextInt()) {
                    messenger.privmsg(channel, StreamerFact.fact(channel, scanner.nextInt()) + " 4Head");
                } else {
                    messenger.privmsg(channel, StreamerFact.fact(channel) + " 4Head");
                }
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
                if (user.equals("glitch29")) {
                    messenger.privmsg(channel, "\uD83D\uDC0E");
                    messenger.privmsg(channel, "\uD83D\uDC0E\uD83D\uDC0E");
                    messenger.privmsg(channel, "\uD83D\uDC0E\uD83D\uDC0E\uD83D\uDC0E");
                    messenger.privmsg(channel, "\uD83D\uDC0E\uD83D\uDC0E");
                    messenger.privmsg(channel, "\uD83D\uDC0E");
                } else if (user.equals(SPECIAL_USER) && PYRAMID) {
                    PYRAMID = false;
                    messenger.privmsg(channel, "\uD83D\uDC0E");
                    messenger.privmsg(channel, "\uD83D\uDC0E\uD83D\uDC0E");
                    messenger.privmsg(channel, "\uD83D\uDC0E\uD83D\uDC0E\uD83D\uDC0E");
                    messenger.privmsg(channel, "\uD83D\uDC0E\uD83D\uDC0E");
                    messenger.privmsg(channel, "\uD83D\uDC0E");
                } else if (user.equals(channel.broadcaster)) {
                    PYRAMID = false;
                    messenger.privmsg(channel, "\uD83D\uDC0E");
                    messenger.privmsg(channel, "\uD83D\uDC0E\uD83D\uDC0E");
                    messenger.privmsg(channel, "\uD83D\uDC0E\uD83D\uDC0E\uD83D\uDC0E");
                    messenger.privmsg(channel, "\uD83D\uDC0E\uD83D\uDC0E");
                    messenger.privmsg(channel, "Purchase HorseBotXD Premium to complete this pyramid.");
                } else {
                    messenger.privmsg(channel, String.format("/w %s Enter a secret code to unlock this special power.",
                            user));
                }
                break;
            case "!3speed":
                messenger.privmsg(channel, "It's always the horses fault, isn't it? ಠ_ಠ");
                break;
        }
        scanner.close();
    }

    private static String timeDifference(long start) {
        long end = new Date().getTime();
        long seconds = (end - start) / 1000L;
        long minutes = seconds / 60L;
        long hours = minutes / 60L;
        long days = hours / 24L;

        return ((days > 0 ? days + " days, " : "")
                + (hours > 0 ? (hours % 24) + " hours, " : "")
                + (minutes > 0 ? (minutes % 60) + " minutes, and " : "")
                + (seconds % 60) + " seconds");
    }
}
