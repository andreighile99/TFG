package events.game;

import java.util.ArrayList;

public class GameEvent {
    //x1, y1, x2, y2
    public String player1;
    public String player2;
    public ArrayList<Float> playerPositions;

    public GameEvent() {
    }

    public ArrayList<Float> getPlayerPositions() {
        return playerPositions;
    }

    public void setPlayerPositions(ArrayList<Float> playerPositions) {
        this.playerPositions = playerPositions;
    }


}
