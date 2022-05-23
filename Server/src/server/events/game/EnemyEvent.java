package server.events.game;

import server.game.elements.EnemyBullet;
import server.game.elements.Soldier;

import java.util.ArrayList;

public class EnemyEvent {
    public ArrayList<Soldier> soldiers;
    public ArrayList<EnemyBullet> enemyBullets;

    public EnemyEvent() {
    }
}
