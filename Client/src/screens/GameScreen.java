package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import customRenderer.OrthogonalTiledMapRendererWithBackground;
import elements.*;
import elements.serverSide.ServerPlayerData;
import events.game.EnemyEvent;
import events.game.GameEvent;
import handlers.FontSizeHandler;
import handlers.ResourceManager;
import main.MontessoriSlug;
import parameters.Parameters;

import java.util.ArrayList;

public class GameScreen extends BScreen{

    private static OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;

    private Player player1;
    private ManagedPlayer player2;

    private final Texture fullBulletH;
    private final Texture halfBulletH;
    private final Texture hpBackground;

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

    private ArrayList<Texture> lifeRepresentation;

    private final BitmapFont font;



    public GameScreen(MontessoriSlug game, String username1, String username2, String lobbyName) {
        super(game);
        this.fullBulletH = ResourceManager.getTexture("assets/player/hp/FullBulletH.png");
        this.halfBulletH = ResourceManager.getTexture("assets/player/hp/HalfBulletH.png");
        this.hpBackground = ResourceManager.getTexture("assets/player/hp/HpBackground.png");
        this.font = FontSizeHandler.INSTANCE.getFont(32, Color.BLACK);
        lifeRepresentation = new ArrayList<>();

        bulletReps = new ArrayList<>();
        soldierReps = new ArrayList<>();
        enemyBulletReps = new ArrayList<>();

        switch(Parameters.level){
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

        mainStage = new Stage();
        renderer = new OrthogonalTiledMapRendererWithBackground(map);

        MapProperties props = map.getProperties();

        tileWidth = props.get("tilewidth", Integer.class);
        tileHeight = props.get("tileheight", Integer.class);
        mapWidthInTiles = props.get("width", Integer.class);
        mapHeightInTiles = props.get("height", Integer.class);
        mapWidthInPixels = tileWidth * mapWidthInTiles;
        mapHeightInPixels = tileHeight * mapHeightInTiles;

        camera = (OrthographicCamera) mainStage.getCamera();
        camera.setToOrtho(false, Parameters.cameraWidth,
                Parameters.cameraHeight);

        ArrayList<MapObject> elements = getRectangleList("Inicio");

        //Add starting x and y
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

        calculateHpRepresentation();

        mainStage.draw();
        uiStage.draw();
        drawHpRepresentation();



    }

    public void centerCamera() {
        if(Parameters.level == 1){
            if(this.player1.getEnabled()){
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
            }else{
                if(this.player2.getX() < Parameters.cameraWidth/2){
                    this.camera.position.x = Parameters.cameraWidth/2;
                }else if(this.player2.getX() > mapWidthInPixels - Parameters.cameraWidth/2){
                    this.camera.position.x = mapWidthInPixels - Parameters.cameraWidth/2;
                }else{
                    this.camera.position.x = player2.getX();
                }
                if(this.player2.getY() < Parameters.cameraHeight / 2){
                    this.camera.position.y = player2.getY() + (Parameters.cameraHeight / 2 - player2.getY());
                }else {
                    this.camera.position.y = player2.getY();
                }
            }
        }else if(Parameters.level == 2){
            if(this.player1.getEnabled()){
                if(this.player1.getX() < Parameters.cameraWidth/2){
                    this.camera.position.x = Parameters.cameraWidth/2;
                }else if(this.player1.getX() > mapWidthInPixels - Parameters.cameraWidth/2){
                    this.camera.position.x = mapWidthInPixels - Parameters.cameraWidth/2;
                }else{
                    this.camera.position.x = player1.getX();
                }
                this.camera.position.y = this.player1.getY()+80;
            }else{
                if(this.player2.getX() < Parameters.cameraWidth/2){
                    this.camera.position.x = Parameters.cameraWidth/2;
                }else if(this.player2.getX() > mapWidthInPixels - Parameters.cameraWidth/2){
                    this.camera.position.x = mapWidthInPixels - Parameters.cameraWidth/2;
                }else{
                    this.camera.position.x = player2.getX();
                }
                this.camera.position.y = this.player1.getY()+80;
            }
        }

        camera.update();
    }

    public void calculateHpRepresentation(){
        int temporalHP = player1.getHp();
        if(!lifeRepresentation.isEmpty()) {
            lifeRepresentation.clear();
        }
        while((temporalHP - 20) >= 0){
            lifeRepresentation.add(fullBulletH);
            temporalHP -= 20;
        }
        while((temporalHP - 10) >= 0){
            lifeRepresentation.add(halfBulletH);
            temporalHP -= 10;
        }
    }

    public void drawHpRepresentation(){
        uiStage.getBatch().begin();
        float offset = 0;

        uiStage.getBatch().draw(hpBackground, 0, Parameters.screenHeight - 50, 276, 50);
        font.draw(uiStage.getBatch(), "HP", 10, Parameters.screenHeight - 16);
        for(Texture texture : lifeRepresentation){
            uiStage.getBatch().draw(texture, 62 + offset, Parameters.screenHeight - 40, 30, 30);
            offset += 40;
        }
        uiStage.getBatch().end();

    }


    public void updatePlayersStatus(GameEvent gameEvent){
        for(ServerPlayerData serverPlayerData : gameEvent.players){
            if(serverPlayerData.getUsername().equalsIgnoreCase(this.player1.getUsername()) && serverPlayerData.isEnabled()){
                player1.setX(serverPlayerData.getPosition().x);
                player1.setY(serverPlayerData.getPosition().y);
                player1.setHp(serverPlayerData.getHp());
            }else if(serverPlayerData.getUsername().equalsIgnoreCase(this.player2.getUsername()) && serverPlayerData.isEnabled()){
                player2.setX(serverPlayerData.getPosition().x);
                player2.setY(serverPlayerData.getPosition().y);
                player2.setHp(serverPlayerData.getHp());
            }
        }

        if(this.player1.getHp() <= 0){
            this.player1.setEnabled(false);
        }

        if(this.player2.getHp() <= 0){
            this.player2.setEnabled(false);
        }
    }

    public void updateEnemyBulletsPosition(EnemyEvent enemyEvent){
        int enemyBulletsOnServer = enemyEvent.enemyBullets.size();
        int deltaEnemyBullets = enemyBulletsOnServer - this.enemyBulletReps.size();
        if(deltaEnemyBullets > 0){
            for(int i = 0; i<deltaEnemyBullets; i++){
                this.enemyBulletReps.add(new EnemyBulletRep(enemyEvent.enemyBullets.get(i).getPosition().x, enemyEvent.enemyBullets.get(i).getPosition().y, mainStage));
            }
        }
        for(int i = 0; i<enemyBulletsOnServer; i++){
            if(enemyEvent.enemyBullets.get(i).isEnabled()){
                this.enemyBulletReps.get(i).moveBy(enemyEvent.enemyBullets.get(i).getPosition().x - this.enemyBulletReps.get(i).getX(), enemyEvent.enemyBullets.get(i).getPosition().y - this.enemyBulletReps.get(i).getY());
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
                //- (anchura bala - anchura bala servidor)/2
                this.bulletReps.add(new BulletRep(gameEvent.bullets.get(i).getPosition().x , gameEvent.bullets.get(i).getPosition().y, mainStage));
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

    public void updateSoldiersPosition(EnemyEvent enemyEvent){
        int soldiersOnServer = enemyEvent.soldiers.size();
        int deltaSoldiers = soldiersOnServer - this.soldierReps.size();
        if(deltaSoldiers > 0){
            for(int i = 0; i<deltaSoldiers; i++){
                this.soldierReps.add(new SoldierRep(enemyEvent.soldiers.get(i).getPosition().x,enemyEvent.soldiers.get(i).getPosition().y, mainStage));
            }
        }
        for(int i = 0; i<soldiersOnServer; i++){
            if(enemyEvent.soldiers.get(i).isEnabled()){
                this.soldierReps.get(i).moveBy(enemyEvent.soldiers.get(i).getPosition().x - this.soldierReps.get(i).getX(), enemyEvent.soldiers.get(i).getPosition().y - this.soldierReps.get(i).getY());
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


    public Player getPlayer1() {
        return player1;
    }

    public ManagedPlayer getPlayer2() {
        return player2;
    }
}
