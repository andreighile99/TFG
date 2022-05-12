package main;

import com.esotericsoftware.kryonet.Client;
import events.lobby.CreateNewLobbyEvent;
import events.lobby.JoinLobbyEvent;
import events.lobby.LobbyCreatedEvent;
import events.lobby.LobbyJoinedEvent;
import listeners.EventListener;

public class MainTest {
    public static void main(String[] args) {
        Client client = new Client();

        //Lobby management
        client.getKryo().register(CreateNewLobbyEvent.class);
        client.getKryo().register(JoinLobbyEvent.class);
        client.getKryo().register(LobbyCreatedEvent.class);
        client.getKryo().register(LobbyJoinedEvent.class);

        client.addListener(new EventListener());

        try {
            client.start();
            client.connect(15000, "localhost", 9998,  9998);
            System.out.println("Conexión con el servidor establecida");
        } catch (Exception e) {
            System.out.println("No se ha podido conectar con el servidor");
        }


        CreateNewLobbyEvent createNewLobbyEvent = new CreateNewLobbyEvent();
        createNewLobbyEvent.lobbyName = "Un pequeño test";
        createNewLobbyEvent.username = "nayorco3";
        client.sendTCP(createNewLobbyEvent);



    }
}
