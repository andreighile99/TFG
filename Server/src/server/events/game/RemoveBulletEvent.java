package server.events.game;

import java.util.ArrayList;

public class RemoveBulletEvent {
    private ArrayList<Integer> bulletIndex;

    public ArrayList<Integer> getBulletIndex() {
        return bulletIndex;
    }

    public void setBulletIndex(ArrayList<Integer> bulletIndex) {
        this.bulletIndex = bulletIndex;
    }
}
