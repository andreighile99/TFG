package server.handlers;

import com.esotericsoftware.kryonet.Connection;
import server.model.Lobby;

import java.util.LinkedHashMap;

public class LobbyHandler {
    public static final LobbyHandler INSTANCE = new LobbyHandler();

    LobbyHandler(){
        lobbies = new LinkedHashMap<String, Lobby>();
    }

    private LinkedHashMap<String, Lobby> lobbies;

    public LinkedHashMap<String, Lobby> getLobbies() {
        return lobbies;
    }

    public void setLobbies(LinkedHashMap<String, Lobby> lobbies) {
        this.lobbies = lobbies;
    }

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


}
