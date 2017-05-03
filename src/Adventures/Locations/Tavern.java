package Adventures.Locations;

import Adventures.Adventure;
import Adventures.Players.AdvCharacter;

import java.util.*;

/**
 * Created by Aaron Fisher on 4/26/2017.
 */
public class Tavern extends AbstractLocation {
    private static final int MIN_PARTY_SIZE = 3;
    private static final String NAME = "the Tavern";
    private static final String BEGIN = "Press " + HORSE + "JOIN to enter " + NAME + " and join the adventure.";
    private static final String LOOK = "You are in " + NAME + ". It's description hasn't been fleshed out much.";
    private static final String TIMING = "The adventure begins in %d seconds.";
    private static final String TOO_SMALL = "The party must have at least 3 members to proceed.";
    private static final String NOT_LEADER = "%s tries to convince everyone to leave, but is ignored.";
    private static final String READY = "Several adventurers have now converged at " + NAME + ". %s is clearly %s " +
            "of the lot, and has emerged as a leader. They alone will decide when to " + HORSE + "EMBARK.";
    private static final String EMBARK = "The party is complete. %s set off on toward %s.";
    private Set<AdvCharacter> party;
    private AdvCharacter leader;

    public Tavern(Adventure adventure) {
        super(adventure);
        party = new HashSet<>();
        publicMessage(BEGIN);
//        publicMessage(String.format(TIMING, 30));
    }

    @Override
    public void join(AdvCharacter character) {
        if (party.add(character)) {
            publicMessage(character.joinMessage());
            if (party.size() >= MIN_PARTY_SIZE && leader == null) {
                chooseLeader();
            }
        }
    }

    @Override
    public void embark(AdvCharacter character) {
        if (party.contains(character)) {
            if (party.size() < 3) {
                publicMessage(TOO_SMALL);
                return;
            }
            if (character != leader) {
                publicMessage(String.format(NOT_LEADER, character));
            }

            publicMessage(String.format(EMBARK,
                    party.size() + " adventurers",
                    "the forest"));
            advanceLocation(new Forest(adventure, party));
        }
    }

    @Override
    public void look() {
        publicMessage(LOOK);
    }

    private void chooseLeader() {
        leader = randomCharacter(new ArrayList<>(party));
        publicMessage(String.format(READY,
                leader,
                randomReason()
        ));
    }
}
