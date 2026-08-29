package dev.propulsionteam.propulsionsimulated.mixin;

import dev.propulsionteam.propulsionsimulated.CreatePropulsion;
import dev.propulsionteam.propulsionsimulated.mixin.plugin.MixinIf;
import net.minecraft.core.Position;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rbasamoyai.createbigcannons.munitions.big_cannon.fluid_shell.EndFluidStack;

@Pseudo
@Mixin(targets = "rbasamoyai.createbigcannons.munitions.big_cannon.fluid_shell.FluidShellProjectile", remap = false)
@MixinIf("is_createbigcannons_loaded")
public abstract class FluidShellProjectileMixin {
    private static final ResourceLocation CORAL_ID = CreatePropulsion.loc("coral");
    private static final float TNT_POWER = 4.0f;
    private static final float CORAL_MB_PER_TNT = 500.0f;

    @Shadow
    private EndFluidStack fluidStack;

    @Inject(method = "detonate", at = @At("HEAD"), cancellable = true, remap = false)
    private void createpropulsion$detonateCoralAsExplosion(Position position, CallbackInfo callback) {
        EndFluidStack contents = this.fluidStack;
        if (contents == null || contents.isEmpty()) {
            return;
        }

        Fluid fluid = contents.fluid();
        int amountMb = contents.amount();
        if (fluid == null || amountMb <= 0 || !CORAL_ID.equals(fluid.builtInRegistryHolder().key().location())) {
            return;
        }

        float explosionPower = amountMb / CORAL_MB_PER_TNT * TNT_POWER;
        if (explosionPower <= 0.0f) {
            return;
        }

        Level level = ((Entity) (Object) this).level();
        level.explode(null, position.x(), position.y(), position.z(), explosionPower,
                Level.ExplosionInteraction.TNT);
        callback.cancel();
    }
}
