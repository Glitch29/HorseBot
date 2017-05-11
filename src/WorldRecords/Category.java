package WorldRecords;

/**
 * Created by Aaron Fisher on 5/3/2017.
 */
public enum Category {
    AD ("All Dungeons","9d8jgv7k", Game.BotW),
    Any ("Any%", "vdoq4xvk", Game.BotW),
    AMQ ("All Main Quests", "n2yj3r82", Game.BotW),
    AS ("All Shrines", "wkpqmw8d", Game.BotW),
    MS ("Master Sword RTA", "02q8pz92", Game.BotW),
    HUNDO ("100%", "xk9jv4gd", Game.BotW);

    String name;
    String code;
    Game game;
    Category(String name, String code, Game game) {
        this.name = name;
        this.code = code;
        this.game = game;
    }
}
