package Adventures.Deaths;

import Adventures.Player;
import HorseDir.Channel;
import HorseDir.HorseBotMessenger;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Aaron Fisher on 4/26/2017.
 */
public abstract class AbstractDeath {
    Collection<Player> players;
    private HorseBotMessenger messenger;
    private Channel channel;

    AbstractDeath(Collection<Player> players, HorseBotMessenger messenger, Channel channel) {
        this.players = players;
        this.messenger = messenger;
        this.channel = channel;
    }

    AbstractDeath(Player player, HorseBotMessenger messenger, Channel channel) {
        players = new ArrayList<>();
        players.add(player);
        this.messenger = messenger;
        this.channel = channel;
    }

    public abstract void kill();

    void say(String message) {
        messenger.privmsg(channel,message);
    }

}
