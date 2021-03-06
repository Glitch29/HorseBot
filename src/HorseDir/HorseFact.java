package HorseDir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * Created by Aaron Fisher on 4/28/2017.
 */
public class HorseFact {
    private static final Random random = new Random(new Date().getTime());
    private static final ArrayList<String> HORSE_FACTS = new ArrayList<>(Arrays.asList(
            "Horses look like this --> 🐎",
            "Horses were first introduced to North America in the late 1600s. They proceeded to be tamed by various " +
                    "indigenous tribes over the next hundred years, but their biggest advancement came when in 1992 " +
                    "The Undertaker threw Mankind off Hell In A Cell, and plummeted 16 ft through an announcer’s table.",
            "A horse's teeth take up a larger amount of space in their head than their brain.",
            "Complimenting your horse through the controller's built-in microphone will make it better at running" +
                    " across bridges.",
            "https://twitter.com/Dystify/status/854732136976351239",
            "What is horse? Baby, don't hurt me.",
            "The original Konami code was ⬆⬆⬇⬇⬅➡⬅➡\uD83D\uDC0E\uD83C\uDD70. But it had to be modified for North " +
                    "American consoles which did not support the horse button.",
            "Domesticated in central Asia around five thousand years ago, the horse was instrumental to the" +
                    " development of Eurasian civilization. Unlike most other large mammals, it was not farmed for " +
                    "its meat, milk or hides. Instead, the horse was harnessed solely for its incredible strength – " +
                    "to pull plows, vehicles, and most significantly, to carry humans themselves.",
            "The horses in Zelda: Breath of the Wild were modeled after the Short-rumped Padowaks that are common " +
                    "to the Gran Chaco region. This quasi-domesticated breed is know for stopping suddenly while " +
                    "crossing bridges, and bucking its riders while running down hills.",
            "Many runners have never completed the ritual to summon Epona, preferring instead to perpetually " +
                    "handicap themselves on their runs.",
            "Horses with solid coloring are faster, but more disobedient than those with patterned coats. The " +
                    "fastest and most disobedient horses look like trees.",
            "The card game Magic: the Gathering has featured horses in its artwork dating all the way back its first " +
                    "set, Alpha, published in 1993. However it was not until the printing of Magic 2010 that Horse " +
                    "was officially recognized as its own creature type.",
            "Look at my Horse. My Horse is amazing!",
            "Don't look at me, my horse, or my horse's son ever again.",
            "H-O-R-S-E is a basketball variant played by 2 or more players. It involves alternating players taking " +
                    "a shot with their choice of style and location which, if successful, their opponents must " +
                    "attempt to reproduce.",
            "4 out of 5 horses recommend Colgate.",
            "In the 16th century, “horse” was a common adjective describing anything strong, big, or coarse. Along " +
                    "with horseplay, that’s how horseradish got its nick.",
            "The Canada horse, commonly referred to as møøse, is the single most frequent cause of automobile " +
                    "accidents in lumberjack country.",
            "A team of researchers is currently hard at work creating top-tier \"horse facts\". To contribute to " +
                    "the research, please type !addfact <your fact>. \uD83D\uDC0E",
            "The secret code is ⬆⬆⬇⬇⬅➡⬅➡\uD83C\uDD71\uD83C\uDD70.",
            "Twitch reserves partnership for streamers who treat their horses with respect.",
            "Horses can sleep both lying down and standing up.",
            "A horse is a horse, of course. Of course! And no one can talk to a horse, of course. That is, of " +
                    "course, unless the horse is the famous Mister Ed.",
            "Did you hear the one about a pony with a throat infection? It was a little hoarse.",
            "Send your ideas and suggestions to HorseBotXD@gmail.com",
            "With horses like that, who needs enemies?",
            "I memed a meme in time gone by \uD83D\uDC34 When lulz were high and life worth living \uD83D\uDC0E " +
                    "I dreamed that spam would never die \uD83D\uDC34 I dreamed that Mods would be forgiving \uD83D\uDC0E " +
                    "Then I was a pleb and unafraid \uD83D\uDC34 And memes were made and copy/pasted \uD83D\uDC0E " +
                    "There was no sub fee to be paid \uD83D\uDC34 No slow mode on, no dankness wasted",
            "Every good Breath of the Wild run will need a 4-speed horse, either from a Super Smash Bros. Series " +
                    "Link amiibo or a lucky spawn in Hyrule Field.",
            "Horses are the stars of this stream. Everyone else is just bit players.",
            "On the internet, nobody knows you're a horse.",
            "Horses can sleep both lying down and standing up.",
            "With horses like these, who needs enemies?",
            "This above all: To thine own horse be true.",
            "\"I'll get you my pretty, and your little horse, too!\" —The Wizard of Oz original script (c. 1939)",
            "The greatest trick the 4-speed horse ever pulled was convincing the world he didn't exist.",
            "The first rule of Horse Club is: You do not talk about Horse Club.",
            "\"You miss 100 percent of the stam resets you never take.\" —Wayne Horzky",
            "Horse Dancing (also known as Dressage) is the only cross-species artistic event at the summer Olympics.",
            "Horsium is a chemical element with symbol Hs and atomic number 108, named after the German state of " +
                    "Horse. It is a synthetic element (an element that can be created in a laboratory but is not " +
                    "found in nature) and radioactive; the most stable known isotope, 277Hs, has a half-life of " +
                    "approximately 30 seconds. More than 100 atoms of horsium have been synthesized to date.",
            "Many think of horses as majestic land mammals. But to the ascetic monks of Vardhaman Mahavira, they " +
                    "are more than that. Horses are a state of mind; Understanding horse is essential to " +
                    "understanding oneself."
            ));

    public static String fact() {
        return fact(random.nextInt(HORSE_FACTS.size()));
    }

    public static String fact(int i) {
        return HORSE_FACTS.get(Math.abs(i) % HORSE_FACTS.size());
    }
}
