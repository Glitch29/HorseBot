package HorseDir;

import HorseDir.Channels.Channel;
import MessageLines.Message;
import MessageLines.ParsedMessage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;
import java.util.PriorityQueue;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public class TwitchMessenger implements Messenger {
    BufferedWriter writer;
    BufferedReader reader;
    PriorityQueue<DelayedLine> timers;

    public TwitchMessenger(BufferedWriter writer, BufferedReader reader) {
        this.writer = writer;
        this.reader = reader;
        timers = new PriorityQueue<>();
    }

    private String nextLine() {
        if (timers.size() > 0 && timers.peek().timestamp <= new Date().getTime()) {
            DelayedLine delayedLine = timers.poll();
            System.out.println(">T> " + delayedLine.line);
            return delayedLine.line;
        }
        try {
            String line = reader.readLine();
            System.out.println(">>> " + line);
            return line;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Message nextMessage() {
        String line = nextLine();
        if (line == null) {
            return nextMessage();
        }
        if (line.startsWith("PING ")) {
            sendLine(new Pong(line.substring(5)));
            return nextMessage();
        }
        ParsedMessage parsedMessage = new ParsedMessage(line);
        if (parsedMessage.type.equals("PRIVMSG")) {
            return new Message(parsedMessage);
        }
        return nextMessage();
    }

    public void join(Channel channel) {
        sendLine(new Join(channel));
    }

    public void message(Channel channel, String body) {
        sendLine(new Privmsg(channel, body));
    }

    public void message(User user, String body, boolean whisper) {
        sendLine(new Privmsg(user.channel, String.format("%s%s %s",
                whisper ? "/w " : "@",
                user.username,
                body)));
    }

    private void sendLine(TwitchMessage... lines) {
        if (lines.length == 0) {
            return;
        }
        try {
            for (TwitchMessage line : lines) {
                System.out.println("<<< " + line);
                writer.write(line + "\r\n");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private interface TwitchMessage {}

    private static class Join implements TwitchMessage {
        private static final String HEADER = "JOIN ";
        Channel channel;

        Join (Channel channel) {
            this.channel = channel;
        }

        @Override
        public String toString() {
            return HEADER + channel.name;
        }
    }

    private static class Privmsg implements TwitchMessage {
        private static final String HEADER = "PRIVMSG ";
        Channel channel;
        String body;

        Privmsg (Channel channel, String body) {
            this.channel = channel;
            this.body = body;
        }

        @Override
        public String toString() {
            return String.format("%s%s :%s",
                    HEADER,
                    channel.name,
                    body);
        }
    }

    private static class Pong implements TwitchMessage {
        private static final String HEADER = "PONG ";
        String body;

        Pong (String body) {
            this.body = body;
        }

        @Override
        public String toString() {
            return HEADER + body;
        }
    }
}
