package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryonet.Client;
import events.game.BulletEvent;
import events.game.PlayerEvent;
import main.MontessoriSlug;
import screens.GameScreen;

public class Player extends Element{
    private Stage stage;

    private String username;
    private String lobbyName;

    private Vector2 position;
    private Vector2 lookingDirection;

    private Animation<TextureRegion> idle;

    private Rectangle boundRect;

    private Client myClient;

    public Player(float x, float y, Stage s, String username, String lobbyName) {
        super(x, y, s);
        this.stage = s;
        this.lookingDirection = new Vector2(1, 0);
        this.position = new Vector2(x,y);
        this.myClient = MontessoriSlug.getInstance().getClient();
        this.username = username;
        this.lobbyName = lobbyName;
        this.boundRect = new Rectangle(this.position.x, this.position.y, 10, 10);

        idle = this.loadFullAnimation("assets/player/idle.png", 1, 1, 0.2f, false);
        this.setPolygon(8);
    }

    @Override
    public void act(float delta) {

        super.act(delta);

        controles();

    }

    private void controles() {
        PlayerEvent playerEvent = new PlayerEvent();
        playerEvent.setUsername(this.username);
        playerEvent.setLobyName(this.lobbyName);
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
            BulletEvent bulletEvent = new BulletEvent();
            bulletEvent.setLobbyName(this.lobbyName);
            bulletEvent.setUsername(this.username);
            bulletEvent.setBulletDirection(this.lookingDirection.nor());
            this.myClient.sendUDP(bulletEvent);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            playerEvent.setDirection(PlayerEvent.DIRECTION.UP);
            this.myClient.sendUDP(playerEvent);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.lookingDirection.set(-1, 0);
            playerEvent.setDirection(PlayerEvent.DIRECTION.LEFT);
            this.myClient.sendUDP(playerEvent);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.lookingDirection.set(1, 0);
            playerEvent.setDirection(PlayerEvent.DIRECTION.RIGHT);
            this.myClient.sendUDP(playerEvent);
        }
        this.boundRect.x = this.getX();
        this.boundRect.y = this.getY();

    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
