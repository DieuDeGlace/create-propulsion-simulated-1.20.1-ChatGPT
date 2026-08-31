# Port decisions

## D-001: baseline commit

Use `cb567609ce9df4acba39b15de69edd40822ae173` as the only initial source
baseline because it is the commit whose working tree corresponds to the user
JAR. Do not start the port from current upstream HEAD.

## D-002: target version and branch

Use branch `port/forge-1.20.1`; `1.1.5-port.1` is the first formal checkpoint
and `1.1.5-port.2` carries the PJ creative-tab regression fix. Preserve upstream
`main` untouched.

## D-003: dependency inputs

Compile against named development artifacts copied from the formal Sable and
Simulated workspaces. Validate real launches with production artifacts. Never
place named development or sources JARs in a production game instance.

## D-004: upstream fixes

Review the `cb56760..c89a53e` delta only after baseline recovery. Backport each
applicable fix by semantics and document it; do not merge the later 1.21.1 tree
wholesale into the initial port.

## D-005: incomplete optional integrations

An unavailable optional dependency may be isolated behind correct conditional
loading while migration proceeds, but its feature must remain marked pending.
Deleting the feature, returning constants, empty implementations or suppressing
registration/runtime errors is not acceptable evidence of completion.

## D-006: preserve 1.21 interaction order on Forge 1.20.1

Where 1.21 splits block interaction into `useWithoutItem` and item-assisted
paths, the Forge 1.20.1 port uses the combined `use` method and preserves the
same empty-hand and held-item ordering. It does not add a second interaction or
force success merely to satisfy the older signature.

## D-007: capability lifetime and native Create fluid handlers

Forge `LazyOptional` values are stable per provider/capability/side and are
invalidated with their block entity. Platinum tank and vessel block entities
retain Create's native `FluidTankBlockEntity` capability instead of installing
a second wrapper that could become stale or expose divergent contents.

## D-008: optional runtime dependencies and isolated runs

Optional integrations stay compile-only in the release. Development properties
may add the exact tested CC:Tweaked, KubeJS, CBC and their declared runtime
dependencies to isolated compatibility run directories. This validates both
present-mod and absent-mod paths without making optional mods mandatory or
polluting a previous test world's registry history.

## D-009: dormant lodestone overlay resource

The supplied JAR, exact source baseline and current upstream all register
`partial/lodestone_tracker_overlay` and contain a texture but no model or call
site. The port keeps the public identifier and records the resulting warning;
it neither invents geometry nor deletes upstream API to make the log quiet.

## D-010: dependency-owned Simulated 1.3.2 fixes

Simulated upstream 1.3.2 adds a swivel-bearing mass correction and JEI/other-mod
creative-tab compatibility. Propulsion's used ABI is unchanged. Those fixes
belong in the formal Simulated port, so Propulsion records the dependency gap
instead of copying foreign logic into this repository.

## D-011: no later feature-chain overfitting

The port includes individually audited post-1.1.5 correctness fixes. Later
symmetric-wing, precise-control, creative-tool and balance feature chains are
excluded from `1.1.5-port.1`; including them would change the supplied JAR's
scope rather than restore its behaviour.

## D-012: remove unreferenced upstream patch artifacts

The baseline root contained `PatchConfig.java`, which hard-coded paths on an
upstream developer's machine, and its compiled `PatchConfig.class`. Neither
file is referenced by Gradle, production sources or runtime resources. Remove
both from the port checkpoint: retaining them would make the recovery workspace
less reproducible without preserving any mod behaviour.

## D-013: native Forge creative-tab contents, deferred visual padding

Forge 1.20.1 and JEI must receive only real stacks from the tab's native
`displayItems` generator. Section-spacing `ItemStack.EMPTY` values are added
only to the selected creative-menu list, following the applicable Simulated
1.3.2 compatibility design. This replaces Propulsion's fragile direct Mixin of
`CreativeModeTab.buildContents` without changing item visibility or section
order. Propulsion also declares its two banner textures as explicit block-atlas
sprites; it does not modify or duplicate Simulated's own atlas entries.

## D-014: make NeoForge automatic event routing explicit on Forge

NeoForge's automatic subscriber routes handlers by whether their event type is
an `IModBusEvent`, while Forge 47 defaults an omitted `EventBusSubscriber.bus`
to the Forge game bus. Therefore `PlumeShaders` and
`PropulsionModConfigEvents` explicitly select `Bus.MOD` on Forge. The other
subscribers whose handlers receive Tick, interaction and tooltip events remain
on the game bus.

This restores the upstream lifecycle semantics instead of treating Veil or
Oculus as the defect. The port does not catch the resulting null pointer,
install a fallback shader, skip plume geometry or branch on the presence of a
renderer mod. A bytecode-level unit test verifies the event type, explicit bus,
static handler and `SubscribeEvent` annotation without starting an artificial
Minecraft/OpenGL runtime.

## D-015: register Mixins in the production Forge artifact

The supplied NeoForge JAR declares `createpropulsion.mixins.json` through its
`[[mixins]]` metadata. Forge 1.20.1 uses the production manifest's
`MixinConfigs` attribute for the equivalent lifecycle. The development plugin
adds `--mixin.config` to its own run configurations, but that launch argument
is not present in a normal game instance and is not a substitute for artifact
metadata.

The production JAR therefore declares
`MixinConfigs: createpropulsion.mixins.json`. Both the ordinary Gradle `check`
lifecycle and the one-click release script fail if the declaration or resource
is absent. This keeps the full Mixin set active in production rather than
manually registering selected classes or special-casing the PJ launcher.
