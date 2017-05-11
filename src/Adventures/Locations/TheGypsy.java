package Adventures.Locations;

import Adventures.Adventure;
import Adventures.Commands.Command;
import Adventures.Deaths.Death;
import Adventures.Players.Hero;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Aaron Fisher on 5/3/2017.
 */
public class TheGypsy extends AbstractLocation {
    private static final String ARRIVE = "The forest gives way to a large meadow.  You see several newts sunning " +
            "themselves on a boulder. Off in the distance there's a log cabin where an old woman tends to her garden " +
            "outside. You may " + Command.TALK + " to her, or " + Command.EMBARK + " and continue on your journey.";
    private static final String TALK = "%s heads off to greet the old woman. You can see them conversing in the " +
            "distance, but can't tell what's being said without venturing closer.";
    private Set<Hero> party;
    private Set<Hero> talkers;
    private Set<Hero> embarkers;
    private Set<Hero> newts;
    private GypsyType gypsyType;
    private Hero joker = null;

    TheGypsy(Adventure adventure, Set<Hero> party, GypsyType gypsyType) {
        super(adventure);
        publicMessage(ARRIVE);
        this.gypsyType = gypsyType;
        this.party = party;
        talkers = new HashSet<>();
        embarkers = new HashSet<>();
        newts = new HashSet<>();
    }

    @Override
    void talk(Hero hero) {
        if (newts.contains(hero)) {
            whisper(hero, "Newts can't talk.");
            return;
        }
        if (embarkers.contains(hero)) {
            whisper(hero, "You are already trying to leave.");
            return;
        }
        if (talkers.add(hero)) {
            if (talkers.size() == 1) {
                publicMessage(String.format(TALK, hero));
            }
            switch (gypsyType) {
                case HERMIT:
                    switch (new Integer(talkers.size()).compareTo((party.size() + 6) / 4)) {
                        case -1:
                            whisper(hero, "I HATE YOU! I HATE YOU! I HATE EVERYONE! If more people talk to me, I will " +
                                    "turn you all into newts!!! (You'd better " + Command.RUN + " away.)");
                            break;
                        case 0:
                            publicMessage("The gypsy woman screams \"WHY CAN'T THEY JUST LEAVE ME ALONE!?!\" In a puff of " +
                                    "magic, " + heroNames(talkers) + " become newts. AngelThump");
                            kill(Death.Newt, (Hero[]) talkers.toArray());
                            newts.addAll(talkers);
                            break;
                        case 1:
                            publicMessage("MORE NEWTS!");
                            kill(Death.Newt, hero);
                            newts.add(hero);
                            break;
                    }
                    break;
                case PRANKSTER:
                    if (joker == null) {
                        whisper(joker = hero, "The witch cackles as you approach. Let's play a funny prank. If you can get " +
                                "someone else to " + Command.RUN + " I will turn them into a newt. Please don't disappoint me.");
                    } else {
                        whisper(hero, "The witch is already laughing manaically as you arrive. Whatever is so funny " +
                                "is clearly an inside joke between her and " + joker + ".");
                    }
                    break;
                case GOSSIP:
                    whisper(hero, "The old woman is friendly enough, but has clearly lost her mind. She rambles off " +
                            "nonsensical advice: \"Raptors usually hunt in pairs.\" \"Always "+ Command.LOOK +
                            " before you " + Command.LEAP +".");
                    break;
            }
        }
    }

    @Override
    public void embark(Hero hero) {
        if (newts.contains(hero)) {
            whisper(hero, "Newts can't run.");
            return;
        }
        leave(hero);
    }

    @Override
    public void run(Hero hero) {
        if (newts.contains(hero)) {
            whisper(hero, "Newts can't run.");
            return;
        }
        if (joker == null) {
            leave(hero);
        } else {
            publicMessage(String.format("%s makes a dash for the woods, but the witch turns and strikes them down " +
                            "mid stride a well-aimed blast of magic. Amid her relentless cackling you could swear " +
                            "she gave %s a wink.",
                            hero,
                            joker
            ));
            kill(Death.Newt, hero);
            joker = null;
            leave(hero);
        }

    }

    @Override
    public void look(Hero hero) {
        whisper(hero, "She doesn't " + Command.LOOK + " like the type of witch that would turn passing adventurers " +
                "into newts. But it's hard to be sure.");
    }

    private void leave(Hero hero) {
        if (embarkers.add(hero)) {
            int neededToLeave = (party.size() - newts.size() + 1) / 2 - embarkers.size();
            if (neededToLeave > 0) {
                if (!newts.contains(hero)) {
                    whisper(hero, String.format("%d more votes needed to leave.", neededToLeave));
                }
            } else {
                if (party.removeAll(newts)) {
                    publicMessage(String.format("The %d adventurers still in their human form leave the meadow and " +
                            "quest forward into the forest.", party.size()));
                } else {
                    publicMessage("Everyone treks onward into the forest.");
                }
                advanceLocation(new TheBear(adventure, party));
            }
        }
    }

    public enum GypsyType {
        HERMIT,
        PRANKSTER,
        GOSSIP;
    }
}
