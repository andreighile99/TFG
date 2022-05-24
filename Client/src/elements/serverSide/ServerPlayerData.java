
package elements.serverSide;

import com.badlogic.gdx.math.Vector2;

/**
 * Clase que se utiliza para recoger los datos del elemento jugador en el servidor.
 *
 * @author Eduard Andrei Ghile
 */
public class ServerPlayerData {
    
    /** The username. */
    private String username;

    /** The position. */
    private Vector2 position;

    /** The hp. */
    private int hp;

    /** The enabled. */
    private boolean enabled;

    public Vector2 lookingDirection;

    public boolean isMoving;


    public boolean isShooting;

    /**
     * Instantiates a new server player data.
     */
    public ServerPlayerData() {
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
     * Gets the position.
     *
     * @return the position
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Checks if is enabled.
     *
     * @return true, if is enabled
     */
    public boolean isEnabled() {
        return enabled;
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
     * Sets the position.
     *
     * @param position the new position
     */
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    /**
     * Gets the hp.
     *
     * @return the hp
     */
    public int getHp() {
        return hp;
    }

    /**
     * Sets the hp.
     *
     * @param hp the new hp
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Sets the enabled.
     *
     * @param enabled the new enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Vector2 getLookingDirection() {
        return lookingDirection;
    }



}
