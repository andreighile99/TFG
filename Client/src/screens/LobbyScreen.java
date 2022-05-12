package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.kryonet.Client;
import events.lobby.CreateNewLobbyEvent;
import events.lobby.JoinLobbyEvent;
import events.lobby.LobbyCreatedEvent;
import events.lobby.LobbyJoinedEvent;
import handlers.LabelHandler;
import listeners.EventListener;
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
                    return false;
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
}
