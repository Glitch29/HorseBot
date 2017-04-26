package HorseDir;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public class Channel {
    String channel;
    String name;
    String nick;

    public Channel(String channel) {
        this.channel = channel;
        name = channel.substring(1);
        nick = name;
    }

    public Channel(String channel, String nick) {
        this.channel = channel;
        name = channel.substring(1);
        this.nick = nick;
    }

    @Override
    public String toString() {
        return channel;
    }
}
