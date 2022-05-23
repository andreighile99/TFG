package server.model;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;

public class ServerPlayer {

    private String username;
    private final Connection connection;

    private Rectangle boundRect;
    private Rectangle feet;

    private boolean onGround;

    private Vector2 position;

    private int hp;

    private boolean enabled;

    public ServerPlayer(String username, Connection connection) {
        this.enabled = true;
        this.hp = 100;
        this.username = username;
        this.connection = connection;
    }

    public void setStartingPoint(float x, float y){
        this.position = new Vector2(x,y);
    }

    public void init(){
        this.enabled = true;
        this.hp = 100;
        this.boundRect = new Rectangle(0, 0, 10, 20);
        this.feet = new Rectangle(0, 0, this.boundRect.width / 8, this.boundRect.height / 10);
        this.onGround = false;
    }

    public String getUsername() {
        return username;
    }

    public Connection getConnection() {
        return connection;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getBoundRect() {
        return boundRect;
    }

    public Rectangle getFeet() {
        return feet;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void updateRectanglePosition(){
        this.boundRect.x = this.position.x;
        this.boundRect.y = this.position.y;
        this.feet.x = this.position.x + this.boundRect.width/2;
        this.feet.y = this.position.y - 0.1f;
    }

    public Vector2 preventOverlap(Polygon anotherPoly)
    {
        //Map the rectangle to a polygon to check overlapping
        Polygon rPoly = new Polygon(new float[] { 0, 0, this.getBoundRect().width, 0, this.getBoundRect().width,
                this.getBoundRect().height, 0, this.getBoundRect().height });
        rPoly.setPosition(this.getBoundRect().x, this.getBoundRect().y);

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
