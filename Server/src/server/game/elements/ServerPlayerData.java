package server.game.elements;

import com.badlogic.gdx.math.Vector2;

/**
 * Clase utilizada para enviarle al cliente los datos de este en el servidor
 *
 * @author Eduard Andrei Ghile
 *
 */
public class ServerPlayerData {
    private String username;

    private Vector2 position;

    private int hp;

    private boolean enabled;

    private Vector2 lookingDirection;

    private boolean isMoving;

    private boolean isShooting;

    public ServerPlayerData() {
    }

    public ServerPlayerData(String username, Vector2 position, int hp, boolean enabled, Vector2 lookingDirection, boolean isMoving, boolean isShooting) {
        this.username = username;
        this.position = position;
        this.hp = hp;
        this.enabled = enabled;
        this.lookingDirection = lookingDirection;
        this.isMoving = isMoving;
        this.isShooting = isShooting;
    }

}
