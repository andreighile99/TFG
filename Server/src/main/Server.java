package main;

import listeners.LobbyListener;
import server.events.lobby.CreateNewLobbyEvent;
import server.events.lobby.JoinLobbyEvent;
import server.events.lobby.LobbyCreatedEvent;
import server.events.lobby.LobbyJoinedEvent;

import java.io.IOException;

public class Server {
    public static Server instance;
    private com.esotericsoftware.kryonet.Server server;

    public static void main(String[] args) {
        Server.instance = new Server();
    }

    public Server() {
        this.server = new com.esotericsoftware.kryonet.Server();

        /** Events register **/

        //Lobby
        this.server.getKryo().register(CreateNewLobbyEvent.class);
        this.server.getKryo().register(JoinLobbyEvent.class);
        this.server.getKryo().register(LobbyCreatedEvent.class);
        this.server.getKryo().register(LobbyJoinedEvent.class);



        /** Listeners **/

        //Lobby
        this.server.addListener(new LobbyListener());


        //LobbyUpdateHandler.INSTANCE.start();

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

    public com.esotericsoftware.kryonet.Server getServer() {
        return server;
    }
}


