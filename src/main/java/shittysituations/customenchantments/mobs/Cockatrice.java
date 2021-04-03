package shittysituations.customenchantments.mobs;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import shittysituations.customenchantments.pathfinder.PathfinderGoalCockatrice;

public class Cockatrice extends EntityChicken{

    // Details of the custom entity
    public Cockatrice(Location loc) { // Parse location so it can spawn
        super(EntityTypes.CHICKEN, ((CraftWorld) loc.getWorld()).getHandle()); // parse entity type and world

        this.setPosition(loc.getX(), loc.getY(), loc.getZ()); // Spawn the entity on the player!

        this.setCustomName(new ChatComponentText(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Cockatrice"));
        this.setCustomNameVisible(true);
        this.setHealth(100);

        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget<>(this, EntityMonster.class, true));
        //this.goalSelector.a(1, new PathfinderGoalMoveTowardsTarget(this, 1, 1));
        this.goalSelector.a(1, new PathfinderGoalCockatrice(this, 1.2, 2, 3)); // sets the cockatrice goal to custom pathfinder
        this.goalSelector.a(2, new PathfinderGoalRandomStrollLand(this, 1.2D));
        this.goalSelector.a(3, new PathfinderGoalRandomLookaround(this));
    }

    @Override
    public void initPathfinder() {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
    }

}
