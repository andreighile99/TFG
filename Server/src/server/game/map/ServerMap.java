package server.game.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import server.events.game.*;
import server.game.elements.Bullet;
import server.game.elements.Soldier;
import server.game.elements.Solid;
import server.handlers.ResourceManager;
import server.model.ServerPlayer;

import java.util.ArrayList;
import java.util.Random;

public class ServerMap {

    private float deltaTime = 0;

    private onUpdate callback;

    private ArrayList<Float> playerPositions;
    private ArrayList<Float> bulletsPositions;
    private ArrayList<Float> soldiersPositions;

    private ServerPlayer player1;
    private ServerPlayer player2;

    private float startingX;
    private float startingY;

    private TiledMap map;
    private ArrayList<Solid> solids;
    private ArrayList<Soldier> soldiers;
    private ArrayList<MapObject> elements;
    private ArrayList<Bullet> bullets;


    public ServerMap(onUpdate callback, ServerPlayer player1, ServerPlayer player2){

        ResourceManager.loadAllResources();
        map = ResourceManager.getMap("assets/maps/map1.tmx");

        elements = new ArrayList<>();

        bullets = new ArrayList<>();

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
            solid = new Solid((float) props.get("x"), (float) props.get("y"), (float) props.get("width"),
                    (float) props.get("height"));
            solids.add(solid);
        }

        //Add soldiers
        Soldier soldier;
        elements = getRectangleList("Soldier");
        this.soldiers = new ArrayList<>();
        for (MapObject mapObject : elements) {
            props = mapObject.getProperties();
            soldier = new Soldier((float) props.get("x"), (float) props.get("y"));
            soldiers.add(soldier);
        }


        this.playerPositions = new ArrayList<>();
        this.bulletsPositions = new ArrayList<>();
        this.soldiersPositions = new ArrayList<>();
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
        this.updateBulletsRectangles();

        this.preventOverlapping();
        this.removeBullets();
        this.removeEnemies();
        this.soldiersAction();
        this.updateBulletsPosition();

        this.applyGravity();

        this.sendGameUpdate();
        this.cleanupDisabledElements();
    }

    public interface onUpdate{
        void sendToBothClients(GameEvent gameEvent);
    }

    public void cleanupDisabledElements(){
        soldiers.removeIf(soldier -> !soldier.isEnabled());
        bullets.removeIf(bullet -> !bullet.isEnabled());
    }

    public void updatePlayerPosition(PlayerEvent message) {
            ServerPlayer player = this.getPlayerByNickName(message);
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
                        v.y += deltaTime * 1500;
                    }
                    break;
                default:
                    break;
            }
    }

    private void updatePlayersRectangles() {
        this.player1.updateRectanglePosition();
        this.player2.updateRectanglePosition();
    }

    public void playerShoots(BulletEvent message){
        ServerPlayer player = this.getPlayerByNickName(message);
        if(message.getBulletDirection() != null){
            Bullet bullet = new Bullet(player.getPosition().x, player.getPosition().y, message.getBulletDirection());
            this.bullets.add(bullet);
        }
    }

    public void soldiersAction(){
        for(Soldier s : soldiers){
            s.updateSoldierPosition();
            Random rand = new Random();
            int x = rand.nextInt(2) + 1;
            //decidir las acciones de los soldados
        }
    }

    public void updateBulletsPosition(){
            for(Bullet b : bullets){
                if(b.isEnabled()){
                    b.getPosition().x += b.getBulletDirection().x * deltaTime * 350;
                    b.getPosition().y += b.getBulletDirection().y * deltaTime * 350;
                    b.updateBulletRectangle();
                }

            }
    }

    public void updateBulletsRectangles(){
            for(Bullet b : bullets){
                if(b.isEnabled()){
                    b.updateBulletRectangle();
                }
            }
    }

    public ServerPlayer getPlayerByNickName(PlayerEvent message){
        if(this.player1.getUsername().equalsIgnoreCase(message.getUsername())){
            return this.player1;
        }else{
            return this.player2;
        }
    }

    public ServerPlayer getPlayerByNickName(BulletEvent message){
        if(this.player1.getUsername().equalsIgnoreCase(message.getUsername())){
            return this.player1;
        }else{
            return this.player2;
        }
    }

    public void gatherPlayerPositions(){
        if(!this.playerPositions.isEmpty()){
            this.playerPositions.clear();
        }
       this.playerPositions.add(this.player1.getPosition().x);
       this.playerPositions.add(this.player1.getPosition().y);
       this.playerPositions.add(this.player2.getPosition().x);
       this.playerPositions.add(this.player2.getPosition().y);
    }

    public void gatherBulletsPositions(){
        bulletsPositions.clear();
        for(Bullet b : bullets){
            bulletsPositions.add(b.getPosition().x);
            bulletsPositions.add(b.getPosition().y);
        }
    }

    public void gatherSoldiersPositions(){
        if(!soldiers.isEmpty()){
            soldiersPositions.clear();
            for(Soldier s : soldiers){
                soldiersPositions.add(s.getPosition().x);
                soldiersPositions.add(s.getPosition().y);
            }
        }
    }

    private void sendGameUpdate(){
        GameEvent gameEvent = new GameEvent();
        this.gatherPlayerPositions();
        this.gatherBulletsPositions();
        this.gatherSoldiersPositions();

        gameEvent.playerPositions = this.playerPositions;
        gameEvent.player1 = this.player1.getUsername();
        gameEvent.player2 = this.player2.getUsername();
        gameEvent.bullets = this.bullets;
        gameEvent.soldiers= this.soldiers;

        this.callback.sendToBothClients(gameEvent);
    }

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

    private void preventOverlapping(){
        player1.setOnGround(false);
        player2.setOnGround(false);
        for(Solid s : this.solids){
            if(s.isCollision(this.player1.getBoundRect())){
                player1.preventOverlap(s.getColision());
            }
            if(s.isCollision(this.player1.getFeet())){
                this.player1.setOnGround(true);
            }
            if(s.isCollision(this.player2.getBoundRect())){
                player2.preventOverlap(s.getColision());
            }
            if(s.isCollision(this.player2.getFeet())){
                this.player2.setOnGround(true);
            }
        }
    }

    private void removeBullets(){
        for(Solid s : solids){
            for(Bullet b : bullets){
                if(s.isCollision(b.getBoundRect())){
                    b.setEnabled(false);
                }
            }
        }
        //If the bullets collide against an enemy, reduce their hp but also disable the bullet
        for(Soldier s : soldiers){
            for(Bullet b : bullets){
                if(s.getBoundRectangle().overlaps(b.getBoundRect())){
                    s.setHp(s.getHp()-1);
                    b.setEnabled(false);
                }
            }
        }

    }

    public void removeEnemies(){
        //Check the soldiers HP for cleanup
        for(Soldier s : soldiers){
            if(s.getHp() <= 0){
                s.setEnabled(false);
            }
        }
    }

}
