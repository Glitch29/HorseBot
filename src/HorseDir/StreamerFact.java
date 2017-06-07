package HorseDir;

import HorseDir.Channels.Channel;

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
            "If %s seems like he's ignoring chat, it's just because he's in the zone. If it seems like he's not in the zone, it's just because he's reading chat.",
            "One time %s went all the way from the Great Plateau to Rito Village without falling off his horse.",
            "%s has gotten at least one gold split in every single segment.",
            "It's important to be able to perform your backup strats under pressure. That's why %s likes to practice them when he's on WR pace.",
            "%s hasn't accidentally depleted the stamina meter even once this run.",
            "#s has seriously never had that happen to him before.",
            "%s uses dim lighting and low webcam resolution to make the game's graphics really pop in comparison.",
            "%s only resets because he's splitting his time between WR attempts and preparation for the Exit Plateau race next Friday.",
            "Once %s's new controller arrives in the mail, he won't need to worry about that input being eaten.",
            "Most of %s's weird movement patterns are actually advanced RNG manipulation.",
            "%s runs this game on hard mode, where 80 percent of all drops are below average.",
            "Be sure to create a clip if you see %s screwing something up. Clips are the easiest way to catalogue what still needs practicing.",
            "Ask %s about the hitboxes in this game. He's a font of knowledge.",
            "That has literally never happened to %s before.",
            "Nothing's worse than having nature call in the middle of a big run. That's why %s uses a backup strat he can Depend on!",
            "%'s good enough, he's smart enough, and doggone it, people like him!",
            "%s believes that the secret to a fast run lies in knowledge of the game. By exploring everything that could go wrong, he might someday discover what can go right.",
            "If you want to send %s some moral support but you can't find the right words for what is happening, just type !believe channelname in any HorseBot-supported stream.",
            "%s has the city-wide record for this category.",
            "%s could get more WRs. But he prefers purist categories.",
            "%s likes his PBs like he likes his women. Sometimes he'll upload videos of them to Speedrun.com.",
            "%s has the type of resolute attitude needed for this job. Even when he knows he can't WR, he keeps on going. Day after day.",
            "%s sometimes likes to add spice into even the most predictable boss fights. Don't blink because you'll never know what might happen."
    ));

    public static String fact(Channel channel) {
        return fact(channel, random.nextInt(STREAMER_FACT.size()));
    }

    public static String fact(Channel channel, int i) {
        return String.format(STREAMER_FACT.get(Math.abs(i) % STREAMER_FACT.size()), channel.nick());
    }

}
