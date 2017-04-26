package MessageLines;

import HorseDir.Channel;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public class Join extends AbstractMessageLine {
    public final static String HEADER = "JOIN ";

    Join(String fullLine) {
        super(fullLine);
    }

    public Join(Channel channel) {
        super(HEADER + channel);
    }
}
