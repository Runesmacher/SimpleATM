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
        if (event.isCancelled()) {
            return;
        }

        if (event.getLine(0).contains("[ATM]") || event.getLine(1).contains("[ATM]")) {
            player = event.getPlayer();
            if (plugin.permissionHandler != null && plugin.permissionHandler.has(player, "SimpleATM.place")) {
                if (event.getLine(0).equals("[ATM]")) {
                    event.setLine(0, ChatColor.GOLD + "[ATM]");
                } else if (event.getLine(1).equals("[ATM]")) {
                    event.setLine(1, ChatColor.GOLD + "[ATM]");
                }
                player.sendMessage(ChatColor.GREEN + "ATM created");
            } else if (plugin.permissionHandler == null && player.isOp()) {
                if (event.getLine(0).equals("[ATM]")) {
                    event.setLine(0, ChatColor.GOLD + "[ATM]");
                } else if (event.getLine(1).equals("[ATM]")) {
                    event.setLine(1, ChatColor.GOLD + "[ATM]");
                }
                player.sendMessage(ChatColor.GREEN + "ATM created");
            } else {
                event.setCancelled(true);
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
            String lines[] = sign.getLines();
            if (sign.getLine(1).contains("[ATM]") || sign.getLine(0).contains("[ATM]")) {
                player = event.getPlayer();
                if (plugin.permissionHandler != null && plugin.permissionHandler.has(player, "SimpleATM.remove")) {
                    player.sendMessage(ChatColor.GREEN + "ATM removed");
                } else if (plugin.permissionHandler == null && player.isOp()) {
                    player.sendMessage(ChatColor.GREEN + "ATM removed");
                } else {
                    event.setCancelled(true);
                    sign.setLine(0, lines[0]);
                    sign.setLine(1, lines[1]);
                    sign.setLine(2, lines[2]);
                    sign.setLine(3, lines[3]);
                    sign.update();
                    player.sendMessage(ChatColor.RED + "You do not have permission to remove an ATM.");
                }
            } else {
                return;
            }
        }
    }
}
