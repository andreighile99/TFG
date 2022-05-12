package main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.esotericsoftware.kryonet.Client;
import handlers.ResourceManager;
import screens.MenuScreen;

public class MontessoriSlug extends Game {

    public ResourceManager resourceManager;
    private Client client;

    @Override
    public void create() {
        InputMultiplexer im = new InputMultiplexer();
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
