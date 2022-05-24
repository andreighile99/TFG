package server.events.lobby;

/**
 * Clase mensajera que se utiliza para recibir datos de los clientes
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class LobbyJoinedEvent {
    public String lobbyName;
    public String code;
    public String description;
    public String player1;
    public String player2;
}
