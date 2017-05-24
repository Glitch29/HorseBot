package HorseDir.Channels;

import org.junit.Test;

/**
 * Created by Aaron Fisher on 5/19/2017.
 */
public class ChannelDataTest {

    @Test
    public void testLoad() {
        ChannelData channelData = new ChannelData("twitchplayspokemon");
        System.out.println(channelData.isLogged());
    }
}
