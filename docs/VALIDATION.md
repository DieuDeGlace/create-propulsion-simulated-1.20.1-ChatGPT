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

Last run on 2026-08-31 against the current working tree:

| Check | Result | Evidence boundary |
| --- | --- | --- |
| Java compilation | Pass | Production and test source sets compile. |
| Unit tests | Pass: 41/41 in 10 suites | Fuel math, cable distribution, worldgen limits, packet field round trips, creative-section padding and Forge event-bus routing invariants. |
| Packet protocol | Pass | Fluid and solid fuel maps, removals and efficiency values survive encode/decode. |
| Current clean build | Pass | `gradlew.bat clean build --no-daemon --stacktrace` completed on 2026-08-31. |
| Production Mixin registration | Pass, static plus bootstrap smoke | The JAR manifest declares `MixinConfigs: createpropulsion.mixins.json`; Gradle `check` and the release script verify both declaration and resource. A normal PJ production launch without `--mixin.config` reached the world with no logged Mixin bootstrap/apply failure; individual behavior remains pending. |
| Formal release gate | Enforced by script | Every package rebuilds from the clean annotated checkpoint and publishes its own build log/checkpoint/SHA-256 evidence. |
| Minimal-workspace recovery | Pass for candidate `79f980d` | The published ZIP was independently extracted, its bundle cloned on the recorded branch, all five ignored named JARs reintroduced with matching hashes, the clone remained clean, and a fresh build passed 37/37 tests in 8 suites. |

The packet tests deliberately use registry-independent identifiers. An earlier
test attempt bootstrapped the full Minecraft registries under JUnit and failed
before the assertions; it was rejected and replaced with a test of the packet
contract itself. Production packet code was not weakened to satisfy the test.

The recovery build contained the same 1,133 JAR entry names as its release
candidate. Its archive SHA-256 was not identical: 31 text entries differed only
by Windows checkout line endings, and the manifest contained the later
per-build `Implementation-Timestamp`. The release SHA-256 therefore identifies
the shipped binary; this checkpoint claims source/build recovery, not a
byte-for-byte reproducible archive.

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

## PJ creative-tab regression

The 2026-08-30 PJ production log for port.1 reports that `Create Propulsion:
Simulated` has no display/search items, while JEI rejects the empty padding
stacks emitted by the old section layout. The same log confirms that
`simulated:banner` is present in the atlas; Propulsion's own banner sprites had
no atlas-source declaration. Port.2 replaces the direct `CreativeModeTab`
Mixin with the Forge-native output generator, defers empty visual slots to the
selected menu, and declares both Propulsion banner sprites. Compilation and all
41 tests pass, including section-padding/idempotence coverage; PJ visual/runtime
confirmation remains pending. The previous production candidate omitted the
Mixin manifest declaration, so its native tab generator and atlas sources were
present but its selected-menu spacing Mixins were not valid production
evidence. The rebuilt candidate corrects that registration boundary.

The 2026-08-31 production smoke did not emit the old "item group has no
display items" message, but it still emitted 37 empty-air entries also observed
without Propulsion. This is not visual evidence for either banner or selected-
menu spacing, so the creative-tab acceptance gate remains open.

The remaining model-bakery warning for
`createpropulsion:partial/lodestone_tracker_overlay` is an inherited dormant
upstream asset gap described in [PORT_STATUS.md](PORT_STATUS.md), not a hidden
runtime pass.

## PJ null-shader crash regression

The 2026-08-31 PJ run reached the single-player world, logged a save/pause and
later failed while Oculus flushed the translucent world batch. Veil was the
first code to dereference the null `ShaderInstance`, but it did not create the
null. `PlumeRenderType` obtains its shader from `PlumeShaders::plume`, and the
candidate JAR's subscriber had been attached to Forge's default game bus even
though `RegisterShadersEvent` is an `IModBusEvent` published on the mod bus.
The registration callback therefore never assigned the shader.

This is a loader-semantics migration gap. The current NeoForge baseline can
omit the bus because its subscriber machinery routes handlers by event type;
Forge 47 defaults an omitted bus to `FORGE`. The same audit found that
`PropulsionModConfigEvents` also receives an `IModBusEvent`, so both classes
now explicitly select `Bus.MOD`. Tick, player-interaction and tooltip
subscribers were inspected and correctly remain on the Forge game bus.

The crash-time save snapshot written at 12:55:46 and inspected before the
retest supplied direct trigger evidence. Its Sable sub-level contained a
`createpropulsion:creative_thruster_block_entity` with plasma plume, normal
control, redstone input 15, ten unobstructed exhaust blocks and full startup
power. The same snapshot recorded substantial linear and angular velocity.
With the PJ client in `PARTICLE` mode this strongly supported the conditions for
crossing the adaptive-trail sampling threshold and submitting the same custom
`PlumeRenderType`; shader mode also uses that RenderType. The aggregated Oculus
stack does not name the originating RenderType, so reproduction remains the
final attribution gate. The shader JSON, vertex/fragment resources, vertex
format and renderer attributes otherwise match.

