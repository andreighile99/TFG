package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Clase para instanciar la representacion del enemigo tipo Soldado en los
 * clientes
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class SoldierRep extends Element {
	private Animation<TextureRegion> idle;

	/**
	 * Constructor de la clase
	 * 
	 * @param x Posicion en el eje x de la pantalla
	 * @param y Posicion en el eje y de la pantalla
	 * @param s Stage al que pertenece la representacion del Soldado
	 * 
	 */
	public SoldierRep(float x, float y, Stage s) {

		super(x, y, s);
		this.idle = loadFullAnimation("assets/enemies/spider.png", 1, 1, 209, true);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

	}
}
