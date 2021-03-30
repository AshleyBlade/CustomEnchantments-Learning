package shittysituations.customenchantments.pathfinder;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.EnumSet;

public class PathfinderGoalCockatrice extends PathfinderGoal {

    private final EntityInsentient cockatrice; // Cockatrice
    private EntityLiving target;

    private final double speed; // Cockatrice speed
    private final float distance; // distance between Cockatrice and Target
    private final float blastRadius;

    // Coords
    private double x;
    private double y;
    private double z;

    // Custom chicken -> Finds target entity (Hostile mob) -> runs to them

    public PathfinderGoalCockatrice(EntityInsentient cockatrice, double speed, float distance, float blastRadius){
        this.cockatrice = cockatrice;
        this.speed = speed;
        this.distance = distance;
        this.blastRadius = blastRadius;
        this.a(EnumSet.of(Type.MOVE, Type.LOOK));
    }

    @Override
    public boolean a() { // runs every tick
        // Starts pathfinding goal if it is true
        this.target = this.cockatrice.getGoalTarget(); // Get the goal target from the Cockatrice Entity
        if (this.target == null) // if target == null return false
            return false;
        else if (this.cockatrice.getDisplayName() == null){ // it target doesn't have a displayname return false
            return false;
        } else{// Follow target

            // Fuck knows
            Vec3D vec = RandomPositionGenerator.a((EntityCreature) cockatrice, 16, 7, this.target.getPositionVector());
            if(vec == null) // Checks if target? is in the air
                return false;

            this.x = this.target.locX(); // Target's X
            this.y = this.target.locY(); // Target's Y
            this.z = this.target.locZ(); // Target's Z

            return true; // <-- runs c()
        }
    }

    public void c() { // runs after a()
        // navigates cockatrice to target!
        this.cockatrice.getNavigation().a(this.x, this.y, this.z, this.speed);
    }

    public boolean b() { // runs after c()
        // if false runs d -> if true continue navigating to target

        if(!(this.cockatrice.getNavigation().m() && this.target.h(this.cockatrice) < (double) (this.distance * this.distance))) return false;

        // true
        //getLogger().info("Cockatrice should be killed!");

        World world = cockatrice.getWorld().getWorld(); // Get the world
        Location cockatriceLoc = new Location(world, cockatrice.locX(), cockatrice.locY(), cockatrice.locZ()); // get cockatrice location
        world.createExplosion(cockatriceLoc, this.blastRadius, false, false); // create explosion at location

        cockatrice.die();

        return true;
    }

    public void d() { // runs if b = false
        this.target = null;
    }
}
