package server.game.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import server.events.game.*;
import server.game.elements.Bullet;
import server.game.elements.EnemyBullet;
import server.game.elements.Soldier;
import server.game.elements.Solid;
import server.handlers.ResourceManager;
import server.model.ServerPlayer;
import server.game.elements.ServerPlayerData;

import java.util.ArrayList;
import java.util.Random;

/**
 * Clase utilizada para definir el mapa en el servidor, equivalente al
 * GameScreen del cliente
 * 
 * @author Eduard Andrei Ghile
 *
 */
/**
 * @author DTUser
 *
 */
public class ServerMap {

	private float deltaTime = 0;

	private onUpdate onUpdateCallback;
	private notifyGameServer notifyGameServerCallback;

	private ServerPlayer player1;
	private ServerPlayer player2;

	private float startingX;
	private float startingY;

	private TiledMap map;
	private int tileWidth;
	private int tileHeight;
	private int mapWidthInTiles;
	private int mapHeightInTiles;
	private int mapWidthInPixels;
	private int mapHeightInPixels;

	private ArrayList<Solid> solids;
	private ArrayList<Soldier> soldiers;
	private ArrayList<MapObject> elements;
	private ArrayList<Bullet> bullets;
	private ArrayList<EnemyBullet> enemyBullets;

	private Solid end;

	public ServerMap(onUpdate onUpdateCallback, notifyGameServer notifyGameServerCallback, ServerPlayer player1,
			ServerPlayer player2, int level) {

		ResourceManager.loadAllResources();
		this.onUpdateCallback = onUpdateCallback;
		this.notifyGameServerCallback = notifyGameServerCallback;

		switch (level) {
		case 1:
			map = ResourceManager.getMap("assets/maps/firstMap.tmx");
			break;
		case 2:
			map = ResourceManager.getMap("assets/maps/secondMap.tmx");
			break;
		default:
			map = ResourceManager.getMap("assets/maps/firstMap.tmx");
			break;
		}

		elements = new ArrayList<>();

		bullets = new ArrayList<>();

		enemyBullets = new ArrayList<>();

		MapProperties props = map.getProperties();

		tileWidth = props.get("tilewidth", Integer.class);
		tileHeight = props.get("tileheight", Integer.class);
		mapWidthInTiles = props.get("width", Integer.class);
		mapHeightInTiles = props.get("height", Integer.class);
		mapWidthInPixels = tileWidth * mapWidthInTiles;
		mapHeightInPixels = tileHeight * mapHeightInTiles;

		elements = getRectangleList("Inicio");
		props = elements.get(0).getProperties();

		startingX = (float) props.get("x");
		startingY = (float) props.get("y");

		// Add solids
		Solid solid;
		elements = getRectangleList("Solid");
		this.solids = new ArrayList<>();
		for (MapObject mapObject : elements) {
			props = mapObject.getProperties();
			solid = new Solid((float) props.get("x"), (float) props.get("y"), (float) props.get("width"),
					(float) props.get("height"));
			solids.add(solid);
		}

		// Add map switch
		elements = getRectangleList("End");
		if (!elements.isEmpty()) {
			props = elements.get(0).getProperties();
			end = new Solid((float) props.get("x"), (float) props.get("y"), (float) props.get("width"),
					(float) props.get("height"));
		}

		// Add soldiers
		Soldier soldier;
		elements = getRectangleList("Soldier");
		this.soldiers = new ArrayList<>();
		for (MapObject mapObject : elements) {
			props = mapObject.getProperties();
			soldier = new Soldier((float) props.get("x"), (float) props.get("y"));
			soldiers.add(soldier);
		}

		this.player1 = player1;
		this.player1.setStartingPoint(startingX, startingY);
		this.player1.init();

		this.player2 = player2;
		this.player2.setStartingPoint(startingX, startingY);
		this.player2.init();
	}

	public void update(float deltaTime) {
		this.deltaTime = deltaTime;
		this.sendGameUpdate();
		this.cleanupDisabledElements();
		this.removePlayers();

		this.updatePlayersRectangles();
		this.updateSoldiersRectangles();

		this.preventOverlapping();
		this.soldiersAction();
		this.removeBullets();
		this.removeEnemies();
		this.updateBulletsPosition();

		this.applyGravity();

		this.checkEnding();
	}

