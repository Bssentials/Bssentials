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
	    if(mob.equalsIgnoreCase("Wolf")){
            spawnMob(EntityType.WOLF, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("Pig")){
            spawnMob(EntityType.PIG, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("PigZombie")){
            spawnMob(EntityType.PIG_ZOMBIE, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("Cow")){
            spawnMob(EntityType.COW, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("Blaze")){
            spawnMob(EntityType.BLAZE, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("CaveSpider")){
            spawnMob(EntityType.CAVE_SPIDER, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("Chicken")){
            spawnMob(EntityType.CHICKEN, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("Creeper")){
            spawnMob(EntityType.CREEPER, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("EnderDragon")){
            spawnMob(EntityType.ENDER_DRAGON, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("Enderman")){
            spawnMob(EntityType.ENDERMAN, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("Ghast")){
            spawnMob(EntityType.GHAST, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("Giant")){
            spawnMob(EntityType.GIANT, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("IronGolem")){
            spawnMob(EntityType.IRON_GOLEM, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("MagmaCube")){
            spawnMob(EntityType.MAGMA_CUBE, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("Ocelot")){
            spawnMob(EntityType.OCELOT, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("Sheep")){
            spawnMob(EntityType.SHEEP, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("SilverFish")){
            spawnMob(EntityType.SILVERFISH, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("Skeleton")){
            spawnMob(EntityType.SKELETON, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("Slime")){
            spawnMob(EntityType.SLIME, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("Spider")){
            spawnMob(EntityType.SPIDER, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("Squid")){
            spawnMob(EntityType.SQUID, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("Villager")){
            spawnMob(EntityType.VILLAGER, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("Zombie")){
            spawnMob(EntityType.ZOMBIE, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("PolarBear")) {
            spawnMob(EntityType.POLAR_BEAR, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("RABBIT")) {
            spawnMob(EntityType.RABBIT, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("MINECART")) {
            spawnMob(EntityType.MINECART, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("HORSE")) {
            spawnMob(EntityType.HORSE, world, TargetLocation);
        }else if(mob.equalsIgnoreCase("SHULKER")) {
            spawnMob(EntityType.SHULKER, world, TargetLocation);
        } else {
            sender.sendMessage("Mob not programed! use /summon!");
        }
    }
}
