package Adventures.Locations;

import Adventures.Adventure;
import Adventures.Players.Hero;

import java.util.Set;

/**
 * Created by Aaron Fisher on 5/2/2017.
 */
public class Victory extends AbstractLocation {
    private static final String ARRIVE = "You've arrived at the edge of the map. Have a " + HORSE + "LOOK around.";
    private static final String LOOK = "You look around, and see the end of the narrative in every direction. The adventure is over.";
    private String survivors;

    public Victory(Adventure adventure, Set<Hero> characters) {
        super(adventure);
        publicMessage(ARRIVE);
        survivors = "";
        for (Hero character : characters) {
            survivors = survivors + character + ", ";
        }
    }

    @Override
    void look() {
        publicMessage(LOOK);
        publicMessage(survivors + "have all survived until the end.");
        adventure.end();
    }

}
