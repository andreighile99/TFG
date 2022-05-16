package server.model;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;

public class ServerPlayer {

    private final String username;
    private final Connection connection;

    private float speed;

    private Vector2 position;

    public ServerPlayer(String username, Connection connection) {
        this.username = username;
        this.connection = connection;
        this.position = new Vector2(16,168);
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
