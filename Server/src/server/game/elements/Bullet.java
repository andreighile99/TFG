package server.game.elements;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private Rectangle boundRect;
    private Vector2 position;
    private Vector2 bulletDirection;
    private boolean enabled;

    public Bullet() {
    }

    public Bullet(Float positionX, Float positionY, Vector2 bulletDirection) {
        this.enabled = true;
        this.boundRect = new Rectangle(positionX, positionY, 2, 2);
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


    public void updateBulletRectangle(){
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
