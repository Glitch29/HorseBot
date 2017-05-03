package Adventures.Commands;

/**
 * Created by Aaron Fisher on 4/30/2017.
 */
public enum Command {
    JOIN,
    LOOK,
    REROLL,
    COMMANDS,
    RUN,
    HIDE,
    EMBARK,
    FIGHT;

    public String toString() {
        return "\uD83D\uDC0E" + super.toString();
    }
}
