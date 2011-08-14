package Runesmacher.SimpleATM;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import com.iConomy.*;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;

public class SimpleATM extends JavaPlugin {

    public final HashMap<Player, ArrayList<Block>> SimpleATMUsers = new HashMap<Player, ArrayList<Block>>();
    public iConomy iConomy = null;
    public static PermissionHandler permissionHandler;
    private final SimpleATMBlockListener blockListener = new SimpleATMBlockListener(this);
    private final SimpleATMPlayerListener playerListener = new SimpleATMPlayerListener(this);
    private final SimpleATMPluginListener pluginListener = new SimpleATMPluginListener(this);

    public static void main(String[] args) {
        //Do Nothing
    }

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Type.PLUGIN_ENABLE, pluginListener, Priority.Monitor, this);
        pm.registerEvent(Type.PLUGIN_DISABLE, pluginListener, Priority.Monitor, this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
        pm.registerEvent(Type.BLOCK_BREAK, blockListener, Priority.Normal, this);
        pm.registerEvent(Type.SIGN_CHANGE, blockListener, Priority.Normal, this);
        System.out.println("SimpleATM STARTED");
        setupPermissions();
    }

    public void onDisable() {
        System.out.println("SimpleATM DISABLED");
    }

    private void setupPermissions() {
        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");

        if (this.permissionHandler == null) {
            if (permissionsPlugin != null) {
                this.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
                System.out.println("SimpleATM hooked into Permissions.");
            } else {
                System.out.println("SimpleATM Permissions not found, defaulting to ops.txt");
            }
        }
    }
}
