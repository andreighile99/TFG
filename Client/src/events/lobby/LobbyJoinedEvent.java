
package events.lobby;

/**
 * Clase mensajera que se utiliza para enviar datos al servidor.
 *
 * @author Eduard Andrei Ghile
 */
public class LobbyJoinedEvent {
    
    /** The lobby name. */
    public String lobbyName;
    
    /** The code. */
    public String code;
    
    /** The description. */
    public String description;
    
    /** The player 1. */
    public String player1;
    
    /** The player 2. */
    public String player2;
}
