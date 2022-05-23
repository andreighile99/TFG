package handlers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
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

        //mapas
        assets.setLoader(TiledMap.class, new TmxMapLoader());
        assets.load("assets/maps/firstMap.tmx", TiledMap.class);
        assets.load("assets/maps/secondMap.tmx", TiledMap.class);

        assets.load("assets/images/background.png", Texture.class);
        assets.load("assets/enemies/spider.png", Texture.class);

        assets.load("assets/player/idle.png", Texture.class);
        assets.load("assets/player/Bola.png", Texture.class);
        assets.load("assets/player/hp/FullBulletH.png", Texture.class);
        assets.load("assets/player/hp/HalfBulletH.png", Texture.class);
        assets.load("assets/player/hp/HpBackground.png", Texture.class);

        assets.finishLoading();

    }

    public static boolean update(){
        return assets.update();
    }
    public static void cargarEstiloBotones() {
        //Cargar la fuente para añadirla
        FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal("Sans.ttf"));
        FreeTypeFontParameter ftfp = new FreeTypeFontParameter();
        ftfp.size = 36;
        ftfp.color = Color.WHITE;
        ftfp.borderColor = Color.BLACK;
        ftfp.borderWidth = 2;
        //Añadimos la fuente
        BitmapFont fuentePropia = ftfg.generateFont(ftfp);
        buttonStyle = new LabelStyle();
        buttonStyle.font = fuentePropia;

        //Generamos el boton con NinePatch para que se escale correctamente
        textButtonStyle  = new TextButtonStyle();
        Texture buttonText = ResourceManager.getTexture("ui/button.png");
        NinePatch buttonPatch = new NinePatch(buttonText);
        buttonPatch.scale(6, 1);
        textButtonStyle.up = new NinePatchDrawable(buttonPatch);
        textButtonStyle.font = fuentePropia;
    }

	/*public static TextureAtlas getAtlas(String path){
		return assets.get(path, TextureAtlas.class);

	}*/

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

