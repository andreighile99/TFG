/*
 * @author Eduard Andrei Ghile
 */
package server.events.lobby;

/**
 * Clase mensajera que se utiliza para recibir datos de los clientes.
 *
 * @author Eduard Andrei Ghile
 */
public class JoinLobbyEvent {
    
    /** The lobby name. */
    public String lobbyName;
    
    /** The username. */
    public String username;
}

