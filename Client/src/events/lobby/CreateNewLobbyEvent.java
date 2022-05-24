
package events.lobby;

/**
 * Clase mensajera que se utiliza para enviar datos al servidor.
 *
 * @author Eduard Andrei Ghile
 */
public class CreateNewLobbyEvent {	
    
    /** The lobby name. */
    public String lobbyName;
    
    /** The username. */
    public String username;
}
