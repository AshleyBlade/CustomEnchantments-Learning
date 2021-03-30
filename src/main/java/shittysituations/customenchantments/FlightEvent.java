package shittysituations.customenchantments;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class FlightEvent implements Listener {

    ArrayList<String> flyingPlayers = new ArrayList<>(); // Change to hashmap<String, long>

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        // on right click toggle flight
        // Store player
        Player player = event.getPlayer();

        // check if right clicks
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !event.getAction().equals(Action.RIGHT_CLICK_AIR)) return;

        // check if player has flight enchantment -> offhand
        ItemStack item = player.getInventory().getItemInOffHand(); // Store the off hand item
        if(!item.hasItemMeta()) return; // if no itemmeta return
        if(!item.getItemMeta().hasEnchant(CustomEnchants.FLIGHT)) return; // if no Flight enchant return
        // toggle player's flight
        if(!flyingPlayers.contains(player.getName())){
            flyingPlayers.add(player.getName());
            return;
        }
        // remove fall damage if player unequips item
        // remove flight if player unequips item
    }

    // flight event
    // change arraylist to hashmap
    // remove flight after 20 minutes -> test with 1 minute
    // remove from hashmap after 20 minutes
}
