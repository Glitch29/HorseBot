package Adventures.Deaths;

/**
 * Created by Aaron Fisher on 4/26/2017.
 */
public enum Death {
    Timeout ("%s took too long to respond, and is eaten by a Grue."),
    OutOfBounds ("$s tries to access part of the adventure that is not ready yet, and is eaten by a Grue."),
    BadCommand ("%s types in one too many nonsense commands, and is eaten by a Grue."),
    CannotJoin ("%s shows up at the Tavern to find that everyone has already left."),
    LastToRun ("The bear charges the party. Everyone flees except for %s, who is promptly mauled to death.");

    public String message;
    Death (String message) {
        this.message = message;
    }
}