	public interface onUpdate {
		void sendToBothClients(Object object);
	}

	public void cleanupDisabledElements() {
		soldiers.removeIf(soldier -> !soldier.isEnabled());
		bullets.removeIf(bullet -> !bullet.isEnabled());
		enemyBullets.removeIf(enemyBullet -> !enemyBullet.isEnabled());
	}

	/**
	 * Metodo que actualiza la posici�n del cliente cuando recibe un mensaje de este
	 * 
	 * @param message
	 */
	public void updatePlayerPosition(PlayerEvent message) {
		ServerPlayer player = this.getPlayerByNickName(message.getUsername());
		Vector2 v = player.getPosition();
		player.setLookingDirection(message.getLookingDirection());
		player.setMoving(message.isMoving());
		if(message.getDirection() != null){
			switch (message.getDirection()) {
				case LEFT:
					v.x -= deltaTime * 100;
					break;
				case RIGHT:
					v.x += deltaTime * 100;
					break;
				case UP:
					if (player.isOnGround()) {
						v.y += deltaTime * 2000;
					}
					break;
				default:
					break;
			}
		}

	}

	/**
	 * Metodo que actualiza la posici�n de la hitbox del jugador en el servidor
	 * 
	 */
	private void updatePlayersRectangles() {
		this.player1.updateRectanglePosition();
		this.player2.updateRectanglePosition();
	}

	/**
	 * 
	 * Metodo que actualiza la posici�n de la hitbox de los enemigos en el servidor
	 */
	private void updateSoldiersRectangles() {
		for (Soldier s : soldiers) {
			s.updateRectanglePosition();
		}
	}

	/**
	 * Metodo que dispara una bala en el servidor cuando se recibe un mensaje del
	 * jugador
	 * 
	 * @param message
	 */
	public void playerShoots(BulletEvent message) {
		ServerPlayer player = this.getPlayerByNickName(message.getUsername());
		player.setShooting(true);
		Bullet bullet = new Bullet(message.getUsername(), player.getPosition().x + 25, player.getPosition().y + 22,
					player.getLookingDirection());
		this.bullets.add(bullet);
	}

	/**
	 * Metodo que calcula la siguiente acci�n de los soldados en el servidor
	 * 
	 */
	public void soldiersAction() {
		for (Soldier s : soldiers) {
			Random rand = new Random();
			s.setActionCounter(s.getActionCounter() + deltaTime);

			float distanceToP1 = this.distanceBetweenTwoPoints(s.getPosition(), player1.getPosition());
			float distanceToP2 = this.distanceBetweenTwoPoints(s.getPosition(), player2.getPosition());
			Vector2 vectorToP1 = new Vector2(player1.getPosition().x - s.getPosition().x,
					player1.getPosition().y - s.getPosition().y).nor();
			Vector2 vectorToP2 = new Vector2(player2.getPosition().x - s.getPosition().x,
					player2.getPosition().y - s.getPosition().y).nor();
			if (s.getActionCounter() >= (rand.nextInt(40 - 3) + 1) + 3) {
				// Soldier shoots
				Vector2 shootingDirection = null;
				if (distanceToP1 <= 200 && player1.isEnabled()) {
					shootingDirection = new Vector2(player1.getPosition().x - s.getPosition().x,
							player1.getPosition().y - s.getPosition().y).nor();
					this.enemyBullets
							.add(new EnemyBullet(s.getPosition().x, s.getPosition().y + 10, shootingDirection));
					// Add market with direction so we can animate it in the clients
				} else if (distanceToP2 <= 200 && player2.isEnabled()) {
					shootingDirection = new Vector2(player2.getPosition().x - s.getPosition().x,
							player2.getPosition().y - s.getPosition().y).nor();
					this.enemyBullets
							.add(new EnemyBullet(s.getPosition().x, s.getPosition().y + 10, shootingDirection));
				}
				s.setActionCounter(0f);
			} else if (distanceToP1 <= 50) {
				// Run from player
				s.getPosition().x -= vectorToP1.x * deltaTime * 100;
			} else if (distanceToP2 <= 50) {
				s.getPosition().x -= vectorToP2.x * deltaTime * 100;
			}
		}
	}

