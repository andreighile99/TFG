
package events.game;

import elements.serverSide.EnemyBullet;
import elements.serverSide.Soldier;

import java.util.ArrayList;

/**
 * Clase mensajera que se utiliza para enviar datos al servidor.
 *
 * @author Eduard Andrei Ghile
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
