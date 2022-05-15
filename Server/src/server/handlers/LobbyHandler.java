package server.handlers;

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
}
