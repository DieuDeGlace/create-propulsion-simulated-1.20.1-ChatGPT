# Upstream baseline

## Scope

This port targets the user-supplied `createpropulsion-1.1.5.jar` on Minecraft
1.21.1 / NeoForge and restores its behaviour on Minecraft 1.20.1 / Forge.
The original JAR is an audit input, not a build dependency and is not committed
to this repository.

## Locked inputs

| Input | Value |
| --- | --- |
| Original JAR | `C:\Users\xy516\Downloads\createpropulsion-1.1.5.jar` |
| Original JAR SHA-256 | `5525A3A0C8B1C476CBC467D021E9D1492B58C78DE31ED269EB04F169D42DB0A1` |
| Fork | `https://github.com/NotAsher999/create-propulsion-simulated-1.20.1` |
| Upstream | `https://github.com/Propulsion-Team/create-propulsion-simulated` |
| Source baseline | `cb567609ce9df4acba39b15de69edd40822ae173` |
| Baseline subject | `Cosmonautics compatibility + sable update` |
| Upstream HEAD inspected on 2026-08-30 | `c89a53e7e7ddf8460eec9ed16ff4f36ff07aa767` |

The port branch starts at `cb56760`, not at the later upstream HEAD. Later
upstream fixes remain an explicit review queue and may only be brought over
after the baseline port is working and their behaviour has been understood.

## JAR/source correspondence

The JAR is a working-tree build corresponding to `cb56760`:

- Manifest version is `1.1.5`; Minecraft is `1.21.1`; NeoForge range starts at
  `21.1.219`; Sable range starts at `2.0.3`.
- 599 non-processed resources are byte-for-byte identical to the commit.
- The remaining ten language/Mixin resources are content-identical after
  normalising line endings and the final newline.
- All 251 effective top-level Java classes are present in the JAR. The only two
  source files without a class entry (`RequestShipDataPacket.java` and
  `SyncShipDataPacket.java`) contain fully commented-out class bodies.
- The JAR contains the `AtmosphericThrustMath` class introduced by `cb56760`.
  `AbstractThrusterBlockEntity` calls Sable's
  `DimensionPhysicsData.getAirPressure` and then that helper, matching source.

These checks exclude the parent commit and the later upstream commits as the
baseline. The manifest build timestamp precedes the commit by about one hour,
which is consistent with a local build followed by committing the same working
tree.

## Original system inventory

The baseline contains 253 Java source files, 611 unique source resources and
344 class entries in the production JAR. The principal runtime chains are:

- mod bootstrap, configuration and registration;
- standard, vector, liquid, solid-fuel, ion and creative thrusters;
- Sable sub-level assembly, force-at-point and atmospheric pressure physics;
- Simulated assembly helpers, creative sections and shared assets;
- fuel reload data, fluid handling, heat, burners and Stirling generation;
- FE cables, hubs, relays, reaction wheel and redstone transmission;
- platinum ore generation, tanks and vessels;
- Flywheel visuals, plume shaders/particles, sounds and tooltips;
- assembler, auto-glue, mover, cloner and remover tools;
- networking, Ponder scenes, ComputerCraft peripherals and KubeJS bindings;
- data-driven optional fuel compatibility for other Create/technology mods.

No subsystem is considered ported merely because its source file exists or its
class compiles.

## Rejected baseline build attempt

A detached `cb56760` audit worktree reached dependency resolution but could not
download optional compile-time artifact
`curse.maven:createindustry-693815:7236535`: CurseMaven terminated the TLS
handshake. This occurred before Java compilation and is recorded as an external
repository failure, not a source mismatch. The offline JAR/source evidence above
does not depend on that download.
