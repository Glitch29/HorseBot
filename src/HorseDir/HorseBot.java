package HorseDir;

import Connections.IrcSession;
import Connections.TwitchIrcSession;
import Accounts.Account;
import Accounts.HorseBotXD;
import MessageLines.Message;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Aaron Fisher on 4/20/2017.
 */
public class HorseBot {
    private static Integer SPOODLES_POINTS;
    private static int SPOODLES_BET = 0;
    private static final long SPOODLES_DELAY = 1000L * 65L;
    private static long NEXT_SPOODLES = setNextSpoodles();
    private static final String DIRECTORY = "C:\\Users\\Aaron Fisher\\IdeaProjects\\HorseBot\\src\\HorseLogs\\";
    private static final String CHANNEL_LIST = "ChannelData\\Channels.txt";
    private static Account ACCOUNT = new HorseBotXD();
    private static String LAST_ENDER = "d";

    public static void main(String[] args) throws Exception {
        SPOODLES_POINTS = null;

        IrcSession ircSession = TwitchIrcSession.getTwitchIRC(ACCOUNT);
        Messenger messenger = new TwitchMessenger(ircSession.getWriter(), ircSession.getReader());
        HorseBotDatabase database = new HorseBotDatabase(DIRECTORY);
        HorseBotAdventurer adventurer = new HorseBotAdventurer(messenger);
        HorseBotCommander commander = new HorseBotCommander(database, adventurer);
        try (Scanner scanner = new Scanner(new File(DIRECTORY + CHANNEL_LIST))) {
            while (scanner.hasNext()) {
                Channel.make(scanner.next(), scanner.next(), messenger);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Keep reading lines from the server.
        Message message = null;
        while ((message = messenger.nextMessage()) != null) {
            if (message.body.charAt(0) == '!') {
                commander.command(message);
            }
            if (message.user.username.equals("disbotdoh") && message.body.startsWith("If ")) {
                messenger.message(message.channel, "Or not. Your choice. \uD83D\uDC0E");
            }
            if (message.user.username.equals("spadespwnzbot") && (
                    message.body.contains("if your enjoying")) ||
                    message.body.contains(" your dead")) {
                messenger.message(message.channel, "http://writingexplained.org/your-vs-youre-difference \uD83D\uDC0E");
            }
            if (message.user.username.equals("spadespwnzbot") && message.body.startsWith("@horsebotxd ") && message.body.endsWith(" points.")) {
                try (Scanner scanner = new Scanner(message.body)) {
                    if (scanner.hasNext()) {
                        System.out.println(scanner.next());
                    }
                    if (scanner.hasNextInt()) {
                        SPOODLES_POINTS = scanner.nextInt();
                        SPOODLES_BET = SPOODLES_POINTS / 40;
                        SPOODLES_POINTS -= SPOODLES_BET;
                        messenger.message(Channel.get("#spades_live"), "!cgss " + (SPOODLES_BET));
                    }
                }
            }
            if (message.user.username.equals("spadespwnzbot") && message.body.startsWith("@horsebotxd You do a perfect CGSS")) {
                SPOODLES_POINTS += SPOODLES_BET * 15;
            }
            if (message.user.username.equals("enderjp")) {
                if (message.body.equals(LAST_ENDER + LAST_ENDER) || message.body.equals(LAST_ENDER + " " + LAST_ENDER)) {
                    messenger.message(message.channel, "HORSEBOT SAYS NEIGH!");
                } else if (message.body.startsWith(LAST_ENDER) && message.body.endsWith(LAST_ENDER) && message.body.length() > LAST_ENDER.length()) {
                    messenger.message(message.channel, "Get that weak shit out of here, Ender.");
                }
                LAST_ENDER = message.body;
            }
            if (message.body.contains("\uD83D\uDC0E")) {
                adventurer.command(message);
            }
            if (new Date().getTime() > NEXT_SPOODLES) {
                NEXT_SPOODLES = setNextSpoodles();
                if (SPOODLES_POINTS == null) {
                    messenger.message(Channel.get("#spades_live"), "!points");
                } else {
                    SPOODLES_BET = SPOODLES_POINTS / 40;
                    SPOODLES_POINTS -= SPOODLES_BET;
                    messenger.message(Channel.get("#spades_live"), "!cgss " + (SPOODLES_BET));
                }
            }
        }
    }

    private static long setNextSpoodles() {
        return new Date().getTime() + SPOODLES_DELAY;
    }
}
