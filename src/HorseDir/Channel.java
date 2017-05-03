package HorseDir;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public class Channel {
    private static final Map<String, Channel> CHANNELS = new HashMap<>();
    String name;
    String broadcaster;
    String nick;

    private Channel(String name) {
        this.name = name;
        broadcaster = name.substring(1);
        nick = broadcaster;
    }

    private Channel setNick(String nick) {
        this.nick = nick;
        return this;
    }

    public static Channel get(String name) {
        if (CHANNELS.containsKey(name)) {
            return CHANNELS.get(name);
        }
        Channel channel = new Channel(name);
        CHANNELS.put(channel.name, channel);
        return channel;
    }

    public static Channel get(String name, String nick) {
        return get(name).setNick(nick);
    }

    @Override
    public String toString() {
        return name;
    }
}
