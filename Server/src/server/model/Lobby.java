package server.model;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import server.events.game.GameEvent;
import server.events.lobby.FinishLobby;
import server.game.gameServer.GameServer;
import server.game.map.ServerMap;

public class Lobby implements ServerMap.onUpdate {
    private String lobbyName;

    private GameServer gameServer;
    private ServerPlayer player1;
    private ServerPlayer player2;



    public void createLobby(final ServerPlayer serverPlayer) {
        this.player1 = serverPlayer;
    }

    public void joinLobby(final ServerPlayer serverPlayer){
        this.player2 = serverPlayer;
    }

    public void startLobbyGame(String lobbyName){
        this.lobbyName = lobbyName;
        this.gameServer = new GameServer(this.lobbyName,this, player1, player2);
        //Headless Application
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        //60 Renders per second
        conf.updatesPerSecond = 60;
        //Start the game server
        new HeadlessApplication(this.gameServer, conf);

    }

    @Override
    public void sendToBothClients(Object object) {
        this.player1.getConnection().sendUDP(object);
        this.player2.getConnection().sendUDP(object);
    }


    public void sendToBothClients(FinishLobby finishLobby){
        try{
            this.player1.getConnection().sendTCP(finishLobby);
        }catch (NullPointerException e){

        }
        try{
            this.player2.getConnection().sendTCP(finishLobby);
        }catch (NullPointerException e){

        }

    }


    public ServerPlayer getPlayerByUsername(String username){
        if(this.player1.getUsername().equalsIgnoreCase(username)){
            return player1;
        }else{
            return player2;
        }
    }

    //End the instance and prepare it for deletion
    public void finish(){
        System.out.println("LOG - Terminando el lobby y notificando a los clientes");
        FinishLobby finishLobby = new FinishLobby();
        finishLobby.code = "00";
        this.sendToBothClients(finishLobby);
        try{
            //Close both connections
            this.player1.getConnection().close();
            this.player2.getConnection().close();
            this.gameServer.dispose();
        }catch (NullPointerException e){

        }

    }

    public String getLobbyName() {
        return lobbyName;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    public GameServer getGameServer() {
        return gameServer;
    }

    public void setGameServer(GameServer gameServer) {
        this.gameServer = gameServer;
    }

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
