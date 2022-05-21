package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.kryonet.Client;
import elements.BulletRep;
import elements.serverSide.Bullet;
import elements.serverSide.Soldier;
import events.game.*;
import events.lobby.*;
import handlers.LabelHandler;
import listeners.EventListener;
import listeners.GameEventListener;
import main.MontessoriSlug;
import parameters.Parameters;

import java.util.ArrayList;

public class MenuScreen extends BScreen {
    private Table table;

    private final TextField ipAddressLabel;
    private final TextField portLabel;
    private final TextField lobbyNameLabel;
    private final TextField usernameLabel;

    private final TextButton newLobbyButton;
    private final TextButton joinLobbyButton;

    private final Label errorLabel;


    public MenuScreen(MontessoriSlug game) {
        super(game);

        this.table = new Table();
        this.table.setBounds(0,0,800,600);



        //Download new skin
        final Skin skin = new Skin(Gdx.files.internal("assets/ui-skin/uiskin.json"));

        this.ipAddressLabel = new TextField("localhost", skin);
        this.portLabel = new TextField("9998", skin);
        this.usernameLabel = new TextField("Username", skin);
        this.lobbyNameLabel = new TextField("Nombre de la sala", skin);

        this.newLobbyButton = new TextButton("Crear sala", skin);
        this.newLobbyButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Connect
                final Client client = new Client();

                //client.addListener(new ConnectionStateListener());
                addListeners(client);
                registerClasses(client);

                try {
                    client.start();
                    client.connect(15000, ipAddressLabel.getText(), Integer.parseInt(portLabel.getText()),  Integer.parseInt(portLabel.getText()));
                } catch (Exception e) {
                    errorLabel.setText(e.getMessage());
                    return super.touchDown(event, x, y, pointer, button);
                }

                Parameters.actualNickname = usernameLabel.getText();

                // Connection established with the server
                MontessoriSlug.getInstance().setClient(client);

                CreateNewLobbyEvent createNewLobbyEvent = new CreateNewLobbyEvent();
                createNewLobbyEvent.lobbyName = lobbyNameLabel.getText();
                createNewLobbyEvent.username = usernameLabel.getText();

                client.sendTCP(createNewLobbyEvent);


                //client.sendTCP(joinRequestEvent);

                return super.touchDown(event, x, y, pointer, button);
            }
        });


        this.joinLobbyButton = new TextButton("Unirse a sala", skin);
        this.joinLobbyButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Connect
                final Client client = new Client();

                addListeners(client);
                registerClasses(client);

                try {
                    client.start();
                    client.connect(15000, ipAddressLabel.getText(), Integer.parseInt(portLabel.getText()),  Integer.parseInt(portLabel.getText()));
                } catch (Exception e) {
                    errorLabel.setText(e.getMessage());
                    return super.touchDown(event, x, y, pointer, button);
                }

                Parameters.actualNickname = usernameLabel.getText();

                // Connection established with the server so set the client to the main game class
                MontessoriSlug.getInstance().setClient(client);

                JoinLobbyEvent joinLobbyEvent = new JoinLobbyEvent();
                joinLobbyEvent.lobbyName = lobbyNameLabel.getText();
                joinLobbyEvent.username = usernameLabel.getText();

                client.sendTCP(joinLobbyEvent);

                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.errorLabel = LabelHandler.INSTANCE.createLabel(null, 16, Color.RED);

        this.uiStage.addActor(table);

        this.setToDefault();
    }

    public void setToDefault() {
        this.table.clear();
        this.table.add(this.ipAddressLabel).width(250).row();
        this.table.add(this.portLabel).width(250).padTop(25).row();
        this.table.add(this.usernameLabel).width(250).padTop(25).row();
        this.table.add(this.lobbyNameLabel).width(250).padTop(25).row();
        this.table.add(this.newLobbyButton).size(250, 50).padTop(100).row();
        this.table.add(this.joinLobbyButton).size(250, 50).padTop(100).row();
        this.table.add(this.errorLabel).padTop(50);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        uiStage.act();
        uiStage.draw();
    }

    private void addListeners(Client client){
        //Add the new listener that retrieves the lobby stuff
        client.addListener(new EventListener());
        //Add the new listener that retrieves the game status
        client.addListener(new GameEventListener());
    }

    private void registerClasses(Client client){
        //Lobby management
        client.getKryo().register(CreateNewLobbyEvent.class);
        client.getKryo().register(JoinLobbyEvent.class);
        client.getKryo().register(LobbyCreatedEvent.class);
        client.getKryo().register(LobbyJoinedEvent.class);
        client.getKryo().register(LobbyStartEvent.class);
        client.getKryo().register(FinishLobby.class);

        //Events happening in-game
        client.getKryo().register(GameEvent.class);
        client.getKryo().register(PlayerEvent.class);
        client.getKryo().register(PlayerEvent.DIRECTION.class);
        client.getKryo().register(BulletEvent.class);

        //Common
        client.getKryo().register(ArrayList.class);
        client.getKryo().register(Vector2.class);
        client.getKryo().register(Rectangle.class);
        client.getKryo().register(Bullet.class);
        client.getKryo().register(Soldier.class);
    }
    public void renderErrorMessage(String errorMessage){
        this.errorLabel.setText(errorMessage);
    }


}

