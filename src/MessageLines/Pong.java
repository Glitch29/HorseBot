package MessageLines;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public class Pong extends AbstractMessageLine {
    public static final String HEADER = "PONG ";
    private String body;

    public Pong (String fullLine) {
        super(fullLine);
        body = fullLine.substring(HEADER.length());
    }

    public Pong (Ping ping) {
        super(HEADER + ping.getBody());
        body = ping.getBody();
    }
}
