package events.game;

import java.util.ArrayList;

public class RemoveEnemyEvent {
    private ArrayList<Integer> soldiersIndex;

    public ArrayList<Integer> getSoldiersIndex() {
        return soldiersIndex;
    }

    public void setSoldiersIndex(ArrayList<Integer> soldiersIndex) {
        this.soldiersIndex = soldiersIndex;
    }
}
