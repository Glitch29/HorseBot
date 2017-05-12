package HorseDir;

import Connections.IrcSession;
import Connections.TwitchIrcSession;
import Accounts.Account;
import Accounts.HorseBotXD;
import MessageLines.Privmsg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Aaron Fisher on 4/20/2017.
 */
public class HorseBot {
    private static final String DIRECTORY = "C:\\Users\\Aaron Fisher\\IdeaProjects\\HorseBot\\src\\HorseLogs\\";
    private static final String CHANNEL_LIST = "ChannelData\\Channels.txt";
    private static Account ACCOUNT = new HorseBotXD();
    private static final List<Channel> CHANNELS = new ArrayList<>();
    private static String LAST_ENDER = "d";
    static {
        try (Scanner scanner = new Scanner(new File(DIRECTORY + CHANNEL_LIST))) {
            while (scanner.hasNext()) {
                CHANNELS.add(Channel.get(scanner.next(), scanner.next()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        IrcSession ircSession = TwitchIrcSession.getTwitchIRC(ACCOUNT);
        HorseBotMessenger messenger = new HorseBotMessenger(ircSession.getWriter());
        HorseBotListener listener = new HorseBotListener(ircSession.getReader());
        HorseBotDatabase database = new HorseBotDatabase(DIRECTORY);
        HorseBotAdventurer adventurer = new HorseBotAdventurer(messenger);
        HorseBotCommander commander = new HorseBotCommander(messenger, database, adventurer);

        // Join the channels.
        for(Channel channel : CHANNELS) {
            messenger.join(channel);
        }

        // Keep reading lines from the server.
        while (listener.readLine() != null) {
            switch (listener.classify()) {
                case PING:
                    messenger.pong(listener.readPing());
                    messenger.privmsg(Channel.get("#spades_live"), "!points");
                    break;
                case PRIVMSG:
                    Privmsg privmsg = listener.readPrivmsg();
                    if (privmsg.body.charAt(0) == '!') {
                        commander.command(privmsg.user, privmsg.channel, privmsg.body);
                    }
                    if (privmsg.user.equals("disbotdoh") && privmsg.body.startsWith("If ")) {
                        messenger.privmsg(privmsg.channel, "Or not. Your choice. \uD83D\uDC0E");
                    }
                    if (privmsg.user.equals("spadespwnzbot") && privmsg.body.contains("if your enjoying")) {
                        messenger.privmsg(privmsg.channel, "http://writingexplained.org/your-vs-youre-difference \uD83D\uDC0E");
                    }
                    if (privmsg.user.equals("spadespwnzbot") && privmsg.body.substring(0, 6).equals("@horse")) {
                        try (Scanner scanner = new Scanner(privmsg.body)) {
                            if (scanner.hasNext()) {
                                System.out.println(scanner.next());
                            }
                            if (scanner.hasNextInt()) {
                                messenger.privmsg(Channel.get("#spades_live"), "!cgss " + (scanner.nextInt() / 40));
                            }
                        }
                    }
                    if (privmsg.user.equals("enderjp")) {
                        if (privmsg.body.equals(LAST_ENDER + LAST_ENDER) || privmsg.body.equals(LAST_ENDER + " " + LAST_ENDER)) {
                            messenger.privmsg(privmsg.channel, "HORSEBOT SAYS NEIGH!");
                        } else if (privmsg.body.startsWith(LAST_ENDER) && privmsg.body.endsWith(LAST_ENDER) && privmsg.body.length() > LAST_ENDER.length()) {
                            messenger.privmsg(privmsg.channel, "Get that weak shit out of here, Ender.");
                        }
                        LAST_ENDER = privmsg.body;
                    }
                    if (privmsg.body.contains("\uD83D\uDC0E")) {
                        adventurer.command(privmsg);
                    }
                    break;
            }
        }
    }
}
