
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

/**
 * Clase utilizada para cargar y gestionar los recursos de la aplicacion.
 *
 * @author Eduard Andrei Ghile
 */
public final class ResourceManager {
	
	/**
	 * Instantiates a new resource manager.
	 */
	private ResourceManager() {
	}

	/** The assets. */
	public static AssetManager assets = new AssetManager();
	
	/** The button style. */
	public static LabelStyle buttonStyle;
	
	/** The text button style. */
	public static TextButtonStyle textButtonStyle;

	/**
	 * Metodo que carga todos los recursos de la aplicacion en un Asset Manager.
	 */
	public static void loadAllResources() {

		// mapas
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

	/**
	 * Metodo update.
	 *
	 * @return true, if successful
	 */
	public static boolean update() {
		return assets.update();
	}

	/**
	 * Metodo que devuelve una textura en base a una ruta dada.
	 *
	 * @param path Ruta en la que se encuentra la textura
	 * @return the texture
	 */
	public static Texture getTexture(String path) {
		return assets.get(path, Texture.class);
	}

	/**
	 * Metodo que devuelve una musica en base a una ruta dada.
	 *
	 * @param path Ruta en la que se encuentra la musica
	 * @return Una musica
	 */
	public static Music getMusic(String path) {
		return assets.get(path, Music.class);
	}

	/**
	 * Metodo que devuelve un sonido en base a una ruta dada.
	 *
	 * @param path Ruta en la que se encuentra el sonido
	 * @return Un sonido
	 */
	public static Sound getSound(String path) {
		return assets.get(path, Sound.class);
	}

	/**
	 * Metodo que devuelve un mapa de tiled en base a una ruta dada.
	 *
	 * @param path Ruta en la que se encuentra el sonido
	 * @return Un mapa de tiled
	 */
	public static TiledMap getMap(String path) {
		return assets.get(path, TiledMap.class);
	}

	/**
	 * Dispose.
	 */
	public static void dispose() {
		assets.dispose();
	}
}
