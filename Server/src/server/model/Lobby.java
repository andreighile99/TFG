package server.model;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import server.events.game.GameEvent;
import server.events.lobby.LobbyCreatedEvent;
import server.game.gameServer.GameServer;
import server.game.map.ServerMap;

public class Lobby implements ServerMap.onUpdate {
    String lobbyName;

    GameServer gameServer;
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

    public void startLobbyGame(){
        this.gameServer = new GameServer(this, player1, player2);
        //¿Quizá tenga que encerrar esto en un thread cada vez?

        //Headless Application
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        //60 Renders per second
        conf.updatesPerSecond = 60;
        //Start the game server
        new HeadlessApplication(this.gameServer, conf);

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

    @Override
    public void sendToBothClients(GameEvent gameEvent) {
        this.player1.getConnection().sendUDP(gameEvent);
        this.player2.getConnection().sendUDP(gameEvent);
    }

    public ServerPlayer getPlayerByUsername(String username){
        if(this.player1.getUsername().equalsIgnoreCase(username)){
            return player1;
        }else{
            return player2;
        }
    }

    public GameServer getGameServer() {
        return gameServer;
    }

    public void setGameServer(GameServer gameServer) {
        this.gameServer = gameServer;
    }
}
