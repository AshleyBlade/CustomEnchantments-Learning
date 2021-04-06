package shittysituations.customenchantments.enchantments;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import shittysituations.customenchantments.CustomEnchants;

import java.util.HashMap;

public class DashEvent implements Listener {

    private final HashMap<String, Long> falldamage = new HashMap<>();
    private final HashMap<String, Long> cooldowns = new HashMap<>();

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        Action action = event.getAction(); // Get event action
        if(!action.equals(Action.RIGHT_CLICK_AIR)) return; // if action was not a right click towards the air return

        Player player = event.getPlayer(); // get event player
        ItemStack item = player.getInventory().getItemInMainHand(); // get the player's main hand
        if(!item.hasItemMeta()) return; // if item has no meta return
        if(!item.getItemMeta().hasEnchant(CustomEnchants.DASH)) return; // if item has meta but no DASH enchant

        // Cooldown
        if(cooldowns.containsKey(player.getName())){ // if cooldowns has player's name as a key
            // player is in hashmap
            Long storedTime = cooldowns.get(player.getName()); // store the remaining time
            if (storedTime > System.currentTimeMillis()){ // if storedTime is greater than current time, they have to wait longer
                player.sendMessage("Please wait before dashing again!"); // send a message to the player if they are on cooldown.
                return;
            }
        }
        cooldowns.put(player.getName(), System.currentTimeMillis() + 1000); // add player to the cooldown hashmap > name, current time

        player.setVelocity(player.getEyeLocation().getDirection().multiply(1.45f));// launch the player where they are looking

        // Damage the item
        Damageable damage = (Damageable) item.getItemMeta(); // Get the ItemMeta
        if(damage.hasDamage()) {
            if (damage.getDamage() > item.getType().getMaxDurability()){
                player.getInventory().removeItem(item);
                return;
            }
        }
        damage.setDamage(damage.getDamage() + 3); // Set the damage to the current damage + 3
        item.setItemMeta((ItemMeta) damage); // Set the damage to the ItemMeta

        if(!falldamage.containsKey(player.getName())){ // If the player's name is not in the HashMap
            falldamage.put(player.getName(), System.currentTimeMillis() + 1000); // Add player to the falldamage array
            //getLogger().info(player.getDisplayName() + " has been added to the falldamage map!"); // log to the console when someone is added
        }
    }

    @EventHandler
    public void onPlayerFall(EntityDamageEvent event){
        if(!(event.getEntity() instanceof Player)) return; // if entity is not a player return
        if(!(event.getCause() == EntityDamageEvent.DamageCause.FALL)) return; // if entity didn't get damage by falling return
        Player player = (Player) event.getEntity(); // Cast getEntity to player
        if(falldamage.containsKey(player.getName())){ // if falldamage array contains current player
            event.setCancelled(true); // set fall distance to -500
            falldamage.remove(player.getName()); // remove from falldamage HashMap -> they will take damage from falling now

            //getLogger().info(player.getDisplayName() + " has been removed from the falldamage map!"); // log to console they when someone is removed
        }
    }
}
