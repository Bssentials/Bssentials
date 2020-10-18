package bssentials.bukkit;

import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

import bssentials.api.IWorld;

public class BukkitWorld implements IWorld {

    public World world;

    public BukkitWorld(World world) {
        this.world = world;
    }

    @Override
    public void generateTree(double x, double y, double z) {
        world.generateTree(new Location(world, x, y, z), TreeType.BIG_TREE);
    }

    @Override
    public String getName() {
        return world.getName();
    }

    @Override
    public int getLoadedChunkCount() {
        return world.getLoadedChunks().length;
    }

    @Override
    public int getEntityCount() {
        return world.getEntities().size();
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getBlockAt(int x, int y, int z) {
        return world.getBlockAt(x, y, z).getTypeId();
    }

    @Override
    public void setStorm(boolean b) {
        world.setStorm(b);
    }

    @Override
    public void spawnEntity(bssentials.api.Location location, String mobName, Object implobject) {
        world.spawnEntity(new Location(world, location.x, location.y, location.z), EntityType.valueOf(mobName.toUpperCase()));
    }

}