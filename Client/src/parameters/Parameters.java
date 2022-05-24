package parameters;

/**
 * Clase para almacenar parametros utilizados por el juego
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class Parameters {

    //Screen
    public static int cameraWidth = 320;
    public static int cameraHeight = 180;

    public static int screenWidth=Resolutions.RES1.width;
    public static int screenHeight=Resolutions.RES1.height;

    //Debug
    public static boolean debug=false;

    //Audio
    public static float musicVolume=1;
    public static float soundVolume=1;

    //variables de juego
    public static int level=1;

    public static int hp=100;

    public static String actualNickname = "";
    public static String lobbyName = "";

    public enum Resolutions{
        RES1(800, 600),
        RES2(1280, 720),
        RES3(1920, 1080);

        public final int width;
        public final int height;

        Resolutions(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }








}
