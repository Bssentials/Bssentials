package bssentials.fabric;

import bssentials.api.IWorld;
import bssentials.api.Location;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.level.ServerWorldProperties;

public class FabricWorld implements IWorld {

    public ServerWorld world;

    public FabricWorld(ServerWorld w) {
        this.world = w;
    }

    @Override
    public void generateTree(double x, double y, double z) {
        String ver = world.getServer().getVersion();
        if (ver.contains("1.17")) {
            System.out.println("WARN: generateTree only supported on 1.18+");
            return;
        }
        TreeGenerate_1_18.generateTree(x, y, z, world);
    }

    @Override
    public String getName() {
        return world.getRegistryKey().getValue().toString();
        //return ((ServerWorldProperties)world.getLevelProperties()).getLevelName();
    }

    @Override
    public int getLoadedChunkCount() {
        return world.getChunkManager().getLoadedChunkCount();
    }

    @Override
    public int getEntityCount() {
        int count = 0;
        // TODO: 1.17: Don't use iterateEntities
        for (Object entity : world.iterateEntities()) {
            count++;
        }
        return count;
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
        
        world.spawnEntity(e);
        e.teleport(location.x, location.y, location.z);
    }

}
