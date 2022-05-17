package server.game.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import server.events.game.GameEvent;
import server.events.game.PositionEvent;
import server.game.elements.Solid;
import server.handlers.ResourceManager;
import server.model.ServerPlayer;

import java.util.ArrayList;

public class ServerMap {

    private float deltaTime = 0;

    private onUpdate callback;

    private ArrayList<Float> playerPositions;

    private ServerPlayer player1;
    private ServerPlayer player2;

    private float startingX;
    private float startingY;

    private TiledMap map;
    private ArrayList<Solid> solids;
    private ArrayList<MapObject> elements;


    public ServerMap(onUpdate callback, ServerPlayer player1, ServerPlayer player2){

        ResourceManager.loadAllResources();
        map = ResourceManager.getMap("assets/maps/map1.tmx");

        elements = new ArrayList<>();

        //mainStage = new Stage();

        //Add starting x and y
        MapProperties props;

        elements = getRectangleList("Inicio");
        props = elements.get(0).getProperties();

        startingX = (float) props.get("x");
        startingY = (float) props.get("y");

        //Add solids
        Solid solid;
        elements = getRectangleList("Solid");
        this.solids = new ArrayList<>();
        for (MapObject mapObject : elements) {
            props = mapObject.getProperties();
            System.out.println("El sólido tiene las siguientes propiedades" + "\nX:" + props.get("x") + "\nY:" +props.get("y")+ "\nW:" +  props.get("width")+
                    "\nH:" + props.get("height"));
            solid = new Solid((float) props.get("x"), (float) props.get("y"), (float) props.get("width"),
                    (float) props.get("height"));
            //System.out.println("El sólido tiene las propiedades: " + "\nX: " + solid.getX() + "\nY: " + solid.getY() + "\nHeight: ");
            solids.add(solid);
            System.out.println(solid.getX()+""+solid.getY());
        }

        this.playerPositions = new ArrayList<>();
        this.callback = callback;

        this.player1 = player1;
        this.player1.setStartingPoint(startingX, startingY);
        this.player1.init();

        this.player2 = player2;
        this.player2.setStartingPoint(startingX, startingY);
        this.player2.init();
    }

    public void update(float deltaTime) {
        this.deltaTime = deltaTime;

        this.updatePlayersRectangles();

        this.preventColision();

        this.applyGravity();

        GameEvent gameEvent = new GameEvent();
        this.gatherPlayerPositions();

        gameEvent.playerPositions = this.playerPositions;
        gameEvent.player1 = this.player1.getUsername();
        gameEvent.player2 = this.player2.getUsername();

        this.callback.sendToBothClients(gameEvent);
    }

    public interface onUpdate{
        void sendToBothClients(GameEvent gameEvent);
    }

    public void updatePlayerPosition(PositionEvent message) {
            ServerPlayer player = this.checkPlayerNickname(message);
            Vector2 v = player.getPosition();
            switch (message.getDirection()) {
                case LEFT:
                    v.x -= deltaTime * 200;
                    break;
                case RIGHT:
                    v.x += deltaTime * 200;
                    break;
                case UP:
                    if(player.isOnGround()){
                        v.y += deltaTime * 1000;
                    }
                    break;
                case DOWN:
                    v.y -= deltaTime * 200;
                    break;
                default:
                    break;
            }
        //System.out.println("El jugador se ha movido a la posición " + player.getPosition().x + " " + player.getPosition().y);
    }

    private void updatePlayersRectangles(){
        this.player1.updateRectanglePosition(player1.getPosition().x, player1.getPosition().y);
        this.player2.updateRectanglePosition(player2.getPosition().x, player2.getPosition().y);
    }

    public ServerPlayer checkPlayerNickname(PositionEvent message){
        if(this.player1.getUsername().equalsIgnoreCase(message.getUsername())){
            //System.out.println("Recibida una actualización del jugador 1");
            return this.player1;
        }else{
            //System.out.println("Recibida una actualización del jugador 2");
            return this.player2;
        }
    }

    public void gatherPlayerPositions(){
        if(!this.playerPositions.isEmpty()){
            this.playerPositions.clear();
        }
       this.playerPositions.add(this.player1.getPosition().x);
       this.playerPositions.add(this.player1.getPosition().y);
       //System.out.println("LOG - Jugador1 posición = " + this.player1.getPosition().x + " " + this.player1.getPosition().y);
       this.playerPositions.add(this.player2.getPosition().x);
       this.playerPositions.add(this.player2.getPosition().y);
    }

    private ArrayList<MapObject> getRectangleList(String propertyName) {
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

    private void applyGravity(){
        if(!this.player1.isOnGround()){
            Vector2 v = this.player1.getPosition();
            v.y -= 1;
        }
        if(!this.player2.isOnGround()){
            Vector2 v = this.player2.getPosition();
            v.y -= 1;
        }
    }

    //?¿?
    private void preventColision(){
        player1.setOnGround(false);
        player2.setOnGround(false);
        for(Solid s : this.solids){
            if(s.isCollision(this.player1.getBoundRect())){
                player1.preventOverlap(s.getColision());
                System.out.println(s.getX()+" "+s.getY());
            }
            if(s.isCollision(this.player1.getFeet())){
                this.player1.setOnGround(true);
                System.out.println(s.getX()+" "+s.getY());
            }
            if(s.isCollision(this.player2.getBoundRect())){
                player2.preventOverlap(s.getColision());
            }
            if(s.isCollision(this.player2.getFeet())){
                this.player2.setOnGround(true);
            }
        }
    }

}
