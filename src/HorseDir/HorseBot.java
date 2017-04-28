package HorseDir;

import Connections.IrcSession;
import Connections.TwitchIrcSession;
import Accounts.Account;
import Accounts.HorseBotXD;
import MessageLines.Privmsg;

import java.util.*;

/**
 * Created by Aaron Fisher on 4/20/2017.
 */
public class HorseBot {
    private static Account ACCOUNT = new HorseBotXD();
    private static final List<Channel> CHANNELS = new ArrayList<>(Arrays.asList(
            new Channel("#jgiga", "Jgiga"),
            new Channel("#loloup", "Lolo"),
            new Channel("#glitch29", "Glitch"),
            new Channel("#jerrytheret","Jerry"),
            new Channel("#detroph", "Detroph")
    ));

    public static void main(String[] args) throws Exception {

        IrcSession ircSession = TwitchIrcSession.getTwitchIRC(ACCOUNT);
        HorseBotMessenger messenger = new HorseBotMessenger(ircSession.getWriter());
        HorseBotListener listener = new HorseBotListener(ircSession.getReader(), CHANNELS);
        HorseBotCommander commander = new HorseBotCommander(messenger);

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
                    break;
            }
        }
    }
}
