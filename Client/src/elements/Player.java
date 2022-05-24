package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryonet.Client;
import events.game.BulletEvent;
import events.game.PlayerEvent;
import main.MontessoriSlug;

/**
 * Clase para instanciar la representación del jugador que mueve el cliente
 * local en los clientes
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class Player extends Element {
	private Stage stage;

	private String username;
	private String lobbyName;

	private Vector2 lookingDirection;

	private Animation<TextureRegion> idle;

	private Client myClient;

	private int hp;

	/**
	 * Constructor de la clase
	 * 
	 * @param x         Posicion en el eje x de la pantalla
	 * @param y         Posicion en el eje y de la pantalla
	 * @param s         Stage al que pertenece la representacion del jugador
	 * @param username  Nombre de usuario del jugador
	 * @param lobbyName Nombre de la sala a la que pertenece el jugador en el
	 *                  servidor
	 */
	public Player(float x, float y, Stage s, String username, String lobbyName) {
		super(x, y, s);
		this.stage = s;
		this.lookingDirection = new Vector2(1, 0);
		this.myClient = MontessoriSlug.getInstance().getClient();
		this.username = username;
		this.lobbyName = lobbyName;

		idle = this.loadFullAnimation("assets/player/idle.png", 1, 1, 0.2f, false);
		this.setPolygon(8);
	}

	@Override
	public void act(float delta) {

		super.act(delta);

		controles();

	}

	/**
	 * Metodo que detecta los inputs introducidos por el usuario y se los envia al
	 * servidor de forma automatica
	 */
	private void controles() {
		if (this.getEnabled()) {
			PlayerEvent playerEvent = new PlayerEvent();
			playerEvent.setUsername(this.username);
			playerEvent.setLobyName(this.lobbyName);
			if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
				BulletEvent bulletEvent = new BulletEvent();
				bulletEvent.setLobbyName(this.lobbyName);
				bulletEvent.setUsername(this.username);
				bulletEvent.setBulletDirection(this.lookingDirection.nor());
				this.myClient.sendUDP(bulletEvent);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				this.lookingDirection.set(0, 1);
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
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
		}
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
