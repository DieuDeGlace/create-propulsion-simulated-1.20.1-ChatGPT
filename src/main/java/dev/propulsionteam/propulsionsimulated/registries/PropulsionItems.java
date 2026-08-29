package dev.propulsionteam.propulsionsimulated.registries;

import dev.propulsionteam.propulsionsimulated.CreatePropulsion;
//import dev.propulsionteam.propulsionsimulated.content.cable.CableSpoolItem;
import dev.propulsionteam.propulsionsimulated.utility.BurnableItem;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class PropulsionItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(net.minecraftforge.registries.ForgeRegistries.ITEMS, CreatePropulsion.ID);

    public static final RegistryObject<BurnableItem> PINE_RESIN =
        ITEMS.register("pine_resin", () -> new BurnableItem(new Item.Properties(), 1200));
    
    public static final RegistryObject<Item> TURPENTINE_BUCKET =
        ITEMS.register("turpentine_bucket", () -> new net.minecraft.world.item.BucketItem(PropulsionFluids.TURPENTINE.get(), new Item.Properties().craftRemainder(net.minecraft.world.item.Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> CORAL_BUCKET =
        ITEMS.register("coral_bucket", () -> new net.minecraft.world.item.BucketItem(PropulsionFluids.CORAL.get(), new Item.Properties().craftRemainder(net.minecraft.world.item.Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> OXIDIZER_BUCKET =
        ITEMS.register("oxidizer_bucket", () -> new net.minecraft.world.item.BucketItem(PropulsionFluids.OXIDIZER.get(), new Item.Properties().craftRemainder(net.minecraft.world.item.Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> PLATINUM_INGOT =
        ITEMS.register("platinum_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PLATINUM_NUGGET =
        ITEMS.register("platinum_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PLATINUM_SHEET =
        ITEMS.register("platinum_sheet", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_PLATINUM =
        ITEMS.register("raw_platinum", () -> new Item(new Item.Properties()));
    // public static final RegistryObject<CableSpoolItem> CABLE_SPOOL =
    //     ITEMS.register("cable_spool", () -> new CableSpoolItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }
}
