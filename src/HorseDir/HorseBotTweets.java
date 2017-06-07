package HorseDir;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * Created by Aaron Fisher on 6/5/2017.
 */
public class HorseBotTweets {
    public static void sendTweet(String body) {
        Twitter twitter = TwitterFactory.getSingleton();
        try {
            Status status = twitter.updateStatus(body);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
