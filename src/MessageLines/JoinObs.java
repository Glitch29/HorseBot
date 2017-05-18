package MessageLines;

import HorseDir.Channel;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public class JoinObs extends AbstractMessageLine {
    public final static String HEADER = "JOIN ";

    JoinObs(String fullLine) {
        super(fullLine);
    }

    public JoinObs(Channel channel) {
        super(HEADER + channel);
    }
}
