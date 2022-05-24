package server.main;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import server.events.game.*;
import server.game.elements.Bullet;
import server.game.elements.EnemyBullet;
import server.game.elements.Soldier;
import server.listeners.GameEventListener;
import server.listeners.LeaveListener;
import server.listeners.LobbyListener;
import server.events.lobby.*;
import server.game.elements.ServerPlayerData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;


/**
 * Clase principal que instancia el servidor y configura los parametros de este
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class Server {
    public static Server instance;

    private com.esotericsoftware.kryonet.Server server;

    public static void main(String[] args) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("server.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("LOG - Se ha detectado un fallo al localizar el archivo server.properties");
        }

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("LOG - Ha ocurrido un error al cargar las propiedades del archivo server.properties");
        }

        Server.instance = new Server(Integer.parseInt(
                properties.getProperty("server.port")
        ));
    }

    public Server(int port) {
        this.server = new com.esotericsoftware.kryonet.Server();
        registerClasses();
        addListeners();

        this.bindServer(port, port);
    }

    public void bindServer(final int tcpPort, final int udpPort) {
        this.server.start();
        try {
            this.server.bind(tcpPort, udpPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[OK] El servidor está levantado en el puerto " + tcpPort);

    }


    /**
     * Le a�ade los listeners al servidor
     */
    public void addListeners(){
        /** Listeners **/

        //Lobby
        this.server.addListener(new LobbyListener());
        this.server.addListener(new LeaveListener());

        //Game events
        this.server.addListener(new GameEventListener());
    }

    /**
     * Registra las instancias de las clases de los tipos de mensajes que pueda enviar o recibir el servidor
     * 
     */
    public void registerClasses(){
        /** Events register **/

        //Lobby
        this.server.getKryo().register(CreateNewLobbyEvent.class);
        this.server.getKryo().register(JoinLobbyEvent.class);
        this.server.getKryo().register(LobbyCreatedEvent.class);
        this.server.getKryo().register(LobbyJoinedEvent.class);
        this.server.getKryo().register(LobbyStartEvent.class);
        this.server.getKryo().register(FinishLobby.class);
        this.server.getKryo().register(SwitchLevel.class);

        //Game events
        this.server.getKryo().register(GameEvent.class);
        this.server.getKryo().register(PlayerEvent.class);
        this.server.getKryo().register(PlayerEvent.DIRECTION.class);
        this.server.getKryo().register(BulletEvent.class);
        this.server.getKryo().register(EnemyEvent.class);
        this.server.getKryo().register(ServerPlayerData.class);

        //Common
        this.server.getKryo().register(ArrayList.class);
        this.server.getKryo().register(Vector2.class);
        this.server.getKryo().register(Rectangle.class);
        this.server.getKryo().register(Bullet.class);
        this.server.getKryo().register(Soldier.class);
        this.server.getKryo().register(EnemyBullet.class);

    }
    public com.esotericsoftware.kryonet.Server getServer() {
        return server;
    }
}


