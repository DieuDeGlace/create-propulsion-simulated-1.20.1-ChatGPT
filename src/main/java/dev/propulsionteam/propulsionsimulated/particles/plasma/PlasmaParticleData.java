package dev.propulsionteam.propulsionsimulated.particles.plasma;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.simibubi.create.foundation.particle.ICustomParticleDataWithSprite;

import net.minecraft.client.particle.ParticleEngine.SpriteParticleRegistration;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import dev.propulsionteam.propulsionsimulated.particles.ParticleTypes;
import dev.propulsionteam.propulsionsimulated.particles.VariableParticleDataSerialization;

public class PlasmaParticleData implements ParticleOptions, ICustomParticleDataWithSprite<PlasmaParticleData> {
    public static final Codec<PlasmaParticleData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ResourceLocation.CODEC.listOf().optionalFieldOf("override_textures", List.of()).forGetter(PlasmaParticleData::overrideTextures),
        Codec.INT.optionalFieldOf("override_color").forGetter(data -> java.util.Optional.ofNullable(data.overrideColor)),
        Codec.FLOAT.optionalFieldOf("override_size").forGetter(data -> java.util.Optional.ofNullable(data.overrideSize)),
        Codec.FLOAT.optionalFieldOf("startup_progress").forGetter(data -> java.util.Optional.ofNullable(data.startupProgress)),
        Codec.DOUBLE.optionalFieldOf("inherited_velocity_x", 0.0d).forGetter(data -> data.inheritedVelocity.x),
        Codec.DOUBLE.optionalFieldOf("inherited_velocity_y", 0.0d).forGetter(data -> data.inheritedVelocity.y),
        Codec.DOUBLE.optionalFieldOf("inherited_velocity_z", 0.0d).forGetter(data -> data.inheritedVelocity.z),
        Codec.FLOAT.optionalFieldOf("trail_coverage", 0.0f).forGetter(PlasmaParticleData::trailCoverage)
    ).apply(instance, (textures, color, size, startup, vx, vy, vz, coverage) -> new PlasmaParticleData(
            textures, color.orElse(null), size.orElse(null), startup.orElse(null), new Vec3(vx, vy, vz), coverage)));
    public static final ParticleOptions.Deserializer<PlasmaParticleData> DESERIALIZER =
            VariableParticleDataSerialization.deserializer(PlasmaParticleData::new);

    private final List<ResourceLocation> overrideTextures;
    private final Integer overrideColor;
    private final Float overrideSize;
    private final Float startupProgress;
    private final Vec3 inheritedVelocity;
    private final float trailCoverage;

    public PlasmaParticleData() {
        this(List.of(), null, null, null, Vec3.ZERO);
    }

    public PlasmaParticleData(List<ResourceLocation> overrideTextures, Integer overrideColor) {
        this(overrideTextures, overrideColor, null, null, Vec3.ZERO);
    }

    public PlasmaParticleData(List<ResourceLocation> overrideTextures, Integer overrideColor, Float overrideSize) {
        this(overrideTextures, overrideColor, overrideSize, null, Vec3.ZERO);
    }

    public PlasmaParticleData(List<ResourceLocation> overrideTextures, Integer overrideColor, Float overrideSize, Float startupProgress) {
        this(overrideTextures, overrideColor, overrideSize, startupProgress, Vec3.ZERO);
    }

    public PlasmaParticleData(List<ResourceLocation> overrideTextures, Integer overrideColor, Float overrideSize,
                              Float startupProgress, Vec3 inheritedVelocity) {
        this(overrideTextures, overrideColor, overrideSize, startupProgress, inheritedVelocity, 0.0f);
    }

    public PlasmaParticleData(List<ResourceLocation> overrideTextures, Integer overrideColor, Float overrideSize,
                              Float startupProgress, Vec3 inheritedVelocity, float trailCoverage) {
        this.overrideTextures = overrideTextures == null ? List.of() : List.copyOf(overrideTextures);
        this.overrideColor = overrideColor;
        this.overrideSize = overrideSize;
        this.startupProgress = startupProgress;
        this.inheritedVelocity = inheritedVelocity == null ? Vec3.ZERO : inheritedVelocity;
        this.trailCoverage = Mth.clamp(trailCoverage, 0.0f, 1.0f);
    }

    public List<ResourceLocation> overrideTextures() {
        return overrideTextures;
    }

    public Integer overrideColor() {
        return overrideColor;
    }

    public Float overrideSize() {
        return overrideSize;
    }

    public Float startupProgress() { return startupProgress; }

    public Vec3 inheritedVelocity() { return inheritedVelocity; }
    public float trailCoverage() { return trailCoverage; }

    @Override
    public ParticleType<?> getType(){
        return ParticleTypes.getPlasmaType();
    }

    public Codec<PlasmaParticleData> getCodec(ParticleType<PlasmaParticleData> type) {
        return CODEC;
    }

    @Override
    public ParticleOptions.Deserializer<PlasmaParticleData> getDeserializer() {
        return DESERIALIZER;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        VariableParticleDataSerialization.write(buffer, overrideTextures, overrideColor, overrideSize,
                startupProgress, inheritedVelocity, trailCoverage);
    }

    @Override
    public String writeToString() {
        return VariableParticleDataSerialization.writeToString(ParticleTypes.PLASMA.parameter(), overrideTextures,
                overrideColor, overrideSize, startupProgress, inheritedVelocity, trailCoverage);
    }

    @Override
    public SpriteParticleRegistration<PlasmaParticleData> getMetaFactory() {
        return PlasmaParticle.Factory::new;
    }
}
