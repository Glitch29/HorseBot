package Adventures.Locations;

import Adventures.Adventure;
import Adventures.Players.Hero;

import java.util.*;

/**
 * Created by Aaron Fisher on 4/26/2017.
 */
public class TheTavern extends AbstractLocation {
    private static final int MIN_PARTY_SIZE = 3;
    private static final String NAME = "the Tavern";
    private static final String BEGIN = "Press " + HORSE + "JOIN to enter " + NAME + " and join the adventure.";
    private static final String LOOK = "You are in " + NAME + ". It's description hasn't been fleshed out much.";
    private static final String TOO_SMALL = "The party must have at least " + MIN_PARTY_SIZE + " members to proceed.";
    private static final String NOT_LEADER = "%s tries to convince everyone to leave, but is ignored.";
    private static final String READY = "Several adventurers have now converged at " + NAME + ". %s is clearly the " +
            "%s of the lot, and has emerged as a leader. They can decide when to " + HORSE + "EMBARK.";
    private static final String EMBARK = "The party is complete. %s set off on toward %s.";
    private Set<Hero> party;
    private Hero leader;

    public TheTavern(Adventure adventure) {
        super(adventure);
        party = new HashSet<>();
        publicMessage(BEGIN);
    }

    @Override
    public void join(Hero hero) {
        if (party.add(hero)) {
            publicMessage(hero.joinMessage());
            if (party.size() >= MIN_PARTY_SIZE && leader == null) {
                chooseLeader();
            }
        }
    }

    @Override
    public void embark(Hero hero) {
        if (party.contains(hero)) {
            if (party.size() < MIN_PARTY_SIZE) {
                publicMessage(TOO_SMALL);
                return;
            }
            if (hero != leader) {
                publicMessage(String.format(NOT_LEADER, hero));
            }
            publicMessage(String.format(EMBARK,
                    party.size() + " adventurers",
                    "the forest"));
            advanceLocation(new TheGypsy(adventure, party, TheGypsy.GypsyType.PRANKSTER));
        }
    }

    @Override
    public void look(Hero hero) {
        whisper(hero, LOOK);
    }

    private void chooseLeader() {
        publicMessage(String.format(READY,
                leader = super.chooseLeader(party),
                randomReason()
        ));
    }
}
