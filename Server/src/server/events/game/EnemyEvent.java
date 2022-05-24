package server.events.game;

import server.game.elements.EnemyBullet;
import server.game.elements.Soldier;

import java.util.ArrayList;

/**
 * Clase que se utiliza para enviar los datos de un eneigo al cliente
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
