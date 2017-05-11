package Adventures.Locations;

import Adventures.Adventure;
import Adventures.Commands.Command;
import Adventures.Players.Hero;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Aaron Fisher on 5/2/2017.
 */
public class TheEnd extends AbstractLocation {
    private static final String ARRIVE = "You've arrived at the edge of the map. Have a " + Command.LOOK + " around.";
    private static final String LOOK = "You look around, and see the end of the narrative in every direction. The adventure is over.";
    private String survivors;
    private Set<Hero> party;

    public TheEnd(Adventure adventure, Set<Hero> party) {
        super(adventure);
        publicMessage(ARRIVE);
        survivors = "";
        this.party = party;
        for (Hero character : party) {
            survivors = survivors + character + ", ";
        }
    }

    @Override
    void look(Hero hero) {
        publicMessage(LOOK);
        publicMessage(survivors + "have all survived until the end.");
        Set<Hero> levelups = new HashSet<>();
        levelups.add(randomCharacter(new ArrayList<>(party)));
        for (Hero winner : levelups) {
            publicMessage(winner + " proved themselves to be the " + randomReason() + " and has " +
                    "advanced to a lvl 2 " + winner.heroClass + ".");
        }
        adventure.end();
    }

}
