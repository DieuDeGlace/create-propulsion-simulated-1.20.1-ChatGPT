# Dependency map

## Target platform

| Layer | Supplied 1.1.5 JAR | Forge port target | Resolution |
| --- | --- | --- | --- |
| Minecraft | 1.21.1 | 1.20.1 | exact |
| Loader | NeoForge 21.1.219+ | Forge 47.4.0 | exact |
| Java | 21 | 17 | toolchain and runtime |
| Create | 6.0.10-280 | 6.0.8-289 | `[6.0.8,6.0.9)` |
| Flywheel | 1.0.6 | 1.0.5 | `[1.0.5,1.0.6)` |
| Ponder | 1.0.82 | 1.0.91 | exact build input |
| Registrate | upstream transitive | MC1.20-1.3.3 | exact build input |
| Veil | upstream transitive | 1.0.0.296 | `[1.0.0,1.1.0)` |
| Sable | 2.0.3 | 2.0.5-port.1 | exact formal port |
| Simulated/Aeronautics/Offroad | 1.2.1 | 1.3.1-port.1 | exact formal bundle |

## Formal local port inputs

Production JARs are used in real game instances. Named development JARs are
only for compilation/runs in the Forge named namespace and must never replace
production JARs in a normal instance.

| Purpose | Artifact | SHA-256 |
| --- | --- | --- |
| Sable production | `sable-forge-1.20.1-2.0.5-port.1.jar` | `B41D28D67D039D1AF755AC4C772CD8152BC6A4383C9B95553D1241F62E7BEB3D` |
| Sable named dev | `libs/sable-forge-1.20.1-2.0.5-port.1-dev.jar` | `5C0147F3032DF1363DDDB2B099F1374ADB15447336DEECDF10B1F57195A747CB` |
| Simulated/Aeronautics/Offroad production bundle | `create-aeronautics-bundled-1.20.1-1.3.1-port.1.jar` | `8E3AA3CEF63313D5E9F69B3607311733C9A72C27A3DAA72DC536402433504332` |
| Simulated named dev | `libs/simulated-forge-1.20.1-1.3.1-port.1-dev.jar` | `E29A48C250698FCE16460AA0CFFF9C44565BF42B0124ED6631DB512168856977` |
| Aeronautics named dev | `libs/aeronautics-forge-1.20.1-1.3.1-port.1-dev.jar` | `C25FB2327ED7F8B6956CDE3967C210BBF88820C62453AE7B70D6F9D77F562FCE` |
| Offroad named dev | `libs/offroad-forge-1.20.1-1.3.1-port.1-dev.jar` | `59C578A0FA939B6639630BDB11BBEA8F7FA948620BDF93276FEBCB899EA6FBD4` |
| Veil named dev | `libs/Veil-forge-1.20.1-1.0.0.296-dev.jar` | `24717637D0B20B2FDE9A25B7456E05FD8F1035FF37000E7BD095742B7DA41E19` |

Read-only dependency state checked 2026-08-30:

- Sable formal repository: `J:\MCModPort\SableYZ\repository`, commit
  `ddd7e224b39f89ee788c8ebcdc4a0a120e076bdb`, clean.
- Simulated formal repository: `J:\MCModPort\SimulatedYZ\repository`, commit
  `9f4cd51485ca207a0281a464bb3a8fb8a7cbd1c2`, clean.
- No files in either formal dependency repository were changed by this port.

Sable's named artifact provides the Companion/API types used by Propulsion.
The Simulated production bundle embeds Simulated, Aeronautics and Offroad with
JarJar; the three separate named JARs are only needed by userdev.

## Optional compatibility inputs

| Integration | Exact tested version | Build/runtime boundary | SHA-256 where locked |
| --- | --- | --- | --- |
| CC:Tweaked | 1.120.2 | compile-only; optional test runtime | Maven-resolved |
| KubeJS | 2001.6.5-build.26 | compile-only; optional test runtime | Maven-resolved |
| Architectury API | 9.1.12 | mandatory KubeJS test-runtime dependency | Maven-resolved |
| Rhino | 2001.2.2-build.17 | mandatory KubeJS test-runtime dependency | Maven-resolved |
| Create Big Cannons | 5.11.4, Modrinth artifact `ZRgu5o3x` | compile-only; optional test runtime | `11845F0D79A9014977F667A57B14D0527928BEE8B66BC6B50BEDCC29EE81878A` |
| Ritchie's Projectile Library | 2.1.1, Modrinth artifact `PdGBsNte` | mandatory CBC test-runtime dependency | `5531EC454792F98993FA29D55F8810444BA3E9EF8F04331C5811B84EC8D0C3DC` |

Legacy ModDev does not propagate KubeJS or CBC mod metadata dependencies into
the test run automatically. The optional properties add their exact declared
runtime dependencies explicitly; this affects development validation only and
does not bundle optional mods into Propulsion.

`gradle/verification-metadata.xml` locks the SHA-256 values of the default build
graph, the five local named artifacts and the fully enabled CC/KubeJS/CBC test
runtime graph. The release script additionally checks the five formal local
JARs before and after building and verifies the Taffy JAR embedded in the final
artifact as `3AE3B857CBB4392CCB3245778C9B7192522238CF66BD5AC413EAD4613FA80425`.

## Vertical migration chains

1. Bootstrap and registries: NeoForge event bus/holders became Forge
   `DeferredRegister`/`RegistryObject` without reordering registrations.
   NeoForge's automatic event-type routing is made explicit on Forge: handlers
   for `IModBusEvent` select `Bus.MOD`, while gameplay events stay on the Forge
   bus. NeoForge `[[mixins]]` metadata becomes the Forge production manifest's
   `MixinConfigs` attribute; development-only launch arguments are not treated
   as release registration.
2. Saved state: 1.21 data components and holder lookup became 1.20.1 NBT and
   registry lookup while retaining field meanings and malformed-data checks.
3. Capabilities: NeoForge block callbacks became Forge providers with stable
   `LazyOptional` instances, side-specific handlers and invalidation. Create's
   native tank capability remains authoritative for platinum tanks/vessels.
4. Networking: NeoForge payload codecs became a direction-locked Forge
   `SimpleChannel`; server fuel data, removals and efficiency overrides share
   protocol version 2 and enqueue on the main thread.
5. Physics: Sable assembly listeners, sub-level transforms, force-at-point,
   atmospheric pressure and particle movement were migrated as a chain.
6. Client: model/resource paths, Flywheel lifecycle, Veil render paths,
   particles, sound and Ponder use their Forge 1.20.1 equivalents.
7. Compatibility: CC:Tweaked uses its Forge peripheral provider, KubeJS stays
   plugin-gated, and CBC's strong Mixin is applied only when CBC is loaded.

## Known dependency boundary

The official Simulated upstream `main` is now version 1.3.2 at
`b9cb3a8ef9cabeb57f7c2add77ca4941cc176d32`, while the formal 1.20.1 port is
1.3.1-port.1. The missing upstream delta contains the swivel-bearing assembled
mass fix (`b7fd205`) and JEI/other-mod creative-tab compatibility fix
(`67a3b1c`). Propulsion's referenced `SimulatedCreativeTab` fields/methods still
exist, so no Propulsion ABI repair is indicated. These fixes belong in the
formal Simulated port and are not duplicated here. Swivel mass and JEI creative
tab coexistence therefore remain dependency-owned pending validations.
