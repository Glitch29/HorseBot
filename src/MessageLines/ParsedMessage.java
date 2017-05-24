package MessageLines;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Aaron Fisher on 5/17/2017.
 */
public class ParsedMessage {
    String source;
    public String type;
    List<String> data;
    String content;

    public ParsedMessage(String line) {
        data = new ArrayList<>();
        Scanner scanner = new Scanner(line);
        if (!scanner.hasNext()) {
            return;
        }
        String next = scanner.next();
        if (next.startsWith(":")) {
            source = next;
            if (!scanner.hasNext()) {
                return;
            }
            next = scanner.next();
        }
        type = next;
        while (scanner.hasNext()) {
            next = scanner.next();
            if (next.startsWith(":")) {
                content = next + (scanner.hasNextLine() ? scanner.nextLine() : "");
                return;
            }
            data.add(next);
        }
    }
}
