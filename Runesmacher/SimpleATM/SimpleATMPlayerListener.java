package Runesmacher.SimpleATM;
//All the imports
import Runesmacher.register.payment.Method.MethodAccount;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

//Starts the class SimpleATMPlayerlistener
public class SimpleATMPlayerListener extends PlayerListener {

    SimpleATMConf config;
    public static SimpleATM plugin;
    Player player;

    public SimpleATMPlayerListener(SimpleATM instance, SimpleATMConf config) {
        plugin = instance;
        this.config = config;
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if ((!event.hasBlock()) || (event.getClickedBlock().getType() != Material.WALL_SIGN)) {
            return;
        }

        Sign sign = (Sign) event.getClickedBlock().getState();
        if (!(sign.getLine(1).contains("[ATM]") || sign.getLine(0).contains("[ATM]"))) {
            return;
        }

        player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            MethodAccount balance = this.plugin.method.getAccount(player.getName());
            int index = config.depositBlocksList.indexOf(player.getItemInHand().getTypeId());

            if (index != -1) {
                balance.add(config.depositvalueList.get(index));
                ItemStack item = new ItemStack(config.depositBlocksList.get(index), 1);
                inventory.removeItem(item);
            } else {
                if (!balance.hasUnder(config.withdrawValue)) {
                    balance.subtract(config.withdrawValue);
                    ItemStack item = new ItemStack(config.withdrawBlock, 1);
                    inventory.addItem(item);
                    player.updateInventory();
                } else {
                    if (config.withdrawSmallValue != 0 && config.withdrawSmallBlock != 0) {
                        if (!balance.hasUnder(config.withdrawSmallValue)) {
                            balance.subtract(config.withdrawSmallValue);
                            ItemStack item = new ItemStack(config.withdrawSmallBlock, 1);
                            inventory.addItem(item);
                            player.updateInventory();
                        } else {
                            player.sendMessage(ChatColor.RED + "You do not have enough to take out a " + Material.getMaterial(config.withdrawSmallBlock).name());
                        }
                    } else {
                            player.sendMessage(ChatColor.RED + "You do not have enough to take out a " + Material.getMaterial(config.withdrawBlock).name());
                        }
                }
            }
            event.setCancelled(true);
        }
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            player.sendMessage(ChatColor.GOLD + "Right click sign to Withdraw a " + Material.getMaterial(config.withdrawBlock).name());
            player.sendMessage(ChatColor.GOLD + "To deposit: Right click sign with:");
            for (int i = 0; i < config.depositBlocksList.size(); i++) {
                player.sendMessage(ChatColor.GOLD + "- " + Material.getMaterial(config.depositBlocksList.get(i)).name());
            }
        }
    }
}
