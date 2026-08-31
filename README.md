<p align="center">
  <img width="170" height="170" src="https://github.com/Propulsion-Team/create-propulsion-simulated/blob/main/src/main/resources/icon.png">
</p>

<h1 align="center">Create Propulsion: Simulated</h1>

> **Forge 1.20.1 port:** `1.1.5-port.2` is under final validation on
> `port/forge-1.20.1`. The source/JAR baseline is locked to upstream
> `cb56760`. Compilation, automated tests and client/server compatibility
> smoke tests pass. The Forge shader/config event-bus and production Mixin
> registration corrections are awaiting an in-world PJ regression run;
> representative behaviour and final production-JAR persistence remain
> acceptance gates. See [port status](docs/PORT_STATUS.md),
> [validation evidence](docs/VALIDATION.md), [baseline evidence](docs/UPSTREAM_BASELINE.md),
> [dependency map](docs/DEPENDENCY_MAP.md) and [release/recovery rules](docs/RELEASE.md).

<p align="center">
  <a href="https://discord.gg/JHcArsDhh4">
    <img src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3.3.1/assets/cozy/social/discord-plural_vector.svg">
  </a>
  <a href="https://ko-fi.com/kyivsec">
    <img src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3.3.1/assets/cozy/donate/kofi-plural_vector.svg">
  </a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/create-propulsion-simulated">
    <img src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3.3.1/assets/cozy/available/curseforge_vector.svg">
  </a>
  <a href="https://modrinth.com/mod/create-propulsion-simulated">
    <img src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3.3.1/assets/cozy/available/modrinth_vector.svg">
  </a>
</p>

<p align="center">
  <img src="https://cdn.modrinth.com/data/cached_images/b945a562109fe5a7c7045931e0e3957fded1d2d0.png">
</p>

---

## Forge 1.20.1 dependencies

The port targets Minecraft 1.20.1, Forge 47.4.0 and Java 17. A normal game
instance requires these production mods:

- Create 6.0.8 and Flywheel 1.0.5;
- Veil 1.0.0.296;
- [Sable 2.0.5-port.1](https://github.com/NotAsher999/sable-1.20.1);
- [Simulated/Aeronautics/Offroad 1.3.1-port.1 bundle](https://github.com/NotAsher999/Simulated-Project-1.20.1).

The last two links are the formal 1.20.1 port repositories used by this
project. Download or build their production distribution JARs for a normal
Minecraft instance. Files ending in `-dev.jar`, `-sources.jar` or `-all.jar`
are development/source artifacts and must not replace the production JARs in
an ordinary game instance.

Optional integrations are available for
[CC:Tweaked](https://modrinth.com/mod/cc-tweaked),
[KubeJS](https://modrinth.com/mod/kubejs) and
[Create Big Cannons](https://modrinth.com/mod/create-big-cannons). The exact
tested compatibility versions and Ritchie's Projectile Library requirement are
recorded in [the dependency map](docs/DEPENDENCY_MAP.md).

## Building this port

The Git source checkout by itself is intentionally not a complete build input.
Five hash-locked named development JARs from the formal Sable/Simulated
workspaces are ignored by Git and must be placed in `libs/`; their exact names
and SHA-256 values are recorded in [the dependency map](docs/DEPENDENCY_MAP.md).
These mapped JARs are for compilation and development runs only and are not the
JARs to install in a normal game instance.

Use Java 17 and run `gradlew.bat clean build` after those inputs are present.
At a clean annotated checkpoint, `build-release.bat` performs the hash-locked
clean build and creates both a tracked-source archive and a recoverable minimal
workspace. See [release and recovery](docs/RELEASE.md). Until the remaining
manual gameplay gates pass, a generated package is an engineering checkpoint,
not a claim that the migration is fully accepted.

## Features

**Create Propulsion: Simulated** brings propulsion-focused Create gameplay to
the Sable + Create Aeronautics ecosystem. This branch restores the supplied
NeoForge 1.21.1 version on Forge 1.20.1 without adding later feature chains.

It adds three thruster types:

- Thruster: standard fuel-based propulsion
- Ion Thruster: FE-powered propulsion
- Creative Thruster: configurable high-power propulsion for testing and creative builds

<p align="center">
  <img src="https://cdn.modrinth.com/data/cached_images/73336c2909266ed71b87964e32c437f633bc2bea_0.webp">
</p>

<p align="center">
  The mod integrates directly with Sable and Create Aeronautics contraption physics, including force-at-point behavior for proper torque and multi-thruster handling on assembled crafts.
</p>

<p align="center">
  <img src="https://cdn.modrinth.com/data/cached_images/4e0d4859721fecffd9d4619ca190f494afee4756.png">
</p>

<p align="center">
  It also includes Copycat Wings for lightweight aerodynamic building with copycat-style visual customization.
</p>

**Thrusters support fuels from these mods:**
- [Create: The Factory Must Grow](https://modrinth.com/mod/create-tfmg)
- [Create: Crafts & Additions](https://modrinth.com/mod/createaddition)
- [Create: Diesel Generators](https://modrinth.com/mod/create-diesel-generators)
- [Immersive Engineering](https://modrinth.com/mod/immersiveengineering)
- [Mekanism: Generators](https://modrinth.com/mod/mekanism-generators)
- [Northstar: Redux](https://modrinth.com/mod/northstar-redux)
- [Stellaris](https://modrinth.com/mod/stellaris)

**Special thanks to:**
- **PacificCyan** - Ion Thruster model
- **FoxyFox909** - Multiblock Thrusters
- **DeltaSF** - Original Create: Propulsion creator for 1.20.1

# Wiki

- [ComputerCraft (CC: Tweaked)](wiki/cc/README.md)
- [KubeJS API](wiki/KubeJS-API.md)
- [ComputerCraft peripherals (full API reference)](wiki/ComputerCraft-Peripherals.md)
- [Datapack example usage (liquid thruster fuels)](wiki/Datapack-Example-Usage.md)
- [Solid Fuel Thruster (block, item fuels, datapack)](wiki/Solid-Fuel-Thruster.md)
