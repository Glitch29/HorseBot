package Adventures.Players;

import java.util.Date;
import java.util.Random;

/**
 * Created by Aaron Fisher on 4/29/2017.
 */
public enum HeroClass {
    Cobbler,
    Gardener,
    Wordsmith,
    Priest,
    Paladin,
    Knight,
    Barbarian,
    Peasant,
    Leper,
    Prestidigitarian,
    Dwarf,
    Prime_Minister ("Prime Minister"),
    Gazebo,
    Gastroenterologist,
    Speedrunner,
    Spelunker,
    Canadian,
    Gargoyle,
    Pikachu,
    Elephant,
    Ninja_Turtle ("Ninja Turtle"),
    Federalist,
    Spambot,
    Menace_to_Society ("Menace to Society"),
    Chess_boxer ("Chess-boxer"),
    Dietician,
    Pleb,
    Detective,
    Chaperone,
    Quizmaster,
    Presbyterian,
    Cowboy,
    Paraglider,
    Rockette,
    Labradoodle,
    Thief,
    Rogue,
    Defenestrator,
    Cleric,
    Hustler,
    Badass,
    Warrior,
    Hunter,
    Druid,
    Shaman,
    Roommate,
    Cruciverbalist,
    Peon,
    Monk,
    Knave,
    Gumshoe;

    String name;
    HeroClass(String name) {
        this.name = name;
    }
    HeroClass() {
        this.name = this.toString();
    }

    private static final Random random = new Random(new Date().getTime());

    static HeroClass randomClass() {
        return HeroClass.values()[random.nextInt(HeroClass.values().length)];
    }

}
