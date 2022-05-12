package model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Connection;

public class ServerPlayer {

    private final String username;
    private final Connection connection;

    public boolean moveUp, moveDown, moveLeft, moveRight;

    private float speed;

    private float x;
    private float y;

    public ServerPlayer(String username, Connection connection) {
        this.username = username;
        this.connection = connection;

        this.speed = 5F;
    }

    public void update() {
        if(this.moveLeft) {
            this.x -= this.speed;
        } else if(this.moveRight) {
            this.x += this.speed;
        }

        if(this.moveUp) {
            this.y += this.speed;
        } else if(this.moveDown) {
            this.y -= this.speed;
        }
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

}
