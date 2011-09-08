package Runesmacher.SimpleATM;

import Runesmacher.register.payment.Method;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class SimpleATM extends JavaPlugin {

    public PluginDescriptionFile info = null;
    public final HashMap<Player, ArrayList<Block>> SimpleATMUsers = new HashMap<Player, ArrayList<Block>>();
    public static PermissionHandler permissionHandler;
    public PluginManager pluginManager = null;
    private final SimpleATMConf conf = new SimpleATMConf(this);
    private final SimpleATMBlockListener blockListener = new SimpleATMBlockListener(this);
    private final SimpleATMPlayerListener playerListener = new SimpleATMPlayerListener(this, conf);
    private final SimpleATMServerListener serverlistener = new SimpleATMServerListener(this);
    public Method method = null;

    public static void main(String[] args) {
        //Do Nothing
    }

    public void onEnable() {
        this.info = getDescription();
        this.pluginManager = getServer().getPluginManager();

        pluginManager.registerEvent(Event.Type.PLUGIN_ENABLE, serverlistener, Priority.Monitor, this);
        pluginManager.registerEvent(Event.Type.PLUGIN_DISABLE, serverlistener, Priority.Monitor, this);
        pluginManager.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
        pluginManager.registerEvent(Type.BLOCK_BREAK, blockListener, Priority.Normal, this);
        pluginManager.registerEvent(Type.SIGN_CHANGE, blockListener, Priority.Normal, this);
        System.out.println(this.info.getName() + " Version " + this.info.getVersion() + " started");
        conf.loadConfig();
        setupPermissions();
    }

    public void onDisable() {
        System.out.println(this.info.getName() + " DISABLED");
        this.info = null;
        this.method = null;
        this.pluginManager = null;
    }

    private void setupPermissions() {
        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");

        if (this.permissionHandler == null) {
            if (permissionsPlugin != null) {
                this.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
                System.out.println(this.info.getName() + " hooked into Permissions.");
            } else {
                System.out.println(this.info.getName() + " Permissions not found, defaulting to ops.txt");
            }
        }
    }
}
