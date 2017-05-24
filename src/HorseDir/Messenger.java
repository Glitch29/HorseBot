package HorseDir;

import HorseDir.Channels.Channel;
import MessageLines.TwitchMessage;

/**
 * Created by Aaron Fisher on 5/17/2017.
 */
public interface Messenger {
    void message(Channel channel, String message);
    void message(User user, String message, boolean whisper);
    TwitchMessage nextMessage();
    void join(Channel channel);
}
