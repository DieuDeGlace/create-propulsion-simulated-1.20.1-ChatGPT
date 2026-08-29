package dev.propulsionteam.propulsionsimulated.compat.kubejs;

import dev.propulsionteam.propulsionsimulated.content.thruster.SolidThrusterFuelManager;
import dev.propulsionteam.propulsionsimulated.content.thruster.ThrusterFuelManager;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;

public class PropulsionKubeJSPlugin extends KubeJSPlugin {
    @Override
    public void registerBindings(BindingsEvent bindings) {
        bindings.add("ThrusterFuelManager", ThrusterFuelManager.class);
        bindings.add("SolidThrusterFuelManager", SolidThrusterFuelManager.class);
    }
}


