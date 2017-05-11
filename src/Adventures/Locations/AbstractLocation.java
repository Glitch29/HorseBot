package Adventures.Locations;

import Adventures.Adventure;
import Adventures.Commands.Command;
import Adventures.Deaths.Death;
import Adventures.Players.Hero;

import java.util.*;

/**
 * Created by Aaron Fisher on 4/26/2017.
 */
public abstract class AbstractLocation {
    private static final Random RANDOM = new Random(new Date().getTime());
    static final String HORSE = "\uD83D\uDC0E";
    Adventure adventure;

    AbstractLocation(Adventure adventure) {
        this.adventure = adventure;
    }

    public void command(Hero hero, Command command) {
        switch (command) {
            case JOIN:
                join(hero);
                break;
            case LOOK:
                look(hero);
                break;
            case REROLL:
                publicMessage(hero.rerollCharacter());
                break;
            case RUN:
                run(hero);
                break;
            case EMBARK:
                embark(hero);
                break;
            case HIDE:
                hide(hero);
                break;
            case FIGHT:
                fight(hero);
                break;
            case LEAP:
                leap(hero);
                break;
            case TALK:
                talk(hero);
                break;
        }
    }

    public void join(Hero hero) {
        publicMessage(String.format("%s shows up at the Tavern to find that everyone has already left.", hero));
        adventure.kill(Death.Lonely, hero);
    }

    void look(Hero hero) {
        whisper(hero, "You look around, but there's nothing much to see.");
    }

    void run(Hero hero) {

    }

    void embark(Hero hero) {

    }

    void hide(Hero hero) {

    }

    void fight(Hero hero) {

    }

    void leap(Hero hero) {

    }

    void talk(Hero hero) {

    }

    void kill(Death death, Hero... heroes) {
        adventure.kill(death, heroes);
    }

    void advanceLocation(AbstractLocation location) {
        adventure.advanceLocation(location);
    }

    void publicMessage(String message) {
        adventure.publicMessage(message);
    }

    void whisper(Hero hero, String message) {
        adventure.whisper(hero, message);
    }

    String heroNames(Collection<Hero> heroes) {
        StringBuilder names = new StringBuilder();
        for (Hero hero : heroes) {
            names.append(hero.heroName + ", ");
        }
        return names.toString();
    }

    Hero chooseLeader(Collection<Hero> heroes) {
        Hero leader = randomCharacter(new ArrayList<>(heroes));
        whisper(leader, "You are the new leader. Type " + HORSE + "EMBARK when you are ready to leave this location.");
        return leader;
    }

    Hero randomCharacter(List<Hero> characters) {
        return characters.get(RANDOM.nextInt(characters.size()));
    }

    String randomReason() {
        int i = RANDOM.nextInt(6);
        switch (i) {
            case 0:
                return "most trustworthy";
            case 1:
                return "strongest";
            case 2:
                return "wisest";
            case 3:
                return "most beautiful";
            case 4:
                return "most courageous";
            default:
                return "most charismatic";
        }
    }
}
