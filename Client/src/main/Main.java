package main;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.esotericsoftware.minlog.Log;

public class Main{
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;
        config.foregroundFPS = 60;
        config.backgroundFPS = 60;
        config.resizable = false;
        new LwjglApplication(new MontessoriSlug(), config);
    }

}
