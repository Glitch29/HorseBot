package HorseDir.Channels;

import HorseDir.Messenger;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
    private ChannelType channelType = ChannelType.TWITCH;

    public ChannelData(String broadcaster) {
        this.broadcaster = broadcaster;
        if (!loadFromFile(broadcaster)) {
            boolean loaded = loadFromFile("DEFAULT");
            assert loaded;
        }
    }

    private boolean loadFromFile(String broadcaster) {
        try (FileReader fileReader = new FileReader(new File(String.format(CHANNEL_DATA_FORMAT, broadcaster)))) {
            JSONObject settings = new JSONObject(fileReader);
            nick = getString(settings, SettingName.NICK);
            mod = getBool(settings, SettingName.MOD);
            chat = getBool(settings, SettingName.CHAT);
            logged = getBool(settings, SettingName.LOGGED);
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
        LOGGED ("logged");

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
