package screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import elements.Player;
import events.game.GameEvent;
import handlers.ResourceManager;
import main.MontessoriSlug;

public class GameScreen extends BScreen{

    private static OrthographicCamera camera;

    private Player player1;
    private Player player2;

    private Stage mainStage;
    private TiledMap map;


    public GameScreen(MontessoriSlug game, String username1, String username2, String lobbyName) {
        super(game);
        ResourceManager.loadAllResources();

        //map = ResourceManager.getMap("src/assets/maps/Test1.tmx");

        mainStage = new Stage();

        camera = (OrthographicCamera) mainStage.getCamera();
        camera.setToOrtho(false, 800,
                600);
        float inicioX;
        float inicioY;

        this.player1 = new Player(100, 200, mainStage, username1, lobbyName);
        this.player2 = new Player(100, 200, mainStage, username2, lobbyName);

    }



    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub

        super.render(delta);
        mainStage.act();
        uiStage.act();

        centerCamera();


        mainStage.draw();
        uiStage.draw();



    }

    public void centerCamera() {
        this.camera.position.x = player1.getX();
        this.camera.position.y = player1.getY();
        camera.update();
    }


    public void updatePlayersPosition(GameEvent gameEvent){
        player1.setX(gameEvent.playerPositions.get(0));
        player1.setY(gameEvent.playerPositions.get(1));
        player2.setX(gameEvent.playerPositions.get(2));
        player2.setY(gameEvent.playerPositions.get(3));
    }


}
