package HorseDir;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public class Channel {
    private static final Map<String, Channel> CHANNELS = new HashMap<>();
    private ChannelData channelData;
    private Messenger messenger;
    String name;
    String broadcaster;
    String nick;

    private Channel(Messenger messenger, String name) {
        this.name = name;
        this.messenger = messenger;
        broadcaster = name.substring(1);
        nick = broadcaster;
    }

    private Channel setNick(String nick) {
        this.nick = nick;
        return this;
    }

    public static Channel get(String name) {
        assert CHANNELS.containsKey(name);
        return CHANNELS.get(name);
    }

    public static void make(String name, String nick, Messenger messenger) {
        assert !CHANNELS.containsKey(name);
        Channel channel = new Channel(messenger, name).setNick(nick);
        messenger.join(channel);
        CHANNELS.put(channel.name, channel);
    }

    public void message(User user, String message, boolean whisper) {
        messenger.message(user, message, whisper);
    }

    public void message(String message) {
        messenger.message(this, message);
    }

    @Override
    public String toString() {
        return name;
    }
}
