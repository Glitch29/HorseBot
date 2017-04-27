package Adventures;

/**
 * Created by Aaron Fisher on 4/26/2017.
 */
public class Player {
    public String name;
    public PlayerStatus status;

    public Player(String name) {
        this.name = name;
        status = PlayerStatus.ACTIVE;
    }
}
