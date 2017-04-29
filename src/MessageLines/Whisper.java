package MessageLines;

import HorseDir.Channel;

/**
 * Created by Aaron Fisher on 4/28/2017.
 */
public class Whisper extends Privmsg {
    public Whisper (Channel channel, String user, String message) {
        super(HEADER + channel + " :/w " + user + " " + message);
    }
}
