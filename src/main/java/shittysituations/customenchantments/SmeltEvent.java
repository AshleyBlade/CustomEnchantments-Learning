package shittysituations.customenchantments;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class SmeltEvent implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();

        // check if the player is in Creative or Spectator modes
        if(player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;

        ItemStack item; // initialise the ItemStack
        try{ // try to store the mainhand as ItemStack
            item = player.getInventory().getItemInMainHand(); // Get the player's main hand item
        } catch(Exception err){ // if mainhand can't be ItemStack return
            return;
        }

        if(!item.hasItemMeta()) return; // check if the item has a meta
        if(!item.getItemMeta().hasEnchant(CustomEnchants.SMELT)) return; // check if the tool has SMELT enchant

        Block block = event.getBlock(); // Store the block
        Material blockMaterial = block.getType();

        // declare new drop
        ItemStack drop = null;
        if(blockMaterial == Material.IRON_ORE){
            drop = new ItemStack(Material.IRON_INGOT);
        }
        if(blockMaterial == Material.GOLD_ORE){
            drop = new ItemStack(Material.GOLD_INGOT);
        }
        if(blockMaterial == Material.NETHER_GOLD_ORE){
            drop = new ItemStack(Material.GOLD_INGOT);
        }
        if(blockMaterial == Material.ANCIENT_DEBRIS){
            drop = new ItemStack(Material.NETHERITE_SCRAP);
        }
        if(blockMaterial == Material.STONE){
            drop = new ItemStack(Material.STONE);
        }
        // Axe smelts
        if(blockMaterial.name().contains("LOG")){
            drop = new ItemStack(Material.CHARCOAL);
        }

        if(drop == null) return;
        event.setDropItems(false); // remove default drops

        if(item.getItemMeta().hasEnchant(CustomEnchants.VACUUM)) {
            if(event.getPlayer().getInventory().firstEmpty() == -1) return; // if inventory full return
            if(block.getState() instanceof Container) return; // if block is a chest return
            player.getInventory().addItem(drop);
            return;
        }

        Location loc = block.getLocation();
        block.getWorld().dropItem(loc, drop);

    }
}
