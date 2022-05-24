package elements.serverSide;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Clase que se utiliza para recoger los datos del elemento bala enemiga del servidor
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Vector2 getBulletDirection() {
        return bulletDirection;
    }

    public void setBulletDirection(Vector2 bulletDirection) {
        this.bulletDirection = bulletDirection;
    }

    public void updateEnemyBulletRectangle(){
        this.boundRect.x = this.getPosition().x;
        this.boundRect.y = this.getPosition().y;
    }

    public void moveBy(float x, float y){
        if (x != 0 || y != 0) {
            this.position.x += x;
            this.position.y += y;
        }
    }
}
