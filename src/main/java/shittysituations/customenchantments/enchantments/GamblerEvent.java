package shittysituations.customenchantments.enchantments;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import shittysituations.customenchantments.CustomEnchants;

import java.util.Random;

public class GamblerEvent implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if(!item.hasItemMeta()) return;
        if(!item.getItemMeta().hasEnchant(CustomEnchants.GAMBLER)) return;

        Block block = event.getBlock();
        Random random = new Random();
        event.setDropItems(false);

        // get the blocks location
        Location location = block.getLocation();
        World world = block.getWorld();

        int chance = random.nextInt(100) + 1;

        switch(block.getType()){
            case DIAMOND_ORE:
                // when mined has a 30% chance to drop a diamond 5% chance to drop block 65% iron ore
                if(chance <= 10){
                    world.dropItemNaturally(location, new ItemStack(Material.DIAMOND_BLOCK, 1));
                } else {
                    dropCoal(world, location);
                }
                break;
            case IRON_ORE:
                if(chance <= 1){
                    dropDiamond(world, location);
                } else if(chance <= 10){
                    dropIron(world, location);
                } else{
                    dropCoal(world, location);
                }
                break;
            case GOLD_ORE:
                if(chance <= 1){
                    dropDiamond(world, location);
                } else if(chance <= 10){
                    dropGold(world, location);
                } else{
                    dropCoal(world, location);
                }
                break;
            case NETHER_GOLD_ORE:
                if(chance <= 1){
                    world.dropItemNaturally(location, new ItemStack(Material.NETHERITE_SCRAP, 1));
                } else if (chance <= 10){
                    dropGold(world, location);
                } else{
                    dropCoal(world, location);
                }
            case COAL_ORE:
                if(chance <= 1){
                    dropDiamond(world, location);
                } else{
                    dropCoal(world, location);
                }
                break;
            case EMERALD_ORE:
                if(chance <= 1){
                    world.dropItemNaturally(location, new ItemStack(Material.EMERALD_BLOCK, 1));
                } else if(chance <= 10){
                    world.dropItemNaturally(location, new ItemStack(Material.EMERALD, 1));
                } else{
                    dropIron(world, location);
                }
                break;
            case LAPIS_ORE:
                if(chance <= 1){
                    dropDiamond(world, location);
                } else if(chance <= 10){
                    world.dropItemNaturally(location, new ItemStack(Material.LAPIS_LAZULI, 3));
                } else{
                    dropCoal(world, location);
                }
                break;
            case REDSTONE_ORE:
                if(chance <= 1){
                    dropDiamond(world, location);
                } else if(chance <= 10){
                    world.dropItemNaturally(location, new ItemStack(Material.REDSTONE, 4));
                } else{
                    dropCoal(world, location);
                }
                break;
            case NETHER_QUARTZ_ORE:
                if(chance <= 1){
                    world.dropItemNaturally(location, new ItemStack(Material.NETHERITE_SCRAP, 1));
                } else if(chance <= 10){
                    world.dropItemNaturally(location, new ItemStack(Material.QUARTZ, 3));
                } else{
                    dropCoal(world, location);
                }
                break;
        }
    }

    public void dropGold(World world, Location location){
        world.dropItemNaturally(location, new ItemStack(Material.GOLD_ORE, 1));
    }

    public void dropDiamond(World world, Location location){
        world.dropItemNaturally(location, new ItemStack(Material.DIAMOND, 1));
    }

    public void dropCoal(World world, Location location){
        world.dropItemNaturally(location, new ItemStack(Material.COAL, 1));
    }

    public void dropIron(World world, Location location){
        world.dropItemNaturally(location, new ItemStack(Material.IRON_ORE, 1));
    }
}
