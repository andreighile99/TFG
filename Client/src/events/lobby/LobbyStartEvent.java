
package events.lobby;

/**
 * Clase mensajera que se utiliza para enviar datos al servidor.
 *
 * @author Eduard Andrei Ghile
 */
public class LobbyStartEvent {
    
    /** The lobby name. */
    public String lobbyName;
    
    /** The code. */
    public String code;
    
    /** The description. */
    public String description;
}
