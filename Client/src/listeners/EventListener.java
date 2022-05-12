package listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import events.lobby.LobbyCreatedEvent;
import events.lobby.LobbyJoinedEvent;
import main.MontessoriSlug;
import screens.LobbyScreen;

public class EventListener extends Listener {

    @Override
    public void received(Connection connection, final Object object) {

        // Join success
        if(object instanceof LobbyCreatedEvent) {
            Gdx.app.postRunnable(()->{
                LobbyCreatedEvent lobbyCreatedEvent = (LobbyCreatedEvent) object;
                //System.out.println("------------------------------------------------------");
                //System.out.println("SE HA CREADO EL LOBBY " + lobbyCreatedEvent.lobbyName);
                MontessoriSlug.getInstance().setScreen(new LobbyScreen(MontessoriSlug.getInstance(), lobbyCreatedEvent.player1, lobbyCreatedEvent.lobbyName));
            });
        }

        else if(object instanceof LobbyJoinedEvent){
            LobbyJoinedEvent lobbyJoinedEvent = (LobbyJoinedEvent) object;
            //If the current player is waiting for others to join just update the label with the name
            if(MontessoriSlug.getInstance().getScreen() instanceof LobbyScreen){
                LobbyScreen lobbyScreen = (LobbyScreen) MontessoriSlug.getInstance().getScreen();
                lobbyScreen.updatePlayer2Name(lobbyJoinedEvent.player2);
            }else{
                //Else change the screen and set the labels to match the player 1
                Gdx.app.postRunnable(()->{
                MontessoriSlug.getInstance().setScreen(new LobbyScreen(MontessoriSlug.getInstance(), lobbyJoinedEvent.player1, lobbyJoinedEvent.lobbyName));
                LobbyScreen lobbyScreen = (LobbyScreen) MontessoriSlug.getInstance().getScreen();
                lobbyScreen.updatePlayer2Name(lobbyJoinedEvent.player2);
                });
            }

        }

        super.received(connection, object);
    }
}
