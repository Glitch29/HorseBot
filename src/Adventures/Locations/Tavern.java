package Adventures.Locations;

import Adventures.Adventure;
import Adventures.Players.AdvCharacter;

import java.util.*;

/**
 * Created by Aaron Fisher on 4/26/2017.
 */
public class Tavern extends AbstractLocation {
    private static final String NAME = "the Tavern";
    private static final String BEGIN = "Press " + HORSE + "JOIN to enter " + NAME + " and join the adventure.";
    private static final String LOOK = "You are in " + NAME + ". It's description hasn't been fleshed out much.";
    private static final String TIMING = "The adventure begins in %d seconds.";
    private static final String TOO_SMALL = "The party must have at least 3 members to proceed.";
    private static final String EMBARK = "The party is complete. %s set off on toward %s.";
    private Set<AdvCharacter> party;

    public Tavern(Adventure adventure) {
        super(adventure);
        party = new HashSet<>();
        publicMessage(BEGIN);
        publicMessage(String.format(TIMING, 30));
    }

    @Override
    public void join(AdvCharacter character) {
        if (party.add(character)) {
            publicMessage(character.joinMessage());
        }
    }

    @Override
    public void embark(AdvCharacter character) {
        if (party.contains(character)) {
            if (party.size() < 3) {
                publicMessage(TOO_SMALL);
                return;
            }
            publicMessage(String.format(EMBARK, "[PARTY MEMBERS]", "[NEXT LOCATION]"));
        }
    }

    @Override
    public void look() {
        publicMessage(LOOK);
    }
}
