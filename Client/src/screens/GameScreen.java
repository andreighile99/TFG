package screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import customRenderer.OrthogonalTiledMapRendererWithBackground;
import elements.*;
import events.game.GameEvent;
import handlers.ResourceManager;
import main.MontessoriSlug;
import parameters.Parameters;

import java.util.ArrayList;

public class GameScreen extends BScreen{

    private static OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;

    private Player player1;
    private ManagedPlayer player2;

    private Stage mainStage;
    private TiledMap map;
    private int tileWidth;
    private int tileHeight;
    private int mapWidthInTiles;
    private int mapHeightInTiles;
    private int mapWidthInPixels;
    private int mapHeightInPixels;

    private float inicioX;
    private float inicioY;

    private ArrayList<BulletRep> bulletReps;
    private ArrayList<SoldierRep> soldierReps;
    private ArrayList<EnemyBulletRep> enemyBulletReps;



    public GameScreen(MontessoriSlug game, String username1, String username2, String lobbyName) {
        super(game);
        ResourceManager.loadAllResources();

        bulletReps = new ArrayList<>();
        soldierReps = new ArrayList<>();
        enemyBulletReps = new ArrayList<>();

        map = ResourceManager.getMap("assets/maps/firstMap.tmx");

        mainStage = new Stage();
        renderer = new OrthogonalTiledMapRendererWithBackground(map);
        //renderer = new OrthogonalTiledMapRenderer(map, mainStage.getBatch());

        MapProperties properties = map.getProperties();

        tileWidth = properties.get("tilewidth", Integer.class);
        tileHeight = properties.get("tileheight", Integer.class);
        mapWidthInTiles = properties.get("width", Integer.class);
        mapHeightInTiles = properties.get("height", Integer.class);
        mapWidthInPixels = tileWidth * mapWidthInTiles;
        mapHeightInPixels = tileHeight * mapHeightInTiles;

        camera = (OrthographicCamera) mainStage.getCamera();
        camera.setToOrtho(false, Parameters.cameraWidth,
                Parameters.cameraHeight);

        ArrayList<MapObject> elements = getRectangleList("Inicio");

        //Add starting x and y
        MapProperties props;
        props = elements.get(0).getProperties();
        this.inicioX = (float) props.get("x");
        this.inicioY = (float) props.get("y");


        this.player1 = new Player(inicioX, inicioY, mainStage, username1, lobbyName);
        this.player2 = new ManagedPlayer(inicioX, inicioY, mainStage, username2, lobbyName);

    }




    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub

        super.render(delta);
        mainStage.act();
        uiStage.act();
        centerCamera();

        renderer.setView(camera);
        renderer.render();