	/**
	 * Metodo que actualiza la posici�n de las balas en el servidor con el paso del
	 * tiempo
	 * 
	 */
	public void updateBulletsPosition() {
		for (Bullet b : bullets) {
			if (b.isEnabled()) {
				b.getPosition().x += b.getBulletDirection().x * deltaTime * 350;
				b.getPosition().y += b.getBulletDirection().y * deltaTime * 350;
				b.updateBulletRectangle();
			}
		}

		for (EnemyBullet eb : enemyBullets) {
			eb.getPosition().x += eb.getBulletDirection().x * deltaTime * 200;
			eb.getPosition().y += eb.getBulletDirection().y * deltaTime * 200;
			eb.updateEnemyBulletRectangle();
		}
	}

	/**
	 * Metodo que busca a un jugador por su nombre
	 * 
	 * @param username
	 * @return
	 */
	public ServerPlayer getPlayerByNickName(String username) {
		if (this.player1.getUsername().equalsIgnoreCase(username)) {
			return this.player1;
		} else {
			return this.player2;
		}
	}

	/**
	 * Metodo que envia el mensaje de actualizacion de la partida a los clientes
	 * 
	 */
	private void sendGameUpdate() {
		GameEvent gameEvent = new GameEvent();
		gameEvent.players = new ArrayList<>();
		gameEvent.players.add(new ServerPlayerData(player1.getUsername(), player1.getPosition(), player1.getHp(),
				player1.isEnabled(),player1.getLookingDirection(), player1.isMoving(), player1.isShooting()));
		if(player1.isShooting() && player1.getAnimationLock() < 0.1f){
			player1.setAnimationLock(player1.getAnimationLock() + deltaTime);
		}else{
			player1.setShooting(false);
			player1.setAnimationLock(0);
		}

		gameEvent.players.add(new ServerPlayerData(player2.getUsername(), player2.getPosition(), player2.getHp(),
				player2.isEnabled(), player2.getLookingDirection(),player2.isMoving(), player2.isShooting()));
		if(player2.isShooting() && player2.getAnimationLock() < 0.1f){
			player2.setAnimationLock(player2.getAnimationLock() + deltaTime);
		}else{
			player2.setShooting(false);
			player2.setAnimationLock(0);
		}
		gameEvent.bullets = this.bullets;
		this.onUpdateCallback.sendToBothClients(gameEvent);

		EnemyEvent enemyEvent = new EnemyEvent();
		enemyEvent.soldiers = this.soldiers;
		enemyEvent.enemyBullets = this.enemyBullets;
		this.onUpdateCallback.sendToBothClients(enemyEvent);

	}

	/**
	 * Metodo que busca una serie de objetos presentes en el mapa creado en tiled en
	 * base a un nombre
	 * 
	 * @param propertyName
	 * @return
	 */
	private ArrayList<MapObject> getRectangleList(String propertyName) {
		ArrayList<MapObject> list = new ArrayList<>();
		for (MapLayer layer : map.getLayers()) {
			for (MapObject obj : layer.getObjects()) {
				if (!(obj instanceof RectangleMapObject))
					continue;
				MapProperties props = obj.getProperties();
				if (props.containsKey("name") && props.get("name").equals(propertyName)) {
					list.add(obj);
				}

			}
		}
		return list;
	}

	/**
	 * Metodo que aplica la gravedad a los jugadores y a los soldados
	 * 
	 */
	private void applyGravity() {
		if (!this.player1.isOnGround()) {
			Vector2 v = this.player1.getPosition();
			v.y -= 1;
		}
		if (!this.player2.isOnGround()) {
			Vector2 v = this.player2.getPosition();
			v.y -= 1;
		}
		for (Soldier s : soldiers) {
			if (!s.isOnGround()) {
				Vector2 v = s.getPosition();
				v.y -= 1;
			}
		}
	}

