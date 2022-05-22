package server.game.elements;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
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

    public Vector2 preventOverlap(Polygon anotherPoly)
    {
        //Map the rectangle to a polygon to check overlapping
        Polygon rPoly = new Polygon(new float[] { 0, 0, this.boundRectangle.width, 0, this.boundRectangle.width,
                this.boundRectangle.height, 0, this.boundRectangle.height });
        rPoly.setPosition(this.boundRectangle.x, this.boundRectangle.y);

        // initial test to improve performance
        if ( !rPoly.getBoundingRectangle().overlaps(anotherPoly.getBoundingRectangle()) )
            return null;

        Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();
        boolean polygonOverlap = Intersector.overlapConvexPolygons(rPoly, anotherPoly, mtv);

        if ( !polygonOverlap )
            return null;

        this.moveBy( mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);
        return mtv.normal;
    }

    /** Add x and y to current position */
    private void moveBy (float x, float y) {
        if (x != 0 || y != 0) {
            this.position.x += x;
            this.position.y += y;
        }
    }

    public void updateRectanglePosition(){
        this.boundRectangle.x = this.position.x;
        this.boundRectangle.y = this.position.y;
        this.feet.x = this.position.x + feet.width / 8;
        this.feet.y = this.position.y - feet.height ;//- 0.1f;
    }
}
