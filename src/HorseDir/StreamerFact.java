package HorseDir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * Created by Aaron Fisher on 5/5/2017.
 */
public class StreamerFact {
    private static final Random random = new Random(new Date().getTime());
    public static final ArrayList<String> STREAMER_FACT = new ArrayList<>(Arrays.asList(
            "Don't let %s convince you that he's bad. He's just better at setting expectations for himself than he is at achieving them.",
            "If %s seems like he's ignoring chat, it's just because he's in the zone. Watch him go fast. WOW!",
            "One time %s went all the way from the Great Plateau to Rito Village without falling off his horse.",
            "%s has gotten at least one gold split in every single segment.",
            "It's important to be able to perform your backup strats under pressure. That's why %s likes to practice them when he's on WR pace.",
            "%s hasn't accidentally depleted the stamina meter even once this run.",
            "#s has seriously never had that happen to him before.",
            "%s uses dim lighting and low webcam resolution to make the game's graphics really pop in comparison.",
            "%s only resets because he's splitting his time between WR attempts and preparation for the Exit Plateau race next Friday.",
            "Once %s's new controller arrives in the mail, he won't need to worry about that input being eaten.",
            "Most of %s's weird movement patterns are actually advanced RNG manipulation."
    ));

    public static String fact(Channel channel) {
        return fact(channel, random.nextInt(STREAMER_FACT.size()));
    }

    public static String fact(Channel channel, int i) {
        return String.format(STREAMER_FACT.get(i % STREAMER_FACT.size()), channel.nick);
    }

}
