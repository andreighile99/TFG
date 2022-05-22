package server.game.elements;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class EnemyBullet {
    private Rectangle boundRect;
    private Vector2 position;
    private Vector2 bulletDirection;
    private boolean enabled;

    public EnemyBullet() {
    }

    public EnemyBullet(Float positionX, Float positionY, Vector2 bulletDirection) {
        this.enabled = true;
        this.boundRect = new Rectangle(positionX, positionY, 2, 2);
        this.position = new Vector2(positionX, positionY);
        this.bulletDirection = bulletDirection;
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
        this.boundRect.setCenter(this.position.x, this.position.y);
        //this.boundRect.x = this.position.x;
        //this.boundRect.y = this.position.y;
    }

    public void moveBy(float x, float y){
        if (x != 0 || y != 0) {
            this.position.x += x;
            this.position.y += y;
        }
    }
}
