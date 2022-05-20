package screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import elements.Bullet;
import elements.ManagedPlayer;
import elements.Player;
import elements.Soldier;
import events.game.GameEvent;
import handlers.ResourceManager;
import main.MontessoriSlug;

import java.util.ArrayList;

public class GameScreen extends BScreen{

    private static OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;

    private Player player1;
    private ManagedPlayer player2;

    private Stage mainStage;
    private TiledMap map;

    private float inicioX;
    private float inicioY;

    private ArrayList<Bullet> bullets;
    private ArrayList<Soldier> soldiers;


    public GameScreen(MontessoriSlug game, String username1, String username2, String lobbyName) {
        super(game);
        ResourceManager.loadAllResources();

        bullets = new ArrayList<>();
        soldiers = new ArrayList<>();

        map = ResourceManager.getMap("assets/maps/map1.tmx");

        mainStage = new Stage();
        renderer = new OrthogonalTiledMapRenderer(map, mainStage.getBatch());

        camera = (OrthographicCamera) mainStage.getCamera();
        camera.setToOrtho(false, 800,
                600);

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
        this.camera.position.x = player1.getX();
        this.camera.position.y = player1.getY();
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

    public void updateBulletsPosition(GameEvent gameEvent){
        //Number of bullets that have been reported by the server
        int numberOfBullets = gameEvent.bulletPositions.size() / 2;
        //Diference between the bullets in the client and the bullets in the server
        int deltaBullets = numberOfBullets - bullets.size();
        //Counter so we can get the positions from the ArrayList
        int i = 0;
        for(Bullet b : bullets){
            //These bullets were already displayed in my client so we just have to update them
            b.moveBy(gameEvent.bulletPositions.get(i) - b.getX(), gameEvent.bulletPositions.get(i+1) - b.getY());
            i += 2;
        }
        //If there are more bullets in the server than there are rendered in my client we have to generate them
        if(deltaBullets != 0) {
            for(int j = 0; j < deltaBullets; j++){
                //Make new bullets from where we left in the ArrayList using i
                bullets.add(new Bullet(gameEvent.bulletPositions.get(i), gameEvent.bulletPositions.get(i+1), mainStage));
                i += 2;
            }
        }
    }

    public void updateSoldiersPosition(GameEvent gameEvent){
        int numberOfSoldiers = gameEvent.soldierPositions.size() / 2;
        int deltaSoldiers = numberOfSoldiers - soldiers.size();
        int i = 0;
        for(Soldier s :soldiers){
            s.moveBy(gameEvent.soldierPositions.get(i) - s.getX(), gameEvent.soldierPositions.get(i+1) - s.getY());
            i+=2;
        }
        if(deltaSoldiers != 0) {
            for(int j = 0; j < deltaSoldiers; j++){
                soldiers.add(new Soldier(gameEvent.soldierPositions.get(i), gameEvent.soldierPositions.get(i+1), mainStage));
                i += 2;
            }
        }

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

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }
}
