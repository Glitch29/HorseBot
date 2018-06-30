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
    private String commands;
    private String wrCommands;
    private long nextTweet = new Date().getTime();
    private static long tweetDelay = 3L*60L*1000L;
    private HorseBotDatabase database;
    private HorseBotAdventurer adventurer;
    private Map<String, Set<String>> votekick = new HashMap<>();
    private Set<String> superusers;
    private Map<String, Event> eventMap;
    private Map<String, Event> resetMap;
    private Map<String, Category> recordMap;
    private Map<String, Restriction> restrictionMap;

    public HorseBotCommander(HorseBotDatabase database, HorseBotAdventurer adventurer) {
        this.database = database;
        this.adventurer = adventurer;
        superusers = new HashSet<>();
        superusers.add("glitch29");
        superusers.add("jerrytheret");
        superusers.add("acearinos");
        resetMap = new HashMap<>();
        eventMap = new HashMap<>();
        commands = "";
        for (Event event : Event.values()) {
            resetMap.put(event.emoji.toLowerCase(), event);
            eventMap.put(event.name.toLowerCase(), event);
            commands = commands + " !" + event.name;
        }
        recordMap = new HashMap<>();
        wrCommands = "";
        for (Category category : Category.values()) {
            recordMap.put(category.command.toLowerCase(), category);
            wrCommands = wrCommands + " !" + category.command;
        }
        restrictionMap = new HashMap<>();
        wrCommands = wrCommands + " |";
        for (Restriction restriction : Restriction.values()) {
            restrictionMap.put("-" + restriction.command, restriction);
            wrCommands = wrCommands + " -" + restriction.command;
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
            channel.message(event.read(database, message.channel));
        }
        if (eventMap.containsKey(command)) {
            Event event = eventMap.get(command);
            if (scanner.hasNext()) {
                String modifier = scanner.next();
                if (modifier.equals("any")) {
                    try {
                        Channel targetChannel = Channel.get(database.latestKey(event.tracker));
                        channel.message(event.read(database, targetChannel));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Channel targetChannel = Channel.get("#" + modifier);
                        channel.message(event.read(database, targetChannel));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                channel.message(event.read(database, message.channel));
            }
        }
        if (recordMap.containsKey(command)) {
            List<Restriction> restrictions = new ArrayList<>();
            while (scanner.hasNext()) {
                String next = scanner.next();
                if (restrictionMap.containsKey(next)) {
                    restrictions.add(restrictionMap.get(next));
                }
            }
            channel.message(RecordLookup.leaderboard(recordMap.get(command), restrictions.toArray(new Restriction[restrictions.size()])));
        }
        switch (command) {
            case "unsmite":
                if (user.username.equals("glitch29")) {
                    channel.message("/unban" + scanner.nextLine());
                }
                break;
            case "smite":
                if (user.username.equals("glitch29")) {
                    channel.message("/ban" + scanner.nextLine());
                }
                break;
            case "race":
                if (channel.broadcaster.equals("spades_live")) {
                    channel.message("\uD83C\uDFC3 Watch Ace and Spoodles try to get the better Plateau at race.spoodles.net \uD83C\uDFC3");
                }
                break;
            case "rupees":
                user.directedMessage("tried to check their rupees, but failed. HorseBot has taken them all. \uD83D\uDC0E");
                break;
            case "16urns":
                channel.message("Some people say a run is made outta' gud \uD83C\uDFB5");
                channel.message("A poor run's made outta' mashing and blood \uD83C\uDFB5");
                channel.message("Mashing and blood and controllers thrown \uD83C\uDFB5");
                channel.message("A mind that's a-slippin' and a rage that's strong \uD83C\uDFB6");
                channel.message("You do sixteen runs, what's the result? \uD83C\uDFB5");
                channel.message("Another day older and deeper in salt \uD83C\uDFB5");
                channel.message("Sweet Nature don't you call me 'cause I can't go \uD83C\uDFB5");
                channel.message("I'm stuck on this chair 'til the run's over \uD83C\uDFB6");
                break;
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
                channel.message("Hot new" + scanner.nextLine() + " strats: http://i.imgur.com/WTSMSxG.jpg \uD83D\uDD25\uD83D\uDD25");
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
                            channel.message(next);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "announce":
                if (superusers.contains(user.username)) {
                    try {
                        if (scanner.hasNext()) {
                            String next = scanner.next().toLowerCase();
                            Channel believe = Channel.get("#" + next);
                            believe.message("/me " +
                                        scanner.nextLine() +
                                        " \uD83D\uDC0E"
                                    );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "lightsout":
                if (superusers.contains(user.username)) {
                    channel.message("From the starting point:");
                    channel.message("[***]");
                    channel.message("[** ]");
                    channel.message("[ * ]");
                    channel.message("Press   the following buttons to turn off all nodes:");
                    channel.message("[*  ]");
                    channel.message("[  *]");
                    channel.message("[  *]");
                } else {
                    channel.message("LuL git gud");
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
                if (user.username.equals("mokmoon123") || user.equals("savevmk")) {
                    user.timeout(15);
                }
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
                        if (t.equals("mokmoon123") && votekick.get(t).size() % 7 == 0) {
                            new User(channel, "mokmoon123").timeout(180);
                        }
                        if (t.equals("savevmk") && votekick.get(t).size() % 7 == 0) {
                            new User(channel, "savevmk").timeout(180);
                        }
                    }
                }
                break;
            case "vote":
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
                        channel.message(String.format("%s now has %d votes.",
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
            case "challenge":
                channel.message("Uggg... Clear every single enemy or clear... defeat 71 bokoblins, 1 talus, 1 lynel before activating the tower. Alr... or do we want to include the flying ones? Do we want to include that?");
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
                    user.timeout(60*60*24*7);
                }
                break;
            case "wr":
                channel.message("WR Commands:" + wrCommands);
                break;
            case "pb":
                if (scanner.hasNext()) {
                    channel.message(RecordLookup.getPBbyName(scanner.next()));
                } else {
                    channel.message(RecordLookup.getPBbyTwitch(channel.broadcaster));
                }
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
                channel.message("!info, !complimentstreamer, !horsefact \uD83D\uDC0E " + commands + " \uD83D\uDC0E");
                break;
            case "lotm":
                channel.message("https://clips.twitch.tv/ResilientSeductiveStarPunchTrees");
                break;
            case "info":
                channel.message("HorseBotXD tweets at twitter.com/HorseBotXD/.  Its code is available at https://github.com/Glitch29/HorseBot/. \uD83D\uDC0E");
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
            case "rules":
                if (scanner.hasNextLine() && scanner.nextLine().toLowerCase().contains("apple")) {
                    channel.message("100 Baked Apples RTA rules: https://pastebin.com/wwkx105i");
                }
                break;
            case "mario":
                channel.message("https://clips.twitch.tv/ToughWiseGarageDansGame");
                break;
            case "100ba":
            case "apples":
            case "100apples":
                channel.message("The best time for The Legend of Zelda: Breath of the Wild, 100 Baked Apples RTA is 46:39 by Glitch29.");
                break;
            case "trueheroes":
                channel.message("See the true heroes of Breath of the Wild at horse.spoodles.net  \uD83D\uDC0E");
                break;
            case "about":
                String next = scanner.nextLine().toLowerCase().substring(1);
                if (next.contains("shadowverse")) {
                    channel.message("Shadowverse is a free-to-play collectible card video game developed and " +
                            "published by Cygames. It was released for iOS and Android devices in June 2016, and " +
                            "it is a well regarded cure for insomnia or overcrowded streams. ResidentSleeper");
                } else if (next.contains("apple")) {
                    channel.message("100 Baked Apples RTA = best category NA.");
                } else if (next.contains("horse")) {
                    channel.message("\uD83D\uDC0E is love. \uD83D\uDC0E is life.");
                } else {
                    channel.message("Who really cares about " + next + "?");
                }
                break;
            case "addclip":
            case "tweet":
                if (user.username.equals("glitch29") || new Date().getTime() > nextTweet) {
                    HorseBotTweets.sendTweet(scanner.nextLine().substring(1));
                    nextTweet = new Date().getTime() + tweetDelay;
                }
                break;
            case "events":
                channel.message("Check out these upcoming events:");
                channel.message("twitch.tv/jgiga 100 Baked Apples RTA, Time: IDK");
                channel.message("live.spoodles.net Drunk 100 Baked Apples RTA, Time: 750 Followers");
                channel.message("twitch.tv/glitch29 Painting with Bob Ross, Time: June 10th, 6:00 PST");
                break;
            case "facepalm":
                channel.message("http://i.imgur.com/yxTTY7m.png");
                break;
        }
        scanner.close();
    }

    private static String timeDifference(long start, long end) {
        long seconds = (end - start) / 1000L;
        long minutes = seconds / 60L;
        long hours = minutes / 60L;
        long days = hours / 24L;

        return ((days > 0 ? days + " days, " : "")
                + (hours > 0 ? (hours % 24) + " hours, " : "")
                + (minutes > 0 ? (minutes % 60) + " minutes, and " : "")
                + (seconds % 60) + " seconds");
    }

    private static String timeDifference(long start) {
        return timeDifference(start, new Date().getTime());
    }

    private enum Event {
        HORSE (Tracker.DEATHS, "was last killed by a horse.", "\uD83D\uDC0E", "horse"),
        BEAST (Tracker.BEAST, "got dunked on by Beast Ganon.", "\uD83D\uDE08", "beast"),
        MURDER (Tracker.MURDERS, "brutally murdered a horse.", "D:", "murder"),
        RIVER (Tracker.ICERIVER, "fell into a river and drowned.", "⛄", "river"),
        PETA (Tracker.PETA, "was protested by PETA.", "ᶘ'ᵒᴥᵒᶅ", "peta"),
        EXPOSURE (Tracker.EXPOSURE, "died to exposure.", "\uD83C\uDF1E", "exposure"),
        SPIDER (Tracker.SPIDER, "died to Calamity Ganon.", "\uD83D\uDD77", "spider"),
        CACTUS (Tracker.CACTUS, "bombed a cactus at point blank range.", "\uD83C\uDF35", "cactus"),
        AMIIBO (Tracker.AMIIBO, "was crushed to death by his own Amiibo crate.", "\uD83D\uDE02", "amiibo");

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

        private String read(HorseBotDatabase database, Channel channel) {
            HorseBotDatabase.LongWithNotes data = database.read(tracker, channel);
            if (data == null) {
                return "";
            }
            if (data.notes == null || data.notes.equals("")) {
                data.notes = "!addclip " + data.date;
            }
            return String.format(
                    PREFIX + message + POSTFIX,
                    timeDifference(data.date),
                    channel.nick(),
                    emoji,
                    data.notes);
        }

        private void write(HorseBotDatabase database, Channel channel) {
            database.track(tracker, channel);
        }
    }
}
