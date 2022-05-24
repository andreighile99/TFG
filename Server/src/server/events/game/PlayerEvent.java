package server.events.game;

/**
 * Clase mensajera que se utiliza para enviar datos al servidor
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class PlayerEvent {

    private String lobyName;
    private String username;
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