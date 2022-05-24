package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import events.game.GameEvent;
import events.lobby.*;
import handlers.LabelHandler;
import handlers.ResourceManager;
import listeners.GameEventListener;
import main.MontessoriSlug;
import parameters.Parameters;

/**
 * Clase que instancia la pantalla de la sala
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class LobbyScreen extends BScreen{
    private static Texture backgroundTexture;
    private SpriteBatch spriteBatch;

    private Table table;

    private final Label labelLobbyName;
    private final Label lobbyName;

    private final Label labelPlayer1Name;
    private final Label labelPlayer2Name;
    private final Label player1Name;
    private final Label player2Name;

    private final TextButton startGameButton;

    private final Label errorLabel;

    public LobbyScreen(MontessoriSlug game, String player1Name, String lobbyName) {

            super(game);

            this.backgroundTexture = ResourceManager.getTexture("assets/images/background.png");
            this.spriteBatch = new SpriteBatch();

            this.table = new Table();
            this.table.setBounds(Parameters.screenWidth/4,0, Parameters.screenWidth,Parameters.screenHeight);

            //Download new skin
            final Skin skin = new Skin(Gdx.files.internal("assets/ui-skin/uiskin.json"));

            this.lobbyName = LabelHandler.INSTANCE.createLabel(lobbyName, 16, Color.WHITE);

            this.player1Name = LabelHandler.INSTANCE.createLabel(player1Name, 16, Color.WHITE);
            this.player2Name = LabelHandler.INSTANCE.createLabel(null, 16, Color.WHITE);

            this.labelLobbyName = LabelHandler.INSTANCE.createLabel("Nombre del lobby: ", 16, Color.WHITE);
            this.labelPlayer1Name = LabelHandler.INSTANCE.createLabel("Jugador 1:", 16, Color.WHITE);
            this.labelPlayer2Name = LabelHandler.INSTANCE.createLabel("Jugador 2:", 16, Color.WHITE);

            this.startGameButton = new TextButton("Start game", skin);
            this.startGameButton.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    LobbyStartEvent lobbyStartEvent = new LobbyStartEvent();
                    lobbyStartEvent.lobbyName = lobbyName;
                    MontessoriSlug.getInstance().getClient().sendTCP(lobbyStartEvent);

                    return super.touchDown(event, x, y, pointer, button);
                }
            });

            this.errorLabel = LabelHandler.INSTANCE.createLabel(null, 16, Color.RED);

            this.uiStage.addActor(table);

            this.setToDefault();
    }

    public void setToDefault() {
        this.table.clear();
        this.table.add(this.labelLobbyName).width(200).padTop(12);
        this.table.add(this.lobbyName).width(250).row();
        this.table.add(this.labelPlayer1Name).width(200).padTop(12);
        this.table.add(this.player1Name).width(250).padTop(25).row();
        this.table.add(this.labelPlayer2Name).width(200).padTop(12);
        this.table.add(this.player2Name).width(250).padTop(25).row();
        this.table.add(this.startGameButton).size(250, 50).padTop(100).row();
        this.table.add(this.errorLabel).padTop(50);
    }

    public void updatePlayer2Name(String username){
        this.player2Name.setText(username);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0,0, Parameters.screenWidth, Parameters.screenHeight);
        spriteBatch.end();

        uiStage.act();
        uiStage.draw();


    }

    public void renderErrorMessage(String errorMessage){
        this.errorLabel.setText(errorMessage);
    }

    public Label getPlayer1Name() {
        return player1Name;
    }

    public Label getPlayer2Name() {
        return player2Name;
    }

    public Label getLobbyName() {
        return lobbyName;
    }
}
