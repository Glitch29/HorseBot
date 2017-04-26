package Connections;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * Created by Aaron Fisher on 4/25/2017.
 */
public interface IrcSession {
    public BufferedReader getReader();
    public BufferedWriter getWriter();
}
