package HorseDir;

import HorseDir.Channels.Channel;
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
        superusers.add("jerrytheret");
    }

    public void command(Message message) {
        Scanner scanner = new Scanner(message.body);
        database.log(String.format("%s %d %s %s",
                message.channel.name(),
                new Date().getTime(),
                message.user,
                message.body));
        Channel channel = message.channel;
        User user = message.user;

        switch (scanner.next().toLowerCase()) {
            case "!❄":
                Event.CRYOSIS.write(database, channel);
            case "!cryosis":
                Event.CRYOSIS.read(database, channel);
                break;
            case "!nopyramid":
                Event.PYRAMID.write(database, channel);
            case "!pyramid":
                Event.PYRAMID.read(database, channel);
                break;
            case "!\uD83C\uDF35":
                Event.CACTUS.write(database, channel);
            case "!cactus":
                Event.CACTUS.read(database, channel);
                break;
            case "!⛄":
                Event.RIVER.write(database, channel);
            case "!river":
                Event.RIVER.read(database, channel);
                break;
            case "!neigh":
                Event.HORSE.write(database, channel);
            case "!horse":
                Event.HORSE.read(database, channel);
                break;
            case "!d:":
                Event.MURDER.write(database, channel);
            case "!murder":
                Event.MURDER.read(database, channel);
                break;
            case "!notlikeseal":
                Event.SEAL.write(database, channel);
            case "!seal":
                Event.SEAL.read(database, channel);
                break;
            case "!bokostrats":
                channel.message("\uD83D\uDD25\uD83D\uDD25 https://clips.twitch.tv/JollySourMomKreygasm \uD83D\uDD25\uD83D\uDD25");
                break;
            case "!strats":
                channel.message("Visual strat: http://i.imgur.com/WTSMSxG.jpg \uD83D\uDD25\uD83D\uDD25");
                break;
            case "!anyhorse":
                try {
                    Channel targetChannel = Channel.get(database.latestKey(HorseBotDatabase.Tracker.DEATHS));
                    HorseBotDatabase.LongWithNotes data = database.read(HorseBotDatabase.Tracker.DEATHS, targetChannel);
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
            case "!anymurder":
                try {
                    Channel targetChannel = Channel.get(database.latestKey(HorseBotDatabase.Tracker.MURDERS));
                    HorseBotDatabase.LongWithNotes data = database.read(HorseBotDatabase.Tracker.MURDERS, targetChannel);
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
            case "!!":
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
            case "!announce":
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
            case "!believe":
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
            case "!try":
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
            case "!magnesisstrats":
                channel.message("https://clips.twitch.tv/PlumpImpossibleChoughGOWSkull");
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
            case "!channel":
            case "!energy":
                    if (!votekick.containsKey(message.channel.name())) {
                        votekick.put(message.channel.name(), new HashSet<>());
                    }
                    if (votekick.get(message.channel.name()).add(user.username)) {
                        channel.message(String.format("%s is channeling his energy. %d people believe in you, %s!",
                                message.user.username,
                                votekick.get(message.channel.name()).size(),
                                message.channel.name()));
                    }
                break;
            case "!panic":
                if (superusers.contains(user.username) || channel.broadcaster.equals(user.username)) {
                    channel.message("The stream! panicBasket The stream! panicBasket The stream is on fire!");
                    channel.message("\uD83D\uDD25\uD83D\uDD25WE\uD83D\uDD25DON'T\uD83D\uDD25NEED\uD83D\uDD25NO\uD83D\uDD25WATER\uD83D\uDD25\uD83D\uDD25LET\uD83D\uDD25THE\uD83D\uDD25MOTHERFUCKER\uD83D\uDD25BURN\uD83D\uDD25\uD83D\uDD25");
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
            case "!\uD83D\uDC0E":
                if (superusers.contains(user.username) || (user.username.equals(channel.broadcaster))) {
                    channel.message("\uD83D\uDC0E");
                    channel.message("\uD83D\uDC0E\uD83D\uDC0E");
                    channel.message("\uD83D\uDC0E\uD83D\uDC0E\uD83D\uDC0E");
                    channel.message("\uD83D\uDC0E\uD83D\uDC0E");
                }
                if (user.username.equals(channel.broadcaster)) {
                    channel.message("Purchase HorseBotXD Premium to complete this pyramid.");
                } else if (superusers.contains(user.username)) {
                    channel.message("\uD83D\uDC0E");
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

    private enum Event {
        HORSE (HorseBotDatabase.Tracker.DEATHS, "It has been %s since %s was last killed by a horse. BibleThump %s"),
        MURDER (HorseBotDatabase.Tracker.MURDERS, "It has been %s since %s brutally murdered a horse. D: %s"),
        RIVER (HorseBotDatabase.Tracker.ICERIVER, "It has been %s since %s fell into a river and drowned. ⛄ %s"),
        SEAL (HorseBotDatabase.Tracker.SEALS, "It has been %s since %s gratuitously abused a Sand Seal. ᶘ ᵒᴥᵒᶅ"),
        CRYOSIS (HorseBotDatabase.Tracker.CRYOSIS, "It has been %s since %s last nailed the skip in Cryosis during a run. ❄"),
        PYRAMID (HorseBotDatabase.Tracker.COUNTER, "It has been %s since %s forgot to put a counter on his Pyramid of the Pantheon. \uD83D\uDD3A %s"),
        CACTUS (HorseBotDatabase.Tracker.CACTUS, "It has been %s since %s bombed a cactus at point blank range. \uD83C\uDF35 %s");

        String message;
        HorseBotDatabase.Tracker tracker;

        Event(HorseBotDatabase.Tracker tracker, String message) {
            this.tracker = tracker;
            this.message = message;
        }

        private void read(HorseBotDatabase database, Channel channel) {
            HorseBotDatabase.LongWithNotes data = database.read(tracker, channel);
            if (data == null) {
                return;
            }
            channel.message(String.format(
                    message,
                    timeDifference(data.date),
                    channel.name(),
                    data.notes));
        }

        private void write(HorseBotDatabase database, Channel channel) {
            database.track(tracker, channel);
        }
    }
}
