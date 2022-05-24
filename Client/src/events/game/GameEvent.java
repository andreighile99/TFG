package events.game;

import elements.serverSide.Bullet;
import elements.serverSide.ServerPlayerData;

import java.util.ArrayList;
/**
 * Clase mensajera que se utiliza para enviar datos al servidor
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class GameEvent {

    public ArrayList<ServerPlayerData> players;
    public ArrayList<Bullet> bullets;

    public GameEvent() {
    }


}
