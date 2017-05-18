package HorseDir;

import MessageLines.Message;
import WorldRecords.Category;
import WorldRecords.RecordLookup;
import WorldRecords.Restriction;

import java.util.*;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public class HorseBotCommander {
    private static final String KONAMI_CODE = "⬆⬆⬇⬇⬅➡⬅➡\uD83C\uDD71\uD83C\uDD70";
    private static final String BANG_KONAMI_CODE = "!" + KONAMI_CODE;
    private HorseBotDatabase database;
    private HorseBotAdventurer adventurer;
    private Map<String, Set<String>> votekick = new HashMap<>();
    private Set<String> superusers;

    public HorseBotCommander(HorseBotDatabase database, HorseBotAdventurer adventurer) {
        this.database = database;
        this.adventurer = adventurer;
        superusers = new HashSet<>();
        superusers.add("glitch29");
    }

    public void command(Message message) {
        Scanner scanner = new Scanner(message.body);
        database.log(String.format("%s %d %s %s",
                message.channel.name,
                new Date().getTime(),
                message.user,
                message.body));
        Channel channel = message.channel;
        User user = message.user;

        switch (scanner.next().toLowerCase()) {
            case "!nopyramid":
                database.track(HorseBotDatabase.Tracker.COUNTER, channel);
            case "!pyramid":
                channel.message(String.format(
                        "It has been %s since %s forgot to put a counter on his Pyramid of the Pantheon. \uD83D\uDD3A",
                        timeDifference(database.read(HorseBotDatabase.Tracker.COUNTER, channel)),
                        channel.nick
                ));
                break;
            case "!⛄":
                database.track(HorseBotDatabase.Tracker.ICERIVER, channel);
            case "!river":
                channel.message(String.format(
                        "It has been %s since %s fell into a river and drowned. ⛄",
                        timeDifference(database.read(HorseBotDatabase.Tracker.ICERIVER, channel)),
                        channel.nick
                ));
                break;
            case "!bokostrats":
                channel.message("\uD83D\uDD25\uD83D\uDD25 https://clips.twitch.tv/JollySourMomKreygasm \uD83D\uDD25\uD83D\uDD25");
                break;
            case "!setup":
                channel.message(String.format("%s uses the following equipment in their stream:",
                        channel.nick));
                break;
            case "!strats":
                channel.message("Visual strat: http://i.imgur.com/WTSMSxG.jpg \uD83D\uDD25\uD83D\uDD25");
                break;
            case "!anyhorse":
                try {
                    Channel targetChannel = Channel.get(database.latestKey(HorseBotDatabase.Tracker.DEATHS));
                    channel.message(String.format(
                            "The last runner to be killed by a horse was %s, %s ago. \uD83D\uDC0E",
                            targetChannel.nick,
                            timeDifference(database.read(HorseBotDatabase.Tracker.DEATHS, targetChannel))
                    ));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            break;
            case "!anymurder":
                try {
                    Channel targetChannel = Channel.get(database.latestKey(HorseBotDatabase.Tracker.MURDERS));
                    channel.message(String.format(
                            "The last runner to murder a horse was %s, %s ago. \uD83D\uDC0E " +
                                    "HorseBot does not condone this type of behavior. \uD83D\uDC0E",
                            targetChannel.nick,
                            timeDifference(database.read(HorseBotDatabase.Tracker.MURDERS, targetChannel))
                    ));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "!horse":
                Channel targetChannel = channel;
                try {
                    targetChannel = Channel.get(scanner.next());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (database.read(HorseBotDatabase.Tracker.DEATHS, targetChannel) > 0L) {
                    channel.message(String.format(
                            "It has been %s since %s was last killed by a horse. \uD83D\uDC0E",
                            timeDifference(database.read(HorseBotDatabase.Tracker.DEATHS, targetChannel)),
                            targetChannel.nick
                    ));
                }
                break;
            case "!!":
                if (superusers.contains(user.username) || channel.broadcaster.equals(user.username)) {
                    try {
                        if (scanner.hasNext()) {
                            String next = scanner.next() + " ";
                            channel.message(next);
                            channel.message(next + next);
                            channel.message(next + next + next);
                            channel.message(next + next);
                            channel.message(next);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "!announce":
                if (superusers.contains(user.username)) {
                    try {
                        if (scanner.hasNext()) {
                            String next = scanner.next().toLowerCase();
                            System.out.println(next);
                            Channel believe = Channel.get("#" + next);
                            believe.message(String.format(
                                    scanner.nextLine(),
                                    believe.broadcaster
                            ));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "!believe2":
                try {
                    if (scanner.hasNext()) {
                        String next = scanner.next().toLowerCase();
                        System.out.println(next);
                        Channel believe = Channel.get("#" + next);
                        believe.message(String.format(
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
                        believe.message(String.format(
                                "HorseBot is trying to believe in you, %s! Sometimes it's a hard thing to do. \uD83D\uDC0E",
                                believe.nick
                        ));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "!fuckamiibos":
                channel.message("PJSalt");
                break;
            case "!magnesisstrats":
                channel.message("https://clips.twitch.tv/PlumpImpossibleChoughGOWSkull");
                break;
            case "!murder":
                if (database.read(HorseBotDatabase.Tracker.MURDERS, channel) > 0L) {
                    channel.message(String.format(
                            "It has been %s since %s brutally murdered a horse. \uD83D\uDC0E",
                            timeDifference(database.read(HorseBotDatabase.Tracker.MURDERS, channel)),
                            channel.nick
                    ));
                }
                break;
            case "!votekick":
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
            case "!energy":
                    if (!votekick.containsKey(message.channel.nick)) {
                        votekick.put(message.channel.nick, new HashSet<>());
                    }
                    if (votekick.get(message.channel.nick).add(user.username)) {
                        channel.message(String.format("%s is channeling his energy. %d people believe in you, %s!",
                                message.user.username,
                                votekick.get(message.channel.nick).size(),
                                message.channel.nick));
                    }
                break;
            case "!seal":
                channel.message(String.format(
                        "It has been %s since %s gratuitously abused a Sand Seal. ᶘ ᵒᴥᵒᶅ",
                        timeDifference(database.read(HorseBotDatabase.Tracker.SEALS, channel)),
                        channel.nick
                ));
                break;
            case "!cryosisstrats":
                channel.message("https://www.youtube.com/watch?v=lzykoWSZ8TQ&list=PL7bccJYa7qUVbgbaC0inZOl-cYarjSrZm");
                break;
            case "!panic":
                if (superusers.contains(user.username) || channel.broadcaster.equals(user.username)) {
                    channel.message("The stream! panicBasket The stream! panicBasket The stream is on fire!");
                    channel.message("\uD83D\uDD25\uD83D\uDD25WE DON'T NEED NO WATER. LET THE MOTHERFUCKER BURN\uD83D\uDD25\uD83D\uDD25");
                }
                break;
            case "!fire":
                if (superusers.contains(user.username) || channel.broadcaster.equals(user.username)) {
                    channel.message("The Link! panicBasket The Link! panicBasket The Link is on fire!");
                    channel.message("\uD83D\uDD25\uD83D\uDD25WE\uD83D\uDD25DON'T\uD83D\uDD25NEED\uD83D\uDD25ELIXIRS\uD83D\uDD25\uD83D\uDD25LET\uD83D\uDD25THE\uD83D\uDD25MOTHERFUCKER\uD83D\uDD25BURN\uD83D\uDD25\uD83D\uDD25");
                }
                break;
            case "!points":
                if (user.username.equals("dylrocks17") && (new Date().getTime() & 30L) == 0L) {
                    channel.message("/timeout DylRocks17 " + 60*60*24*7);
                }
                break;
            case "!wr":
                channel.message("!ad, !any%, !amq, !as, !ms, !ad-a, !any%-a, !amq-a, !as-a, !ms-a, !ad-a-na");
                break;
            case "!ad":
                channel.message(RecordLookup.leaderboard(Category.AD));
                break;
            case "!any%":
                channel.message(RecordLookup.leaderboard(Category.Any));
                break;
            case "!amq":
                channel.message(RecordLookup.leaderboard(Category.AMQ));
                break;
            case "!as":
                channel.message(RecordLookup.leaderboard(Category.AS));
                break;
            case "!ms":
                channel.message(RecordLookup.leaderboard(Category.MS));
                break;
            case "!100%":
                channel.message(RecordLookup.leaderboard(Category.HUNDO));
                break;
            case "!ad-a":
                channel.message(RecordLookup.leaderboard(Category.AD, Restriction.Amiiboless));
                break;
            case "!any%-a":
                channel.message(RecordLookup.leaderboard(Category.Any, Restriction.Amiiboless));
                break;
            case "!amq-a":
                channel.message(RecordLookup.leaderboard(Category.AMQ, Restriction.Amiiboless));
                break;
            case "!as-a":
                channel.message(RecordLookup.leaderboard(Category.AS, Restriction.Amiiboless));
                break;
            case "!ms-a":
                channel.message(RecordLookup.leaderboard(Category.MS, Restriction.Amiiboless));
                break;
            case "!ad-a-na":
                channel.message(RecordLookup.leaderboard(Category.AD, Restriction.Amiiboless, Restriction.USA));
                break;
            case "!pb":
                channel.message(RecordLookup.getPBbyName(scanner.hasNext() ? scanner.next() : channel.broadcaster));
                break;
            case "!adventure":
                if (superusers.contains(user.username) || channel.broadcaster.equals(user.username)) {
                    adventurer.beginAdventure(channel);
                }
                break;
            case "!abort":
                if (superusers.contains(user.username) || channel.broadcaster.equals(user.username)) {
                    adventurer.endAdventure(channel);
                }
                break;
            case "!commands":
                channel.message("!info, !complimentstreamer, !horse, !murder, !horsefact \uD83D\uDC0E");
                break;
            case "!info":
                channel.message("HorseBotXD was created by Glitch29. Its code is available at https://github.com/Glitch29/HorseBot/. \uD83D\uDC0E");
                break;
            case "!horsefact":
                if (scanner.hasNextInt()) {
                    channel.message(HorseFact.fact(scanner.nextInt()));
                } else {
                    channel.message(HorseFact.fact());
                }
                break;
            case "!complimentstreamer":
                if (scanner.hasNextInt()) {
                    channel.message(StreamerFact.fact(channel, scanner.nextInt()) + " 4Head");
                } else {
                    channel.message(StreamerFact.fact(channel) + " 4Head");
                }
                break;
            case "!neigh":
                if (superusers.contains(user.username) || channel.broadcaster.equals(user.username)) {
                   channel.message(String.format(
                            "It has been %s since %s was last killed by a horse. BibleThump",
                            timeDifference(database.track(HorseBotDatabase.Tracker.DEATHS, channel)),
                            channel.nick
                    ));
                }
                break;
            case "!d:":
                if (superusers.contains(user.username) || channel.broadcaster.equals(user.username)) {
                    channel.message(String.format(
                            "It has been %s since %s brutally murdered a horse. D:",
                            timeDifference(database.track(HorseBotDatabase.Tracker.MURDERS, channel)),
                            channel.nick
                    ));
                }
                break;
            case "!\uD83D\uDC0E":
                if (superusers.contains(user.username)) {
                    channel.message("\uD83D\uDC0E");
                    channel.message("\uD83D\uDC0E\uD83D\uDC0E");
                    channel.message("\uD83D\uDC0E\uD83D\uDC0E\uD83D\uDC0E");
                    channel.message("\uD83D\uDC0E\uD83D\uDC0E");
                    channel.message("\uD83D\uDC0E");
                } else if (user.username.equals(channel.broadcaster)) {
                    channel.message("\uD83D\uDC0E");
                    channel.message("\uD83D\uDC0E\uD83D\uDC0E");
                    channel.message("\uD83D\uDC0E\uD83D\uDC0E\uD83D\uDC0E");
                    channel.message("\uD83D\uDC0E\uD83D\uDC0E");
                    channel.message("Purchase HorseBotXD Premium to complete this pyramid.");
                } else {
                    user.personalMessage(String.format("/w %s Enter a secret code to unlock this special power.",
                            user));
                }
                break;
            case "!3speed":
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
}
