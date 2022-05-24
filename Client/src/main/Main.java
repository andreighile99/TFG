
package main;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.esotericsoftware.minlog.Log;
import parameters.Parameters;

/**
 * Clase principal que inicia la aplicacion LWJGL y comienza el juego.
 *
 * @author Eduard Andrei Ghile
 */
public class Main{
    
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Parameters.screenWidth;
        config.height = Parameters.screenHeight;
        config.foregroundFPS = 60;
        config.backgroundFPS = 60;
        config.resizable = false;
        new LwjglApplication(new MontessoriSlug(), config);
    }

}
