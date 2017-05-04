package Adventures.Players;

/**
 * Created by Aaron Fisher on 5/1/2017.
 */
public class Hero {
    private static final String INTRODUCTION = "%s, the lvl 1 %s has joined the adventure. They will be played by %s.";
    private static final String REROLL = "%s has rerolled into %s, the lvl 1 %s.";
    private static final String NO_REROLL = "%s is out of rerolls, and is stuck as %s.";
    public Player player;
    private HeroName heroName;
    private HeroClass heroClass;
    private int rerolls = 2;

    public Hero(Player player) {
        this.player = player;
        reroll();
    }

    public String joinMessage() {
        return String.format(INTRODUCTION,
                heroName,
                heroClass,
                player.username);
    }

    public String rerollCharacter() {
        if (rerolls > 0) {
            rerolls--;
            reroll();
            return String.format(REROLL,
                    player.username,
                    heroName,
                    heroClass);
        } else {
            return String.format(NO_REROLL,
                    player.username,
                    heroName);
        }
    }

    private void reroll() {
        heroName = HeroName.randomName();
        heroClass = HeroClass.randomClass();
    }

    @Override
    public String toString() {
        return heroName.toString();
    }
}
