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

    public ServerPlayer(String username, Connection connection) {
        this.username = username;
        this.connection = connection;
    }

    public ServerPlayer(Connection connection) {
        this.connection = connection;
    }

    public void setStartingPoint(float x, float y){
        this.position = new Vector2(x,y);
    }

    public void init(){
        this.boundRect = new Rectangle(this.position.x, this.position.y, 10, 10);
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

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Rectangle getBoundRect() {
        return boundRect;
    }

    public void setBoundRect(Rectangle boundRect) {
        this.boundRect = boundRect;
    }

    public Rectangle getFeet() {
        return feet;
    }

    public void setFeet(Rectangle feet) {
        this.feet = feet;
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
        this.feet.x = this.position.x + feet.width / 8;
        this.feet.y = this.position.y - feet.height ;//- 0.1f;
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

}
