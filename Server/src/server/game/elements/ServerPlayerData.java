package server.game.elements;

import com.badlogic.gdx.math.Vector2;

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

}
