package Adventures.Locations;

import Adventures.Adventure;
import Adventures.Deaths.Death;
import Adventures.Players.Hero;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Aaron Fisher on 5/2/2017.
 */
public class TheBear extends AbstractLocation{
    private static final String LOOK = "The forest is as scenic as one of Bob Ross's landscapes. If you weren't " +
            "being chased by a bear, you might enjoy this place. CoolStoryBob";
    private static final String ARRIVE = "You embark on your journey, and soon find yourself deep within a forest " +
            "where you stumble across a mother bear and her cubs. She doesn't look happy to see you here. I strongly " +
            "suggest that you " + HORSE + "RUN.";
    private static final String CHARGE = "The bear charges the party. Those that took too long to run meet a grizzly end.";
    private static final String READY = "As the bear drags off the carcass, %s regathers the group. They may choose " +
            "when to " + HORSE + "EMBARK.";
    private Set<Hero> party;
    private Set<Hero> runners;
    private Hero leader;

    public TheBear(Adventure adventure, Set<Hero> characters) {
        super(adventure);
        party = characters;
        runners = new HashSet<>();
        leader = null;
        publicMessage(ARRIVE);
    }

    @Override
    void look(Hero hero) {
        whisper(hero, LOOK);
    }

    @Override()
    void run(Hero hero) {
        party.remove(hero);
        runners.add(hero);
        if (party.size() <= runners.size() / 3 + 1) {

            for (Hero slowpoke : party) {
                kill(Death.Bear, slowpoke);
            }
            leader = randomCharacter(new ArrayList<>(runners));
            publicMessage(String.format(READY, leader));
        }
    }

    @Override
    void embark(Hero hero) {
        if (hero == leader) {
            advanceLocation(new TheEnd(adventure, runners));
        }
    }
}
