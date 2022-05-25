package server.game.elements;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase utilizada para definir las balas de los clientes en el servidor
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class Bullet {
    private String userWhoShot;
    private Rectangle boundRect;
    private Vector2 position;
    private Vector2 bulletDirection;
    private boolean enabled;

    public Bullet() {
    }

    public Bullet(String userWhoShot, Float positionX, Float positionY, Vector2 bulletDirection) {
        this.userWhoShot = userWhoShot;
        this.enabled = true;
        this.boundRect = new Rectangle(positionX, positionY, 10, 10);
        this.position = new Vector2(positionX, positionY);
        this.bulletDirection = bulletDirection;
    }

    public Rectangle getBoundRect() {
        return boundRect;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Vector2 getBulletDirection() {
        return bulletDirection;
    }

    public String getUserWhoShot() {
        return userWhoShot;
    }

    public void updateBulletRectangle(){
        this.boundRect.setCenter(this.position.x, this.position.y);
        //this.boundRect.x = this.position.x;
        //this.boundRect.y = this.position.y;
    }

}
