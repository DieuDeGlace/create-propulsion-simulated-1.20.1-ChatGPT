# Dependency map

## Target platform

| Layer | Upstream 1.1.5 | Forge port target | Verified local input |
| --- | --- | --- | --- |
| Minecraft | 1.21.1 | 1.20.1 | Forge userdev |
| Loader | NeoForge 21.1.219 | Forge 47.4.0 | Gradle toolchain, pending setup |
| Java | 21 | 17 | Microsoft OpenJDK 17.0.13 available |
| Create | 6.0.10-280 | 6.0.8-289 | Maven, exact range to be locked |
| Flywheel | 1.0.6 | 1.0.5 | Maven, exact range to be locked |
| Ponder | 1.0.82 | 1.0.91 | Maven, exact version to be locked |
| Veil | transitive | 1.0.0.296 | named development JAR available |
| Sable | 2.0.3 | 2.0.5-port.1 | formal local project |
| Simulated/Aeronautics/Offroad | 1.2.1 | 1.3.1-port.1 | formal bundled port |

## Hash-locked local development inputs

Production JARs are for real game/runtime validation. Named development JARs
are for compilation in the Forge userdev namespace and must not be distributed
as runtime replacements.

| Purpose | Artifact | SHA-256 |
| --- | --- | --- |
| Sable production | `sable-forge-1.20.1-2.0.5-port.1.jar` | `BEF473505E965CFA19E7FEA1AB41E1FBDAD4955036CC78C451D47D247D45AB64` |
| Sable named dev | `sable-forge-1.20.1-2.0.5-port.1.jar` from `forge/build/devlibs` | `D3C7B0E251582E9926641C313A5C82E48C4BA22697E841E626F1A0C7BB742FFF` |
| Aeronautics bundle production | `create-aeronautics-bundled-1.20.1-1.3.1-port.1.jar` | `8E3AA3CEF63313D5E9F69B3607311733C9A72C27A3DAA72DC536402433504332` |
| Aeronautics bundle named dev | same name from `aeronautics-bundled/build/devlibs` | `05D373EC1D72DFE10474FFD5E8D2868957C5DF84968B40A47F234FCE0F58ACC5` |
| Simulated named dev | `simulated-forge-1.20.1-1.3.1-port.1.jar` | `E29A48C250698FCE16460AA0CFFF9C44565BF42B0124ED6631DB512168856977` |
| Aeronautics named dev | `aeronautics-forge-1.20.1-1.3.1-port.1.jar` | `C25FB2327ED7F8B6956CDE3967C210BBF88820C62453AE7B70D6F9D77F562FCE` |
| Offroad named dev | `offroad-forge-1.20.1-1.3.1-port.1.jar` | `59C578A0FA939B6639630BDB11BBEA8F7FA948620BDF93276FEBCB899EA6FBD4` |

Sable's named dev JAR contains the Sable Companion math/API classes used by
Propulsion. The production Aeronautics bundle embeds the Simulated,
Aeronautics and Offroad production JARs through JarJar.

## Vertical migration chains

1. Bootstrap and registries: NeoForge event bus/deferred holders to Forge
   registries and `RegistryObject` while preserving registration order.
2. Persistent state: 1.21 data components/holder lookup APIs to 1.20.1 NBT and
   registry access without changing saved meanings.
3. Capabilities: NeoForge block/item capability queries to Forge capability
   exposure, invalidation and `LazyOptional` lifecycle.
4. Networking: NeoForge payload registrar/codecs to Forge channels with the
   same direction, validation and main-thread handling.
5. Physics: retain Sable assembly listeners, rigid-body force groups,
   sub-level transforms, atmospheric pressure and Simulated assembly checks.
6. Client: port Flywheel visual lifecycles, renderer/model data, particles,
   shaders, sound and Ponder registration as complete chains.
7. Compatibility: ComputerCraft and KubeJS are conditional integrations;
   data-driven fuel integrations must remain safe when their mods are absent.

## Known version boundaries

- Create 6.0.8 lacks or renames some 1.21.1 APIs, including data maps and some
  registry/model helpers. Replacements must be based on behaviour, not names.
- Minecraft 1.20.1 has no 1.21 item data-component system.
- NeoForge capability and payload APIs are not source-compatible with Forge
  47.4.0.
- Client-only integrations must remain physically safe on a dedicated server;
  side checks alone do not make an eagerly loaded client class safe.
- Optional compatibility is not validated until both absent-mod and
  present-mod launch matrices have been exercised.
