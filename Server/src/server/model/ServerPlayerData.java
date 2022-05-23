package server.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import server.main.Server;

public class ServerPlayerData {
    private String username;

    private Vector2 position;

    private int hp;

    private boolean enabled;

    public ServerPlayerData() {
    }

    public ServerPlayerData(String username, Vector2 position, int hp, boolean enabled) {
        this.username = username;
        this.position = position;
        this.hp = hp;
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
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
