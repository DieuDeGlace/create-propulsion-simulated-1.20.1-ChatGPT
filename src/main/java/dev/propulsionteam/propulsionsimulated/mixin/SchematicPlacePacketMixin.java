package dev.propulsionteam.propulsionsimulated.mixin;

import com.simibubi.create.content.schematics.packet.SchematicPlacePacket;
import dev.propulsionteam.propulsionsimulated.assemblerstick.interaction.GluedContraptionMoverService;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.concurrent.CompletableFuture;

@Mixin(value = SchematicPlacePacket.class, remap = false)
public abstract class SchematicPlacePacketMixin {
    @Shadow
    public ItemStack stack;

    /**
     * Create 6.0.8 schedules schematic placement on the server thread. Wrap
     * that exact task so mover cleanup happens after every placement target,
     * matching the upstream post-handle ordering.
     */
    @Redirect(
            method = "handle",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/network/NetworkEvent$Context;enqueueWork(Ljava/lang/Runnable;)Ljava/util/concurrent/CompletableFuture;"),
            remap = false)
    private CompletableFuture<Void> assemblystick$finalizeMoveAfterPlacement(NetworkEvent.Context context,
                                                                             Runnable placementTask) {
        return context.enqueueWork(() -> {
            placementTask.run();
            ServerPlayer player = context.getSender();
            if (player != null) {
                GluedContraptionMoverService.finalizePlacedMove(player, this.stack);
            }
        });
    }
}
