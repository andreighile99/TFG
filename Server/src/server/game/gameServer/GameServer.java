package server.game.gameServer;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import server.events.game.SwitchLevel;
import server.events.lobby.FinishLobby;
import server.game.map.ServerMap;
import server.handlers.LobbyHandler;
import server.model.Headless;
import server.model.ServerPlayer;

public class GameServer extends Game implements ServerMap.notifyGameServer{

    private String lobbyName;
    private int level;

    private ServerMap serverMap;
    private ServerMap.onUpdate serverMapOnUpdate;

    private ServerPlayer player1;
    private ServerPlayer player2;

    public GameServer(String lobbyName, ServerMap.onUpdate onUpdate, ServerPlayer player1, ServerPlayer player2) {

        Headless.loadHeadless();
        this.lobbyName = lobbyName;
        this.serverMapOnUpdate = onUpdate;
        this.level = 1;
        this.player1 = player1;
        this.player2 = player2;
        serverMap = new ServerMap(onUpdate, this, this.player1, this.player2, level);

    }

    @Override
    public void create() {
        System.out.println("LOG - Servidor de juego levantado");
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        serverMap.update(deltaTime);
    }

    @Override
    public void dispose() {
    }


    public ServerMap getServerMap() {
        return serverMap;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    @Override
    public void switchLevel() {
        this.setLevel(this.getLevel()+1);

        if(this.getLevel() <= 2){
            SwitchLevel switchLevel = new SwitchLevel();
            this.player1.getConnection().sendTCP(switchLevel);
            this.player2.getConnection().sendTCP(switchLevel);
            this.serverMap = new ServerMap(serverMapOnUpdate, this, this.player1, this.player2, level);
        }else{
            FinishLobby finishLobby = new FinishLobby();
            finishLobby.code = "01";
            this.player1.getConnection().sendTCP(finishLobby);
            this.player2.getConnection().sendTCP(finishLobby);
            LobbyHandler.INSTANCE.finishLobbyByName(this.lobbyName);
        }

    }

    @Override
    public void endLobby() {
        FinishLobby finishLobby = new FinishLobby();
        finishLobby.code = "01";
        this.player1.getConnection().sendTCP(finishLobby);
        this.player2.getConnection().sendTCP(finishLobby);
        LobbyHandler.INSTANCE.finishLobbyByName(this.lobbyName);
    }

}