package events.lobby;

/**
 * Clase mensajera que se utiliza para enviar datos al servidor
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
