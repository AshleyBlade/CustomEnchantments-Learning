package shittysituations.customenchantments.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import shittysituations.customenchantments.Main;

import static org.bukkit.Bukkit.getLogger;

public class ChickenDeaths implements CommandExecutor {

    Main plugin;
    public ChickenDeaths(Main main) {
        this.plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("chickens")){
            if(!(commandSender instanceof Player)) return true;
            Player player = (Player) commandSender;
            player.sendMessage("This many chickens have died: " + plugin.getDeathsConfig().getInt("deaths.chickens"));
        } else{
            getLogger().info("How did this happen? " + plugin.getDeathsConfig().getInt("deaths.chickens"));
        }
        return true;
    }
}
