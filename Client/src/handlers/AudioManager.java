package handlers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import parameters.Parameters;

public class AudioManager {
    public static Music currentMusic;
    public static String currentMusicName="";
    public static Sound sound;



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

    public static void playSound(String path) {
        sound=ResourceManager.getSound(path);
        sound.play(Parameters.soundVolume, 0, 0);

    }

    public static void applyVolume() {
        currentMusic.setVolume(Parameters.musicVolume);
    }

    static public void stopMusic() {
        currentMusic.stop();
    }



}