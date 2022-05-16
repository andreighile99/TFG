package server.game.gameServer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import server.game.map.ServerMap;
import server.handlers.ResourceManager;
import server.model.Headless;
import server.model.ServerPlayer;

public class GameServer extends Game {

    private float time;

    private ServerMap serverMap;

    private ServerPlayer player1;
    private ServerPlayer player2;

    public GameServer(ServerMap.onUpdate onUpdate, ServerPlayer player1, ServerPlayer player2) {

        Headless.loadHeadless();
        time = 0;
        this.player1 = player1;
        this.player2 = player2;
        serverMap = new ServerMap(onUpdate, this.player1, this.player2);

    }

    @Override
    public void create() {
        System.out.println("LOG - Servidor de juego levantado");
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        time += deltaTime;
        if (time >= 1) {
            time = 0;
        }
        serverMap.update(deltaTime);
    }

    @Override
    public void dispose() {
    }


    public ServerMap getServerMap() {
        return serverMap;
    }

    public void setServerMap(ServerMap serverMap) {
        this.serverMap = serverMap;
    }
}