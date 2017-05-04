package HorseDir;

import Adventures.Adventure;
import MessageLines.Privmsg;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aaron Fisher on 5/1/2017.
 */
public class HorseBotAdventurer {
    private Map<Channel, Adventure> adventureMap;
    private HorseBotMessenger messenger;

    public HorseBotAdventurer(HorseBotMessenger messenger) {
        this.messenger = messenger;
        adventureMap = new HashMap<>();
    }

    public void beginAdventure(Channel channel) {
        endAdventure(channel);
        adventureMap.put(channel, new Adventure(channel, messenger));
    }

    public void endAdventure(Channel channel) {
        if (adventureMap.containsKey(channel)) {
            adventureMap.get(channel).end();
            adventureMap.remove(channel);
        }
    }

    public void command(Privmsg message) {
        if (adventureMap.containsKey(message.channel)) {
            adventureMap.get(message.channel).command(message);
        }
    }
}
