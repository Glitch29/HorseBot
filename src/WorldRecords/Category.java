package WorldRecords;

/**
 * Created by Aaron Fisher on 5/3/2017.
 */
public enum Category {
    AD ("All Dungeons","9d8jgv7k", Game.BotW),
    Any ("Any%", "vdoq4xvk", Game.BotW);

    String name;
    String code;
    Game game;
    Category(String name, String code, Game game) {
        this.name = name;
        this.code = code;
        this.game = game;
    }
}
