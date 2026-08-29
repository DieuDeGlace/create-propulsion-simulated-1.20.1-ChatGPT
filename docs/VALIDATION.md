# Validation record

This file records evidence for the Forge 1.20.1 port. A successful compile or
main-menu launch is not treated as proof that a gameplay system is complete.

## Locked inputs

- Source/JAR baseline: upstream `cb567609ce9df4acba39b15de69edd40822ae173`.
- Original JAR SHA-256:
  `5525A3A0C8B1C476CBC467D021E9D1492B58C78DE31ED269EB04F169D42DB0A1`.
- Baseline checkpoint: `97f6db3a66db479adb4a310c8c5b9b944b320d05`.
- Target: Minecraft 1.20.1, Forge 47.4.0 and Java 17.
- Formal dependency commits and artifact hashes are recorded in
  [DEPENDENCY_MAP.md](DEPENDENCY_MAP.md).

## Automated target checks

Last run on 2026-08-30 against the current working tree:

| Check | Result | Evidence boundary |
| --- | --- | --- |
| Java compilation | Pass | Production and test source sets compile. |
| Unit tests | Pass: 37/37 in 8 suites | Fuel math, cable distribution, worldgen limits and packet field round trips. |
| Packet protocol | Pass | Fluid and solid fuel maps, removals and efficiency values survive encode/decode. |
| Current clean build | Pass | `gradlew.bat clean build --no-daemon --stacktrace` completed on 2026-08-30. |
| Formal release gate | Enforced by script | Every package rebuilds from the clean annotated checkpoint and publishes its own build log/checkpoint/SHA-256 evidence. |

The packet tests deliberately use registry-independent identifiers. An earlier
test attempt bootstrapped the full Minecraft registries under JUnit and failed
before the assertions; it was rejected and replaced with a test of the packet
contract itself. Production packet code was not weakened to satisfy the test.

## Runtime matrix

All rows below use isolated development run directories. Named development
JARs are valid only in this mapped userdev environment; production game
instances must use the production Sable and Simulated bundle JARs.

| Profile | Mods exercised | Result | What it proves |
| --- | --- | --- | --- |
| Base client | Required target stack | Reached main menu; no Propulsion error or Mixin failure | Client bootstrap, registries, assets and required integrations load. |
| Base dedicated server | Required target stack | Reached `Done`; save and restart completed | Server bootstrap and a basic world persistence cycle work. |
| Optional server | CC:Tweaked 1.120.2 + KubeJS 2001.6.5 | Reached `Done`; KubeJS startup/server reported 0 errors; clean save | Both optional integrations coexist on a dedicated server. |
| Optional client | CC:Tweaked + KubeJS | Reached main menu; KubeJS startup/client reported 0 errors | Client-side present-mod paths and absent-mod gating work. |
| CBC server | Create Big Cannons 5.11.4 + RPL 2.1.1 | Reached `Done`; `FluidShellProjectileMixin` applied; 0 Propulsion errors; clean stop/save | CBC's strong optional Mixin is correctly gated and applies to the exact tested CBC build. |
| Maximum optional client | CBC + RPL + CC:Tweaked + KubeJS + formal Sable/Simulated/Aeronautics/Offroad | Reached main menu; no dependency, registry or Mixin failure | The tested optional-mod combination can bootstrap together. |

The remaining model-bakery warning for
`createpropulsion:partial/lodestone_tracker_overlay` is an inherited dormant
upstream asset gap described in [PORT_STATUS.md](PORT_STATUS.md), not a hidden
runtime pass.

## Rejected or superseded runs

- The detached original NeoForge source build stopped while CurseMaven
  terminated TLS for an optional artifact. It is evidence of an external
  resolution failure only, not a baseline build pass or source mismatch.
- The first KubeJS runtime attempt lacked Architectury and Rhino because the
  legacy development plugin did not propagate KubeJS metadata dependencies.
  Exact declared versions were then added to the optional test runtime and the
  isolated reruns passed.
- The first CBC attempt reused a world previously opened with CC:Tweaked and
  KubeJS, producing missing-registry messages for the removed test mods. That
  run was rejected. The isolated `compat-server` rerun created its own world
  and passed.
- The first packet unit-test design attempted full registry bootstrap and
  failed during test initialization. The registry-independent round-trip tests
  supersede it and all 37 tests pass.
- The first formal-packaging attempt was rejected before the build because its
  Gradle-version gate did not accept the carriage return in the real Windows
  PowerShell 5.1 output. The observed launcher was Gradle 8.14.3 on Microsoft
  Java 17.0.13; the gate was corrected to accept only that same version line
  with either platform line ending. No release or dist artifact was published.

## Manual acceptance still required

- Assemble and fly representative Sable crafts with each thruster type; compare
  force-at-point torque, obstruction, atmosphere and tilt behaviour with the
  supplied 1.1.5 JAR.
- Exercise liquid, solid, ion and creative thrusters; refuelling, burners,
  platinum tanks/vessels, FE cable networks and coral generation.
- Exercise assembler/glue/mover/cloner/remover tools, Ponder scenes, plume and
  sub-level rendering, sounds and tooltips.
- Call every documented CC peripheral method and execute KubeJS fuel
  add/remove/reload scripts through a reconnect.
- Fire a coral-filled CBC shell and compare explosion scaling.
- Save, stop and reload a world containing representative machines, fluids,
  cable state and an assembled craft using final production JARs.
- Recheck JEI/creative-tab coexistence and swivel-bearing mass after the formal
  Simulated port incorporates the dependency-owned 1.3.2 fixes.

These pending items remain explicit acceptance gates. They are not replaced by
successful compilation or menu/server smoke tests.