The regression test reads compiled class annotations instead of booting a
Minecraft client. It first failed against both omitted bus values and passes
after the explicit routing repair. A first reflection-based form was rejected:
accessing Forge's bus enum outside an initialized FML runtime triggered Forge
`Bindings` before reaching the assertion. The replacement uses the already
locked ASM 9.6 test dependency and validates bytecode metadata without weakening
production code. The full clean build now passes 41/41 tests in 10 suites.

## Production Mixin registration regression

The supplied NeoForge JAR declares `createpropulsion.mixins.json` in its
`[[mixins]]` metadata. The earlier Forge candidate contained the JSON, refmap
and eight Mixin classes but neither a `MixinConfigs` manifest attribute nor a
production launcher argument. Development runs masked this because ModDevGradle
adds `--mixin.config` to its run configurations. A normal PJ launch does not.

The Forge artifact now declares the configuration in `META-INF/MANIFEST.MF`.
`verifyProductionJar`, wired into Gradle `check`, opens the final JAR and checks
both the attribute and resource. The release script independently repeats the
manifest check. No Mixin was copied, deleted or selectively registered; this
restores the whole upstream configuration's production lifecycle.

An entry-by-entry SHA-256 comparison before this manifest correction found the
same 972 production archive entries and only the two expected subscriber
classes plus the manifest timestamp changed. The final candidate adds only the
manifest declaration to that same behavior change. Relative to the crashing
candidate, the final archive still changes only those same three entries; the
manifest difference is its timestamp plus `MixinConfigs`. Its exact hash is
`1E399E6FB3D50B11F90F860676D1A098D59CB5890B3A95E22FDCEA3EDD801F92`;
the installed PJ copy and `build/libs` artifact matched exactly. The crashing
candidate remains recoverable beside it as the non-loadable
`createpropulsion-forge-1.20.1-1.1.5-port.2.jar.backup-C9E1CA0F8961-before-lifecycle-fix`.

### 2026-08-31 bounded PJ production smoke

The normal PJ launcher found the hash-locked candidate in the instance `mods`
folder and entered the same single-player world at 13:46:46. The run saved the
overworld, Nether, End and Sable sub-level data, then stopped the integrated
server and client cleanly at 13:48:59. It produced no new crash report and the
1,505-line log contains zero `shader is null` and zero `ReportedException`
matches. The captured `latest.log` SHA-256 is
`F801351165EE9BB1712A3039A50C608D2D4400E7FECE63B0EA74FC19D552E426`.

This verifies production bootstrap, world entry, save and clean exit only. The
two save/pause messages were followed by exit and do not prove pause/resume.
The log does not prove that the craft moved far enough to submit an adaptive
plume, that the plume was visible, that F3+T reloaded it, that either creative-
tab banner rendered, or that every configured Mixin executed its behavior.
Iris/Oculus explicitly reported shader packs disabled, so shaderpack-on
compatibility was not part of this smoke.

The post-run sub-level save contains four powered plasma Propulsion thrusters in
the player's loaded craft, but its final linear speed is only about 0.132 m/s
and it has no serialized angular velocity. This proves that active thrusters
were loaded inside the test scene, not that the client accumulated enough
motion samples to activate and draw the adaptive trail.

Runtime acceptance remains open: clean exit was observed once, but the repaired
production JAR must still render the moving sub-level trail with the existing
Veil/Oculus stack and survive pause/resume and resource reload. No renderer-mod-
specific null guard or disabled draw path is part of the fix.

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
- The first post-crash rebuild corrected the two Forge MOD-bus subscribers and
  passed 41/41 tests, but an independent artifact review found that production
  Mixins still relied on a development-only launch argument. It was not
  installed into PJ and was superseded by the manifest-registered candidate.
- The first formal-packaging attempt was rejected before the build because its
  Gradle-version gate did not accept the carriage return in the real Windows
  PowerShell 5.1 output. The observed launcher was Gradle 8.14.3 on Microsoft
  Java 17.0.13; the gate was corrected to accept only that same version line
  with either platform line ending. No release or dist artifact was published.
- The first complete package from checkpoint `238f91d` passed all gates but was
  superseded before distribution when an independent recovery inspection found
  the baseline's unreferenced root `PatchConfig.java/.class` helper artifacts.
  They were removed without production-code changes and the package was rebuilt
  from a new checkpoint.

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
