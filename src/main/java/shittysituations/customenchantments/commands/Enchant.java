package shittysituations.customenchantments.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import shittysituations.customenchantments.CustomEnchants;

import java.util.ArrayList;
import java.util.Locale;

public class Enchant implements CommandExecutor {

    // Commands
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(label.equalsIgnoreCase("enchant")){
            if(!(sender instanceof Player)) return true;
            if(args.length == 0) return true;
            Player player = (Player) sender;

            if(getPlayerItem(player) == null) {
                player.sendMessage("You need to have an item in your hand!");
                return true;
            }

            if(args[0].equalsIgnoreCase("vacuum")){

                ItemStack item = getPlayerItem(player); // get the player's item
                if(!item.getType().name().contains("AXE")) return true;
                applyEnchantment(item, player, "vacuum");

                return true;
            } else if(args[0].equalsIgnoreCase("smelt")){

                ItemStack item = getPlayerItem(player);
                if(!item.getType().name().contains("AXE")) {
                    player.sendMessage("This can only be applied to Axes and pickaxes!");
                    return true;
                }

                applyEnchantment(item, player, "smelt");

                return true;
            } else if(args[0].equalsIgnoreCase("dash")) {

                ItemStack item = getPlayerItem(player);
                if(!item.getType().name().contains("SWORD")) {
                    player.sendMessage("This can only be applied to a sword!");
                    return true;
                }

                applyEnchantment(item, player, "dash");

                return true;
            } else if(args[0].equalsIgnoreCase("gambler")) {

                ItemStack item = getPlayerItem(player);
                if (!item.getType().name().contains("PICKAXE")) {
                    player.sendMessage("This can only be applied to a Pickaxe");
                    return true;
                }

                applyEnchantment(item, player, "gambler");

                return true;
            } else if(args[0].equalsIgnoreCase("cockatrice")) { // WORKS

                ItemStack item = getPlayerItem(player); // Get player's item
                if (!item.getType().name().contains("SWORD") && !item.getType().name().contains("AXE")) { // check if item is a sword or axe
                    player.sendMessage("This can only be applied to an Axe or a Sword!"); // send message to player if isn't a sword or axe
                    return true;
                }

                applyEnchantment(item, player, "cockatrice");

                return true;
            }
        }
        return true;
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

    public static void applyEnchantment(ItemStack item, Player player, String enchant){
        player.getInventory().removeItem(item); // Delete item from player's inventory

        ArrayList<String> lore = new ArrayList<>(); // Create new arraylist for Lore
        switch(enchant){
            case "cockatrice": // if enchant == cockatrice
                item.addUnsafeEnchantment(CustomEnchants.COCKATRICE, 1); // Add cockatrice enchant
                lore.add(ChatColor.GRAY + "Cockatrice I"); // Add cockatrice lore
                break;
            case "dash": // if enchant == dash
                item.addUnsafeEnchantment(CustomEnchants.DASH, 1); // Add dash enchant
                lore.add(ChatColor.GRAY + "Dash I"); // Add dash lore
                break;
            case "vacuum": // if enchant == vacuum
                item.addUnsafeEnchantment(CustomEnchants.VACUUM, 1); // Add vacuum enchant
                lore.add(ChatColor.GRAY + "Vacuum I"); // Add vacuum lore
                break;
            case "smelt": // if enchant == smelt
                item.addUnsafeEnchantment(CustomEnchants.SMELT, 1); // Add smelt enchant
                lore.add(ChatColor.GRAY + "Smelt I"); // Add smelt lore
                break;
            case "gambler": // if enchant == gambler
                item.addUnsafeEnchantment(CustomEnchants.GAMBLER, 1); // Add gambler enchant
                lore.add(ChatColor.GRAY + "Gambler I"); // Add gambler lore
                break;
        }

        ItemMeta meta = item.getItemMeta(); // get the item's meta and store it as meta

        if(meta.hasLore()) // if the meta has lore
            for(String l : meta.getLore()) // iterate through lore
                lore.add(l); // add old lore to new lore arraylist
        meta.setLore(lore); // set the new lore in the meta
        item.setItemMeta(meta); // set the meta to the item

        player.getInventory().addItem(item); // add the item back to the player's inventory
    }
}
