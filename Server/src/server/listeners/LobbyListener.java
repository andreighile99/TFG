package server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import server.handlers.LobbyHandler;
import server.model.Lobby;
import server.model.ServerPlayer;
import server.events.lobby.*;


/**
 * Clase listener para eventos de los lobbys y gestion de las partidas
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class LobbyListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);
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
            }else{
                final LobbyCreatedEvent lobbyCreatedEvent = new LobbyCreatedEvent();
                lobbyCreatedEvent.lobbyName = createNewLobbyEvent.lobbyName;
                lobbyCreatedEvent.code = "01";
                lobbyCreatedEvent.player1 = createNewLobbyEvent.username;
                connection.sendTCP(lobbyCreatedEvent);
                System.out.println("LOG - Sala [ " + createNewLobbyEvent.lobbyName + " ] creada con éxito");

                //Lobby is created with the following properties
                final Lobby lobby = new Lobby();
                lobby.createLobby(new ServerPlayer(createNewLobbyEvent.username, connection));
                LobbyHandler.INSTANCE.getLobbies().put(createNewLobbyEvent.lobbyName, lobby);

                return;
            }
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
            }else if(LobbyHandler.INSTANCE.getLobbies().get(joinLobbyEvent.lobbyName).getPlayer1().getUsername().equals(joinLobbyEvent.username)){
                final LobbyJoinedEvent lobbyJoinedEvent = new LobbyJoinedEvent();
                lobbyJoinedEvent.lobbyName = joinLobbyEvent.lobbyName;
                lobbyJoinedEvent.code = "00";
                lobbyJoinedEvent.description = "LOG - No se puede acceder con nombres de usuario duplicados";
                connection.sendTCP(lobbyJoinedEvent);
                return;
            }

            LobbyHandler.INSTANCE.getLobbies().get(joinLobbyEvent.lobbyName).joinLobby(new ServerPlayer(joinLobbyEvent.username, connection));

            //Two messages one for whoever joins, other message for whoever is waiting in the lobby

            //First message
            final LobbyJoinedEvent lobbyJoinedEvent = new LobbyJoinedEvent();
            lobbyJoinedEvent.lobbyName = joinLobbyEvent.lobbyName;
            lobbyJoinedEvent.code = "01";
            lobbyJoinedEvent.player1 = LobbyHandler.INSTANCE.getLobbies().get(joinLobbyEvent.lobbyName).getPlayer1().getUsername();
            lobbyJoinedEvent.player2 = LobbyHandler.INSTANCE.getLobbies().get(joinLobbyEvent.lobbyName).getPlayer2().getUsername();
            connection.sendTCP(lobbyJoinedEvent);
            System.out.println("LOG - El jugador que ha intentado conectarse pulsando el botón ha sido notificado");

            //Second message
            LobbyHandler.INSTANCE.getLobbies().get(joinLobbyEvent.lobbyName).getPlayer1().getConnection().sendTCP(lobbyJoinedEvent);
            System.out.println("LOG - El jugador que estaba esperando ha sido notificado");
            return;
        }

        else if(object instanceof LobbyStartEvent){
            final LobbyStartEvent lobbyStartEvent = (LobbyStartEvent) object;
            final LobbyStartEvent lobbyStartEventResponse = new LobbyStartEvent();
            //Check lobby existance
            if(LobbyHandler.INSTANCE.getLobbies().getOrDefault(lobbyStartEvent.lobbyName, null) == null){
                lobbyStartEventResponse.code = "00";
                //Notify the player who sent the start (Improbable)
                lobbyStartEventResponse.description = "El lobby no existe";
                connection.sendTCP(lobbyStartEventResponse);
                return;
            }
            //Check if both players are connected
            else if(LobbyHandler.INSTANCE.getLobbies().get(lobbyStartEvent.lobbyName).getPlayer1() == null || LobbyHandler.INSTANCE.getLobbies().get(lobbyStartEvent.lobbyName).getPlayer2() == null){
                lobbyStartEventResponse.code = "00";
                //Notify the player who sent the start (Improbable)
                lobbyStartEventResponse.description = "No puedes comenzar a jugar sin otro jugador mas";
                connection.sendTCP(lobbyStartEventResponse);
                return;
            }else{
                lobbyStartEventResponse.code = "01";
                connection.sendTCP(lobbyStartEventResponse);
                System.out.println("LOG - Notificación a los clientes satisfactoria");
                //Notify both players
                System.out.println("LOG - Se comienza a ejecutar la partida de la sala [ " + lobbyStartEvent.lobbyName + " ]");
                LobbyHandler.INSTANCE.getLobbies().get(lobbyStartEvent.lobbyName).getPlayer1().getConnection().sendTCP(lobbyStartEventResponse);
                LobbyHandler.INSTANCE.getLobbies().get(lobbyStartEvent.lobbyName).getPlayer2().getConnection().sendTCP(lobbyStartEventResponse);
                LobbyHandler.INSTANCE.getLobbies().get(lobbyStartEvent.lobbyName).startLobbyGame(lobbyStartEvent.lobbyName);
                return;
            }
        }
    }
}
