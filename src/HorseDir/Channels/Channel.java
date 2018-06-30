package HorseDir.Channels;

import HorseDir.Messenger;
import HorseDir.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public class Channel {
    private static final Map<String, Channel> CHANNELS = new HashMap<>();
    private static final Map<Channel, ChannelData> SETTINGS = new HashMap<>();
    private Messenger messenger;
    public String broadcaster;

    private Channel(Messenger messenger, String name) {
        this.messenger = messenger;
        broadcaster = name.substring(1);
    }

    public static Channel get(String name) {
        assert CHANNELS.containsKey(name);
        return CHANNELS.get(name);
    }

    public static void make(String name, Messenger messenger) {
        assert !CHANNELS.containsKey(name);
        Channel channel = new Channel(messenger, name);
        if (channel.settings().isJoined()) {
            messenger.join(channel);
        }
        CHANNELS.put(name, channel);
    }

    public void message(User user, String message, boolean whisper) {
        if (whisper || settings().canChat()) {
            messenger.message(user, message, whisper);
        }
    }

    public void message(String message) {
        message(message, false);
    }

    public void message(String message, boolean asMod) {
        if (message.startsWith("/") ? settings().canMod() : settings().canChat()) {
            messenger.message(this, message, asMod);
        }
    }

    public ChannelData settings() {
        if (!SETTINGS.containsKey(this)) {
            SETTINGS.put(this, readData());
        }
        return SETTINGS.get(this);
    }

    private ChannelData readData() {
        return new ChannelData(broadcaster);
    }

    public String nick() {
        return settings().name();
    }

    public String channelCode() {
        return settings().channel();
    }

    @Override
    public String toString() {
        return nick();
    }
}
