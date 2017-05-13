package HorseDir;

import java.util.*;

/**
 * Created by Aaron Fisher on 5/12/2017.
 */
public class ChannelData {
    private String channel;
    private String broadcaster;
    private String nick;
    private Map<String, String> properties;

    public ChannelData(String channel) {
        this.channel = channel;
        broadcaster = channel.substring(1);
        nick = broadcaster;
        properties = new HashMap<>();
    }

    public String getBroadcaster() {
        return broadcaster;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public String addProperty(String key, String value) {
        return properties.put(key, value);
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    @Override
    public String toString() {
        return nick;
    }
}
