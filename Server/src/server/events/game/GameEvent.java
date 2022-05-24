/*
 * @author Eduard Andrei Ghile
 */
package server.events.game;

import server.game.elements.Bullet;
import server.game.elements.ServerPlayerData;

import java.util.ArrayList;

/**
 * The Class GameEvent.
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
