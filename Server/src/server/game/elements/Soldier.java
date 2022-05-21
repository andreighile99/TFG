package server.game.elements;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Soldier {
    private Vector2 position;
    private Rectangle boundRectangle;
    private int hp;
    private boolean enabled;

    public Soldier() {
    }

    public Soldier(float x, float y) {
        this.enabled = true;
        this.hp = 5;
        this.position = new Vector2(x,y);
        this.boundRectangle = new Rectangle(x,y,20, 20);
    }

    public void updateSoldierPosition(){
        Vector2 center = this.boundRectangle.getCenter(this.position);
        this.boundRectangle.setPosition(center.x, center.y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Rectangle getBoundRectangle() {
        return boundRectangle;
    }

    public void setBoundRectangle(Rectangle boundRectangle) {
        this.boundRectangle = boundRectangle;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
