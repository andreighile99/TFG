package server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import server.events.game.PositionEvent;
import server.handlers.LobbyHandler;

public class GameEventListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof PositionEvent){
            System.out.println("He recibido una nueva posición");
            PositionEvent positionEvent = (PositionEvent) object;
            //Get the lobby in which a player has moved, get the game server, get the map and update the player position
            LobbyHandler.INSTANCE.getLobbies().get(positionEvent.getLobyName()).getGameServer().getServerMap().updatePlayerPosition(positionEvent);
            return;
        }
        super.received(connection, object);
    }
}
