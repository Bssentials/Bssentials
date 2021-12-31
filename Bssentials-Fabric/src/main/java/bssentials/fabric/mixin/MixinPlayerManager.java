package bssentials.fabric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import bssentials.Bssentials;
import bssentials.fabric.BssentialsMod;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(PlayerManager.class)
public class MixinPlayerManager {

    /**
     * Remove Player from Users if
     * Player has left the game.
     */
    @Inject(at = @At("HEAD"), method = "remove")
    public void bssentials$fonPlayerQuit(ServerPlayerEntity player, CallbackInfo ci) {
        BssentialsMod mod = ((BssentialsMod)Bssentials.getInstance());
        mod.users.remove(player.getUuid());
    }

}
