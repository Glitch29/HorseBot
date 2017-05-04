package WorldRecords;

/**
 * Created by Aaron Fisher on 5/3/2017.
 */
public enum Restriction {
    Amiiboless ("e8m0zxl6","9qj24o31","Amiiboless");
    String key;
    String value;
    String description;
    Restriction(String key, String value, String description) {
        this.key = key;
        this.value = value;
        this.description = description;
    }
}