        mainStage.draw();
        uiStage.draw();



    }

    public void centerCamera() {
        System.out.println(this.player1.getY());
        if(this.player1.getX() < Parameters.cameraWidth/2){
            this.camera.position.x = Parameters.cameraWidth/2;
        }else if(this.player1.getX() > mapWidthInPixels - Parameters.cameraWidth/2){
            this.camera.position.x = mapWidthInPixels - Parameters.cameraWidth/2;
        }else{
            this.camera.position.x = player1.getX();
        }

        if(this.player1.getY() < Parameters.cameraHeight / 2){
            this.camera.position.y = player1.getY() + (Parameters.cameraHeight / 2 - player1.getY());
        }else {
            this.camera.position.y = player1.getY();
        }

        camera.update();
    }


    public void updatePlayersPosition(GameEvent gameEvent){
        if(gameEvent.player1.equalsIgnoreCase(this.player1.getUsername())){
            player1.setX(gameEvent.playerPositions.get(0));
            player1.setY(gameEvent.playerPositions.get(1));
            player2.setX(gameEvent.playerPositions.get(2));
            player2.setY(gameEvent.playerPositions.get(3));
        }else{
            player2.setX(gameEvent.playerPositions.get(0));
            player2.setY(gameEvent.playerPositions.get(1));
            player1.setX(gameEvent.playerPositions.get(2));
            player1.setY(gameEvent.playerPositions.get(3));
        }
    }

    public void updateEnemyBulletsPosition(GameEvent gameEvent){
        int enemyBulletsOnServer = gameEvent.enemyBullets.size();
        int deltaEnemyBullets = enemyBulletsOnServer - this.enemyBulletReps.size();
        if(deltaEnemyBullets > 0){
            for(int i = 0; i<deltaEnemyBullets; i++){
                this.enemyBulletReps.add(new EnemyBulletRep(gameEvent.enemyBullets.get(i).getPosition().x, gameEvent.enemyBullets.get(i).getPosition().y, mainStage));
            }
        }
        for(int i = 0; i<enemyBulletsOnServer; i++){
            if(gameEvent.enemyBullets.get(i).isEnabled()){
                this.enemyBulletReps.get(i).moveBy(gameEvent.enemyBullets.get(i).getPosition().x - this.enemyBulletReps.get(i).getX(), gameEvent.enemyBullets.get(i).getPosition().y - this.enemyBulletReps.get(i).getY());
            }else{
                this.enemyBulletReps.get(i).setEnabled(false);
            }
        }
        cleanupDisabledElements();
    }

    public void updateBulletsPosition(GameEvent gameEvent){
        int bulletsOnServer = gameEvent.bullets.size();
        int deltaBullets = bulletsOnServer - this.bulletReps.size();
        if(deltaBullets > 0){
            for(int i = 0; i<deltaBullets; i++){
                this.bulletReps.add(new BulletRep(gameEvent.bullets.get(i).getPosition().x, gameEvent.bullets.get(i).getPosition().y, mainStage));
            }
        }
        for(int i = 0; i<bulletsOnServer; i++){
            if(gameEvent.bullets.get(i).isEnabled()){
                this.bulletReps.get(i).moveBy(gameEvent.bullets.get(i).getPosition().x - this.bulletReps.get(i).getX(), gameEvent.bullets.get(i).getPosition().y - this.bulletReps.get(i).getY());
            }else{
                    this.bulletReps.get(i).setEnabled(false);
            }
        }
        cleanupDisabledElements();
    }

    public void updateSoldiersPosition(GameEvent gameEvent){
        int soldiersOnServer = gameEvent.soldiers.size();
        int deltaSoldiers = soldiersOnServer - this.soldierReps.size();
        if(deltaSoldiers > 0){
            for(int i = 0; i<deltaSoldiers; i++){
                this.soldierReps.add(new SoldierRep(gameEvent.soldiers.get(i).getPosition().x,gameEvent.soldiers.get(i).getPosition().y, mainStage));
            }
        }
        for(int i = 0; i<soldiersOnServer; i++){
            if(gameEvent.soldiers.get(i).isEnabled()){
                this.soldierReps.get(i).moveBy(gameEvent.soldiers.get(i).getPosition().x - this.soldierReps.get(i).getX(), gameEvent.soldiers.get(i).getPosition().y - this.soldierReps.get(i).getY());
            }else{
                this.soldierReps.get(i).setEnabled(false);
            }
        }
        cleanupDisabledElements();
    }

    public void cleanupDisabledElements(){
        soldierReps.removeIf(soldier -> !soldier.getEnabled());
        bulletReps.removeIf(bullet -> !bullet.getEnabled());
        enemyBulletReps.removeIf(enemyBulletRep -> !enemyBulletRep.getEnabled());
    }

    public ArrayList<MapObject> getRectangleList(String propertyName) {
        ArrayList<MapObject> list = new ArrayList<MapObject>();
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


    public ArrayList<BulletRep> getBulletReps() {
        return bulletReps;
    }

    public void setBulletReps(ArrayList<BulletRep> bulletReps) {
        this.bulletReps = bulletReps;
    }

    public ArrayList<SoldierRep> getSoldierReps() {
        return soldierReps;
    }

    public void setSoldierReps(ArrayList<SoldierRep> soldierReps) {
        this.soldierReps = soldierReps;
    }
}
