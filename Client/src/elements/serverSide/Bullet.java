
package elements.serverSide;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase que se utiliza para recoger los datos del elemento bala del servidor.
 *
 * @author Eduard Andrei Ghile
 */
public class Bullet {

    /** The username of the player who shot **/
    private String userWhoShot;
    
    /** The bound rect. */
    private Rectangle boundRect;
    
    /** The position. */
    private Vector2 position;
    
    /** The bullet direction. */
    private Vector2 bulletDirection;
    
    /** The enabled. */
    private boolean enabled;

    /**
     * Instantiates a new bullet.
     */
    public Bullet() {
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

}
