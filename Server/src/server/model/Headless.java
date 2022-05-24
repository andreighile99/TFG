package server.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.mock.graphics.MockGraphics;
import com.badlogic.gdx.backends.lwjgl.LwjglFiles;
import com.badlogic.gdx.backends.lwjgl.LwjglNativesLoader;

/**
 * Clase de configuración para instancias de LibGDX headless, es decir, que no poseen imagen
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class Headless {
    public static void loadHeadless() {
        LwjglNativesLoader.load();
        Gdx.files = new LwjglFiles();
    }
}