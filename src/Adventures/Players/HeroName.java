package Adventures.Players;

import java.util.Date;
import java.util.Random;

/**
 * Created by Aaron Fisher on 5/1/2017.
 */
public enum HeroName {
    Edwin,
    Gwendolyn,
    William,
    Eustace,
    Huckleberry,
    Gertrude,
    Florence,
    Alfonso,
    Todd,
    Tybalt,
    Merek,
    Rowan,
    Gregory,
    Frederick,
    Lief,
    Cedric,
    Quinn,
    Zane,
    Tristan,
    Winifred,
    Katelyn,
    Isabel,
    Millicent,
    Hildegard,
    Seraphina,
    Elspeth,
    Moriarty,
    Clarence,
    Timothy,
    Desdemona,
    Othello,
    Doc,
    Spud;

    private static final Random random = new Random(new Date().getTime());

    static HeroName randomName() {
        return HeroName.values()[random.nextInt(HeroName.values().length)];
    }
}
