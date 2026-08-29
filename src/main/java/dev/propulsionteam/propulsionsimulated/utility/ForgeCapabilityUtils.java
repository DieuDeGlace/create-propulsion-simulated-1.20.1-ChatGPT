package dev.propulsionteam.propulsionsimulated.utility;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

/**
 * Forge 1.20.1 equivalent of NeoForge's level-scoped block capability lookup.
 */
public final class ForgeCapabilityUtils {
    private ForgeCapabilityUtils() {
    }

    @Nullable
    public static <T> T getBlockCapability(Level level, BlockPos pos, Capability<T> capability,
                                           @Nullable Direction side) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity == null ? null : blockEntity.getCapability(capability, side).orElse(null);
    }
}
