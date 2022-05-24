package events.game;

import elements.serverSide.EnemyBullet;
import elements.serverSide.Soldier;

import java.util.ArrayList;

/**
 * Clase mensajera que se utiliza para enviar datos al servidor
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class EnemyEvent {
    public ArrayList<Soldier> soldiers;
    public ArrayList<EnemyBullet> enemyBullets;

    public EnemyEvent() {
    }
}
