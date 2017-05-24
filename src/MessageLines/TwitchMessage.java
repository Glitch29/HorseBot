package MessageLines;

import HorseDir.Channels.Channel;
import HorseDir.User;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public class TwitchMessage {
    public User user;
    public Channel channel;
    public String body;


    public TwitchMessage(User user, Channel channel, String body) {
        this.user = user;
        this.channel = channel;
        this.body = body;
    }

    public TwitchMessage(ParsedMessage message) {
        assert message.type.equals("PRIVMSG");
        assert message.data != null;
        assert message.data.size() > 0;
        channel = Channel.get(message.data.get(0));
        assert channel != null;
        user = new User(channel, message.source.substring(1, message.source.indexOf("!")));
        body = message.content.substring(1);
    }
}
