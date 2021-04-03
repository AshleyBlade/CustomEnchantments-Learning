package shittysituations.customenchantments;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import shittysituations.customenchantments.commands.ChickenDeaths;
import shittysituations.customenchantments.commands.Enchant;
import shittysituations.customenchantments.enchantments.*;

import java.io.File;

public final class Main extends JavaPlugin{

    private final File deaths = new File(getDataFolder(), "Deaths.yml"); // store deaths as File
    private final FileConfiguration deathsConfig = YamlConfiguration.loadConfiguration(deaths);  // store deathsConfig as ymlconfig

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
        getCommand("enchant").setExecutor(new Enchant());

        // Pickaxe Enchants
        // Vacuum enchantment function
        this.getServer().getPluginManager().registerEvents(new VacuumEvent(), this);
        // Smelt enchantment function
        this.getServer().getPluginManager().registerEvents(new SmeltEvent(), this);
        // Gambler enchantment -> NEEDS Balancing
        this.getServer().getPluginManager().registerEvents(new GamblerEvent(), this);

        // Dash enchantment function
        this.getServer().getPluginManager().registerEvents(new DashEvent(), this);
        // CrazyChickens enchantment function
        this.getServer().getPluginManager().registerEvents(new ChickenEvent(this), this);

    }

    @Override
    public void onDisable() {
        // Shutdown logic
    }
}
