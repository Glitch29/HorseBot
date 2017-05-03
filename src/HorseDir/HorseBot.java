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
                    break;
                case PRIVMSG:
                    Privmsg privmsg = listener.readPrivmsg();
                    if (privmsg.body.charAt(0) == '!') {
                        commander.command(privmsg.user, privmsg.channel, privmsg.body);
                    }
                    if (privmsg.body.contains("\uD83D\uDC0E")) {
                        adventurer.command(privmsg);
                    }
                    break;
            }
        }
    }
}
