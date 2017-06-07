package HorseLogs.Trackers;

/**
 * Created by Aaron Fisher on 5/19/2017.
 */
public enum Tracker {
    DEATHS ("DeathLog.txt"),
    MURDERS ("MurderLog.txt"),
    PETA ("Peta.txt"),
    ICERIVER ("IceRiver.txt"),
    CACTUS ("Cactus.txt"),
    SPIDER ("Spider.txt"),
    EXPOSURE ("Exposure.txt"),
    AMIIBO ("Amiibo.txt");

    public String fileName;

    Tracker(String fileName) {
        this.fileName = fileName;
    }
}
