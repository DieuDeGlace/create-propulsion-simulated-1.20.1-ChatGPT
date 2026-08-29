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
