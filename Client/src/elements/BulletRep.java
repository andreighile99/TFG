package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Clase para instanciar la representacion de una bala de los jugadores en los clientes
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class BulletRep extends Element{
	private Animation<TextureRegion> bullet;
	
	/**
	 * Constructor de la clase
	 * 
	 * @param x Posicion en el eje x de la pantalla
	 * @param y Posicion en el eje y de la pantalla
	 * @param s Stage al que pertenece la bala
	 */
	public BulletRep(float x, float y, Stage s) {
		super(x, y, s);
		this.bullet = loadFullAnimation("assets/player/Bola.png", 1, 1, 209, true);
		
	}

	@Override
	public void act(float delta) {
		super.act(delta);

	}
	

}
