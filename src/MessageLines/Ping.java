package MessageLines;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public class Ping extends AbstractMessageLine {
    public static final String HEADER = "PING ";
    private String body;

    public Ping (String fullLine) {
        super(fullLine);
        body = fullLine.substring(HEADER.length());
    }

    public String getBody() {
        return body;
    }
}
