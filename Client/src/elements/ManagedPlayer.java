
package elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryonet.Client;
import main.MontessoriSlug;

/**
 * Clase para instanciar la representaci�n del jugador que no mueve el cliente
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

	/** The walkingRightAnimation. */
	private Animation<TextureRegion> rightWalk;

	/** The walkingLeftAnimation. */
	private Animation<TextureRegion> leftWalk;

	private Animation<TextureRegion> leftShoot;
	private Animation<TextureRegion> rightShoot;
	
	/** The hp. */
	private int hp;

	private Vector2 lookingDirection;

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
		this.lookingDirection = new Vector2(0,0);

		idle = this.loadFullAnimation("assets/player/idle.png", 1, 1, 0.2f, false);
		rightWalk = this.loadFullAnimation("assets/player/PlayerCaminaDerecha.png", 1, 5, 0.3f, true);
		leftWalk = this.loadFullAnimation("assets/player/PlayerCaminaIzquierda.png", 1, 5, 0.3f, true);
		rightShoot = this.loadFullAnimation("assets/player/PlayerDisparaDerecha.png", 1, 1, 0.1f, false);
		leftShoot = this.loadFullAnimation("assets/player/PlayerDisparaIzquierda.png", 1, 1, 0.1f, false);
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

	public Vector2 getLookingDirection() {
		return lookingDirection;
	}

	public void setLookingDirection(Vector2 lookingDirection) {
		this.lookingDirection = lookingDirection;
	}

	public Animation<TextureRegion> getIdle() {
		return idle;
	}

	public Animation<TextureRegion> getRightWalk() {
		return rightWalk;
	}

	public Animation<TextureRegion> getLeftWalk() {
		return leftWalk;
	}

	public Animation<TextureRegion> getLeftShoot() {
		return leftShoot;
	}

	public Animation<TextureRegion> getRightShoot() {
		return rightShoot;
	}
}
