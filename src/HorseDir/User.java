package HorseDir;

import HorseDir.Channels.Channel;

/**
 * Created by Aaron Fisher on 5/17/2017.
 */
public class User {
    public Channel channel;
    public String username;

    public User(Channel channel, String username) {
        this.channel = channel;
        this.username = username;
    }

    public void publicMessage(String message) {
        channel.message(message);
    }

    public void personalMessage(String message) {
        channel.message(this, message, true);
    }

    public void directedMessage(String message) {
        channel.message(this, message, false);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof User &&
                ((User) other).channel.equals(channel) &&
                ((User) other).username.equals(username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
