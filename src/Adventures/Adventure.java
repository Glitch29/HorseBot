package Adventures;

import Adventures.Commands.Command;
import Adventures.Locations.AbstractLocation;
import Adventures.Locations.Tavern;
import Adventures.Players.AdvCharacter;
import Adventures.Players.Player;
import HorseDir.Channel;
import HorseDir.HorseBotMessenger;
import MessageLines.Privmsg;

import java.util.*;

/**
 * Created by Aaron Fisher on 4/26/2017.
 */
public class Adventure {
    private Channel channel;
    private long endTime;
    private Map<Player, AdvCharacter> playerMap;
    private List<Player> playerList;
    private AbstractLocation location;
    private HorseBotMessenger messenger;

    public Adventure (Channel channel, int minutes, HorseBotMessenger messenger) {
        this.channel = channel;
        this.endTime = new Date().getTime() + (minutes * 60 * 1000);
        this.messenger = messenger;
        playerMap = new HashMap<>();
        playerList = new ArrayList<>();
        location = new Tavern(this);
    }

    public void command(Privmsg message) {
        Player player = new Player(message.channel, message.user);
        if (!playerMap.containsKey(player)) {
            playerMap.put(player, new AdvCharacter(player));
        }
        try {
            Command command = Command.valueOf(message.body.substring(2));
            location.command(playerMap.get(player),command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void advanceLocation(AbstractLocation location) {
//        this.location.embark()
//
//    }

    public void end() {
        publicMessage("RIP adventure.");
    }

    public void publicMessage(String message) {
        messenger.privmsg(channel, message);
    }
}
