package WorldRecords;

/**
 * Created by Aaron Fisher on 5/3/2017.
 */
public enum Category {
    Tutorial (Game.Overwatch, "Tutorial", "7dggreld", "overwatch"),
    AD (Game.BotW, "All Dungeons","9d8jgv7k", "ad"),
    Any (Game.BotW, "Any%", "vdoq4xvk", "any%"),
    AMQ (Game.BotW, "All Main Quests", "n2yj3r82", "amq"),
    AS (Game.BotW, "All Shrines", "wkpqmw8d", "as"),
    MS (Game.BotW, "Master Sword RTA", "02q8pz92", "ms"),
    HUNDO (Game.BotW, "100%", "xk9jv4gd", "100%");

    Game game;
    String name;
    String categoryId;
    public String command;

    Category(Game game, String name, String categoryId, String command) {
        this.name = name;
        this.categoryId = categoryId;
        this.game = game;
        this.command = command;
    }
}
