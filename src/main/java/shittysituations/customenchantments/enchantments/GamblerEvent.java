package shittysituations.customenchantments.enchantments;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import shittysituations.customenchantments.CustomEnchants;

import java.util.Collection;
import java.util.Random;

import static org.bukkit.Bukkit.getLogger;

public class GamblerEvent implements Listener {

    /*
        Overhaul the whole gambler enchantment.

        Re-balance the drop chance of everything -> Coal shouldn't get a chance to do anything

        Change enchantment to increase of decrease the amount of drops maybe multiply by a number like .75x or 1.25x
        based on random chance

     */

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if(!item.hasItemMeta()) return;
        if(!item.getItemMeta().hasEnchant(CustomEnchants.GAMBLER)) return;

        Block block = event.getBlock();
        Random random = new Random();

        // get the blocks location
        Location location = block.getLocation();
        World world = block.getWorld();

        // if the broken block is any of the below run the code, else break;
        switch(block.getType()){
            case DIAMOND_ORE:
            case COAL_ORE:
            case EMERALD_ORE:
            case LAPIS_ORE:
            case REDSTONE_ORE:
            case NETHER_QUARTZ_ORE:
                event.setDropItems(false); // set the default drops to false -> doesn't drop normal shit
                Collection<ItemStack> drops = event.getBlock().getDrops(event.getPlayer().getInventory().getItemInMainHand());
                ItemStack drop = drops.iterator().next(); // store the first (only) drop from the getDrops collection

                double multiplier = 0.5 + (1.75 - 0.5) * random.nextDouble(); // store the multiplier that will be used
                double newQuantity = drop.getAmount() * multiplier; // store the new amount of drops

                if((int) newQuantity == 0) break; // if there isn't any resulting drops break

                // Create item and drop it in the world -> cast the double to int
                world.dropItemNaturally(location, new ItemStack(drop.getType(), ((int) newQuantity)));
            default:
                break;
        }
    }

}
