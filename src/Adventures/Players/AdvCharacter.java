package Adventures.Players;

import Adventures.Deaths.Death;

/**
 * Created by Aaron Fisher on 5/1/2017.
 */
public class AdvCharacter {
    private static final String INTRODUCTION = "%s, the lvl 1 %s has joined the adventure. They will be played by %s.";
    private static final String REROLL = "%s has rerolled into %s, the lvl 1 %s.";
    private static final String NO_REROLL = "%s is out of rerolls, and is stuck as %s.";
    public Player player;
    private CharacterName characterName;
    private CharacterClass characterClass;
    private int rerolls = 2;

    public AdvCharacter(Player player) {
        this.player = player;
        reroll();
    }

    public String joinMessage() {
        return String.format(INTRODUCTION,
                characterName,
                characterClass,
                player.username);
    }

    public String rerollCharacter() {
        if (rerolls > 0) {
            rerolls--;
            reroll();
            return String.format(REROLL,
                    player.username,
                    characterName,
                    characterClass);
        } else {
            return String.format(NO_REROLL,
                    player.username,
                    characterName);
        }
    }

    private void reroll() {
        characterName = CharacterName.randomName();
        characterClass = CharacterClass.randomClass();
    }

    public void kill(Death death) {

    }

    @Override
    public String toString() {
        return characterName.toString();
    }
}
