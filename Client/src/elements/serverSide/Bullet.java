package elements.serverSide;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase que se utiliza para recoger los datos del elemento bala del servidor
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class Bullet {
    private Rectangle boundRect;
    private Vector2 position;
    private Vector2 bulletDirection;
    private boolean enabled;

    public Bullet() {
    }

    public Rectangle getBoundRect() {
        return boundRect;
    }

    public void setBoundRect(Rectangle boundRect) {
        this.boundRect = boundRect;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getBulletDirection() {
        return bulletDirection;
    }

    public void setBulletDirection(Vector2 bulletDirection) {
        this.bulletDirection = bulletDirection;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
