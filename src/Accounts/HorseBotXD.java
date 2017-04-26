package Accounts;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public class HorseBotXD implements Account {
    private static final String HORSEBOT_NICK = "horsebotxd";
    private static final String HORSEBOT_PASS = readPass();

    public HorseBotXD() {}

    @Override
    public String getNick() {
        return HORSEBOT_NICK;
    }

    @Override
    public String getPass() {
        return HORSEBOT_PASS;
    }

    private static String readPass() {
        String pass = "";
        try (Scanner scanner = new Scanner(new File("C:\\Users\\Aaron Fisher\\IdeaProjects\\HorseBot\\src\\Accounts\\HorseBotXD.txt")))
        {
            pass = scanner.next();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pass;
    }
}
