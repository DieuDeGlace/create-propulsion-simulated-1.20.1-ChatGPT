package dev.propulsionteam.propulsionsimulated.mixin.client;

import dev.propulsionteam.propulsionsimulated.registries.PropulsionCreativeTab;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.ItemPickerMenu.class)
public class ItemPickerMenuMixin {
    @Shadow
    public NonNullList<ItemStack> items;

    @Inject(method = "scrollTo", at = @At("HEAD"))
    private void createpropulsion$padSectionRows(float scroll, CallbackInfo ci) {
        if (CreativeModeInventoryScreenSelectedTabAccessor.createpropulsion$getSelectedTab()
                == PropulsionCreativeTab.TAB) {
            PropulsionCreativeTab.padMenuItems(this.items);
        }
    }
}
