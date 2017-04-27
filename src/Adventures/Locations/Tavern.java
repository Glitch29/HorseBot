package Adventures.Locations;

import Adventures.Player;
import HorseDir.Channel;
import HorseDir.HorseBotMessenger;

import java.util.Collection;

/**
 * Created by Aaron Fisher on 4/26/2017.
 */
public class Tavern extends AbstractLocation {
    private static final String NAME = "the Tavern";
    private static final String BEGIN = "Press " + HORSE + " to enter " + NAME + " and join the adventuring party.";
    private static final String TIMING = "The adventure begins in %d seconds.";
    private static final String EMBARK = "The party is complete. %s set off on toward %s.";

    public Tavern(Channel channel, HorseBotMessenger messenger, long endTime) {
        super(channel, messenger, endTime);
    }

    @Override
    public void beginLocation(Collection<Player> players) {
        say(BEGIN);
        say(String.format(TIMING,secondsLeft()));
    }

    @Override
    public boolean canAddPlayer() {
        return true;
    }

    @Override
    public void playerCommand(Player player, int button) {

    }

    @Override
    public void endLocation(AbstractLocation next) {
        say(String.format(EMBARK, players(), next.name()));
    }

    @Override
    public String name() {
        return NAME;
    }
}
