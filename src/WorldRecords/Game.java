package WorldRecords;

/**
 * Created by Aaron Fisher on 5/3/2017.
 */
public enum Game {
    BotW("The Legend of Zelda: Breath of the Wild","76rqjqd8"),
    Overwatch("Overwatch","kdkpol1m");
    String name;
    String code;

    Game(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
