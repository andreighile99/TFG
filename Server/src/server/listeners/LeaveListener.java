package server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import server.handlers.LobbyHandler;
import server.model.ServerPlayer;

public class LeaveListener extends Listener {

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
        System.out.println("Desconexión detectada, finalizando lobby");
        LobbyHandler.INSTANCE.finishLobbyByConnection(connection);
        return;
    }
}