package Adventures.Deaths;

import Adventures.Player;
import HorseDir.Channel;
import HorseDir.HorseBotMessenger;

/**
 * Created by Aaron Fisher on 4/26/2017.
 */
public class EmptyTavern extends AbstractDeath {
    private static final String MESSAGE = "%s showed up at the Tavern, but everyone had already left.";

    public EmptyTavern(Player player, HorseBotMessenger messenger, Channel channel) {
        super(player, messenger, channel);
    }

    @Override
    public void kill() {
        for (Player player : players) {
            say(String.format(MESSAGE, player.name));
        }
    }
}
