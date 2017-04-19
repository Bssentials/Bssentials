package ml.bssentials.api;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType; 

public class MobAPI {
    //TODO Add all mobs

	public static void spawnMob(EntityType entity, World world, Location TargetLocation) {
        world.spawnEntity(TargetLocation, entity);
    }
	
	public static void spawnMobCommand(String mob, CommandSender sender, World world, Location TargetLocation) {
		spawnMob(getEntity(mob), world, TargetLocation);
	}
	
	public static EntityType getEntity(String name) {
		for(EntityType entityType : EntityType.values()) {
			if(entityType.toString().toLowerCase().equalsIgnoreCase(name.toLowerCase())) {
				return entityType;
			}
		}
		
		return EntityType.PIG;
	}
}
