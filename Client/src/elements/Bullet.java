package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Bullet extends Element{
	private Animation<TextureRegion> bullet;
	
	public Bullet(float x, float y, Stage s) {
		
	
		super(x, y, s);
		this.bullet = loadFullAnimation("assets/player/Bola.png", 1, 1, 209, true);
		
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);

	}
	

}
