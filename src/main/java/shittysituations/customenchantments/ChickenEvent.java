package shittysituations.customenchantments;

import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import shittysituations.customenchantments.mobs.Cockatrice;

import java.util.HashMap;
import java.util.Random;

import static shittysituations.customenchantments.Main.applyCockatriceEnchantment;

public class ChickenEvent implements Listener {

    HashMap<String, Long> cooldown = new HashMap<>();
    public static int deaths = 0;

    Main plugin;
    public ChickenEvent(Main main) {
        this.plugin = main;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        if(!event.getHand().equals(EquipmentSlot.HAND)) return;
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !event.getAction().equals(Action.RIGHT_CLICK_AIR)) return; // if the player doesn't right click return
        Player player = event.getPlayer(); // Store the player
        ItemStack item = player.getInventory().getItemInMainHand(); // Store the item in the players hand
        if(!item.hasItemMeta()) return; // if the item has no meta return
        if(!item.getItemMeta().hasEnchant(CustomEnchants.COCKATRICE)) return; // if the item does not has the Cockatrice enchant return

        if(cooldown.containsKey(player.getName())) { // if the player has a cooldown
            Long storedTime = cooldown.get(player.getName()); // Get the time from HashMap
            if (storedTime > System.currentTimeMillis()){ // if storedTime is greater than current time, they have to wait longer
                player.sendMessage("Please wait before sending another chicken!"); // Send the player a message
                return;
            }
        }
        cooldown.put(player.getName(), System.currentTimeMillis() + 200); // Add player to cooldowns

        Damageable damage = (Damageable) item.getItemMeta(); // Cast the item meta to a Damageable
        if(damage.hasDamage())  // Check if the Damage has any damage
            if (damage.getDamage() > item.getType().getMaxDurability()){ // Check if the current durability is greater than maximum durability
                player.getInventory().removeItem(item); // Remove the item if no druability left
                return;
            }
        damage.setDamage(damage.getDamage() + 5); // Set the item damage to its current damage + 5
        item.setItemMeta((ItemMeta) damage); // Update ItemMeta with new damage

        Location playerLoc = player.getLocation(); // Store the player's location
        Cockatrice chicken = new Cockatrice(playerLoc); // Create Cockatrice with player location
        WorldServer world = ((CraftWorld) player.getWorld()).getHandle(); // Get the server's world
        world.addEntity(chicken); // Spawn the Cockatrice in the world -> at the player!
    }

    // Create enchantment book for Cockatrice enchantment
    @EventHandler
    public void onChickenDeath(EntityDamageByEntityEvent event){
        if(!event.getEntity().getType().equals(EntityType.CHICKEN)) return; // If not a chicken return
        if(!(event.getDamager() instanceof Player)) return; // check if didn't die to player

        plugin.getDeathsConfig().set("deaths.chickens", (plugin.getDeathsConfig().getInt("deaths.chickens") + 1)); ;
        try{
            plugin.getDeathsConfig().save(plugin.getDeathsFile());
        } catch(Exception err){
            System.out.println(err.getMessage());
        }


        Player player = (Player) event.getDamager(); // store the player
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!item.getType().name().contains("SWORD") && !item.getType().name().contains("AXE")) return;// check if item is a sword or axe

        Random random = new Random(); // create random

        if(!((random.nextInt(1000) + 1) == 1 )) return; // if the integer isn't 1 | 10% chance

        applyCockatriceEnchantment(item, player);
    }
}
