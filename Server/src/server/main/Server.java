package server.main;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import server.events.game.PositionEvent;
import server.game.gameServer.GameServer;
import server.listeners.GameEventListener;
import server.listeners.LobbyListener;
import server.events.game.GameEvent;
import server.events.lobby.*;

import java.io.IOException;
import java.util.ArrayList;

public class Server {
    public static Server instance;

    private com.esotericsoftware.kryonet.Server server;

    public static void main(String[] args) {
        Server.instance = new Server();
    }

    public Server() {
        this.server = new com.esotericsoftware.kryonet.Server();
        registerClasses(server);
        addListeners(server);

        this.bindServer(9998, 9998);
    }

    public void bindServer(final int tcpPort, final int udpPort) {
        this.server.start();
        try {
            this.server.bind(tcpPort, udpPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[OK] El servidor está levantado");

    }


    public void addListeners(com.esotericsoftware.kryonet.Server server){
        /** Listeners **/

        //Lobby
        this.server.addListener(new LobbyListener());

        //Game events
        this.server.addListener(new GameEventListener());
    }

    public void registerClasses(com.esotericsoftware.kryonet.Server server){
        /** Events register **/

        //Lobby
        this.server.getKryo().register(CreateNewLobbyEvent.class);
        this.server.getKryo().register(JoinLobbyEvent.class);
        this.server.getKryo().register(LobbyCreatedEvent.class);
        this.server.getKryo().register(LobbyJoinedEvent.class);
        this.server.getKryo().register(LobbyStartEvent.class);

        //Game events
        this.server.getKryo().register(GameEvent.class);
        this.server.getKryo().register(PositionEvent.class);
        this.server.getKryo().register(PositionEvent.DIRECTION.class);

        //Common
        this.server.getKryo().register(ArrayList.class);

    }
    public com.esotericsoftware.kryonet.Server getServer() {
        return server;
    }
}


