package bssentials.fabric;

import java.util.Random;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;

public class TreeGenerate_1_18 {

    private static final Random rand = new Random();

    public static void generateTree(double x, double y, double z, ServerWorld world) {
        TreeConfiguredFeatures.OAK.generate(world, world.getChunkManager().getChunkGenerator(), rand, new BlockPos(x,y,z));
    }

}