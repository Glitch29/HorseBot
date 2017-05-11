package WorldRecords;

/**
 * Created by Aaron Fisher on 5/3/2017.
 */
public enum Restriction {
    Amiiboless ("9qj24o31","Amiiboless", false, "values", "e8m0zxl6"),
    USA ("pr184lqn", "USA/NTSC", true, "system", "region");
//    NA ("region", "pr184lqn", true, "system", )
    String[] key;
    String value;
    String description;
    boolean match;
    Restriction(String value, String description, boolean match, String... key) {
        this.key = key;
        this.value = value;
        this.description = description;
        this.match = match;
    }
}
