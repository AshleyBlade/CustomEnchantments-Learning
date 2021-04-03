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
}
