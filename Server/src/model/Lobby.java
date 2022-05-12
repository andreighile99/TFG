package model;

import server.events.lobby.LobbyCreatedEvent;
import server.model.ServerMap;

public class Lobby {
    String lobbyName;

    ServerMap serverMap;
    ServerPlayer player1;
    ServerPlayer player2;

    public void createLobby(final ServerPlayer serverPlayer) {
        if(this.player1 == null){
            final LobbyCreatedEvent lobbyCreatedEvent = new LobbyCreatedEvent();
            lobbyCreatedEvent.lobbyName = this.lobbyName;
            serverPlayer.getConnection().sendTCP(lobbyCreatedEvent);
        }
        this.player1 = serverPlayer;
    }

    public void joinLobby(final ServerPlayer serverPlayer){
        this.player2 = serverPlayer;
    }

    public void startLobby(ServerPlayer player1, ServerPlayer player2){
        this.serverMap = new ServerMap(player1, player2);
    }

    //MUST IMPLEMENT REMOVE


    public ServerPlayer getPlayer1() {
        return player1;
    }

    public void setPlayer1(ServerPlayer player1) {
        this.player1 = player1;
    }

    public ServerPlayer getPlayer2() {
        return player2;
    }

    public void setPlayer2(ServerPlayer player2) {
        this.player2 = player2;
    }
}
