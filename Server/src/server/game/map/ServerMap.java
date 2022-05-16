package server.game.map;

import com.badlogic.gdx.math.Vector2;
import server.events.game.GameEvent;
import server.events.game.PositionEvent;
import server.model.ServerPlayer;

import java.util.ArrayList;

public class ServerMap {

    private float deltaTime = 0;

    private onUpdate callback;

    private ArrayList<Float> playerPositions;

    private ServerPlayer player1;
    private ServerPlayer player2;


    public ServerMap(onUpdate callback, ServerPlayer player1, ServerPlayer player2){
        this.playerPositions = new ArrayList<>();
        this.callback = callback;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void update(float deltaTime) {

        this.deltaTime = deltaTime;

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
                    v.y += deltaTime * 200;
                    break;
                case DOWN:
                    v.y -= deltaTime * 200;
                    break;
                default:
                    break;
            }
        //System.out.println("El jugador se ha movido a la posición " + player.getPosition().x + " " + player.getPosition().y);
    }

    public ServerPlayer checkPlayerNickname(PositionEvent message){
        if(this.player1.getUsername().equalsIgnoreCase(message.getUsername())){
            //System.out.println(message.getUsername());
            System.out.println("Recibida una actualización del jugador 1");
            return this.player1;
        }else{
            System.out.println("Recibida una actualización del jugador 2");
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

}
