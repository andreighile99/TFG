package server.handlers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Clase que carga en memoria los mapas para el servidor
 * 
 * @author Eduard Andrei Ghile
 *
 */
public final class ResourceManager {
    private ResourceManager() {}
    public static AssetManager assets=new AssetManager();


    public static void loadAllResources(){
        //Maps
        assets.setLoader(TiledMap.class, new TmxMapLoader());
        assets.load("assets/maps/firstMap.tmx", TiledMap.class);
        assets.load("assets/maps/secondMap.tmx", TiledMap.class);
        assets.finishLoading();

    }

    public static TiledMap getMap(String path){
        return assets.get(path, TiledMap.class);
    }

}

