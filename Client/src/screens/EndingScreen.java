package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import handlers.LabelHandler;
import handlers.ResourceManager;
import main.MontessoriSlug;
import parameters.Parameters;

/**
 * Clase que instancia la pantalla de fin del juego
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class EndingScreen extends BScreen{
    private static Texture backgroundTexture;
    private SpriteBatch spriteBatch;

    private Table table;

    private final Label titleLabel;

    private final TextButton returnToMenu;

    private final TextButton exitGame;

    public EndingScreen(MontessoriSlug game) {
        super(game);

        this.backgroundTexture = ResourceManager.getTexture("assets/images/background.png");
        this.spriteBatch = new SpriteBatch();

        //Download new skin
        final Skin skin = new Skin(Gdx.files.internal("assets/ui-skin/uiskin.json"));

        this.table = new Table();
        this.table.setBounds(0,0, Parameters.screenWidth,Parameters.screenHeight);

        this.titleLabel = LabelHandler.INSTANCE.createLabel("Fin del juego", 64, Color.WHITE);

        this.returnToMenu = new TextButton("Volver al menu", skin);
        this.returnToMenu.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MontessoriSlug.getInstance().setScreen(new MenuScreen(MontessoriSlug.getInstance()));
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.exitGame = new TextButton("Salir", skin);
        this.exitGame.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MontessoriSlug.getInstance().dispose();
                Gdx.app.exit();
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.uiStage.addActor(table);

        this.setToDefault();
    }

    public void setToDefault() {
        this.table.clear();
        this.table.add(titleLabel).padBottom(100).row();
        this.table.add(returnToMenu).width(150).height(50).padTop(50).row();
        this.table.add(exitGame).width(150).height(50).padTop(50).row();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        uiStage.act();
        uiStage.draw();
    }
}
