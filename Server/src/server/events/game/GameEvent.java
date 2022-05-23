package server.events.game;

import server.game.elements.Bullet;
import server.game.elements.ServerPlayerData;

import java.util.ArrayList;

public class GameEvent {

    public ArrayList<ServerPlayerData> players;
    public ArrayList<Bullet> bullets;

    public GameEvent() {
    }

}
