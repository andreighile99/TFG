
package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import handlers.LabelHandler;
import main.MontessoriSlug;
import parameters.Parameters;

/**
 * Clase que instancia la pantalla de opciones.
 *
 * @author Eduard Andrei Ghile
 */
public class OptionsScreen extends BScreen{
    
    /** The table. */
    private Table table;

    /** The title label. */
    private final Label titleLabel;

    /** The volume text label. */
    private final Label volumeTextLabel;
    
    /** The volume label. */
    private final Label volumeLabel;
    
    /** The resolution text label. */
    private final Label resolutionTextLabel;
    
    /** The resolution label. */
    private final Label resolutionLabel;
    
    /** The music text label. */
    private final Label musicTextLabel;
    
    /** The music label. */
    private final Label musicLabel;

    /** The volume plus button. */
    private final TextButton volumePlusButton;
    
    /** The volume minus button. */
    private final TextButton volumeMinusButton;

    /** The music plus button. */
    private final TextButton musicPlusButton;
    
    /** The music minus button. */
    private final TextButton musicMinusButton;

    /** The resolution plus button. */
    private final TextButton resolutionPlusButton;
    
    /** The resolution minus button. */
    private final TextButton resolutionMinusButton;

    /** The return to menu button. */
    private final TextButton returnToMenuButton;

    /** The apply changes button. */
    private final TextButton applyChangesButton;

    /** The wanted screen height. */
    private int wantedScreenHeight = Parameters.screenHeight;
    
    /** The wanted screen width. */
    private int wantedScreenWidth = Parameters.screenWidth;

    /** The wanted volume. */
    private float wantedVolume = Parameters.soundVolume;
    
    /** The wanted music volume. */
    private float wantedMusicVolume = Parameters.musicVolume;

