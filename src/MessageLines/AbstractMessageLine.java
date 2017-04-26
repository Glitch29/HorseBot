package MessageLines;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public abstract class AbstractMessageLine {
    private String fullLine;

    AbstractMessageLine(String fullLine) {
        this.fullLine = fullLine;
    }

    @Override
    public String toString() {
        return fullLine;
    }
}
