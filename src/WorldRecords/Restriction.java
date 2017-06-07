package WorldRecords;

/**
 * Created by Aaron Fisher on 5/3/2017.
 */
public enum Restriction {
    Amiiboless ("a","Amiiboless", "var-e8m0zxl6=jq6vxwj1");

    String description;
    public String command;
    public String parameter;

    Restriction(String command, String description, String parameter) {
        this.description = description;
        this.command = command;
        this.parameter = parameter;
    }
}
