package Adventures;

import Adventures.Deaths.AbstractDeath;
import Adventures.Deaths.EmptyTavern;
import Adventures.Locations.AbstractLocation;
import Adventures.Locations.Tavern;
import HorseDir.Channel;
import HorseDir.HorseBotMessenger;

import java.util.*;

/**
 * Created by Aaron Fisher on 4/26/2017.
 */
public class Adventure {
    private Channel channel;
    private long endTime;
    private Map<String, Player> playerMap;
    private List<Player> playerList;
    private AbstractLocation location;
    private HorseBotMessenger messenger;

    public Adventure (Channel channel, int minutes, HorseBotMessenger messenger) {
        this.channel = channel;
        this.endTime = new Date().getTime() + (minutes * 60 * 1000);
        this.messenger = messenger;
        playerMap = new HashMap<>();
        playerList = new ArrayList<>();
        location = new Tavern(channel, messenger, new Date().getTime() + 30L * 1000L);
        location.beginLocation(playerList);
    }

    public void command(Channel channel, String user, String body) {
        if (!playerMap.containsKey(channel.toString() + user)) {
            Player newPlayer = new Player(user);
            playerMap.put(channel.toString() + user, newPlayer);
            if (location.canAddPlayer()) {
                playerList.add(newPlayer);
            } else {
                new EmptyTavern(newPlayer, messenger, channel).kill();
            }
        }

    }

    public void advance() {

    }

    private int translate(String body) {
        switch ()
    }
}
