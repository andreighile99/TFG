package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class SoldierRep extends Element{
    private Animation<TextureRegion> idle;

    public SoldierRep(float x, float y, Stage s) {

        super(x, y, s);
        this.idle = loadFullAnimation("assets/enemies/spider.png", 1, 1, 209, true);
    }

    @Override
    public void act(float delta) {
        // TODO Auto-generated method stub
        super.act(delta);

    }
}
