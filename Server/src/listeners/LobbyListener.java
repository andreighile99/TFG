package listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import handlers.LobbyHandler;
import model.Lobby;
import model.ServerPlayer;
import server.events.lobby.CreateNewLobbyEvent;
import server.events.lobby.JoinLobbyEvent;
import server.events.lobby.LobbyCreatedEvent;
import server.events.lobby.LobbyJoinedEvent;

public class LobbyListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {

        if(object instanceof CreateNewLobbyEvent) {
            final CreateNewLobbyEvent createNewLobbyEvent = (CreateNewLobbyEvent) object;

            //Return in case lobby is already created
            if(LobbyHandler.INSTANCE.getLobbies().containsKey(createNewLobbyEvent.lobbyName)){
                System.out.println("LOG - Un usuario esta intentando crear un lobby que ya existe");
                final LobbyCreatedEvent lobbyCreatedEvent = new LobbyCreatedEvent();
                lobbyCreatedEvent.lobbyName = createNewLobbyEvent.lobbyName;
                lobbyCreatedEvent.code = "00";
                lobbyCreatedEvent.description = "No se puede crear un lobby que ya existe";
                connection.sendTCP(lobbyCreatedEvent);
                return;
            }

            //Lobby is created with the following properties
            final Lobby lobby = new Lobby();
            lobby.createLobby(new ServerPlayer(createNewLobbyEvent.username, connection));
            LobbyHandler.INSTANCE.getLobbies().put(createNewLobbyEvent.lobbyName, lobby);

            final LobbyCreatedEvent lobbyCreatedEvent = new LobbyCreatedEvent();
            lobbyCreatedEvent.lobbyName = createNewLobbyEvent.lobbyName;
            lobbyCreatedEvent.code = "01";
            lobbyCreatedEvent.player1 = createNewLobbyEvent.username;
            connection.sendTCP(lobbyCreatedEvent);
            System.out.println("LOG - Sala [ " + createNewLobbyEvent.lobbyName + " ] creada con éxito");
        }

        else if(object instanceof JoinLobbyEvent){
            final JoinLobbyEvent joinLobbyEvent = (JoinLobbyEvent) object;

            //Return in case lobby is not created
            if(LobbyHandler.INSTANCE.getLobbies().getOrDefault(joinLobbyEvent.lobbyName, null) == null){
                final LobbyJoinedEvent lobbyJoinedEvent = new LobbyJoinedEvent();
                lobbyJoinedEvent.lobbyName = joinLobbyEvent.lobbyName;
                lobbyJoinedEvent.code = "00";
                lobbyJoinedEvent.description = "LOG - No se puede acceder a un lobby que no existe todavía";
                connection.sendTCP(lobbyJoinedEvent);
                return;
            }

            LobbyHandler.INSTANCE.getLobbies().get(joinLobbyEvent.lobbyName).joinLobby(new ServerPlayer(joinLobbyEvent.username, connection));

            //Two messages one for whoever joins, other message for whoever is waiting in the lobby

            final LobbyJoinedEvent lobbyJoinedEvent = new LobbyJoinedEvent();
            lobbyJoinedEvent.lobbyName = joinLobbyEvent.lobbyName;
            lobbyJoinedEvent.code = "01";
            lobbyJoinedEvent.player1 = LobbyHandler.INSTANCE.getLobbies().get(joinLobbyEvent.lobbyName).getPlayer1().getUsername();
            lobbyJoinedEvent.player2 = LobbyHandler.INSTANCE.getLobbies().get(joinLobbyEvent.lobbyName).getPlayer2().getUsername();

            //Return to the player who initialized the joining
            connection.sendTCP(lobbyJoinedEvent);
            System.out.println("LOG - El jugador que ha intentado conectarse pulsando el botón ha sido notificado");

            //Return to the other player that is waiting for someone to join
            LobbyHandler.INSTANCE.getLobbies().get(joinLobbyEvent.lobbyName).getPlayer1().getConnection().sendTCP(lobbyJoinedEvent);
            System.out.println("LOG - El jugador que estaba esperando ha sido notificado");
        }

    }
}
