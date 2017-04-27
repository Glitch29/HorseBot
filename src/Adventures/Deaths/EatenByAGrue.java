package Adventures.Deaths;

import Adventures.Player;
import HorseDir.Channel;
import HorseDir.HorseBotMessenger;

import java.util.Collection;

/**
 * Created by Aaron Fisher on 4/26/2017.
 */
public class EatenByAGrue extends AbstractDeath {
    private static final String MESSAGE = "%s took too long to respond, and was eaten by a Grue.";

    EatenByAGrue(Collection<Player> players, HorseBotMessenger messenger, Channel channel) {
        super(players, messenger, channel);
    }

    @Override
    public void kill() {
        for (Player player : players) {
            say(String.format(MESSAGE, player.name));
        }
    }
}
