package WorldRecords;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import java.util.Scanner;

import static WorldRecords.Category.AD;
import static WorldRecords.Category.Any;
import static java.lang.Boolean.FALSE;

/**
 * Created by Aaron Fisher on 5/3/2017.
 */
public class RecordLookup {
    private static final String SPEEDRUN_API = "http://www.speedrun.com/api/v1/";
    private static final String SPEEDRUN_LEADERBOARD = "leaderboards/%s/category/%s";
    private static final String SPEEDRUN_PLAYER = "users/%s";

    public static String leaderboard(Category category, Restriction... restrictions) {
        try {
            InputStream response = new URL(SPEEDRUN_API + String.format(SPEEDRUN_LEADERBOARD,
                    category.game.code,
                    category.code)).openStream();
            Scanner scanner = new Scanner(response);
            JSONObject leaderboard = new JSONObject(scanner.useDelimiter("\\A").next());
            JSONObject data = leaderboard.getJSONObject("data");
            JSONArray runs = data.getJSONArray("runs");
            for (int i = 0; i < runs.length(); i++) {
                JSONObject run = runs.getJSONObject(i).getJSONObject("run");
                boolean isValid = true;
                for (Restriction restriction : restrictions) {
                    if (run.getJSONObject("values").getString(restriction.key).equals(restriction.value)) {
                        isValid = false;
                    }
                }
                if (isValid) {
                    String modifiers = "";
                    for (Restriction restriction : restrictions) {
                        modifiers = modifiers + restriction.description + " ";
                    }
                    return String.format("The best time for %s, %s%s is %s by %s.",
                            category.game.name,
                            modifiers,
                            category.name,
                            formatTime(run.getJSONObject("times").getInt("realtime_t"), FALSE),
                            findPlayer(run.getJSONArray("players").getJSONObject(0).getString("id")));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String findPlayer(String id) throws IOException {
        InputStream response = new URL(SPEEDRUN_API + String.format(SPEEDRUN_PLAYER,
                id)).openStream();
        Scanner scanner = new Scanner(response);
        JSONObject users = new JSONObject(scanner.useDelimiter("\\A").next());
        JSONObject data = users.getJSONObject("data");
        JSONObject names = data.getJSONObject("names");
        return names.getString("international");
    }

    private static String formatTime(int time, boolean padded) {
        if (time > 59) {
            return formatTime(time / 60, false) + ":" + formatTime(time % 60, true);
        }
        return (time < 10 && padded ? "0" : "") + Integer.toString(time, 10);
    }

    @Test
    public void test() {
        System.out.println(leaderboard(AD, Restriction.Amiiboless));
    }
}
