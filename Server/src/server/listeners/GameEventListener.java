package server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import server.events.game.BulletEvent;
import server.events.game.PlayerEvent;
import server.handlers.LobbyHandler;

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
            BulletEvent bulletEvent = (BulletEvent)object;
            LobbyHandler.INSTANCE.getLobbies().get(bulletEvent.getLobbyName()).getGameServer().getServerMap().playerShoots(bulletEvent);
            return;
        }
    }
}
