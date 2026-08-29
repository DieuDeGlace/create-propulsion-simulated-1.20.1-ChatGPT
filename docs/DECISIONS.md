# Port decisions

## D-001: baseline commit

Use `cb567609ce9df4acba39b15de69edd40822ae173` as the only initial source
baseline because it is the commit whose working tree corresponds to the user
JAR. Do not start the port from current upstream HEAD.

## D-002: target version and branch

Use branch `port/forge-1.20.1` and planned version `1.1.5-port.1`. Preserve
upstream `main` untouched.

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
