package listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import events.game.GameEvent;
import events.game.RemoveBulletEvent;
import events.game.RemoveEnemyEvent;
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
                //New player positions
                gameScreen.updatePlayersPosition(gameEvent);
                    Gdx.app.postRunnable(()->{
                        //New bullets positions inside Gdx app so we have a GL20 context (Can render)
                        gameScreen.updateBulletsPosition(gameEvent);
                        gameScreen.updateSoldiersPosition(gameEvent);
                    });
            }catch (ClassCastException e){
                System.out.println("LOG - Todavía no había cambiado la screen en el cliente");
            }
        }

        else if(object instanceof RemoveBulletEvent){
            RemoveBulletEvent removeBulletEvent = (RemoveBulletEvent) object;
            GameScreen gameScreen = (GameScreen) MontessoriSlug.getInstance().getScreen();
            //Remove all bullets that were removed from the server, we get each index in the ArrayList sent by the server
            for(Integer i : removeBulletEvent.getBulletIndex()){
                gameScreen.getBullets().get(i).setEnabled(false);
            }
            //Remove them completely from the ArrayList so they don't collide with further instructions for each bullet
            gameScreen.getBullets().removeAll(removeBulletEvent.getBulletIndex());
        }

        else if(object instanceof RemoveEnemyEvent){
            RemoveEnemyEvent removeEnemyEvent = (RemoveEnemyEvent) object;
            GameScreen gameScreen = (GameScreen) MontessoriSlug.getInstance().getScreen();
            for(Integer i : removeEnemyEvent.getSoldiersIndex()){
                //ADD Code to remove enemies, just like above
            }
        }

    }
}
