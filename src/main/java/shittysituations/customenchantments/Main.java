package shittysituations.customenchantments;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import shittysituations.customenchantments.commands.ChickenDeaths;

import java.io.File;
import java.util.ArrayList;

public final class Main extends JavaPlugin{

    private File deaths = new File(getDataFolder(), "Deaths.yml"); // store deaths as File
    private FileConfiguration deathsConfig = YamlConfiguration.loadConfiguration(deaths);  // store deathsConfig as ymlconfig

    public FileConfiguration getDeathsConfig() { // get deathsConfig
        return deathsConfig;
    }

    public File getDeathsFile() { // get deathsFile
        return deaths;
    }

    @Override
    public void onEnable() {
        // Startup logic
        if(!deaths.exists()){ // if config doesn't exist, create it!
            saveResource("Deaths.yml", false);
        }

        // Register all the enchantments
        CustomEnchants.register();

        // Register commands
        getCommand("chickens").setExecutor(new ChickenDeaths(this));

        // Vacuum enchantment function
        this.getServer().getPluginManager().registerEvents(new VacuumEvent(), this);
        // Smelt enchantment function
        this.getServer().getPluginManager().registerEvents(new SmeltEvent(), this);
        // Dash enchantment function
        this.getServer().getPluginManager().registerEvents(new DashEvent(), this);
        // Flight enchantment function
        this.getServer().getPluginManager().registerEvents(new FlightEvent(), this);
        // CrazyChickens enchantment function
        this.getServer().getPluginManager().registerEvents(new ChickenEvent(this), this);
    }

    @Override
    public void onDisable() {
        // Shutdown logic
    }

    // Get player item
    private ItemStack getPlayerItem(Player player){
        ItemStack item = null;
        try{
            item = player.getInventory().getItemInMainHand();// get the players held item
        } catch(Exception err){ // check if its an item
            err.printStackTrace();
        }
        return item;
    }

    // Commands
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if(label.equalsIgnoreCase("enchant")){
            if(!(sender instanceof Player)) return true;
            if(args.length == 0) return true;
            Player player = (Player) sender;
            if(args[0].equalsIgnoreCase("vacuum")){

                ItemStack item = getPlayerItem(player); // get the player's item
                if(item == null) player.sendMessage("You need an item in your hand to apply this enchantment!"); // send a message to the player if they don't have an item

                player.getInventory().removeItem(item); // Remove the item the player is enchanting

                item.addUnsafeEnchantment(CustomEnchants.VACUUM, 1); // add smelt to item meta
                ItemMeta meta = item.getItemMeta(); // get the current ItemStack meta
                ArrayList<String> lore = new ArrayList<>(); // create lore arraylist
                lore.add(ChatColor.GRAY + "Vacuum I"); // add lore for the custom enchant

                if(meta.hasLore()) // check if the item has lore
                    for(String l : meta.getLore()) // iterate through the old lore
                        lore.add(l); // add old lore to the new lore

                meta.setLore(lore); // sets the lore new lore
                item.setItemMeta(meta); // sets the new meta

                player.getInventory().addItem(item); // add item back to the player's inventory
                return true;
            } else if(args[0].equalsIgnoreCase("smelt")){

                ItemStack item = getPlayerItem(player);
                if(!item.getType().name().contains("AXE")) {
                    player.sendMessage("This can only be applied to axes and pickaxes!");
                    return true;
                }

                player.getInventory().removeItem(item);

                item.addUnsafeEnchantment(CustomEnchants.SMELT, 1);
                ItemMeta meta = item.getItemMeta();
                ArrayList<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "Smelt I");

                if(meta.hasLore())
                    for(String l : meta.getLore())
                        lore.add(l);
                meta.setLore(lore);
                item.setItemMeta(meta);

                player.getInventory().addItem(item);
                return true;
            } else if(args[0].equalsIgnoreCase("dash")) {

                ItemStack item = getPlayerItem(player);
                if(!item.getType().name().contains("SWORD")) {
                    player.sendMessage("This can only be applied to a sword!");
                    return true;
                }
                player.getInventory().removeItem(item);

                item.addUnsafeEnchantment(CustomEnchants.DASH, 1);
                ItemMeta meta = item.getItemMeta();
                ArrayList<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "Dash I");

                if(meta.hasLore())
                    for(String l : meta.getLore())
                        lore.add(l);
                meta.setLore(lore);
                item.setItemMeta(meta);

                player.getInventory().addItem(item);
                return true;
            } else if(args[0].equalsIgnoreCase("flight")){

                ItemStack item = player.getInventory().getItemInOffHand(); // Get item in offhand
                if(!(item.getType().equals(Material.TRIPWIRE_HOOK))) {
                    player.sendMessage("This can only be applied to a tripwire hook!");
                }; // if no tripwire hook return
                player.getInventory().removeItem(item); // remove the old item from the player

                item.addUnsafeEnchantment(CustomEnchants.FLIGHT, 1); // Add Flight enchant
                ItemMeta meta = item.getItemMeta(); // Create item meta
                ArrayList<String> lore = new ArrayList<>(); // Create item lore
                lore.add(ChatColor.GRAY + "Flight I"); // add to the lore

                if(meta.hasLore())
                    for(String l : meta.getLore())
                        lore.add(l);
                meta.setLore(lore);
                item.setItemMeta(meta);

                player.getInventory().addItem(item);
                return true;
            } else if(args[0].equalsIgnoreCase("cockatrice")) { // WORKS

                ItemStack item = getPlayerItem(player); // Get player's item
                if (!item.getType().name().contains("SWORD") && !item.getType().name().contains("AXE")) { // check if item is a sword or axe
                    player.sendMessage("This can only be applied to an Axe or a Sword!"); // send message to player if isn't a sword or axe
                    return true;
                }

                applyCockatriceEnchantment(item, player);

                return true;
            }
        }
        return true;
    }

    public static void applyCockatriceEnchantment(ItemStack item, Player player) {
        player.getInventory().removeItem(item); // remove the old item from the player

        item.addUnsafeEnchantment(CustomEnchants.COCKATRICE, 1); // add Crazy Chickens enchantment to item
        ItemMeta meta = item.getItemMeta(); // get Itemmeta


        Damageable damage = (Damageable) meta;
        damage.setDamage(0);
        meta = (ItemMeta) damage;

        ArrayList<String> lore = new ArrayList<>(); // create lore arraylist
        lore.add(ChatColor.GRAY + "Cockatrice I"); // add enchantment lore to the item

        if(meta.hasLore()) // check if the item has a lore
            for(String l : meta.getLore()) // iterate through the lore
                lore.add(l); // add the old lore to the new lore
        meta.setLore(lore); // set the new lore to the item meta
        item.setItemMeta(meta); // set the new item meta to the item

        player.getInventory().addItem(item); // give the item back to the player
    }
}
