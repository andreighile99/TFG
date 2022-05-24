
package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryonet.Client;
import main.MontessoriSlug;

/**
 * Clase para instanciar la representación del jugador que no mueve el cliente
 * local en los clientes.
 *
 * @author Eduard Andrei Ghile
 */
public class ManagedPlayer extends Element {
	
	/** The username. */
	private String username;
	
	/** The lobby name. */
	private String lobbyName;
	
	/** The idle. */
	private Animation<TextureRegion> idle;
	
	/** The hp. */
	private int hp;

	/**
	 * Constructor de la clase.
	 *
	 * @param x         Posicion en el eje x de la pantalla
	 * @param y         Posicion en el eje y de la pantalla
	 * @param s         Stage al que pertenece la representacion del jugador
	 * @param username  Nombre de usuario del jugador
	 * @param lobbyName Nombre de la sala a la que pertenece el jugador en el
	 *                  servidor
	 */
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

	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the hp.
	 *
	 * @return the hp
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * Sets the hp.
	 *
	 * @param hp the new hp
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}
}
