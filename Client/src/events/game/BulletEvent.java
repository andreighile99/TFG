package events.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Clase mensajera que se utiliza para enviar datos al servidor
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class BulletEvent {
    private String lobbyName;
    private String username;
    private Vector2 bulletDirection;


    public BulletEvent() {
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Vector2 getBulletDirection() {
        return bulletDirection;
    }

    public void setBulletDirection(Vector2 bulletDirection) {
        this.bulletDirection = bulletDirection;
    }
}
