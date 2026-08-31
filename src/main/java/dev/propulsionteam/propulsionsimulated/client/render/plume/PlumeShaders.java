package dev.propulsionteam.propulsionsimulated.client.render.plume;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import dev.propulsionteam.propulsionsimulated.CreatePropulsion;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.client.event.RegisterShadersEvent;

import java.io.IOException;

@EventBusSubscriber(modid = CreatePropulsion.ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class PlumeShaders {
    private static ShaderInstance plume;

    private PlumeShaders() {
    }

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(
                new ShaderInstance(
                        event.getResourceProvider(),
                        new ResourceLocation(CreatePropulsion.ID, "plume"),
                        DefaultVertexFormat.POSITION_TEX_COLOR
                ),
                shader -> plume = shader
        );
    }

    public static ShaderInstance plume() {
        return plume;
    }
}
