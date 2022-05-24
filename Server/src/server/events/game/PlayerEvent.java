/*
 * @author Eduard Andrei Ghile
 */
package server.events.game;

/**
 * The Class PlayerEvent.
 */
public class PlayerEvent {

    /** The loby name. */
    private String lobyName;
    
    /** The username. */
    private String username;
    
    /** The direction. */
    private DIRECTION direction;



    /**
     * The Enum DIRECTION.
     */
    public enum DIRECTION {
        
        /** The left. */
        LEFT, 
 
 /** The right. */
 RIGHT, 
 
 /** The up. */
 UP
    }

    /**
     * Instantiates a new player event.
     */
    public PlayerEvent() {
    }


    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the direction.
     *
     * @return the direction
     */
    public DIRECTION getDirection() {
        return direction;
    }

    /**
     * Sets the direction.
     *
     * @param direction the new direction
     */
    public void setDirection(DIRECTION direction) {
        this.direction = direction;
    }

    /**
     * Gets the loby name.
     *
     * @return the loby name
     */
    public String getLobyName() {
        return lobyName;
    }

    /**
     * Sets the loby name.
     *
     * @param lobyName the new loby name
     */
    public void setLobyName(String lobyName) {
        this.lobyName = lobyName;
    }
}