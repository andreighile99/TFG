
package listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import events.game.EnemyEvent;
import events.game.GameEvent;
import events.game.SwitchLevel;
import handlers.AudioManager;
import main.MontessoriSlug;
import parameters.Parameters;
import screens.GameScreen;

/**
 * Clase listener para eventos de dentro del juego.
 *
 * @author Eduard Andrei Ghile
 */
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
                gameScreen.updatePlayersStatus(gameEvent);
                    Gdx.app.postRunnable(()->{
                        //New bullets positions inside Gdx app so we have a GL20 context (Can render)
                        gameScreen.updateBulletsPosition(gameEvent);
                    });
            }catch (ClassCastException e){
                System.out.println("LOG - Todavía no había cambiado la screen en el cliente");
            }
        }else if(object instanceof EnemyEvent){
            EnemyEvent enemyEvent = (EnemyEvent) object;
            try{
                GameScreen gameScreen = (GameScreen) MontessoriSlug.getInstance().getScreen();
                Gdx.app.postRunnable(()->{
                    //New bullets positions inside Gdx app so we have a GL20 context (Can render)
                    gameScreen.updateEnemyBulletsPosition(enemyEvent);
                    gameScreen.updateSoldiersPosition(enemyEvent);
                });
            }catch (ClassCastException e){
                System.out.println("LOG - Todavía no había cambiado la screen en el cliente");
            }
        }else if(object instanceof SwitchLevel){
            Parameters.level += 1;
                GameScreen gameScreen = (GameScreen) MontessoriSlug.getInstance().getScreen();
                String username1 = gameScreen.getPlayer1().getUsername();
                String username2 = gameScreen.getPlayer2().getUsername();
                MontessoriSlug.getInstance().getScreen().dispose();
                Gdx.app.postRunnable(()->{
                    AudioManager.playSound("assets/sound/missionComplete.mp3");
                    MontessoriSlug.getInstance().setScreen(new GameScreen(MontessoriSlug.getInstance(), username1, username2, Parameters.lobbyName));
                });
        }

    }
}
