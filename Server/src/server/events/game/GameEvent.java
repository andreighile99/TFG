package server.events.game;

import server.game.elements.Bullet;
import server.game.elements.EnemyBullet;
import server.game.elements.Soldier;

import java.util.ArrayList;

public class GameEvent {

    //x1, y1, x2, y2
    public ArrayList<Float> playerPositions;
    public String player1;
    public String player2;
    public ArrayList<Bullet> bullets;
    public ArrayList<Soldier> soldiers;
    public ArrayList<EnemyBullet> enemyBullets;

    public GameEvent() {
    }

}
