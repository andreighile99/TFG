
package elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import handlers.ResourceManager;
import parameters.Parameters;

/**
 * Clase para la gestion de los elementos visibles de la aplicacion.
 *
 * @author Eduard ANdrei Ghile
 */
public class Element extends Actor {
	
	/** The animation. */
	private Animation<TextureRegion> animation;
	
	/** The animation time. */
	private float animationTime;
	
	/** The enabled. */
	private boolean enabled;
	
	/** The colision. */
	protected Polygon colision;
	
	/** The shape renderer. */
	private ShapeRenderer shapeRenderer;
	
	/** The poly width. */
	private float polyWidth;
	
	/** The poly high. */
	private float polyHigh;
	
	/** The pad Y. */
	private float padY = 0;
	
	/** The pad X. */
	private float padX = 0;

	/**
	 * Gets the enabled.
	 *
	 * @return the enabled
	 */
	public boolean getEnabled() {
		return enabled;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled the new enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Instantiates a new element.
	 *
	 * @param x the x
	 * @param y the y
	 * @param s the s
	 */
	public Element(float x, float y, Stage s) {

		this.setPosition(x, y);
		s.addActor(this);
		enabled = true;

		shapeRenderer = new ShapeRenderer();

	}

	/**
	 * Constructor de la clase.
	 *
	 * @param x Posicion en el eje x de la pantalla
	 * @param y Posicion en el eje y de la pantalla
	 * @param s Stage al que pertenece el objeto
	 * @param w Anchura del objeto mostrado
	 * @param h Altura del objeto mostrado
	 */
	public Element(float x, float y, Stage s, float w, float h) {
		this.setPosition(x, y);
		s.addActor(this);
		enabled = true;

		shapeRenderer = new ShapeRenderer();
		this.polyWidth = w;
		this.polyHigh = h;

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		if (this.getEnabled()) {

			if (animation != null) {
				batch.draw(animation.getKeyFrame(animationTime), getX(), getY(), getOriginX(), getOriginY(),
						animation.getKeyFrame(animationTime).getRegionWidth(),
						animation.getKeyFrame(animationTime).getRegionHeight(), getScaleX(), getScaleY(),
						getRotation());
			}
			if (Parameters.debug) {
				pintarCaja(batch);
			}
			super.draw(batch, parentAlpha);
		}

	}

	/**
	 * Gets the boundary polygon.
	 *
	 * @return the boundary polygon
	 */
	public Polygon getBoundaryPolygon() {
		colision.setPosition(getX() + this.padX, getY() + this.padY);
		colision.setOrigin(getOriginX(), getOriginY());
		colision.setRotation(getRotation());
		colision.setScale(getScaleX(), getScaleY());
		return colision;
	}

	/**
	 * Pintar caja.
	 *
	 * @param batch the batch
	 */
	public void pintarCaja(Batch batch) {
		batch.end();
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.WHITE);
		if (this.getBoundaryPolygon() != null) {
			float[] vertices = new float[this.getBoundaryPolygon().getTransformedVertices().length];

			for (int i = 0; i < vertices.length / 2; i++) {
				vertices[2 * i] = (this.getBoundaryPolygon().getTransformedVertices()[2 * i]);
				vertices[2 * i + 1] = (this.getBoundaryPolygon().getTransformedVertices()[2 * i + 1]);

			}

			shapeRenderer.polygon(vertices);

		}
		shapeRenderer.end();
		batch.begin();
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		if (this.getEnabled()) {
			super.act(delta);
			animationTime += delta;
		}
	}

	/**
	 * Sets the animation.
	 *
	 * @param anim the new animation
	 */
	public void setAnimation(Animation<TextureRegion> anim) {
		animation = anim;
		if (anim == null) {
			System.out.println("Es nulaaaa");
		}
		TextureRegion tr = animation.getKeyFrame(0);
		float w = tr.getRegionWidth();
		float h = tr.getRegionHeight();
		setSize(w, h);
		this.polyHigh = h;
		this.polyWidth = w;

		setOrigin(w / 2, h / 2);

		if (colision == null)
			setRectangle();
	}

	/**
	 * Load full animation.
	 *
	 * @param name the name
	 * @param rows the rows
	 * @param cols the cols
	 * @param frameDuration the frame duration
	 * @param loop the loop
	 * @return the animation
	 */
	public Animation<TextureRegion> loadFullAnimation(String name, int rows, int cols, float frameDuration,
			boolean loop) {

		Texture texture = ResourceManager.getTexture(name);
		// texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		int frameWidth = texture.getWidth() / cols;
		int frameHeight = texture.getHeight() / rows;

		TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);

		Array<TextureRegion> textureArray = new Array<TextureRegion>();

		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				textureArray.add(temp[r][c]);

		Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);

		if (loop)
			anim.setPlayMode(Animation.PlayMode.LOOP);
		else
			anim.setPlayMode(Animation.PlayMode.NORMAL);

		if (animation == null) {

			this.setAnimation(anim);
		}

		return anim;
	}

	/**
	 * Sets the rectangle.
	 */
	public void setRectangle() {
		float w, h;
		if (this.polyWidth != getWidth() && this.polyWidth > 0) {
			w = this.polyWidth;
		} else {
			w = this.getWidth();
		}
		if (this.polyHigh != this.getHeight() && this.polyHigh > 0) {
			h = this.polyHigh;
		} else {
			h = getHeight();
		}
		float[] vertices = { padX, padY, w - padX, padY, w - padX, h - padY, padX, h - padY };
		colision = new Polygon(vertices);
		this.setOrigin(w / 2, h / 2);
	}

	/**
	 * Sets the rectangle.
	 *
	 * @param polyWidth the poly width
	 * @param polyHigh the poly high
	 * @param padX the pad X
	 * @param padY the pad Y
	 */
	public void setRectangle(float polyWidth, float polyHigh, float padX, float padY) {
		this.polyWidth = polyWidth;
		this.polyHigh = polyHigh;
		this.padX = padX;
		this.padY = padY;
		setRectangle();
	}

	/**
	 * Sets the polygon.
	 *
	 * @param numSides the new polygon
	 */
	public void setPolygon(int numSides) {
		this.setOrigin(polyWidth / 2, polyHigh / 2);
		float[] vertices = new float[2 * numSides];
		for (int i = 0; i < numSides; i++) {
			float angle = i * 6.28f / numSides;
			vertices[2 * i] = this.polyWidth / 2 * MathUtils.cos(angle) + this.polyWidth / 2;
			vertices[2 * i + 1] = this.polyHigh / 2 * MathUtils.sin(angle) + this.polyHigh / 2;
			colision = new Polygon(vertices);

		}
	}

	public boolean overlaps(Element elemento)
	{
		Polygon poly1 = this.getBoundaryPolygon();
		Polygon poly2 = elemento.getBoundaryPolygon();

		// initial test to improve performance
		if ( !poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()) )
			return false;

		return Intersector.overlapConvexPolygons( poly1, poly2 );
	}

}
