package HorseDir.Channels;

import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Aaron Fisher on 5/12/2017.
 */
public class ChannelData {
    private static final String CHANNEL_DATA_DIRECTORY = "C:\\Users\\Aaron Fisher\\IdeaProjects\\HorseBot\\src\\HorseLogs\\ChannelData\\";
    private static final String CHANNEL_DATA_FORMAT = CHANNEL_DATA_DIRECTORY + "%s.txt";
    private String broadcaster;
    private String nick;
    private boolean chat;
    private boolean mod;
    private boolean logged;
    private boolean joined;
    private ChannelType channelType = ChannelType.TWITCH;

    public ChannelData(String broadcaster) {
        this.broadcaster = broadcaster;
        if (!loadFromFile(broadcaster)) {
            boolean loaded = loadFromFile("DEFAULT");
            assert loaded;
        }
    }

    private boolean loadFromFile(String broadcaster) {
        try {
            String file = new String(Files.readAllBytes(Paths.get(String.format(CHANNEL_DATA_FORMAT, broadcaster))));
            JSONObject settings = new JSONObject(file);
            nick = getString(settings, SettingName.NICK);
            mod = getBool(settings, SettingName.MOD);
            chat = getBool(settings, SettingName.CHAT);
            logged = getBool(settings, SettingName.LOGGED);
            joined = getBool(settings, SettingName.JOINED);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String channel() {
        return "#" + broadcaster;
    }

    public String broadcaster() {
        return broadcaster;
    }

    public String name() {
        return nick == null ? broadcaster : nick;
    }

    public boolean canMod() {
        return mod;
    }

    public boolean canChat() {
        return chat;
    }

    public boolean isLogged() {
        return logged;
    }

    public boolean isJoined() {
        return joined;
    }

    private static String getString(JSONObject jsonObject, SettingName key) {
        if (!jsonObject.has(key.toString())) {
            return null;
        }
        return jsonObject.getString(key.toString());
    }

    private static boolean getBool(JSONObject jsonObject, SettingName key) {
        return jsonObject.has(key.toString()) && jsonObject.getBoolean(key.toString());
    }

    @Override
    public String toString() {
        return name();
    }

    private enum SettingName {
        NICK ("nick"),
        MOD ("mod"),
        CHAT ("chat"),
        LOGGED ("logged"),
        JOINED ("join");

        private String name;

        SettingName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
