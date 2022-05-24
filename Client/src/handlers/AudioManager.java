package handlers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import parameters.Parameters;


/**
 *  Clase que gestiona la musica  y los sonidos de la aplicacion
 * 
 * 
 * @author Eduard Andrei Ghile
 *
 *
 */
public class AudioManager {
    public static Music currentMusic;
    public static String currentMusicName="";
    public static Sound sound;



    /**
     * Metodo que controla la ejecucion de la musica recibiendo como parametro la ruta donde
     * se encuentra el archivo de musica
     * 
     * @param path
     */
    public static void playMusic(String path) {
        if (currentMusic != null && currentMusicName!=path) {
            currentMusic.stop();
            currentMusicName=path;
            currentMusic=ResourceManager.getMusic(path);
            currentMusic.setVolume(Parameters.musicVolume);
            currentMusic.setLooping(true);
            currentMusic.play();
        }else {
            currentMusicName=path;
            currentMusic=ResourceManager.getMusic(path);
            currentMusic.setVolume(Parameters.musicVolume);
            currentMusic.setLooping(true);
            currentMusic.play();
        }
    }

    /**
     * Metodo que controla la ejecucion de un sonido concreto recibiendo como parametro la ruta
     * donde se encuentra el archivo del sonido
     * 
     * @param path
     */
    public static void playSound(String path) {
        sound=ResourceManager.getSound(path);
        sound.play(Parameters.soundVolume, 0, 0);

    }

    /**
     * Metodo que ajusta el volumen de la musica en base a los parametros de la aplicacion
     * 
     */
    public static void applyMusicVolume() {
        currentMusic.setVolume(Parameters.musicVolume);
    }

    
    /**
     * Metodo que para la ejecucion de la musica
     * 
     */
    static public void stopMusic() {
        currentMusic.stop();
    }



}