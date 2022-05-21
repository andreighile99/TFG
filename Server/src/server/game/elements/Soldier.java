package server.game.elements;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Soldier {
    private Vector2 position;
    private Rectangle boundRectangle;
    private int hp;

    public Soldier(float x, float y) {
        this.hp = 5;
        this.position = new Vector2(x,y);
        this.boundRectangle = new Rectangle(x,y,10, 10);
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



}
