package dev.propulsionteam.propulsionsimulated.content.heat;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import net.minecraft.nbt.CompoundTag;

public class HeatSourceBehavior extends BlockEntityBehaviour {
    //Have some tea
    public static final BehaviourType<HeatSourceBehavior> TYPE = new BehaviourType<>();
    private HeatBuffer heatBuffer; 

    public HeatSourceBehavior(SmartBlockEntity be, float capacity, float expectedHeatProduction) {
        super(be);
        this.heatBuffer = new HeatBuffer(0, capacity, expectedHeatProduction);
    }

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    }

    public IHeatSource getHeatSource() {
        return this.heatBuffer;
    }

    @Override
    public void write(CompoundTag nbt,  boolean clientPacket) {
        nbt.put("HeatBuffer", heatBuffer.serializeNBT());
        super.write(nbt, clientPacket);
    }

    @Override
    public void read(CompoundTag nbt,  boolean clientPacket) {
        heatBuffer.deserializeNBT(nbt.getCompound("HeatBuffer"));
        super.read(nbt, clientPacket);
    }

}

