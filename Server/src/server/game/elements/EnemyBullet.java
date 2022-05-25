package server.game.elements;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase utilizada para definir las balas de los enemigos en el servidor
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class EnemyBullet {
    private Rectangle boundRect;
    private Vector2 position;
    private Vector2 bulletDirection;
    private boolean enabled;

    public EnemyBullet() {
    }

    public EnemyBullet(Float positionX, Float positionY, Vector2 bulletDirection) {
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

    public void updateEnemyBulletRectangle(){
        this.boundRect.setCenter(this.position.x, this.position.y);
        //this.boundRect.x = this.position.x;
        //this.boundRect.y = this.position.y;
    }

}
