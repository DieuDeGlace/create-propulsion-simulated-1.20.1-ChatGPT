package dev.propulsionteam.propulsionsimulated.assemblerstick.item;

import dev.propulsionteam.propulsionsimulated.CreatePropulsion;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(net.minecraftforge.registries.ForgeRegistries.ITEMS, CreatePropulsion.ID);

    public static final RegistryObject<AssemblerStickItem> ASSEMBLER_STICK = ITEMS.register("assembler_stick",
            () -> new AssemblerStickItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<AutoGlueItem> AUTO_GLUE = ITEMS.register("auto_glue",
            () -> new AutoGlueItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<GluedContraptionMoverItem> GLUED_CONTRAPTION_MOVER = ITEMS.register("glued_contraption_mover",
            () -> new GluedContraptionMoverItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<GluedContraptionClonerItem> GLUED_CONTRAPTION_CLONER = ITEMS.register("glued_contraption_cloner",
            () -> new GluedContraptionClonerItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<ContraptionRemoverItem> CONTRAPTION_REMOVER = ITEMS.register("contraption_remover",
            () -> new ContraptionRemoverItem(new Item.Properties().stacksTo(1)));

    private ModItems() {
    }

    public static void register(final IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
