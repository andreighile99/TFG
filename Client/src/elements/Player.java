package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryonet.Client;
import events.game.PositionEvent;
import main.MontessoriSlug;

public class Player extends Element{
    private String username;
    private String lobbyName;

    private Animation<TextureRegion> idle;

    private Element feet;
    private boolean onGround;

    private Client myClient;

    public Player(float x, float y, Stage s, String username, String lobbyName) {
        super(x, y, s);
        this.myClient = MontessoriSlug.getInstance().getClient();
        this.username = username;
        this.lobbyName = lobbyName;

        this.setX(x);
        this.setY(y);

        idle = this.loadFullAnimation("assets/player/idle.png", 1, 1, 0.2f, true);
        this.setPolygon(8);
    }

    @Override
    public void act(float delta) {

        super.act(delta);

        controles();

        //System.out.println("I'm acting!!");

    }

    private void controles() {
        PositionEvent positionEvent = new PositionEvent();
        positionEvent.setUsername(this.username);
        positionEvent.setLobyName(this.lobbyName);
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            positionEvent.setDirection(PositionEvent.DIRECTION.DOWN);
            this.myClient.sendUDP(positionEvent);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            positionEvent.setDirection(PositionEvent.DIRECTION.UP);
            this.myClient.sendUDP(positionEvent);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            positionEvent.setDirection(PositionEvent.DIRECTION.LEFT);
            this.myClient.sendUDP(positionEvent);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            positionEvent.setDirection(PositionEvent.DIRECTION.RIGHT);
            this.myClient.sendUDP(positionEvent);
        }

    }

}
