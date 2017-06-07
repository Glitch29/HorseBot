package WorldRecords;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static WorldRecords.Category.AMQ;
import static java.lang.Boolean.FALSE;

/**
 * Created by Aaron Fisher on 5/3/2017.
 */
public class RecordLookup {
    private static final String HORSE = "\uD83D\uDC0E";

    private static final String SPEEDRUN_API = "http://www.speedrun.com/api/v1/";
    private static final String SPEEDRUN_LEADERBOARD = "leaderboards/%s/category/%s";
    private static final String SPEEDRUN_PLAYER = "users/%s";
    private static final String SPEEDRUN_PLAYER_FROM_TWITCH = "users?twitch=%s";
    private static final String SPEEDRUN_GAME = "games/%s";
    private static final String SPEEDRUN_CATEGORY = "categories/%s";
    private static final String SPEEDRUN_PBS = "users/%s/personal-bests";

    private static final String LEADERBOARD_RESPONSE = "The best time for %s, %s%s is %s by %s.";
    private static final String PB_RESPONSE = "%s has achieved these PBs over the past 90 days:";
    private static final String PB_NULL_RESPONSE = "No PBs found for %s within the past 90 days.";

    private static final long THREE_MONTHS = 1000L * 60L * 60L * 24L * 90L;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final int RADIX = 10;

    public static String leaderboard(Category category, Restriction... restrictions) {
        String parameters = "";
        for (int i = 0; i < restrictions.length; i++) {
            parameters = parameters + (i == 0 ? "?" : ",") + restrictions[i].parameter;
        }

        try (InputStream response = new URL(SPEEDRUN_API + String.format(SPEEDRUN_LEADERBOARD + parameters,
                category.game.code,
                category.categoryId)).openStream())
        {
            Scanner scanner = new Scanner(response);
            JSONObject leaderboard = new JSONObject(scanner.useDelimiter("\\A").next());
            JSONObject data = leaderboard.getJSONObject("data");
            JSONArray runs = data.getJSONArray("runs");
            for (int i = 0; i < runs.length(); i++) {
                JSONObject run = runs.getJSONObject(i).getJSONObject("run");
                String modifiers = "";
                for (Restriction restriction : restrictions) {
                    modifiers = modifiers + restriction.description + " ";
                }
                return String.format(LEADERBOARD_RESPONSE,
                        category.game.name,
                        modifiers,
                        category.name,
                        formatTime(run.getJSONObject("times").getInt("realtime_t"), FALSE),
                        findPlayerName(run.getJSONArray("players").getJSONObject(0).getString("id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getPBbyName(String name) {
        if (name.toLowerCase().equals("glitch29")) {
            return "Glitch29 has achieved these PBs over the past 90 days: \uD83D\uDC0E IRL 10k run 1:03:12";
        }
        try{
            String id = findPlayerID(name);
            if (id.isEmpty()) {
                return "Player not found.";
            }
            return getPBs(id) + (name.equals("spades") ? " \uD83D\uDC0E The Legend of Zelda: Breath of the Wild 100 Baked Apples RTA 1:17:39" : "");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getPBbyTwitch(String twitch) {
        try{
            String id = findTwitchID(twitch);
            if (id.isEmpty()) {
                return "Player not found.";
            }
            return getPBs(id);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getPBs(String id) {
        try (InputStream response = new URL(SPEEDRUN_API + String.format(SPEEDRUN_PBS, id)).openStream())
        {
            StringBuilder stringBuilder = new StringBuilder();
            JSONArray runs = getArray(
                new JSONObject(new Scanner(response).useDelimiter("\\A").next()),
                "data");
            for (int i = 0; i < runs.length(); i++) {
                try {
                    JSONObject run = runs.getJSONObject(i);
                    Long cutoff = new Date().getTime() - THREE_MONTHS;
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(getString(run, "run", "date"));
                    if (date.getTime() > cutoff) {
                        stringBuilder.append(String.format(" %s %s %s %s",
                                HORSE,
                                findGameName(getString(run, "run", "game")),
                                findCategoryName(getString(run, "run", "category")),
                                formatTime(getInt(run, "run", "times", "realtime_t"), false)
                        ));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String playerName = findPlayerName(id);
            if (stringBuilder.length() == 0) {
                return String.format(PB_NULL_RESPONSE, playerName);
            }
            return String.format(PB_RESPONSE, playerName) + stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String findPlayerName(String id) throws IOException {
        return findName(id, SPEEDRUN_PLAYER, "data", "names", "international");
    }

    private static String findPlayerID(String name) throws IOException {
        return findName(name, SPEEDRUN_PLAYER, "data", "id");
    }

    private static String findTwitchID(String twitch) throws IOException {
        try (InputStream response = new URL(SPEEDRUN_API + String.format(SPEEDRUN_PLAYER_FROM_TWITCH, twitch)).openStream()) {
            JSONObject object = new JSONObject(new Scanner(response).useDelimiter("\\A").next());
            object = object.getJSONArray("data").getJSONObject(0);
            return getString(object, "id");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    private static String findGameName(String id) throws IOException {
        return findName(id, SPEEDRUN_GAME, "data", "names", "international");
    }

    private static String findCategoryName(String id) throws IOException {
        return findName(id, SPEEDRUN_CATEGORY, "data", "name");
    }

    private static String findName(String id, String apiFormat, String... tree) {
        try (InputStream response = new URL(SPEEDRUN_API + String.format(apiFormat, id)).openStream()) {
            return getString(new JSONObject(new Scanner(response).useDelimiter("\\A").next())
                    ,tree);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String formatTime(int time, boolean padded) {
        if (time >= SECONDS_PER_MINUTE) {
            return formatTime(time / SECONDS_PER_MINUTE, false) + ":" + formatTime(time % SECONDS_PER_MINUTE, true);
        }
        return (time < RADIX && padded ? "0" : "") + Integer.toString(time, RADIX);
    }

    private static String getString(JSONObject jsonObject, String... tree) {
        for (int i = 0; i < tree.length - 1; i++) {
            jsonObject = jsonObject.getJSONObject(tree[i]);
        }
        return jsonObject.getString(tree[tree.length - 1]);
    }

    private static int getInt(JSONObject jsonObject, String... tree) {
        for (int i = 0; i < tree.length - 1; i++) {
            jsonObject = jsonObject.getJSONObject(tree[i]);
        }
        return jsonObject.getInt(tree[tree.length - 1]);
    }

    private static JSONArray getArray(JSONObject jsonObject, String... tree) {
        for (int i = 0; i < tree.length - 1; i++) {
            jsonObject = jsonObject.getJSONObject(tree[i]);
        }
        return jsonObject.getJSONArray(tree[tree.length - 1]);
    }

    @Test
    public void testJgigaPBs() {
        System.out.println(getPBs("y8dwy1gj"));
    }

    @Test
    public void testJgigaID() throws IOException {
        System.out.println(findPlayerID("Jgiga"));
    }

    @Test
    public void testAndyByName() {
        System.out.println(getPBbyName("Andy"));
    }

    @Test
    public void testSpoodlesByTwitch() {
        System.out.println(getPBbyTwitch("spades_live"));
    }
}
