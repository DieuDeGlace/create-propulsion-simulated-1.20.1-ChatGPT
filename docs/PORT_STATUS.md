# Forge 1.20.1 port status

## Current checkpoint

- Branch: `port/forge-1.20.1`
- Source/JAR baseline: `cb567609ce9df4acba39b15de69edd40822ae173`
- Baseline checkpoint: `97f6db3a66db479adb4a310c8c5b9b944b320d05`
- Port version: `1.1.5-port.2` candidate
- Previous formal checkpoint: annotated tag `v1.1.5-port.1`
- Current formal checkpoint: pending PJ creative-tab/plume regression
  validation and a verified tagged release; exact commit/tag objects are
  written into each release `CHECKPOINT.txt`
- State: recoverable engineering checkpoint; manual gameplay acceptance gates
  remain open
- Original JAR SHA-256:
  `5525A3A0C8B1C476CBC467D021E9D1492B58C78DE31ED269EB04F169D42DB0A1`

## System matrix

| System | Status | Current evidence | Remaining acceptance gate |
| --- | --- | --- | --- |
| Source/JAR baseline | Complete | Exact resource/class/bytecode correspondence at `cb56760` | None |
| Forge workspace | Implemented, verified | Forge 47.4.0, Java 17; current clean build, production-Mixin manifest gate and 41/41 tests pass | Repeat through the hash-locked release script at the tagged checkpoint |
| Registries/config | Implemented, partially verified | Client/server bootstrap plus a bytecode contract test for Forge MOD-bus shader/config subscribers | Change and reload the common config in a real instance |
| Thrusters/physics | Implemented, partially verified | Math tests, Sable assembly/particle APIs compile and both sides load | Assemble/fly crafts; torque, obstruction and atmospheric comparison |
| Fuel/heat/fluids | Implemented, partially verified | Reload summaries, fuel-consumption tests, packet round trips | Fill/burn/refuel and tank-side interaction gameplay |
| FE cables/generation | Implemented, partially verified | Cable distribution tests; Forge capability cache/invalidation fixed | Live cable, hub, ion and coral generator network |
| Platinum/worldgen | Implemented, partially verified | Five worldgen boundary tests; Forge biome modifiers load in a clean world | Confirm ore distribution and tank/vessel gameplay/persistence |
| Client rendering/audio | Implemented, regression fix under test | Banner atlas sources are present; the PJ null-shader crash was traced to the Forge MOD-bus boundary; production Mixins are now declared instead of relying on development launch arguments | Reopen the exact PJ save with Veil/Oculus and move the powered sub-level thruster; then compare plume, creative-tab layout, Flywheel, GUI, sound and sub-level rendering |
| Networking | Implemented, partially verified | Direction-locked protocol 2; fluid/solid packets preserve all fields in tests | Real client login, reload and server-authoritative desync test |
| Tools/Ponder/GUI | Implemented, smoke verified | Resources and Ponder scenes register | Exercise assembler/glue/mover/cloner/remover and every Ponder scene |
| CC:Tweaked | Present-mod/absent-mod loading verified | 1.120.2 server/client coexistence; official Forge peripheral provider | Attach peripherals and call each documented method |
| KubeJS | Present-mod/absent-mod loading verified | Plugin source found; startup/client/server scripts report 0 errors | Execute fuel add/remove/reload scripts and reconnect client |
| Create Big Cannons | Present-mod/absent-mod loading verified | CBC 5.11.4 + RPL 2.1.1; the FluidShell Mixin applies in development runs and is now registered in production metadata | Confirm production-JAR application, then fire a coral fluid shell and compare blast scaling |
| Dedicated server | Smoke verified | Isolated server reaches `Done`, 0 errors, graceful three-dimension save | Final production-JAR server and player login |
| Save compatibility | Partially verified | Earlier core start/save/restart passed | Repeat at final checkpoint with machines, fluids and assembled craft |
| Multi-mod compatibility | Smoke verified; PJ regressions under test | CBC + RPL + CC + KubeJS + formal Sable/Simulated family reaches client menu; no Veil/Oculus special case was added, and both lifecycle events and Mixins now use Forge production registration paths | Confirm Propulsion classification/banner with JEI, production Mixin application, the moving-plume scene, and the dependency-owned Simulated 1.3.2 fixes |
| Release/recovery | Implemented | Java/Git/input/test/JAR checks, dependency verification, portable source/minimal-workspace archives and rollback-safe publish | Every formal package must be produced by the tagged one-click script |

## Known upstream/dependency gaps

- `createpropulsion:partial/lodestone_tracker_overlay` has a texture and public
  registration in the supplied JAR, baseline source and current upstream, but
  no model or call site in any of them. It produces one model-bakery warning.
  The port does not fabricate geometry or remove the public identifier.
- Formal Simulated 1.3.1-port.1 predates upstream 1.3.2. The upstream
  swivel-bearing mass and JEI/creative-tab fixes remain owned by Simulated;
  Propulsion does not copy them into the wrong project.
- Optional missing-mod warnings emitted by Sable/CBC for their own conditional
  Mixin targets are dependency diagnostics, not Propulsion injection failures.

## Next gates

1. Complete focused gameplay validation for physics, fluids/heat, FE and tools.
2. Reopen the recorded PJ crash world, resume the moving Sable craft and
   verify the adaptive plume with Veil/Oculus before and after a resource reload.
3. Run final save/restart with representative Propulsion block entities and an
   assembled Sable craft.
4. Repeat the production-JAR client/server launch and representative
   save/restart after any behaviour change.
5. Use the one-click script for every formal package so the commit, annotated
   tag, dependencies, tests, archives and published hashes remain one unit.

Until those gates pass, this project is an advanced but incomplete port, not a
final release.
