package listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import events.game.GameEvent;
import events.game.RemoveBulletEvent;
import main.MontessoriSlug;
import screens.GameScreen;

public class GameEventListener extends Listener {

    @Override
    public void received(Connection connection, final Object object) {
        super.received(connection, object);
        // Join success
        if(object instanceof GameEvent) {
            GameEvent gameEvent = (GameEvent) object;
            /**Añadido el try catch puesto que nada asegura que ambos clientes hayan ejecutado ya el cambio de screen
             * debido a posibles discrepancias entre los hilos del procesador, especialmente en una sola máquina y a través
             * de internet
             */
            try{
                GameScreen gameScreen = (GameScreen) MontessoriSlug.getInstance().getScreen();
                gameScreen.updatePlayersPosition(gameEvent);
                    Gdx.app.postRunnable(()->{
                        gameScreen.updateBulletsPosition(gameEvent);
                    });
            }catch (ClassCastException e){
                System.out.println("LOG - Todavía no había cambiado la screen en el cliente");
            }
        }

        if(object instanceof RemoveBulletEvent){
            RemoveBulletEvent removeBulletEvent = (RemoveBulletEvent) object;
            GameScreen gameScreen = (GameScreen) MontessoriSlug.getInstance().getScreen();
            for(Integer i : removeBulletEvent.getBulletIndex()){
                gameScreen.getBullets().get(i).setEnabled(false);
            }
            gameScreen.getBullets().removeAll(removeBulletEvent.getBulletIndex());
        }
    }
}
