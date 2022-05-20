package events.game;

import java.util.ArrayList;

public class GameEvent {
    //x1, y1, x2, y2
    public ArrayList<Float> playerPositions;
    public String player1;
    public String player2;
    //x1, y1, x2, y2, x3, y3
    public ArrayList<Float> bulletPositions;
    public ArrayList<Float> soldierPositions;

    public GameEvent() {
    }



}
