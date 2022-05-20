package main;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.esotericsoftware.minlog.Log;

public class Main{
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1600;
        config.height = 900;
        config.foregroundFPS = 60;
        config.backgroundFPS = 60;
        config.resizable = false;
        new LwjglApplication(new MontessoriSlug(), config);
    }

}
