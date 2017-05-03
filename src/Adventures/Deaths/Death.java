package Adventures.Deaths;

import Adventures.Players.Player;

/**
 * Created by Aaron Fisher on 4/26/2017.
 */
public enum Death {
    EatenByAGrue ("%s took too long to respond, and was eaten by a Grue."),
    EmptyTavern ("%s showed up at the Tavern, but everyone had already left.");

    String message;
    Death (String message) {
        this.message = message;
    }
}
