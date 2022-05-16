package server.handlers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public final class ResourceManager {
    private ResourceManager() {}
    public static AssetManager assets=new AssetManager();
    public static LabelStyle buttonStyle;
    public static TextButtonStyle textButtonStyle;




    public static void loadAllResources(){
        //maps
        assets.setLoader(TiledMap.class, new TmxMapLoader());
        assets.load("assets/maps/map1.tmx", TiledMap.class);
        assets.finishLoading();

    }

    public static boolean update(){
        return assets.update();
    }

    public static Texture getTexture(String path) {
        return assets.get(path, Texture.class);
    }

    public static Music getMusic(String path){
        return assets.get(path, Music.class);
    }

    public static Sound getSound(String path)
    {
        return assets.get(path, Sound.class);
    }

    public static TiledMap getMap(String path){
        return assets.get(path, TiledMap.class);
    }

    public static void dispose(){
        assets.dispose();
    }
}

