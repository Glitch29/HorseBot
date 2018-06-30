package HorseDir;

import Connections.IrcSession;
import Connections.TwitchIrcSession;
import Accounts.Account;
import Accounts.HorseBotXD;
import HorseDir.Channels.Channel;
import MessageLines.TwitchMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Aaron Fisher on 4/20/2017.
 */
public class HorseBot {
    private static int SPOODLES_BET = 0;
    private static final int SPOODLES_GOAL = 1000000000;
    private static final long SPOODLES_DELAY = 1000L * 650000L;
    private static long NEXT_SPOODLES = setNextSpoodles();
    private static final String DIRECTORY = "C:\\Users\\Aaron Fisher\\IdeaProjects\\HorseBot\\src\\HorseLogs\\";
    private static final String CHANNEL_LIST = "ChannelData\\Channels.txt";
    private static Account ACCOUNT = new HorseBotXD();

    public static void main(String[] args) throws Exception {
        Integer SPOODLES_POINTS = null;

        IrcSession ircSession = TwitchIrcSession.getTwitchIRC(ACCOUNT);
        Messenger messenger = new TwitchMessenger(ircSession.getWriter(), ircSession.getReader());
        HorseBotDatabase database = new HorseBotDatabase(DIRECTORY);
        HorseBotAdventurer adventurer = new HorseBotAdventurer(messenger);
        HorseBotCommander commander = new HorseBotCommander(database, adventurer);
        try (Scanner scanner = new Scanner(new File(DIRECTORY + CHANNEL_LIST))) {
            while (scanner.hasNext()) {
                Channel.make(scanner.next(), messenger);
                scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Keep reading lines from the server.
        TwitchMessage message;
        while (true) {
            do {
                message = messenger.nextMessage();
            } while (message == null);
            if (message.channel.settings().isLogged()) {
                System.out.println(String.format(">>> %s %s %s",
                        message.user.username,
                        message.channel.channelCode(),
                        message.body));
            }
            if (message.body.charAt(0) == '!') {
                commander.command(message);
            }
            if (message.user.username.equals("disbotdoh") && message.body.startsWith("If ")) {
                messenger.message(message.channel, "Or not. Your choice. \uD83D\uDC0E", false);
            }
            if (message.user.username.equals("spadespwnzbot") && (
                    message.body.contains("if your enjoying")) ||
                    message.body.contains(" your dead")) {
                messenger.message(message.channel, "http://writingexplained.org/your-vs-youre-difference \uD83D\uDC0E", false);
            }
            if (message.user.username.equals("spadespwnzbot") && message.body.startsWith("@horsebotxd ") && message.body.endsWith(" points.")) {
                try (Scanner scanner = new Scanner(message.body)) {
                    if (scanner.hasNext()) {
                        System.out.println(scanner.next());
                    }
                    if (scanner.hasNextInt()) {
                        SPOODLES_POINTS = scanner.nextInt();
                        SPOODLES_BET = 2 * SPOODLES_POINTS / 77;
                        SPOODLES_POINTS -= SPOODLES_BET;
                        messenger.message(Channel.get("#spades_live"), "!cgss " + (SPOODLES_BET), true);
                    }
                }
            }
            if (message.user.username.equals("spadespwnzbot") && message.body.startsWith("@horsebotxd You do a perfect CGSS")) {
                SPOODLES_POINTS += SPOODLES_BET * 15;
            }
            if (message.body.contains("\uD83D\uDC0E")) {
                adventurer.command(message);
            }
            if (new Date().getTime() > NEXT_SPOODLES) {
                NEXT_SPOODLES = setNextSpoodles();
                if (SPOODLES_POINTS == null) {
                    messenger.message(Channel.get("#spades_live"), "!points", true);
                } else {
                    SPOODLES_BET = Math.min((13 + SPOODLES_GOAL - SPOODLES_POINTS) / 14, SPOODLES_POINTS / 40);
                    SPOODLES_POINTS -= SPOODLES_BET;
                    messenger.message(Channel.get("#spades_live"), "!cgss " + SPOODLES_BET, true);
                }
            }
        }
    }

    private static long setNextSpoodles() {
        return new Date().getTime() + SPOODLES_DELAY;
    }
}
