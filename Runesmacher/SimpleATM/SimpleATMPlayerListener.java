package Runesmacher.SimpleATM;
//All the imports
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import com.iConomy.iConomy;
import com.iConomy.system.Holdings;
//Starts the class SimpleATMPlayerlistener
public class SimpleATMPlayerListener extends PlayerListener {

    public static SimpleATM plugin;
    Player player;

    public SimpleATMPlayerListener(SimpleATM instance) {
        plugin = instance;
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getState() instanceof Sign) {
                Sign sign = (Sign) event.getClickedBlock().getState();
                if (sign.getLine(1).equals(ChatColor.GOLD + "[ATM]")) {
                    Holdings balance = iConomy.getAccount(player.getName()).getHoldings();
                    if (player.getItemInHand().getType() == Material.GOLD_BLOCK) {
                        balance.add(9);

                        ItemStack item = new ItemStack(Material.GOLD_BLOCK, 1);

                        inventory.removeItem(item);
                    } else if (player.getItemInHand().getType() == Material.GOLD_INGOT) {
                        balance.add(1);

                        ItemStack item = new ItemStack(Material.GOLD_INGOT, 1);

                        inventory.removeItem(item);
                    } else {
                        if (balance.hasOver(9.00) || balance.hasEnough(9.00)) {
                            balance.subtract(9.00);

                            ItemStack item = new ItemStack(Material.GOLD_BLOCK, 1);

                            inventory.addItem(item);
                            player.updateInventory();
                        } else {
                            player.sendMessage(ChatColor.RED + "You do not have enough to take out a gold bar.");
                        }
                    }
                }
                event.setCancelled(true);
            }
        }
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (event.getClickedBlock().getState() instanceof Sign) {
                Sign sign = (Sign) event.getClickedBlock().getState();
                if (sign.getLine(1).equals(ChatColor.GOLD + "[ATM]")) {
                    player.sendMessage(ChatColor.GOLD + "Right click sign to Withdraw a gold block");
                    player.sendMessage(ChatColor.GOLD + "Right click sign with a gold block or ingot to Deposit");
                }
            }
        }
    }
}
