package Runesmacher.SimpleATM;

import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.block.BlockBreakEvent;

public class SimpleATMBlockListener extends BlockListener {
    private final SimpleATM plugin;
    Player player;
    int isActive;

    public SimpleATMBlockListener(SimpleATM instance) {
        plugin = instance;
    }

    @Override
    public void onSignChange(SignChangeEvent event) {
        if (event.getLine(1).equals("[ATM]")) {
            player = event.getPlayer();
            if (plugin.permissionHandler != null && plugin.permissionHandler.has(player, "SimpleATM.place")) {
                event.setLine(1, ChatColor.GOLD + "[ATM]");
                player.sendMessage(ChatColor.GREEN + "ATM created");
            } else if (plugin.permissionHandler == null) {
                event.setLine(1, ChatColor.GOLD + "[ATM]");
                player.sendMessage(ChatColor.GREEN + "ATM created");
            } else {
                player.sendMessage(ChatColor.RED + "You do not have permission to make an ATM.");
                event.setLine(0, "");
                event.setLine(1, "");
                event.setLine(2, "");
                event.setLine(3, "");
            }
        } else {
            return;
        }
    }

    public void onBlockBreak(final BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getBlock().getState() instanceof Sign) {
            Sign sign = (Sign) event.getBlock().getState();
            if (sign.getLine(1).equals(ChatColor.GOLD + "[ATM]")) {
                player = event.getPlayer();
                if (plugin.permissionHandler != null && plugin.permissionHandler.has(player, "SimpleATM.remove")) {
                    player.sendMessage(ChatColor.GREEN + "ATM removed");
                } else if (plugin.permissionHandler == null) {
                    player.sendMessage(ChatColor.GREEN + "ATM removed");
                } else {
                    event.setCancelled(true);
                    sign.setLine(1, ChatColor.GOLD + "[ATM]");
                    sign.update();
                    player.sendMessage(ChatColor.RED + "You do not have permission to remove an ATM.");
                }
            } else {
                return;
            }
        }
    }
}
