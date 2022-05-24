
package elements.serverSide;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase que se utiliza para recoger los datos del Soldado en el servidor.
 *
 * @author Eduard Andrei Ghile
 */
public class Soldier {
    
    /** The position. */
    private Vector2 position;
    
    /** The bound rectangle. */
    private Rectangle boundRectangle;
    
    /** The feet. */
    private Rectangle feet;
    
    /** The hp. */
    private int hp;
    
    /** The enabled. */
    private boolean enabled;
    
    /** The action counter. */
    private float actionCounter;
    
    /** The on ground. */
    private boolean onGround;

    /**
     * Instantiates a new soldier.
     */
    public Soldier() {
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
     * Gets the bound rectangle.
     *
     * @return the bound rectangle
     */
    public Rectangle getBoundRectangle() {
        return boundRectangle;
    }

    /**
     * Sets the bound rectangle.
     *
     * @param boundRectangle the new bound rectangle
     */
    public void setBoundRectangle(Rectangle boundRectangle) {
        this.boundRectangle = boundRectangle;
    }

    /**
     * Gets the feet.
     *
     * @return the feet
     */
    public Rectangle getFeet() {
        return feet;
    }

    /**
     * Sets the feet.
     *
     * @param feet the new feet
     */
    public void setFeet(Rectangle feet) {
        this.feet = feet;
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

    /**
     * Gets the action counter.
     *
     * @return the action counter
     */
    public float getActionCounter() {
        return actionCounter;
    }

    /**
     * Sets the action counter.
     *
     * @param actionCounter the new action counter
     */
    public void setActionCounter(float actionCounter) {
        this.actionCounter = actionCounter;
    }

    /**
     * Checks if is on ground.
     *
     * @return true, if is on ground
     */
    public boolean isOnGround() {
        return onGround;
    }

    /**
     * Sets the on ground.
     *
     * @param onGround the new on ground
     */
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

}
