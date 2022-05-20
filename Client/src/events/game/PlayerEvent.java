package events.game;


import com.badlogic.gdx.math.Vector2;

/***
 * Whenever player press a button from a keyboard this message should be send.
 */
public class PlayerEvent {

    private String lobyName;

    /** Player username */
    private String username;

    /**
     * The direction that player wants to move. Server will check the direction and
     * let player move if its possible.
     */
    private DIRECTION direction;



    public enum DIRECTION {
        LEFT, RIGHT, UP
    }

    public PlayerEvent() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public DIRECTION getDirection() {
        return direction;
    }

    public void setDirection(DIRECTION direction) {
        this.direction = direction;
    }

    public String getLobyName() {
        return lobyName;
    }

    public void setLobyName(String lobyName) {
        this.lobyName = lobyName;
    }
}