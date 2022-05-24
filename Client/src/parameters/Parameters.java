
package parameters;

/**
 * Clase para almacenar parametros utilizados por el juego.
 *
 * @author Eduard Andrei Ghile
 */
public class Parameters {

    /** The camera width. */
    //Screen
    public static int cameraWidth = 320;
    
    /** The camera height. */
    public static int cameraHeight = 180;

    /** The screen width. */
    public static int screenWidth=Resolutions.RES1.width;
    
    /** The screen height. */
    public static int screenHeight=Resolutions.RES1.height;

    /** The debug. */
    //Debug
    public static boolean debug=false;

    /** The music volume. */
    //Audio
    public static float musicVolume=1;
    
    /** The sound volume. */
    public static float soundVolume=1;

    /** The level. */
    //variables de juego
    public static int level=1;

    /** The hp. */
    public static int hp=100;

    /** The actual nickname. */
    public static String actualNickname = "";
    
    /** The lobby name. */
    public static String lobbyName = "";

    /**
     * The Enum Resolutions.
     */
    public enum Resolutions{
        
        /** The res1. */
        RES1(800, 600),
        
        /** The res2. */
        RES2(1280, 720),
        
        /** The res3. */
        RES3(1920, 1080);

        /** The width. */
        public final int width;
        
        /** The height. */
        public final int height;

        /**
         * Instantiates a new resolutions.
         *
         * @param width the width
         * @param height the height
         */
        Resolutions(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }








}
