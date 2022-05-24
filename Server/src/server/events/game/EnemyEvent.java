/*
 * @author Eduard Andrei Ghile
 */
package server.events.game;

import server.game.elements.EnemyBullet;
import server.game.elements.Soldier;

import java.util.ArrayList;

/**
 * The Class EnemyEvent.
 */
public class EnemyEvent {
    
    /** The soldiers. */
    public ArrayList<Soldier> soldiers;
    
    /** The enemy bullets. */
    public ArrayList<EnemyBullet> enemyBullets;

    /**
     * Instantiates a new enemy event.
     */
    public EnemyEvent() {
    }
}
