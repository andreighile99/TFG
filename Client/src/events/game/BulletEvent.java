
package events.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Clase mensajera que se utiliza para enviar datos al servidor.
 *
 * @author Eduard Andrei Ghile
 */
public class BulletEvent {
    
    /** The lobby name. */
    private String lobbyName;
    
    /** The username. */
    private String username;
    
    /** The bullet direction. */
    private Vector2 bulletDirection;


    /**
     * Instantiates a new bullet event.
     */
    public BulletEvent() {
    }

    /**
     * Gets the lobby name.
     *
     * @return the lobby name
     */
    public String getLobbyName() {
        return lobbyName;
    }

    /**
     * Sets the lobby name.
     *
     * @param lobbyName the new lobby name
     */
    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
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
     * Gets the bullet direction.
     *
     * @return the bullet direction
     */
    public Vector2 getBulletDirection() {
        return bulletDirection;
    }

    /**
     * Sets the bullet direction.
     *
     * @param bulletDirection the new bullet direction
     */
    public void setBulletDirection(Vector2 bulletDirection) {
        this.bulletDirection = bulletDirection;
    }
}
