package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import elements.serverSide.Bullet;
import elements.serverSide.EnemyBullet;
import elements.serverSide.ServerPlayerData;
import elements.serverSide.Soldier;
import events.game.*;
import events.lobby.*;
import handlers.LabelHandler;
import handlers.ResourceManager;
import listeners.EventListener;
import listeners.GameEventListener;
import main.MontessoriSlug;
import parameters.Parameters;

import java.util.ArrayList;

/**
 * Clase que instancia la pantalla de menu
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class MenuScreen extends BScreen {
    private static Texture backgroundTexture;
    private SpriteBatch spriteBatch;

    private Table table;

    private final Label ipAddressLabel;
    private final TextField ipAddressTextField;
    private final Label portLabel;
    private final TextField portTextField;
    private final Label lobbyNameLabel;
    private final TextField lobbyNameTextField;
    private final Label usernameLabel;
    private final TextField usernameTextField;

    private final TextButton newLobbyButton;
    private final TextButton joinLobbyButton;
    private final TextButton optionsButton;
    private final TextButton exitGame;

    private final Label errorLabel;


    public MenuScreen(MontessoriSlug game) {
        super(game);


        this.backgroundTexture = ResourceManager.getTexture("assets/images/background.png");
        this.spriteBatch = new SpriteBatch();

        this.table = new Table();
        this.table.setBounds(0,0,Parameters.screenWidth,Parameters.screenHeight);



        //Download new skin
        final Skin skin = new Skin(Gdx.files.internal("assets/ui-skin/uiskin.json"));

        this.ipAddressTextField = new TextField("localhost", skin);
        this.portTextField = new TextField("9998", skin);
        this.usernameTextField = new TextField("Usuario", skin);
        this.lobbyNameTextField = new TextField("Sala", skin);

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
                    client.connect(15000, ipAddressTextField.getText(), Integer.parseInt(portTextField.getText()),  Integer.parseInt(portTextField.getText()));
                } catch (Exception e) {
                    errorLabel.setText(e.getMessage());
                    return super.touchDown(event, x, y, pointer, button);
                }

                Parameters.actualNickname = usernameTextField.getText();
                Parameters.lobbyName = lobbyNameTextField.getText();

                // Connection established with the server
                MontessoriSlug.getInstance().setClient(client);

                CreateNewLobbyEvent createNewLobbyEvent = new CreateNewLobbyEvent();
                createNewLobbyEvent.lobbyName = lobbyNameTextField.getText();
                createNewLobbyEvent.username = usernameTextField.getText();

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
                    client.connect(15000, ipAddressTextField.getText(), Integer.parseInt(portTextField.getText()),  Integer.parseInt(portTextField.getText()));
                } catch (Exception e) {
                    errorLabel.setText(e.getMessage());
                    return super.touchDown(event, x, y, pointer, button);
                }

                Parameters.actualNickname = usernameTextField.getText();
                Parameters.lobbyName = lobbyNameTextField.getText();

                // Connection established with the server so set the client to the main game class
                MontessoriSlug.getInstance().setClient(client);

                JoinLobbyEvent joinLobbyEvent = new JoinLobbyEvent();
                joinLobbyEvent.lobbyName = lobbyNameTextField.getText();
                joinLobbyEvent.username = usernameTextField.getText();

                client.sendTCP(joinLobbyEvent);

                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.optionsButton = new TextButton("Opciones", skin);
        this.optionsButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MontessoriSlug.getInstance().setScreen(new OptionsScreen(MontessoriSlug.getInstance()));
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.exitGame = new TextButton("Salir", skin);
        this.exitGame.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MontessoriSlug.getInstance().dispose();
                Gdx.app.exit();
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.errorLabel = LabelHandler.INSTANCE.createLabel(null, 16, Color.RED);
        this.ipAddressLabel  = LabelHandler.INSTANCE.createLabel("Direccion IP ", 16, Color.WHITE);
        this.lobbyNameLabel = LabelHandler.INSTANCE.createLabel("Nombre de la sala ", 16, Color.WHITE);
        this.portLabel= LabelHandler.INSTANCE.createLabel("Puerto ", 16, Color.WHITE);
        this.usernameLabel = LabelHandler.INSTANCE.createLabel("Nombre de usuario ", 16, Color.WHITE);
        this.uiStage.addActor(table);

        this.setToDefault();
    }

    public void setToDefault() {
        this.table.clear();
        this.table.add(this.ipAddressLabel).width(200).padTop(12);
        this.table.add(this.ipAddressTextField).width(250).row();
        this.table.add(this.portLabel).width(200).padTop(12);
        this.table.add(this.portTextField).width(250).padTop(25).row();
        this.table.add(this.usernameLabel).width(200).padTop(12);
        this.table.add(this.usernameTextField).width(250).padTop(25).row();
        this.table.add(this.lobbyNameLabel).width(200).padTop(12);
        this.table.add(this.lobbyNameTextField).width(250).padTop(25).row();
        this.table.add().width(200);
        this.table.add(this.newLobbyButton).size(250, 50).padTop(50).row();
        this.table.add().width(200);
        this.table.add(this.joinLobbyButton).size(250, 50).padTop(30).row();
        this.table.add().width(200);
        this.table.add(this.optionsButton).size(250, 50).padTop(30).row();
        this.table.add().width(200);
        this.table.add(this.exitGame).size(250, 50).padTop(30).row();
        this.table.add(this.errorLabel).padTop(50);
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
        client.getKryo().register(SwitchLevel.class);

        //Events happening in-game
        client.getKryo().register(GameEvent.class);
        client.getKryo().register(PlayerEvent.class);
        client.getKryo().register(PlayerEvent.DIRECTION.class);
        client.getKryo().register(BulletEvent.class);
        client.getKryo().register(EnemyEvent.class);
        client.getKryo().register(ServerPlayerData.class);

        //Common
        client.getKryo().register(ArrayList.class);
        client.getKryo().register(Vector2.class);
        client.getKryo().register(Rectangle.class);
        client.getKryo().register(Bullet.class);
        client.getKryo().register(Soldier.class);
        client.getKryo().register(EnemyBullet.class);
        client.getKryo().register(Connection.class);
    }

    public void renderErrorMessage(String errorMessage){
        this.errorLabel.setText(errorMessage);
    }


}

