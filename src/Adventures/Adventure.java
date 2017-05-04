package Adventures;

import Adventures.Commands.Command;
import Adventures.Deaths.Death;
import Adventures.Locations.AbstractLocation;
import Adventures.Locations.Tavern;
import Adventures.Players.Hero;
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
    private Map<Player, Hero> playerMap;
    private List<Player> timeOutList;
    private AbstractLocation location;
    private HorseBotMessenger messenger;
    private boolean ended = false;

    public Adventure (Channel channel, HorseBotMessenger messenger) {
        this.channel = channel;
        this.messenger = messenger;
        playerMap = new HashMap<>();
        timeOutList = new ArrayList<>();
        location = new Tavern(this);
    }

    public void command(Privmsg message) {
        if (ended) {
            return;
        }
        Player player = new Player(message.channel, message.user);
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
        messenger.privmsg(channel, message);
    }

    public void whisper(Hero character, String message) {
        messenger.privmsg(channel, "/w " + character.player.username + " " + message);
    }

    public void kill(Hero character, Death death) {
        publicMessage(String.format(death.message, character) + " AngelThump");
        publicMessage("/timeout " + character.player.username);
        whisper(character, "You are dead. :-(");
        playerMap.remove(character.player);
        timeOutList.add(character.player);
    }
}
