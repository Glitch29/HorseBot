package Connections;

import Accounts.Account;

import java.io.*;
import java.net.Socket;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public class TwitchIrcSession implements IrcSession {
    private static final String TWITCH_IRC_ADDRESS = "irc.chat.twitch.tv";
    private static final int TWITCH_IRC_PORT = 6667;
    private static TwitchIrcSession twitchIrcSession;
    private Account credential;
    private BufferedWriter writer;
    private BufferedReader reader;

    public static TwitchIrcSession getTwitchIRC(Account credential) {
        if (twitchIrcSession != null) {
            return (twitchIrcSession.credential == credential ? twitchIrcSession : null);
        }
        try {
            return twitchIrcSession = new TwitchIrcSession(credential);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private TwitchIrcSession(Account credential) throws IOException {
        this.credential = credential;
        setReaderWriter();
        sendLogin();
        verifyLogin();
    }

    private void setReaderWriter() throws IOException {
        // Connect directly to the IRC server.
        Socket socket = new Socket(TWITCH_IRC_ADDRESS, TWITCH_IRC_PORT);
        writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream( )));
        reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream( )));
    }

    private void sendLogin() throws IOException {
        // Log on to the server.
        writer.write("PASS " + credential.getPass() + "\r\n");
        writer.write("NICK " + credential.getNick() + "\r\n");
        writer.flush( );
    }

    private void verifyLogin() throws IOException {
        String line;
        while ((line = reader.readLine( )) != null) {
            System.out.println(">>> " + line);
            if (line.contains("004")) {
                // We are now logged in.
                break;
            }
            else if (line.contains("433")) {
                System.out.println("Nickname is already in use.");
                return;
            }
        }
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public BufferedReader getReader() {
        return reader;
    }
}
