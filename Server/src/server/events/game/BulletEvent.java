/*
 * @author Eduard Andrei Ghile
 */
package server.events.game;

import com.badlogic.gdx.math.Vector2;

/**
 * 
 */
public class BulletEvent {
    
    /**  */
    private String lobbyName;
    
    /**  */
    private String username;
    
    /**  */
    private Vector2 bulletDirection;


    /**
     * 
     */
    public BulletEvent() {
    }

    /**
     * 
     *
     * @return 
     */
    public String getLobbyName() {
        return lobbyName;
    }

    /**
     * 
     *
     * @param lobbyName 
     */
    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    /**
     * 
     *
     * @return 
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     *
     * @param username 
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 
     *
     * @return 
     */
    public Vector2 getBulletDirection() {
        return bulletDirection;
    }

    /**
     * 
     *
     * @param bulletDirection 
     */
    public void setBulletDirection(Vector2 bulletDirection) {
        this.bulletDirection = bulletDirection;
    }
}
