package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import events.game.GameEvent;
import events.lobby.*;
import handlers.LabelHandler;
import listeners.GameEventListener;
import main.MontessoriSlug;

public class LobbyScreen extends BScreen{

    private Table table;

    private final Label lobbyName;

    private final Label player1Name;
    private final Label player2Name;

    private final TextButton startGameButton;

    private final Label errorLabel;

    public LobbyScreen(MontessoriSlug game, String player1Name, String lobbyName) {

            super(game);

            this.table = new Table();
            this.table.setBounds(0,0,800,600);

            //Download new skin
            final Skin skin = new Skin(Gdx.files.internal("assets/ui-skin/uiskin.json"));

            this.lobbyName = new Label(lobbyName, skin);

            this.player1Name = new Label(player1Name, skin);
            this.player2Name = new Label(null, skin);

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
        this.table.add(this.lobbyName).width(250).row();
        this.table.add(this.player1Name).width(250).padTop(25).row();
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
