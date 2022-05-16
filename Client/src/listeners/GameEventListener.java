package listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import events.game.GameEvent;
import main.MontessoriSlug;
import screens.GameScreen;

public class GameEventListener extends Listener {

    @Override
    public void received(Connection connection, final Object object) {
        // Join success
        if(object instanceof GameEvent) {
            GameEvent gameEvent = (GameEvent) object;
            /**Añadido el try catch puesto que nada asegura que ambos clientes hayan ejecutado ya el cambio de screen
             * debido a posibles discrepancias entre los hilos del procesador, especialmente en una sola máquina
             */
            try{
                GameScreen gameScreen = (GameScreen) MontessoriSlug.getInstance().getScreen();
                gameScreen.updatePlayersPosition(gameEvent);
            }catch (ClassCastException e){
                System.out.println("LOG - Todavía no había cambiado la screen en el cliente");
            }
        }


        super.received(connection, object);
    }
}
