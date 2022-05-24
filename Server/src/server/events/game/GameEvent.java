package server.events.game;

import server.game.elements.Bullet;
import server.game.elements.ServerPlayerData;

import java.util.ArrayList;

/**
 * Clase que se utiliza para enviar los datos de los jugadores y las balas de estos a los clientes
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
