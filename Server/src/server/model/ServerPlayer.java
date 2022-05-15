package server.model;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;

public class ServerPlayer {

    private final String username;
    private final Connection connection;

    public boolean moveUp, moveDown, moveLeft, moveRight;

    private float speed;

    private float x;
    private float y;
    private Vector2 position;

    public ServerPlayer(String username, Connection connection) {
        this.username = username;
        this.connection = connection;
        this.position = new Vector2(100,200);
        this.speed = 5F;
    }


    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
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
}
