
package handlers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Clase utilizada para generar etiquetas mediante unos parametros predefinidos.
 *
 * @author Eduard Andrei Ghile
 */
public class LabelHandler {

	/** The instance. */
	public static LabelHandler INSTANCE = new LabelHandler();

	/**
	 * Metodo que genera una nueva etiqueta en base a un texto.
	 * 
	 * @param text  Texto que deseamos en la etiqueta
	 * @param size  Tamaño de la fuente que se va a colocar en la etiqueta
	 * @param color Color de la fuente que se va a colocar en la etiqueta
	 * @return Una nueva etiqueta generada en base a los parametros recibidos
	 */
	public Label createLabel(final String text, final int size, final Color color) {
		Label.LabelStyle style = new Label.LabelStyle();
		style.font = FontSizeHandler.INSTANCE.getFont(size, color);
		style.fontColor = color;

		return new Label(text, style);
	}
}