    /**
     * Instantiates a new options screen.
     *
     * @param game the game
     */
    public OptionsScreen(MontessoriSlug game){
        super(game);
        this.table = new Table();
        this.table.setBounds(0,0,Parameters.screenWidth,Parameters.screenHeight);

        //Download new skin
        final Skin skin = new Skin(Gdx.files.internal("assets/ui-skin/uiskin.json"));

        this.titleLabel = LabelHandler.INSTANCE.createLabel("Opciones", 48, Color.WHITE);

        this.volumeTextLabel = LabelHandler.INSTANCE.createLabel("Sonidos", 32, Color.WHITE);
        this.resolutionTextLabel = LabelHandler.INSTANCE.createLabel("Resolucion", 32, Color.WHITE);
        this.musicTextLabel = LabelHandler.INSTANCE.createLabel("Musica", 32, Color.WHITE);

        this.volumeLabel = LabelHandler.INSTANCE.createLabel(String.valueOf(Math.round(Parameters.soundVolume * 100)), 16, Color.WHITE);
        this.musicLabel = LabelHandler.INSTANCE.createLabel(String.valueOf(Math.round(Parameters.musicVolume * 100)), 16, Color.WHITE);
        this.resolutionLabel = LabelHandler.INSTANCE.createLabel(Parameters.screenWidth + " x " + Parameters.screenHeight, 12, Color.WHITE);

        this.volumeMinusButton = new TextButton("-", skin);
        this.volumeMinusButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(Parameters.soundVolume > 0){
                    wantedVolume = wantedVolume - 0.1f;
                }
                volumeLabel.setText(String.valueOf(Math.round(wantedVolume * 100)));
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.volumePlusButton = new TextButton("+", skin);
        this.volumePlusButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(Parameters.soundVolume < 1){
                    wantedVolume = wantedVolume + 0.1f;
                }
                volumeLabel.setText(String.valueOf(Math.round(wantedVolume * 100)));
                return super.touchDown(event, x, y, pointer, button);
            }
        });


        this.musicMinusButton = new TextButton("-", skin);
        this.musicMinusButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(Parameters.musicVolume > 0){
                    wantedMusicVolume = wantedMusicVolume - 0.1f;
                }
                musicLabel.setText(String.valueOf(Math.round(wantedMusicVolume * 100)));
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.musicPlusButton = new TextButton("+", skin);
        this.musicPlusButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(Parameters.musicVolume < 1){
                    wantedMusicVolume = wantedMusicVolume + 0.1f;
                }
                musicLabel.setText(String.valueOf(Math.round(wantedMusicVolume * 100)));
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.resolutionMinusButton = new TextButton("-", skin);
        this.resolutionMinusButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(Parameters.Resolutions.RES2.height < Float.valueOf(resolutionLabel.getText().toString().split("x")[1])){
                    wantedScreenHeight = Parameters.Resolutions.RES2.height;
                    wantedScreenWidth = Parameters.Resolutions.RES2.width;
                    resolutionLabel.setText(wantedScreenWidth + " x " + wantedScreenHeight);
                }else{
                    wantedScreenHeight = Parameters.Resolutions.RES1.height;
                    wantedScreenWidth = Parameters.Resolutions.RES1.width;
                    resolutionLabel.setText(wantedScreenWidth + " x " + wantedScreenHeight);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });


        this.resolutionPlusButton = new TextButton("+", skin);
        this.resolutionPlusButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(Parameters.Resolutions.RES2.height > Float.valueOf(resolutionLabel.getText().toString().split("x")[1])){
                    wantedScreenHeight = Parameters.Resolutions.RES2.height;
                    wantedScreenWidth = Parameters.Resolutions.RES2.width;
                    resolutionLabel.setText(wantedScreenWidth + " x " + wantedScreenHeight);
                }else{
                    wantedScreenHeight = Parameters.Resolutions.RES3.height;
                    wantedScreenWidth = Parameters.Resolutions.RES3.width;
                    resolutionLabel.setText(wantedScreenWidth + " x " + wantedScreenHeight);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.returnToMenuButton = new TextButton("Volver", skin);
        this.returnToMenuButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MontessoriSlug.getInstance().setScreen(new MenuScreen(MontessoriSlug.getInstance()));
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.applyChangesButton = new TextButton("Aplicar cambios", skin);
        this.applyChangesButton.addListener(new ClickListener() {
            //Revisar por el amor de dios que pasa por que hace scroll
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Parameters.soundVolume = wantedVolume;
                Parameters.musicVolume = wantedMusicVolume;
                if(wantedScreenHeight != Parameters.screenHeight && Parameters.screenWidth != wantedScreenWidth){
                    Parameters.screenWidth = wantedScreenWidth;
                    Parameters.screenHeight = wantedScreenHeight;
                    Gdx.graphics.setWindowedMode(Parameters.screenWidth, Parameters.screenHeight);
                }
                MontessoriSlug.getInstance().setScreen(new OptionsScreen(MontessoriSlug.getInstance()));
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.uiStage.addActor(table);

        this.setToDefault();
    }

    /**
     * Sets the to default.
     */
    public void setToDefault() {
        this.table.clear();
        this.table.add(titleLabel).padBottom(100).row();
        this.table.add(volumeTextLabel).width(150);
        this.table.add(volumeMinusButton).width(100);
        this.table.add(volumeLabel).width(65).padLeft(20);
        this.table.add(volumePlusButton).width(100).row();
        this.table.add(musicTextLabel).width(150);
        this.table.add(musicMinusButton).width(100);
        this.table.add(musicLabel).width(65).padLeft(20);
        this.table.add(musicPlusButton).width(100).row();
        this.table.add(resolutionTextLabel).width(150);
        this.table.add(resolutionMinusButton).width(100);
        this.table.add(resolutionLabel).width(50).padLeft(-20);
        this.table.add(resolutionPlusButton).width(100).row();
        this.table.add().width(150);
        this.table.add(applyChangesButton).width(150).height(50).padTop(50).row();
        this.table.add().width(150);
        this.table.add(returnToMenuButton).width(150).height(50).padTop(50).row();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        uiStage.act();
        uiStage.draw();
    }
}
