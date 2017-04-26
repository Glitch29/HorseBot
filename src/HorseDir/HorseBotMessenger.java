package HorseDir;

import MessageLines.*;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public class HorseBotMessenger {
    BufferedWriter writer;

    public HorseBotMessenger(BufferedWriter writer) {
        this.writer = writer;
    }

    public void join(Channel channel) throws IOException {
        sendLine(new Join(channel).toString());
    }

    public void pong(Ping ping) {
        sendLine(new Pong(ping));
    }

    public void privmsg(Channel channel, String body) {
        sendLine(new Privmsg(channel, ":"+body));
    }

    private void sendLine(AbstractMessageLine line) {
        sendLine(line.toString());
    }

    private void sendLine(String line) {
        try {
            System.out.println("<<< " + line);
            writer.write(line + "\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
