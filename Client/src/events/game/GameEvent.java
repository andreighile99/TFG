
package events.game;

import elements.serverSide.Bullet;
import elements.serverSide.ServerPlayerData;

import java.util.ArrayList;

/**
 * Clase mensajera que se utiliza para enviar datos al servidor.
 *
 * @author Eduard Andrei Ghile
 */
public class GameEvent {

    /** The players. */
    public ArrayList<ServerPlayerData> players;
    
    /** The bullets. */
    public ArrayList<Bullet> bullets;

    /**
     * Instantiates a new game event.
     */
    public GameEvent() {
    }


}
