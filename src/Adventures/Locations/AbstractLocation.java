package Adventures.Locations;

import Adventures.Player;
import HorseDir.Channel;
import HorseDir.HorseBotMessenger;

import java.util.Collection;
import java.util.Date;

/**
 * Created by Aaron Fisher on 4/26/2017.
 */
public abstract class AbstractLocation {
    static final String HORSE = "\uD83D\uDC0E";
    private HorseBotMessenger messenger;
    private Channel channel;
    private long endTime;

    AbstractLocation(Channel channel, HorseBotMessenger messenger, long endTime) {
        this.channel = channel;
        this.messenger = messenger;
        this.endTime = endTime;
    }

    public abstract void beginLocation(Collection<Player> players);
    public abstract boolean canAddPlayer();
    public abstract void playerCommand(Player player, int button);
    public abstract void endLocation(AbstractLocation next);
    public abstract String name();

    int secondsLeft() {
        return (int) ((endTime - new Date().getTime()) / 1000);
    }

    void say(String message) {
        messenger.privmsg(channel,message);
    }

    String players() {
        for ()
    }
}
