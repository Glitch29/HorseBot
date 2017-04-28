package HorseDir;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Aaron Fisher on 4/28/2017.
 */
public class HorseFact {
    public static final ArrayList<String> HORSE_FACTS = new ArrayList<>(Arrays.asList(
            "Horses look like this --> 🐎",
            "Horses were first introduced to North America in the late 1600's. They proceeded to be tamed by various indigenous tribes over the next hundred years, their biggest advancement came when in 1992 The Undertaker threw Mankind off Hell In A Cell, and plummeted 16 ft through an announcer’s table.",
            "A horse's teeth take up a larger amount of space in their head than their brain.",
            "Complimenting your horse through the controller's built-in microphone will make it better at running across bridges.",
            "https://twitter.com/Dystify/status/854732136976351239",
            "What is horse? Baby, don't hurt me.",
            "The original Konami code was 🐃🐃🐇🐇🐎🐍🐎🐍🐪🐴, but had to be modified for North American consoles which did not support the horse button.",
            "Domesticated in central Asia around five thousand years ago, the horse was instrumental to the development of Eurasian civilization. Unlike most other large mammals, it was not farmed for its meat, milk or hides. Instead, the horse was harnessed solely for its incredible strength – to pull plows, vehicles, and most significantly, to carry humans themselves.",
            "The horses in Zelda: Breath of the Wild were modeled after the Short-rumped Padowaks that are common to the Gran Chaco region. This quasi-domesticated breed is know for stopping suddenly while crossing bridges, and bucking its riders while running down hills.",
            "Many runners have never completed the ritual to summon Epona, preferring instead to perpetually handicap themselves on their runs.",
            "Horses with solid coloring are faster, but more disobedient than those with patterned coats. The fastest and most disobedient horses look like trees.",
            "The card game Magic: the Gathering has featured horses in its artwork dating all the way back its first set, Alpha, published in 1993. However it was not until the printing of Magic 2010 that Horse was officially recognized as its own creature type.",
            "Look at my Horse. My Horse is amazing!",
            "Don't look at me, my horse, or my horse's son ever again."
            ));

    public static String fact() {
        return fact((int) (Math.random() * HORSE_FACTS.size()));
    }

    public static String fact(int i) {
        return HORSE_FACTS.get(i);
    }
}
