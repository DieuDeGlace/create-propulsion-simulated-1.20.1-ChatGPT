package dev.propulsionteam.propulsionsimulated.utility;

import com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;

/** Resolves the direction a directional block should use from a placement context. */
public final class DirectionalPlacement {
    private DirectionalPlacement() {
    }

    public static Direction nearestLookingDirection(BlockPlaceContext context) {
        if (context.getPlayer() instanceof DeployerFakePlayer) {
            // The Deployer interacts with the face opposite its extension direction. The face is
            // authoritative here because the fake player's horizontal look rotation is arbitrary.
            return context.getClickedFace().getOpposite();
        }
        return context.getNearestLookingDirection();
    }
}
