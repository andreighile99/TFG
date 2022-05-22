package elements.serverSide;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Soldier {
    private Vector2 position;
    private Rectangle boundRectangle;
    private Rectangle feet;
    private int hp;
    private boolean enabled;
    private float actionCounter;
    private boolean onGround;

    public Soldier() {
    }

    public Soldier(float x, float y) {
        this.onGround = false;
        this.actionCounter = 0;
        this.enabled = true;
        this.hp = 5;
        this.position = new Vector2(x,y);
        this.boundRectangle = new Rectangle(x,y,20, 20);
        this.feet = new Rectangle(x, y, this.boundRectangle.width / 8, this.boundRectangle.height / 10);
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

    public Rectangle getFeet() {
        return feet;
    }

    public void setFeet(Rectangle feet) {
        this.feet = feet;
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

    public float getActionCounter() {
        return actionCounter;
    }

    public void setActionCounter(float actionCounter) {
        this.actionCounter = actionCounter;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

}
