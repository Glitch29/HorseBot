package HorseDir;

import HorseDir.Channels.Channel;
import HorseLogs.Trackers.Tracker;
import MessageLines.TwitchMessage;
import WorldRecords.Category;
import WorldRecords.RecordLookup;
import WorldRecords.Restriction;

import java.util.*;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public class HorseBotCommander {
    private HorseBotDatabase database;
    private HorseBotAdventurer adventurer;
    private Map<String, Set<String>> votekick = new HashMap<>();
    private Set<String> superusers;
    private Map<String, Event> eventMap;
    private Map<String, Event> resetMap;

    public HorseBotCommander(HorseBotDatabase database, HorseBotAdventurer adventurer) {
        this.database = database;
        this.adventurer = adventurer;
        superusers = new HashSet<>();
        superusers.add("glitch29");
        superusers.add("jerrytheret");
        resetMap = new HashMap<>();
        eventMap = new HashMap<>();
        for (Event event : Event.values()) {
            resetMap.put(event.emoji, event);
            eventMap.put(event.name, event);
        }
    }

    public void command(TwitchMessage message) {
        Scanner scanner = new Scanner(message.body);
        if (!scanner.hasNext()) {
            return;
        }
        if (message.channel.settings().isLogged()) {
            database.log(String.format("%s %d %s %s",
                    message.channel.channelCode(),
                    new Date().getTime(),
                    message.user.username,
                    message.body));
        }
        Channel channel = message.channel;
        User user = message.user;

        String command = scanner.next().substring(1).toLowerCase();
        if (command.equals("neigh")) {
            command = "\uD83D\uDC0E";
        }

        if (resetMap.containsKey(command)) {
            Event event = resetMap.get(command);
            event.write(database, message.channel);
            event.read(database, message.channel);
        }
        if (eventMap.containsKey(command)) {
            Event event = eventMap.get(command);
            event.read(database, message.channel);
        }
        switch (command) {
            case "f":
                if (scanner.hasNextInt()) {
                    int degrees = scanner.nextInt();
                    message.user.directedMessage(String.format("%d° Fahrenheit = %d° Canadian",
                            degrees,
                            ((degrees-32) * 5 + 4) / 9));
                }
                break;
            case "c":
                if (scanner.hasNextInt()) {
                    int degrees = scanner.nextInt();
                    message.user.directedMessage(String.format("%d° Canadian = %d° Fahrenheit",
                            degrees,
                            (degrees * 9 + 2) / 5 + 32));
                }
                break;
            case "rupeestrats":
                channel.message("\uD83D\uDCB2\uD83D\uDCB2 https://clips.twitch.tv/PrettiestDarkShieldPeoplesChamp \uD83D\uDCB2\uD83D\uDCB2");
                break;
            case "bokostrats":
                channel.message("\uD83D\uDD25\uD83D\uDD25 https://clips.twitch.tv/JollySourMomKreygasm \uD83D\uDD25\uD83D\uDD25");
                break;
            case "strats":
                channel.message("Visual strat: http://i.imgur.com/WTSMSxG.jpg \uD83D\uDD25\uD83D\uDD25");
                break;
            case "anyhorse":
                try {
                    Channel targetChannel = Channel.get(database.latestKey(Tracker.DEATHS));
                    HorseBotDatabase.LongWithNotes data = database.read(Tracker.DEATHS, targetChannel);
                    channel.message(String.format(
                           "The last runner to be killed by a horse was %s, %s ago. \uD83D\uDC0E %s",
                            targetChannel.settings().name(),
                            timeDifference(data.date),
                            data.notes
                    ));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            break;
            case "anymurder":
                try {
                    Channel targetChannel = Channel.get(database.latestKey(Tracker.MURDERS));
                    HorseBotDatabase.LongWithNotes data = database.read(Tracker.MURDERS, targetChannel);
                    channel.message(String.format(
                            "The last runner to murder a horse was %s, %s ago. \uD83D\uDC0E " +
                                    "HorseBot does not condone this type of behavior. \uD83D\uDC0E",
                            targetChannel.settings().name(),
                            timeDifference(data.date)
                    ));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "!":
                if (superusers.contains(user.username) || channel.broadcaster.equals(user.username)) {
                    try {
                        if (scanner.hasNext()) {
                            String next = scanner.next() + " ";
                            channel.message(next);
                            channel.message(next + next);
                            channel.message(next + next + next);
                            channel.message(next + next);
                            if (superusers.contains(user.username)) {
                                channel.message(next);
                            } else {
                                channel.message("Purchase HorseBot Premium to complete this pyramid.");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    user.personalMessage("Interrupt one of Enderjp's pyramids, and post a screenshot of it to enable this feature.");
                }
                break;
            case "announce":
                if (superusers.contains(user.username)) {
                    try {
                        if (scanner.hasNext()) {
                            String next = scanner.next().toLowerCase();
                            Channel believe = Channel.get("#" + next);
                            believe.message(
                                    (user.username.equals("glitch29") ? "" : "\uD83D\uDC0E ") +
                                    String.format(
                                        scanner.nextLine(),
                                        believe.broadcaster
                                    ));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "believe":
                try {
                    if (scanner.hasNext()) {
                        String next = scanner.next().toLowerCase();
                        System.out.println(next);
                        Channel believe = Channel.get("#" + next);
                        believe.message(String.format(
                                "HorseBot believes in you, %s. You're not going to screw it up! \uD83D\uDC0E",
                                believe
                        ));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "try":
                try {
                    if (scanner.hasNext()) {
                        String next = scanner.next().toLowerCase();
                        System.out.println(next);
                        Channel believe = Channel.get("#" + next);
                        believe.message(String.format(
                                "HorseBot is trying to believe in you, %s! Sometimes it's a hard thing to do. \uD83D\uDC0E",
                                believe
                        ));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "magnesisstrats":
                channel.message("https://clips.twitch.tv/PlumpImpossibleChoughGOWSkull");
                break;
            case "votekick":
                if (scanner.hasNext()) {
                    String target = scanner.next();
                    if (target.toLowerCase().equals("glitch29") || target.toLowerCase().equals("horsebotxd")) {
                        target = user.username;
                    }
                    String t = target.toLowerCase();
                    if (!votekick.containsKey(t)) {
                        votekick.put(t, new HashSet<>());
                    }
                    if (votekick.get(t).add(user.username)) {
                        channel.message(String.format("%s now has %d votes to be removed from this channel.",
                                target,
                                votekick.get(t).size()));
                    }
                }
                break;
            case "channel":
            case "energy":
                    if (!votekick.containsKey(message.channel.nick())) {
                        votekick.put(message.channel.nick(), new HashSet<>());
                    }
                    if (votekick.get(message.channel.nick()).add(user.username)) {
                        channel.message(String.format("%s is channeling his energy. %d people believe in you, %s!",
                                message.user.username,
                                votekick.get(message.channel.nick()).size(),
                                message.channel.nick()));
                    }
                break;
            case "panic":
                if (superusers.contains(user.username) || channel.broadcaster.equals(user.username)) {
                    channel.message("The stream! panicBasket The stream! panicBasket The stream is on fire!");
                    channel.message("\uD83D\uDD25\uD83D\uDD25WE\uD83D\uDD25DON'T\uD83D\uDD25NEED\uD83D\uDD25NO\uD83D\uDD25WATER\uD83D\uDD25\uD83D\uDD25LET\uD83D\uDD25THE\uD83D\uDD25MOTHERFUCKER\uD83D\uDD25BURN\uD83D\uDD25\uD83D\uDD25");
                }
                break;
            case "fire":
                if (superusers.contains(user.username) || channel.broadcaster.equals(user.username)) {
                    channel.message("The Link! panicBasket The Link! panicBasket The Link is on fire!");
                    channel.message("\uD83D\uDD25\uD83D\uDD25WE\uD83D\uDD25DON'T\uD83D\uDD25NEED\uD83D\uDD25ELIXIRS\uD83D\uDD25\uD83D\uDD25LET\uD83D\uDD25THE\uD83D\uDD25MOTHERFUCKER\uD83D\uDD25BURN\uD83D\uDD25\uD83D\uDD25");
                }
                break;
            case "points":
                if (user.username.equals("dylrocks17") && (new Date().getTime() & 30L) == 0L) {
                    channel.message("/timeout DylRocks17 " + 60*60*24*7);
                }
                break;
            case "wr":
                channel.message("!ad, !any%, !amq, !as, !ms, !ad-a, !any%-a, !amq-a, !as-a, !ms-a \uD83D\uDC0E");
                break;
            case "ad":
                channel.message(RecordLookup.leaderboard(Category.AD));
                break;
            case "any%":
                channel.message(RecordLookup.leaderboard(Category.Any));
                break;
            case "amq":
                channel.message(RecordLookup.leaderboard(Category.AMQ));
                break;
            case "as":
                channel.message(RecordLookup.leaderboard(Category.AS));
                break;
            case "ms":
                channel.message(RecordLookup.leaderboard(Category.MS));
                break;
            case "100%":
                channel.message(RecordLookup.leaderboard(Category.HUNDO));
                break;
            case "ad-a":
                channel.message(RecordLookup.leaderboard(Category.AD, Restriction.Amiiboless));
                break;
            case "any%-a":
                channel.message(RecordLookup.leaderboard(Category.Any, Restriction.Amiiboless));
                break;
            case "amq-a":
                channel.message(RecordLookup.leaderboard(Category.AMQ, Restriction.Amiiboless));
                break;
            case "as-a":
                channel.message(RecordLookup.leaderboard(Category.AS, Restriction.Amiiboless));
                break;
            case "ms-a":
                channel.message(RecordLookup.leaderboard(Category.MS, Restriction.Amiiboless));
                break;
            case "pb":
                channel.message(RecordLookup.getPBbyName(scanner.hasNext() ? scanner.next() : channel.broadcaster));
                break;
            case "adventure":
                if (superusers.contains(user.username) || channel.broadcaster.equals(user.username)) {
                    adventurer.beginAdventure(channel);
                }
                break;
            case "abort":
                if (superusers.contains(user.username) || channel.broadcaster.equals(user.username)) {
                    adventurer.endAdventure(channel);
                }
                break;
            case "commands":
                channel.message("!info, !complimentstreamer, !horse, !murder, !horsefact \uD83D\uDC0E");
                break;
            case "info":
                channel.message("HorseBotXD was created by Glitch29. Its code is available at https://github.com/Glitch29/HorseBot/. \uD83D\uDC0E");
                break;
            case "horsefact":
                if (scanner.hasNextInt()) {
                    channel.message(HorseFact.fact(scanner.nextInt()));
                } else {
                    channel.message(HorseFact.fact());
                }
                break;
            case "complimentstreamer":
                if (scanner.hasNextInt()) {
                    channel.message(StreamerFact.fact(channel, scanner.nextInt()) + " 4Head");
                } else {
                    channel.message(StreamerFact.fact(channel) + " 4Head");
                }
                break;
            case "3speed":
                channel.message("It's always the horses fault, isn't it? ಠ_ಠ");
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

    private enum Event {
        HORSE (Tracker.DEATHS, "was last killed by a horse.", "\uD83D\uDC0E", "horse"),
        MURDER (Tracker.MURDERS, "brutally murdered a horse.", "D:", "murder"),
        RIVER (Tracker.ICERIVER, "fell into a river and drowned.", "⛄", "river"),
        PETA (Tracker.PETA, "was protested by PETA.", "ᶘ'ᵒᴥᵒᶅ", "peta"),
        EXPOSURE (Tracker.EXPOSURE, "died to exposure.", "\uD83C\uDF1E", "exposure"),
        SPIDER (Tracker.SPIDER, "died to Calamity Ganon.", "\uD83D\uDD77", "spider"),
        CACTUS (Tracker.CACTUS, "bombed a cactus at point blank range.", "\uD83C\uDF35", "cactus");

        private static String PREFIX = "It has been %s since %s ";
        private static String POSTFIX = " %s %s";
        String message;
        Tracker tracker;
        String emoji;
        String name;

        Event(Tracker tracker, String message, String emoji, String name) {
            this.tracker = tracker;
            this.message = message;
            this.emoji = emoji;
            this.name = name;
        }

        private void read(HorseBotDatabase database, Channel channel) {
            HorseBotDatabase.LongWithNotes data = database.read(tracker, channel);
            if (data == null) {
                return;
            }
            channel.message(String.format(
                    PREFIX + message + POSTFIX,
                    timeDifference(data.date),
                    channel.nick(),
                    emoji,
                    data.notes));
        }

        private void write(HorseBotDatabase database, Channel channel) {
            database.track(tracker, channel);
        }
    }
}
