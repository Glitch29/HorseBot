package Adventures;

import Adventures.Players.AdvCharacter;

/**
 * Created by Aaron Fisher on 5/2/2017.
 */
public class IllegalAdventureCommandException extends Exception {
    AdvCharacter character;

    public IllegalAdventureCommandException (AdvCharacter character) {
        this.character = character;
    }
}
