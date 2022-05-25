
package main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.esotericsoftware.kryonet.Client;
import handlers.AudioManager;
import handlers.ResourceManager;
import screens.MenuScreen;

/**
 * Clase que instancia el juego.
 *
 * @author Eduard Andrei Ghile
 */
public class MontessoriSlug extends Game {

    /** The resource manager. */
    public ResourceManager resourceManager;
    
    /** The client. */
    private Client client;

    @Override
    public void create() {
    	//Load all resources that the game needs
        ResourceManager.loadAllResources();
        //New Input
        InputMultiplexer im = new InputMultiplexer();
        //Add the input
        Gdx.input.setInputProcessor(im);
        AudioManager.playMusic("assets/music/menuTheme.mp3");
        this.setScreen(new MenuScreen(this));

    }

    /**
     * Gets the single instance of MontessoriSlug.
     *
     * @return single instance of MontessoriSlug
     */
    public static MontessoriSlug getInstance() {
        return (MontessoriSlug) Gdx.app.getApplicationListener();
    }

    /**
     * Gets the client.
     *
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * Sets the client.
     *
     * @param client the new client
     */
    public void setClient(Client client) {
        this.client = client;
    }
}
