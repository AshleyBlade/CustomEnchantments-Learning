package shittysituations.customenchantments.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import shittysituations.customenchantments.commands.Enchant;

import java.util.Arrays;

import static org.bukkit.Bukkit.getLogger;

public class EnchantGUI implements CommandExecutor, Listener {
    public static Inventory inventory = null;

    public EnchantGUI() {
        // creates new inventory and stores it.
        inventory = Bukkit.createInventory(null, 45, "Enchantment");

        initialiseItems(); //
    }

    public void initialiseItems(){
        createGuiBorder(Material.CYAN_STAINED_GLASS_PANE);

        ItemStack vacuum = createGuiItem(Material.HOPPER, "Vacuum","Places broken blocks into your inventory", "Can only be applied to pickaxes and axes.");
        ItemStack cockatrice = createGuiItem(Material.EGG, "Cockatrice", "Summons a Cockatrice when right-clicking!", "Can be applied to an axe or a sword.");
        ItemStack dash = createGuiItem(Material.POTION, "Dash", "Dashes forwards when you right-click!", "Can be applied to a sword.");
        ItemStack gambler = createGuiItem(Material.DIAMOND_ORE, "Gambler", "You may get more ore less, when breaking ores!", "Can only be applied to a pickaxe.");
        ItemStack smelt = createGuiItem(Material.FURNACE, "Smelt", "Auto-smelts ores when breaking them", "Can only be applied to pickaxes and axes.");

        inventory.setItem(20, vacuum);
        inventory.setItem(21, cockatrice);
        inventory.setItem(22, dash);
        inventory.setItem(23, gambler);
        inventory.setItem(24, smelt);
    }

    protected void createGuiBorder(final Material border){
        ItemStack item = createGuiItem(border, " ", " ");
        // for every slot in top row
        for (int i = 0; i < 9; i++){
            inventory.setItem(i, item);
        }
        // for every slot in bottom row
        for(int i = 36; i < 45; i++){
            inventory.setItem(i, item);
        }
        // if slot % 9 = 8 it is right side; if slot % 9 = 0 it is left side;
        for(int i = 0; i < 45; i++){
            if(i % 9 == 8 || i % 9 == 0){
                inventory.setItem(i, item);
            }
        }
    }

    protected ItemStack createGuiItem(final Material material, final String name, final String... lore){
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public void openInventory(final HumanEntity player){
        player.openInventory(inventory);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(label.equalsIgnoreCase("enchants")) {
            openInventory((HumanEntity) sender);
            return true;
        }
        return true;
    }

    @EventHandler
    public void onEnchantGUIClick(InventoryClickEvent event){
        if(event.getInventory() != inventory) return; // if clicked inventory isn't EnchantGUI
        if(!(event.getWhoClicked() instanceof Player)) return; // if the entity that clicked isn't a player return -> should always be a player
        event.setCancelled(true); // cancel standard event

        ItemStack clickedItem = event.getCurrentItem(); // store the clicked item
        if(clickedItem == null || clickedItem.getType().equals(Material.AIR)) return; // check if the item is null or air blocks
        if(!(clickedItem.getItemMeta().hasDisplayName())) return; // if the item doesn't have a display name return -> should always have a display name at this point
        if(clickedItem.getItemMeta().getDisplayName().equals(" ")) return;
        String enchant = clickedItem.getItemMeta().getDisplayName().toLowerCase(); // store the display name of the enchant to a lowercase string

        if(event.getWhoClicked().getInventory().getItemInMainHand().getType().equals(Material.AIR)) return;
        ItemStack mainItem = event.getWhoClicked().getInventory().getItemInMainHand(); // store the player's main hand item -> this is what will be enchanted
        Enchant.applyEnchantment(mainItem, (Player) event.getWhoClicked(), enchant); // apply the enchant to the weapon.

        // Add extra functionality here -> enchantment cost or something

        event.getWhoClicked().closeInventory(); // close the inventory after player clicks
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event){
        if(event.getInventory() == inventory) event.setCancelled(true);
    }
}
