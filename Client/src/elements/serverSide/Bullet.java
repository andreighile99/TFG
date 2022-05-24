
package elements.serverSide;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase que se utiliza para recoger los datos del elemento bala del servidor.
 *
 * @author Eduard Andrei Ghile
 */
public class Bullet {
    
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
     * Gets the bound rect.
     *
     * @return the bound rect
     */
    public Rectangle getBoundRect() {
        return boundRect;
    }

    /**
     * Sets the bound rect.
     *
     * @param boundRect the new bound rect
     */
    public void setBoundRect(Rectangle boundRect) {
        this.boundRect = boundRect;
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
     * Sets the position.
     *
     * @param position the new position
     */
    public void setPosition(Vector2 position) {
        this.position = position;
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

    /**
     * Checks if is enabled.
     *
     * @return true, if is enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled.
     *
     * @param enabled the new enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
