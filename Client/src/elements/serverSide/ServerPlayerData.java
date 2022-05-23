package elements.serverSide;

import com.badlogic.gdx.math.Vector2;

public class ServerPlayerData {
    private String username;

    private Vector2 position;

    private int hp;

    private boolean enabled;

    public ServerPlayerData() {
    }

    public String getUsername() {
        return username;
    }


    public Vector2 getPosition() {
        return position;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
