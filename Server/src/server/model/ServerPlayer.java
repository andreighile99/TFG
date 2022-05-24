package server.model;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;

/**
 * Clase modelo para el jugador en el servidor.
 *
 * @author Eduard Andrei Ghile
 */
public class ServerPlayer {

	/** The username. */
	private String username;
	
	/** The connection. */
	private final Connection connection;

	/** The bound rect. */
	private Rectangle boundRect;
	
	/** The feet. */
	private Rectangle feet;

	/** The on ground. */
	private boolean onGround;

	/** The position. */
	private Vector2 position;

	/** The hp. */
	private int hp;

	/** The enabled. */
	private boolean enabled;

	/**
	 * Constructor del jugador en el servidor.
	 *
	 * @param username the username
	 * @param connection the connection
	 */
	public ServerPlayer(String username, Connection connection) {
		this.enabled = true;
		this.hp = 100;
		this.username = username;
		this.connection = connection;
	}

	/**
	 * Posicion inicial del jugador.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void setStartingPoint(float x, float y) {
		this.position = new Vector2(x, y);
	}

	/**
	 * Inicializar los parametros del jugador.
	 */
	public void init() {
		this.enabled = true;
		this.hp = 100;
		this.boundRect = new Rectangle(0, 0, 10, 20);
		this.feet = new Rectangle(0, 0, this.boundRect.width / 8, this.boundRect.height / 10);
		this.onGround = false;
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
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * Gets the bound rect.
	 *
	 * @return the bound rect
	 */
	public Rectangle getBoundRect() {
		return boundRect;
	}

	/**
	 * Gets the feet.
	 *
	 * @return the feet
	 */
	public Rectangle getFeet() {
		return feet;
	}

	/**
	 * Checks if is on ground.
	 *
	 * @return true, if is on ground
	 */
	public boolean isOnGround() {
		return onGround;
	}

	/**
	 * Sets the on ground.
	 *
	 * @param onGround the new on ground
	 */
	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	/**
	 * Update rectangle position.
	 */
	public void updateRectanglePosition() {
		this.boundRect.x = this.position.x;
		this.boundRect.y = this.position.y;
		this.feet.x = this.position.x + this.boundRect.width / 2 - feet.width / 2;
		this.feet.y = this.position.y - 0.1f;
	}

	/**
	 * Prevent overlap.
	 *
	 * @param anotherPoly the another poly
	 * @return the vector 2
	 */
	public Vector2 preventOverlap(Polygon anotherPoly) {
		// Map the rectangle to a polygon to check overlapping
		Polygon rPoly = new Polygon(new float[] { 0, 0, this.getBoundRect().width, 0, this.getBoundRect().width,
				this.getBoundRect().height, 0, this.getBoundRect().height });
		rPoly.setPosition(this.getBoundRect().x, this.getBoundRect().y);

		// initial test to improve performance
		if (!rPoly.getBoundingRectangle().overlaps(anotherPoly.getBoundingRectangle()))
			return null;

		Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();
		boolean polygonOverlap = Intersector.overlapConvexPolygons(rPoly, anotherPoly, mtv);

		if (!polygonOverlap)
			return null;

		this.moveBy(mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);
		return mtv.normal;
	}

	/**
	 *  Add x and y to current position.
	 *
	 * @param x the x
	 * @param y the y
	 */
	private void moveBy(float x, float y) {
		if (x != 0 || y != 0) {
			this.position.x += x;
			this.position.y += y;
		}
	}

	/**
	 * Checks if is enabled.
	 *
	 * @return true, if is enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled the new enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
