package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryonet.Client;
import main.MontessoriSlug;

public class ManagedPlayer extends Element{
    private String username;
    private String lobbyName;
    private Animation<TextureRegion> idle;
    private int hp;



    public ManagedPlayer(float x, float y, Stage s, String username, String lobbyName) {
        super(x, y, s);
        this.username = username;
        this.lobbyName = lobbyName;

        idle = this.loadFullAnimation("assets/player/idle.png", 1, 1, 0.2f, true);
        this.setPolygon(8);
    }

    @Override
    public void act(float delta) {

        super.act(delta);


        //System.out.println("I'm acting!!");

    }

    public String getUsername() {
        return username;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
