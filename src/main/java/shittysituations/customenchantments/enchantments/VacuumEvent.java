package shittysituations.customenchantments.enchantments;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import shittysituations.customenchantments.CustomEnchants;

import java.util.Collection;

import static org.bukkit.Bukkit.getLogger;

public class VacuumEvent implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        // Check if the player is in Creative or Spectator mode
        if(event.getPlayer().getGameMode() == GameMode.CREATIVE || event.getPlayer().getGameMode() == GameMode.SPECTATOR) return;

        ItemStack item; // Initialise item
        try{
            item = event.getPlayer().getInventory().getItemInMainHand(); // Try to store main hand as an itemstack
        } catch (Exception err){ // if err then there is no item!!!
            return;
        }

        if(!item.hasItemMeta()) return; // if item doesn't have META return;
        if(!item.getItemMeta().hasEnchant(CustomEnchants.VACUUM)) return; // if item doesn't have Vacuum enchant return
        if(item.getItemMeta().hasEnchant(CustomEnchants.SMELT)){ // if item has Smelt enchant don't vacuum
            switch(event.getBlock().getType()){
                case STONE:
                case IRON_ORE:
                case NETHER_GOLD_ORE:
                case ANCIENT_DEBRIS:
                case GOLD_ORE:
                case ACACIA_LOG:
                case BIRCH_LOG:
                case DARK_OAK_LOG:
                case JUNGLE_LOG:
                case OAK_LOG:
                case SPRUCE_LOG:
                case STRIPPED_ACACIA_LOG:
                case STRIPPED_BIRCH_LOG:
                case STRIPPED_DARK_OAK_LOG:
                case STRIPPED_JUNGLE_LOG:
                case STRIPPED_OAK_LOG:
                case STRIPPED_SPRUCE_LOG:
                    return;
                default:
                    break;
            }
        }
        if(event.getPlayer().getInventory().firstEmpty() == -1) return; // if inventory full return

        Block block = event.getBlock(); // Store the block from the event
        if(block.getState() instanceof Container) return; // if block is a chest return

        Player player = event.getPlayer(); // Store the player that fired the event
        event.setDropItems(false); // Remove the blocks drops

        Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand()); // create a collection of ItemStacks from getDrops
        if (drops.isEmpty()) return; // if there are no drops return
        player.getInventory().addItem(drops.iterator().next()); // add the drops to the player's inventory by iterating
    }
}
