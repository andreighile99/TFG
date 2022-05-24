package server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import server.events.game.BulletEvent;
import server.events.game.PlayerEvent;
import server.handlers.LobbyHandler;

/**
 * Clase listener para eventos de la partida
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class GameEventListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);
        if(object instanceof PlayerEvent){
            PlayerEvent playerEvent = (PlayerEvent) object;
            //Get the lobby in which a player has moved, get the game server, get the map and update the player position
            LobbyHandler.INSTANCE.getLobbies().get(playerEvent.getLobyName()).getGameServer().getServerMap().updatePlayerPosition(playerEvent);
            return;
        }else if(object instanceof BulletEvent){
            //The client shot a bullet
            BulletEvent bulletEvent = (BulletEvent)object;
            //Make the bullet appear in the server
            LobbyHandler.INSTANCE.getLobbies().get(bulletEvent.getLobbyName()).getGameServer().getServerMap().playerShoots(bulletEvent);
            return;
        }
    }
}
