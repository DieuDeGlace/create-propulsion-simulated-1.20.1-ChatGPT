# Forge 1.20.1 port status

## Current checkpoint

- Branch: `port/forge-1.20.1`
- Baseline: `cb567609ce9df4acba39b15de69edd40822ae173`
- Planned port version: `1.1.5-port.1`
- State: baseline locked; platform migration not yet started
- Original JAR SHA-256:
  `5525A3A0C8B1C476CBC467D021E9D1492B58C78DE31ED269EB04F169D42DB0A1`

## System status

| System | Status | Evidence / next gate |
| --- | --- | --- |
| Source/JAR baseline | Complete | Resource, class and bytecode checks recorded |
| Dependency graph | In progress | Core artifacts and API boundaries mapped |
| Forge Gradle workspace | Not started | Must target Forge 47.4.0 / Java 17 |
| Registries/config | Not started | Compile plus registry enumeration required |
| Thrusters/physics | Not started | Sable force/assembly parity and real craft test |
| Fuel/heat/fluids | Not started | Datapack reload, tank persistence and burn tests |
| FE cables/generation | Not started | Capability lifecycle and network boundary tests |
| Platinum/worldgen | Not started | New world, existing world and config tests |
| Client rendering/audio | Not started | Flywheel, plume, shader, sound and resource tests |
| Networking | Not started | Client/server direction and desync tests |
| Tools/Ponder/GUI | Not started | Interaction and client presentation tests |
| ComputerCraft | Not started | Absent/present CC:Tweaked launch and peripheral tests |
| KubeJS | Not started | Absent/present launch and binding tests |
| Dedicated server | Not started | Server launch without client classloading |
| Save compatibility | Not started | Save/restart/reload and malformed-data boundaries |
| Release/recovery | Not started | One-click build, hashes, source and minimal workspace |

## Upstream update queue

Upstream `main` is currently `c89a53e`. Its commits after `cb56760` include
particle, interaction, fuel, worldgen, balancing, Copycat, ComputerCraft and
dedicated-server fixes. They are not silently mixed into the baseline. After
the baseline port compiles and runs, each applicable change must be reviewed
for behaviour and migrated explicitly, with a record of inclusion or rejection.

## Immediate next work

1. Replace NeoForge ModDev configuration with Forge 47.4.0 legacy userdev.
2. Add exact, hash-checked named development inputs without modifying the
   formal Sable or Simulated repositories.
3. Compile to obtain a complete API-difference inventory.
4. Migrate bootstrap/registries/config first, then capabilities/networking,
   then physics and client systems in dependency order.
