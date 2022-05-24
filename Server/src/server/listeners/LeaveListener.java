package server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import server.handlers.LobbyHandler;

/**
 * Clase listener para eventos de desconexión
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class LeaveListener extends Listener {

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
        System.out.println("DesconexiÃ³n detectada, finalizando lobby");
        LobbyHandler.INSTANCE.finishLobbyByConnection(connection);
        return;
    }
}