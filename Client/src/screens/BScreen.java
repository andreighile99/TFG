
package screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import handlers.FontSizeHandler;
import handlers.ResourceManager;
import main.MontessoriSlug;


/**
 * Clase padre de todas las pantallas del juego.
 *
 * @author Eduard Andrei Ghile
 */
public class BScreen implements Screen, InputProcessor{
	
	/** The game. */
	private final MontessoriSlug game;
	
	/** The resource manager. */
	public final ResourceManager resourceManager;
	
	/** The ui style. */
	public final LabelStyle uiStyle;
	
	/** The im. */
	private InputMultiplexer im;
	
	/** The ui stage. */
	public Stage uiStage;

	/**
	 * Instantiates a new b screen.
	 *
	 * @param game the game
	 */
	public BScreen(MontessoriSlug game){
		this.uiStage=new Stage();
		BitmapFont font= FontSizeHandler.INSTANCE.getFont(5, Color.WHITE);
		uiStyle=new LabelStyle(font, Color.WHITE);
		this.game=game;
		this.resourceManager=game.resourceManager;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		im=(InputMultiplexer)Gdx.input.getInputProcessor();
		im.addProcessor(this);
		im.addProcessor(this.uiStage);
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		InputMultiplexer im=(InputMultiplexer)Gdx.input.getInputProcessor();
		im.removeProcessor(this);
		im.removeProcessor(this.uiStage);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
