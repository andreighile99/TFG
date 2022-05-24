package main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.esotericsoftware.kryonet.Client;
import handlers.ResourceManager;
import screens.MenuScreen;

/**
 * Clase que instancia el juego
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class MontessoriSlug extends Game {

    public ResourceManager resourceManager;
    private Client client;

    @Override
    public void create() {
    	//Load all resources that the game needs
        ResourceManager.loadAllResources();
        //New Input
        InputMultiplexer im = new InputMultiplexer();
        //Add the input
        Gdx.input.setInputProcessor(im);
        this.setScreen(new MenuScreen(this));

    }

    public static MontessoriSlug getInstance() {
        return (MontessoriSlug) Gdx.app.getApplicationListener();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
