package Adventures;

import Adventures.Players.Hero;

/**
 * Created by Aaron Fisher on 5/2/2017.
 */
public class IllegalAdventureCommandException extends Exception {
    Hero character;

    public IllegalAdventureCommandException (Hero character) {
        this.character = character;
    }
}