	/**
	 * Metodo que previene la superposicion entre las distintas entidades del
	 * servidor
	 * 
	 * Por ejemplo: solidos, jugadores, soldados..
	 */
	private void preventOverlapping() {
		player1.setOnGround(false);
		player2.setOnGround(false);
		for (Solid s : this.solids) {
			if (s.isCollision(this.player1.getBoundRect())) {
				player1.preventOverlap(s.getColision());
			}
			if (s.isCollision(this.player1.getFeet())) {
				this.player1.setOnGround(true);
			}
			if (s.isCollision(this.player2.getBoundRect())) {
				player2.preventOverlap(s.getColision());
			}
			if (s.isCollision(this.player2.getFeet())) {
				this.player2.setOnGround(true);
			}
			for (Soldier soldier : this.soldiers) {
				soldier.setOnGround(false);
				if (s.isCollision(soldier.getBoundRectangle())) {
					soldier.preventOverlap(s.getColision());
				}
				if (s.isCollision(soldier.getFeet())) {
					soldier.setOnGround(true);
				}
			}
		}
	}

	/**
	 * Metodo que elimina las balas en caso de impacto en alguna entidad
	 * 
	 */
	private void removeBullets() {
		for (EnemyBullet eb : enemyBullets) {
			if (eb.getPosition().x > this.mapWidthInPixels || eb.getPosition().y > this.mapHeightInPixels) {
				eb.setEnabled(false);
			} else if (eb.isEnabled() && eb.getBoundRect().overlaps(player1.getBoundRect()) && player1.isEnabled()) {
				// Add damage to the player
				player1.setHp(player1.getHp() - 10);
				eb.setEnabled(false);
			} else if (eb.isEnabled() && eb.getBoundRect().overlaps(player2.getBoundRect()) && player2.isEnabled()) {
				// Add damage to the player
				player2.setHp(player2.getHp() - 10);
				eb.setEnabled(false);
			}
		}

		for (Bullet b : bullets) {
			float distanceToPlayerWhoShot = this.distanceBetweenTwoPoints(b.getPosition(), this.getPlayerByNickName(b.getUserWhoShot()).getPosition());
			if (b.getPosition().x > this.mapWidthInPixels || b.getPosition().y > this.mapHeightInPixels || distanceToPlayerWhoShot > 200) {
				b.setEnabled(false);
			}
			for (Soldier s : soldiers) {
				if (s.getBoundRectangle().overlaps(b.getBoundRect())) {
					s.setHp(s.getHp() - 1);
					b.setEnabled(false);
				}
			}
		}

	}

	/**
	 * Metodo que marca a los enemigos con vida menor o igual a 0
	 * 
	 */
	public void removeEnemies() {
		// Check the soldiers HP for cleanup
		for (Soldier s : soldiers) {
			if (s.getHp() <= 0) {
				s.setEnabled(false);
			}
		}
	}

	/**
	 * Metodo que marca a los jugadores con una vida menor o igual a 0
	 * 
	 */
	public void removePlayers() {
		if (player1.getHp() <= 0) {
			player1.setEnabled(false);
		}
		if (player2.getHp() <= 0) {
			player2.setEnabled(false);
		}
	}

	/**
	 * Metodo que calcula la distancia entre dos puntos
	 * 
	 * @param vec1
	 * @param vec2
	 * @return
	 */
	public float distanceBetweenTwoPoints(Vector2 vec1, Vector2 vec2) {
		return (float) (Math.sqrt((vec1.x - vec2.x) * (vec1.x - vec2.x) + (vec1.y - vec2.y) * (vec1.y - vec2.y)));
	}

	/**
	 * Interfaz generada para realizar callbacks, invirtiendo el flujo de datos, es
	 * decir, la clase contenida avisa a la contendora de esta
	 *
	 */
	public interface notifyGameServer {
		void switchLevel();
		void endLobby();
	}

	/**
	 * Metodo que comprueba si se debe terminar la partida
	 * 
	 */
	public void checkEnding() {
		if (this.end.isCollision(player1.getBoundRect())) {
			this.notifyGameServerCallback.switchLevel();
		} else if (this.end.isCollision(player2.getBoundRect())) {
			this.notifyGameServerCallback.switchLevel();
		} else if (!this.player1.isEnabled() && !this.player2.isEnabled()) {
			this.notifyGameServerCallback.endLobby();
		}
	}

}
