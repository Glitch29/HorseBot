package MessageLines;

import HorseDir.Channel;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public class Privmsg extends AbstractMessageLine {
    public final static String HEADER = "PRIVMSG ";
    public String user;
    public Channel channel;
    public String body;

    public Privmsg(String fullLine) {
        super(fullLine);
    }

    public Privmsg(Channel channel, String body) {
        super(HEADER + channel + " :" + body);
    }

    public Privmsg(String user, Channel channel, String body) {
        super(user + " " + HEADER + channel + " " + body);
        this.user = user.substring(1,user.indexOf("!"));
        this.channel = channel;
        this.body = body;
    }
}
