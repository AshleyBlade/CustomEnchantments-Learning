package shittysituations.customenchantments;

import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.bukkit.Bukkit.getLogger;

public class CustomEnchants {

    public static final Enchantment VACUUM = new EnchantmentWrapper("vacuum", "Vacuum", 1);
    public static final Enchantment SMELT = new EnchantmentWrapper("smelt", "Smelt", 1);
    public static final Enchantment DASH = new EnchantmentWrapper("dash", "Dash", 1);
    public static final Enchantment FLIGHT = new EnchantmentWrapper("flight", "Flight", 1);
    public static final Enchantment COCKATRICE = new EnchantmentWrapper("cockatrice", "Cockatrice", 1);

    public static void register(){
        boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(VACUUM);

        if(!registered){
            registerEnchantments(VACUUM);
            registerEnchantments(SMELT);
            registerEnchantments(DASH);
            registerEnchantments(FLIGHT);
            registerEnchantments(COCKATRICE);
        }
    }

    public static void registerEnchantments(Enchantment enchantment){
        boolean registered = true;
        try{
            Field field = Enchantment.class.getDeclaredField("acceptingNew");
            field.setAccessible(true);
            field.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch(Exception err){
            registered = false;
            err.printStackTrace();
        }

        if (registered) {
            getLogger().info("The " + enchantment.getKey() + " has registered successfully!");
        }
    }

}
