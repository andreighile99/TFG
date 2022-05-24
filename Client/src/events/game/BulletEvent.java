
package events.game;


/**
 *
 */
public class BulletEvent {

    /**  */
    private String lobbyName;

    /**  */
    private String username;



    /**
     *
     */
    public BulletEvent() {
    }

    /**
     *
     *
     * @return
     */
    public String getLobbyName() {
        return lobbyName;
    }

    /**
     *
     *
     * @param lobbyName
     */
    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    /**
     *
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

}
