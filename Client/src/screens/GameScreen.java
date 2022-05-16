package screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import elements.ManagedPlayer;
import elements.Player;
import elements.Solid;
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

    public ArrayList<Solid> solids;

    public GameScreen(MontessoriSlug game, String username1, String username2, String lobbyName) {
        super(game);
        ResourceManager.loadAllResources();

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


}
