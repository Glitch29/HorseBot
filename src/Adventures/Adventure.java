package Adventures;

import Adventures.Commands.Command;
import Adventures.Deaths.Death;
import Adventures.Locations.AbstractLocation;
import Adventures.Locations.TheTavern;
import Adventures.Players.Hero;
import Adventures.Players.Player;
import HorseDir.Channels.Channel;
import HorseDir.Messenger;
import MessageLines.Message;

import java.util.*;

/**
 * Created by Aaron Fisher on 4/26/2017.
 */
public class Adventure {
    private Channel channel;
    private Map<Player, Hero> playerMap;
    private List<Player> timeOutList;
    private AbstractLocation location;
    private Messenger messenger;
    private boolean ended = false;

    public Adventure (Channel channel, Messenger messenger) {
        this.channel = channel;
        this.messenger = messenger;
        playerMap = new HashMap<>();
        timeOutList = new ArrayList<>();
        location = new TheTavern(this);
    }

    public void command(Message message) {
        if (ended) {
            return;
        }
        Player player = new Player(message.channel, message.user.username);
        if (timeOutList.contains(player)) {
            return;
        }
        if (!playerMap.containsKey(player)) {
            playerMap.put(player, new Hero(player));
        }
        try {
            Command command = Command.valueOf(message.body.substring(2).toUpperCase());
            location.command(playerMap.get(player),command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void advanceLocation(AbstractLocation location) {
        this.location = location;
    }

    public void end() {
        if (ended) {
            return;
        }
        ended = true;
        publicMessage("The adventure has ended. All deceased players have had their chat privileges restored.");
        for (Player player : timeOutList) {
            publicMessage("/unban " + player.username);
        }
    }

    public void publicMessage(String message) {
        messenger.message(channel, message);
    }

    public void whisper(Hero character, String message) {
        messenger.message(channel, "/w " + character.player.username + " " + message);
    }

    public void kill(Death death, Hero... heroes) {
        for (Hero hero : heroes) {
            publicMessage(String.format(death.message, hero));
            publicMessage("/timeout " + hero.player.username);
            whisper(hero, "You are dead. :-(");
            playerMap.remove(hero.player);
            timeOutList.add(hero.player);
        }
    }
}
