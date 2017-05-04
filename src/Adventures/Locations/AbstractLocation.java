package Adventures.Locations;

import Adventures.Adventure;
import Adventures.Commands.Command;
import Adventures.Deaths.Death;
import Adventures.Players.Hero;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Aaron Fisher on 4/26/2017.
 */
public abstract class AbstractLocation {
    private static final Random RANDOM = new Random(new Date().getTime());
    private static final String COMMANDS = "";
    static final String HORSE = "\uD83D\uDC0E";
    Adventure adventure;
    private long endTime;

    AbstractLocation(Adventure adventure) {
        this.adventure = adventure;
        this.endTime = new Date().getTime() + 60L * 1000L;
    }

    public void command(Hero character, Command command) {
        switch (command) {
            case JOIN:
                join(character);
                break;
            case LOOK:
                look();
                break;
            case REROLL:
                publicMessage(character.rerollCharacter());
                break;
            case RUN:
                run(character);
                break;
            case EMBARK:
                embark(character);
                break;
            case HIDE:
                hide(character);
                break;
            case FIGHT:
                fight(character);
                break;
            case COMMANDS:
                commands();
                break;
        }
    }

    public void join(Hero character) {

    }

    abstract void look();

    void run(Hero character) {

    }

    void embark(Hero character) {

    }

    void hide(Hero character) {

    }

    void fight(Hero character) {

    }

    void commands() {

    }

    void kill(Hero character, Death death) {
        adventure.kill(character, death);
    }

    void advanceLocation(AbstractLocation location) {
        adventure.advanceLocation(location);
    }

    int secondsLeft() {
        return (int) ((endTime - new Date().getTime()) / 1000);
    }

    void publicMessage(String message) {
        adventure.publicMessage(message);
    }

    Hero randomCharacter(List<Hero> characters) {
        return characters.get(RANDOM.nextInt(characters.size()));
    }

    String randomReason() {
        int i = RANDOM.nextInt(5);
        switch (i) {
            case 0:
                return "the most trustworthy";
            case 1:
                return "the strongest";
            case 2:
                return "the wisest";
            case 3:
                return "the most beautiful";
            default:
                return "the most charismatic";
        }
    }
}
