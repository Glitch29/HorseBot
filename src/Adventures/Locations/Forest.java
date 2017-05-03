package Adventures.Locations;

import Adventures.Adventure;
import Adventures.Deaths.Death;
import Adventures.Players.AdvCharacter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Aaron Fisher on 5/2/2017.
 */
public class Forest extends AbstractLocation{
    private static final String LOOK = "The forest is as scenic as one of Bob Ross's landscapes. If you weren't " +
            "being chased by a bear, you might enjoy this place. CoolStoryBob";
    private static final String ARRIVE = "You embark on your journey, and soon find yourself deep within a forest " +
            "where you stumble across a mother bear and her cubs. She doesn't look happy to see you here. I strongly " +
            "suggest that you " + HORSE + "RUN.";
    private static final String READY = "As the bear drags off the carcass, %s regathers the group. They may choose " +
            "when to " + HORSE + "EMBARK.";
    private Set<AdvCharacter> party;
    private Set<AdvCharacter> runners;
    private AdvCharacter leader;

    public Forest(Adventure adventure, Set<AdvCharacter> characters) {
        super(adventure);
        party = characters;
        runners = new HashSet<>();
        leader = null;
        publicMessage(ARRIVE);
    }

    @Override
    void look() {
        publicMessage(LOOK);
    }

    @Override()
    void run(AdvCharacter character) {
        party.remove(character);
        runners.add(character);
        if (party.size() == 1) {
            for (AdvCharacter slowpoke : party) {
                kill(slowpoke, Death.LastToRun);
            }
            leader = randomCharacter(new ArrayList<>(runners));
            publicMessage(String.format(READY, leader));
        }
    }

    @Override
    void embark(AdvCharacter character) {
        if (character == leader) {
            advanceLocation(new Victory(adventure, runners));
        }
    }
}
