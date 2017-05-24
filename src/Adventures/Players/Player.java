package Adventures.Players;

import HorseDir.Channels.Channel;

/**
 * Created by Aaron Fisher on 4/26/2017.
 */
public class Player {
    private Channel channel;
    public String username;

    public Player(Channel channel, String username) {
        this.channel = channel;
        this.username = username;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Player)) {
            return false;
        }
        Player otherPlayer = (Player) other;
        if (channel == null) {
            if (otherPlayer.channel != null) {
                return false;
            }
        } else {
            if (!channel.equals(otherPlayer.channel)) {
                return false;
            }
        }
        if (username == null) {
            if (otherPlayer.username != null) {
                return false;
            }
        } else {
            if (!username.equals(otherPlayer.username)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return channel.toString().hashCode() ^ username.hashCode();
    }
}
