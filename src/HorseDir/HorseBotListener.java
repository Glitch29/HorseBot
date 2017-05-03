package HorseDir;

import MessageLines.Ping;
import MessageLines.Privmsg;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public class HorseBotListener {
    BufferedReader reader;
    String line;

    public HorseBotListener(BufferedReader reader) {
        this.reader = reader;
    }

    public String readLine() {
        try {
            line = reader.readLine();
            System.out.println(">>> " + line);
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
        Channel channel = Channel.get(scanner.next());
        String body = scanner.nextLine().substring(2);
        return new Privmsg(user, channel, body);
    }

}
