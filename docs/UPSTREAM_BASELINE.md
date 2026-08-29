# Upstream baseline and fix audit

## Locked baseline

This port targets the user-supplied `createpropulsion-1.1.5.jar` on Minecraft
1.21.1 / NeoForge and restores its behaviour on Minecraft 1.20.1 / Forge. The
original JAR is an audit input, not a build dependency and is not committed.

| Input | Value |
| --- | --- |
| Original JAR | `C:\Users\xy516\Downloads\createpropulsion-1.1.5.jar` |
| Size | 1,707,765 bytes |
| SHA-256 | `5525A3A0C8B1C476CBC467D021E9D1492B58C78DE31ED269EB04F169D42DB0A1` |
| Fork | `https://github.com/NotAsher999/create-propulsion-simulated-1.20.1` |
| Upstream | `https://github.com/Propulsion-Team/create-propulsion-simulated` |
| Source baseline | `cb567609ce9df4acba39b15de69edd40822ae173` |
| Baseline subject | `Cosmonautics compatibility + sable update` |
| Baseline checkpoint | `97f6db3a66db479adb4a310c8c5b9b944b320d05` |
| Fork/upstream `main`, checked 2026-08-30 | `c89a53e7e7ddf8460eec9ed16ff4f36ff07aa767` |

The port starts from `cb56760`, not the later upstream head. Later commits are
reviewed individually below; this prevents later features and balance changes
from being mistaken for the behaviour of the supplied 1.1.5 JAR.

## JAR/source correspondence

The supplied JAR is a working-tree build corresponding to `cb56760`:

- its manifest identifies version 1.1.5, Minecraft 1.21.1 and NeoForge;
- 599 non-processed resources are byte-for-byte identical;
- ten language/Mixin resources match after line-ending/final-newline
  normalisation;
- all 251 effective top-level Java classes are present in the JAR;
- the only two source files without classes contain commented-out bodies;
- the atmospheric-pressure call chain and `AtmosphericThrustMath` bytecode
  match the commit.

The baseline contains 253 Java files, 611 unique source resources and 344 JAR
class entries. Its systems include registration/config, six thruster families,
Sable/Simulated physics, fuel/heat/fluids, FE cables, platinum worldgen and
storage, rendering/audio, tools, networking, Ponder, CC:Tweaked, KubeJS and
optional fuel integrations. A source file existing is not evidence that its
runtime chain has been restored.

## Post-1.1.5 upstream review

The following fixes have been ported by behaviour, with 1.20.1 APIs used only
where the platform requires them.

| Commit | Decision | Port meaning |
| --- | --- | --- |
| `3115a8e` | Included | Preserve dye colours in creative/ion/vector plumes. |
| `7a3524a`, `0695b1c` | Equivalent already present | The 1.20.1 combined `use` path unloads dye only for an empty hand and otherwise passes to item interaction. |
| `c0b40a8` | Included | Remove three dead test shaders exactly as upstream did. |
| `1a01f63` | Included | Transform particle spawn/motion through Sable sub-levels and avoid duplicate collision movement. |
| `a6a0d18` | Included | Group FE endpoints, reject self loops and prevent same-tick re-extraction. |
| `4d0a686` | Included | Use a deployer's clicked face for directional thruster/tilt placement. |
| `164902d` | Included | Restore upstream loot corrections. |
| `ccf7eb5` | Included | Count Copycat wings as sails; standard Mixin redirect is the Forge 1.20.1 semantic equivalent. |
| `6217f58` | Included | Restore tilt wrench rotation, shaft axis and multipart model behaviour. |
| `9d9e6ac` | Included | Remove unsafe Create SafeNBT treatment from platinum tanks. |
| `63829bd` | Included | Restore platinum generation through Forge biome modifiers. |
| `43988e2` | Included | Accumulate fractional per-tick fuel use instead of draining the wrong amount. |
| `bcfdf62` | Included | Correct tilt blockstates. |
| `c89a53e` | Included | Sync server fuel-efficiency overrides to clients; protocol is version 2. |

The following are deliberately not folded into 1.1.5-port.1 because they are
later features or balance reworks, not fixes required to reproduce 1.1.5:

- `52283f3`: solid-fuel ignition and balance rework;
- `fb316a8`, `fe80403`: precise CC/vector thrust controls;
- `41714a6`, `bafb5af`, `7198853`, `7f94052`, `52d8aa7`, `dd8a44e`,
  `03c2a0e`: symmetric-wing feature chain;
- `5339bcd`: creative tool configuration;
- `c61ce5e`: creative-tab removal that assumes the later symmetric/copycat
  replacement feature exists.

Merge-only commits have no independent behaviour to port. Exclusions remain in
the audit record so a later feature release can review them deliberately.

## Rejected baseline build attempt

A detached NeoForge baseline build could not download optional CurseMaven input
`createindustry-693815:7236535` because that repository terminated TLS before
Java compilation. This is recorded as an external dependency failure, not a
source mismatch and not a build pass.
