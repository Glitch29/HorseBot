package HorseDir;

import MessageLines.Ping;
import MessageLines.Privmsg;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public class HorseBotListener {
    Map<String, Channel> channelMap;
    BufferedReader reader;
    String line;

    public HorseBotListener(BufferedReader reader, Collection<Channel> channels) {
        this.reader = reader;
        channelMap = new HashMap<>();
        for (Channel channel : channels) {
            channelMap.put(channel.channel, channel);
        }
    }

    public String readLine() {
        try {
            System.out.print(">>> ");
            line = reader.readLine();
            System.out.println(line);
            return line;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public LineType classify() {
        if (line.startsWith(Ping.HEADER)) {
            return LineType.PING;
        }
        if (line.contains(" PRIVMSG ")) {
            return LineType.PRIVMSG;
        }
        return LineType.PONG;
    }

    public Ping readPing() {
        return new Ping(line);
    }

    public Privmsg readPrivmsg() {
        Scanner scanner = new Scanner(line);
        String user = scanner.next();
        String header = scanner.next();
        Channel channel = channelMap.get(scanner.next());
        String body = scanner.nextLine().substring(2);
        return new Privmsg(user, channel, body);
    }

}
