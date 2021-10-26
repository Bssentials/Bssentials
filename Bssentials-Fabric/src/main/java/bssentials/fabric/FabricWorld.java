package bssentials.fabric;

import java.util.Random;

import bssentials.api.IWorld;
import bssentials.api.Location;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.level.ServerWorldProperties;

public class FabricWorld implements IWorld {

    public ServerWorld world;

    public FabricWorld(ServerWorld w) {
        this.world = w;
    }

    private static final Random rand = new Random();

    @Override
    public void generateTree(double x, double y, double z) {
        BlockPos pos = new BlockPos(x,y,z);
        // TODO: Update to 1.17:
        // ConfiguredFeatures.FANCY_OAK.feature.generate(world, world.getChunkManager().getChunkGenerator(), rand, pos, ConfiguredFeatures.FANCY_OAK.config);
    }

    @Override
    public String getName() {
        return ((ServerWorldProperties)world.getLevelProperties()).getLevelName();
    }

    @Override
    public int getLoadedChunkCount() {
        return world.getChunkManager().getLoadedChunkCount();
    }

    @SuppressWarnings("unchecked")
    @Override
    public int getEntityCount() {
        int count = 0;
        // TODO: 1.17: Don't use iterateEntities
        for (Object entity : world.iterateEntities()) {
            count++;
        }
        return count;

        /*
        try {
            Field f = world.getClass().getDeclaredField("entitiesById");
            f.setAccessible(true);
            Int2ObjectMap<Entity> map = (Int2ObjectMap<Entity>) f.get(world);
            return map.values().size();
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            return -999;
        }*/
    }

    @Override
    public int getBlockAt(int x, int y, int z) {
        return Block.getRawIdFromState(world.getBlockState(new BlockPos(x, y, z)));
    }

    @Override
    public void setStorm(boolean b) {
        ((ServerWorldProperties)world.getLevelProperties()).setRaining(true);
    }

    @Override
    public void spawnEntity(Location location, String mobName, Object implobject) {
        EntityType<?> type = EntityType.get(mobName).get();
        Entity e = type.create(world);
        e.teleport(location.x, location.y, location.z);
    }

}
