/*
 * @author Eduard Andrei Ghile
 */
package server.handlers;

import com.esotericsoftware.kryonet.Connection;
import server.model.Lobby;

import java.util.LinkedHashMap;

/**
 * Clase que gestiona y almacena las instancias de todas las partidas.
 *
 * @author Eduard Andrei Ghile
 */
public class LobbyHandler {
    
    /** The Constant INSTANCE. */
    public static final LobbyHandler INSTANCE = new LobbyHandler();

    /**
     * Instantiates a new lobby handler.
     */
    LobbyHandler(){
        lobbies = new LinkedHashMap<String, Lobby>();
    }

    /** The lobbies. */
    private LinkedHashMap<String, Lobby> lobbies;

    /**
     * Gets the lobbies.
     *
     * @return the lobbies
     */
    public LinkedHashMap<String, Lobby> getLobbies() {
        return lobbies;
    }

    /**
     * Finish lobby by connection.
     *
     * @param conn the conn
     */
    public void finishLobbyByConnection(Connection conn){
        Lobby lobbyToDelete = null;
        for(Lobby lobby : lobbies.values()){
            if(lobby.getPlayer1().getConnection() == conn || lobby.getPlayer2().getConnection() == conn){
                lobby.finish();
                lobbyToDelete = lobby;
            }
        }
        this.lobbies.values().remove(lobbyToDelete);
    }

    /**
     * Finish lobby by name.
     *
     * @param lobbyName the lobby name
     */
    public void finishLobbyByName(String lobbyName){
        Lobby lobbyToDelete = null;
        String lobbyKey = null;
        for(Lobby lobby : lobbies.values()){
            try{
                if(lobby.getLobbyName().equalsIgnoreCase(lobbyName)){
                    lobbyKey = lobbyName;
                    lobby.finish();
                    lobbyToDelete = lobby;
                }
            }catch (NullPointerException e){

            }

        }
        this.lobbies.remove(lobbyKey, lobbyToDelete);
    }



}
