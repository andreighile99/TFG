package listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import events.lobby.FinishLobby;
import events.lobby.LobbyCreatedEvent;
import events.lobby.LobbyJoinedEvent;
import events.lobby.LobbyStartEvent;
import main.MontessoriSlug;
import parameters.Parameters;
import screens.EndingScreen;
import screens.GameScreen;
import screens.LobbyScreen;
import screens.MenuScreen;

public class EventListener extends Listener {

    @Override
    public void received(Connection connection, final Object object) {
        super.received(connection, object);
        // Join success
        if(object instanceof LobbyCreatedEvent) {
            LobbyCreatedEvent lobbyCreatedEvent = (LobbyCreatedEvent) object;
            if(lobbyCreatedEvent.code.equalsIgnoreCase("00")){
                MenuScreen menuScreen = (MenuScreen) MontessoriSlug.getInstance().getScreen();
                menuScreen.renderErrorMessage("Ha ocurrido un error al crear el lobby, es probable que ya exista");
                return;
            }else{
                Gdx.app.postRunnable(()->{
                    MontessoriSlug.getInstance().setScreen(new LobbyScreen(MontessoriSlug.getInstance(), lobbyCreatedEvent.player1, lobbyCreatedEvent.lobbyName));
                    return;
                });
            }

        }

        else if(object instanceof LobbyJoinedEvent){
            final LobbyJoinedEvent lobbyJoinedEvent = (LobbyJoinedEvent) object;
            if(lobbyJoinedEvent.code.equalsIgnoreCase("01")){
                //If the current player is waiting for others to join just update the label with the name
                if(MontessoriSlug.getInstance().getScreen() instanceof LobbyScreen){
                    LobbyScreen lobbyScreen = (LobbyScreen) MontessoriSlug.getInstance().getScreen();
                    lobbyScreen.updatePlayer2Name(lobbyJoinedEvent.player2);
                    return;
                }else{
                    //Else change the screen and set the labels to match the player 1
                    Gdx.app.postRunnable(()->{
                        MontessoriSlug.getInstance().setScreen(new LobbyScreen(MontessoriSlug.getInstance(), lobbyJoinedEvent.player1, lobbyJoinedEvent.lobbyName));
                        LobbyScreen lobbyScreen = (LobbyScreen) MontessoriSlug.getInstance().getScreen();
                        lobbyScreen.updatePlayer2Name(lobbyJoinedEvent.player2);
                        return;
                    });
                }
            }else{
                MenuScreen menuScreen = (MenuScreen) MontessoriSlug.getInstance().getScreen();
                menuScreen.renderErrorMessage(lobbyJoinedEvent.description);
            }

        }


        else if(object instanceof LobbyStartEvent){
            final LobbyStartEvent lobbyStartEvent = (LobbyStartEvent) object;
            //If the server succeeds starting the game it retrieves code 01 to the clients
            if(lobbyStartEvent.code.equals("01")){
                LobbyScreen lobbyScreen = (LobbyScreen) MontessoriSlug.getInstance().getScreen();
                //Get all the data to start the Game such as players name and lobby name
                String player1username = lobbyScreen.getPlayer1Name().getText().toString();
                String player2username = lobbyScreen.getPlayer2Name().getText().toString();
                String lobbyName = lobbyScreen.getLobbyName().getText().toString();
                if(Parameters.actualNickname.equalsIgnoreCase(player1username)){
                    Gdx.app.postRunnable(()->{
                        MontessoriSlug.getInstance().setScreen(new GameScreen(MontessoriSlug.getInstance(), player1username, player2username, lobbyName));
                        return;
                    });
                }else{
                    Gdx.app.postRunnable(()->{
                        MontessoriSlug.getInstance().setScreen(new GameScreen(MontessoriSlug.getInstance(), player2username, player1username, lobbyName));
                        return;
                    });
                }

            }else{
                Gdx.app.postRunnable(()->{
                    LobbyScreen lobbyScreen = (LobbyScreen) MontessoriSlug.getInstance().getScreen();
                    lobbyScreen.renderErrorMessage(lobbyStartEvent.description);
                    return;
                });
            }
        }else if(object instanceof FinishLobby){
            FinishLobby finishLobby = (FinishLobby) object;
            System.out.println(finishLobby.code);

            if(!finishLobby.code.equalsIgnoreCase("00")){
                Gdx.app.postRunnable(()->{
                    MontessoriSlug.getInstance().setScreen(new EndingScreen(MontessoriSlug.getInstance()));
                    return;
                });
            } else if(!(MontessoriSlug.getInstance().getScreen() instanceof EndingScreen)){
                Gdx.app.postRunnable(()->{
                    MontessoriSlug.getInstance().setScreen(new MenuScreen(MontessoriSlug.getInstance()));
                    //MenuScreen menuScreen = (MenuScreen) MontessoriSlug.getInstance().getScreen();
                    //menuScreen.renderErrorMessage("Ha ocurrido un error de conexion y se ha terminado la partida");
                    return;
                });
            }

        }
    }
}
