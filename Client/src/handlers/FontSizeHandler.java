
package handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase utilizada para generar texto mediante una fuente predefinida.
 * 
 * Utilizada para mantener la homogeneidad de fuentes en la aplicacion.
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class FontSizeHandler {

    /** The instance. */
    public static FontSizeHandler INSTANCE = new FontSizeHandler("assets/fonts/LRpixel.ttf");

    /** The fonts. */
    private final Map<Integer, BitmapFont> fonts = new HashMap<>();

    /** The font path. */
    private final String fontPath;

    /**
     * Instantiates a new font size handler.
     *
     * @param fontPath the font path
     */
    public FontSizeHandler(String fontPath) {
        this.fontPath = fontPath;
    }

    /**
     * Metodo que genera una fuente de texto en base a un tamaño y color dados.
     * 
     * @param size Tamaño de la fuente
     * @param color Color deseado
     * @return font La fuente generada
     */
    public BitmapFont getFont(final int size, Color color) {
        if(this.fonts.containsKey(size)) return this.fonts.get(size);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(this.fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;

        BitmapFont font = generator.generateFont(parameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setColor(color);

        this.fonts.put(size, font);

        return font;
    }
}
