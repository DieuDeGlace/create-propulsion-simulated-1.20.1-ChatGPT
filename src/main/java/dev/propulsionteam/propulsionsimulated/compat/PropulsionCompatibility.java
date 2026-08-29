package dev.propulsionteam.propulsionsimulated.compat;

import net.minecraftforge.fml.ModList;

public class PropulsionCompatibility {
    public static final boolean CC_ACTIVE =
        ModList.get().isLoaded("computercraft") || ModList.get().isLoaded("cc_tweaked");
}
