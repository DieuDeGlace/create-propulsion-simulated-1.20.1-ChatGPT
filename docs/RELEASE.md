# Release and recovery

## One-click verified build

Run `build-release.bat` from the repository root. The script intentionally
refuses to package unless all of these conditions are true:

- the current branch is `port/forge-1.20.1`;
- the worktree is clean;
- annotated tag `v1.1.5-port.1` points to the current commit;
- Gradle 8.14.3 is launched with Java 17;
- the five ignored named development JARs match the SHA-256 values in
  [DEPENDENCY_MAP.md](DEPENDENCY_MAP.md);
- Gradle dependency verification succeeds;
- the clean build produces exactly 37 passing tests in 8 suites;
- the production JAR metadata and embedded Taffy 1.1.4 hash match the locked
  release inputs.

The build rechecks the Git refs, clean worktree and local dependency hashes
after compilation and again before publication. A changed source or build input
therefore cannot silently be combined with the original checkpoint record.

## Outputs

Successful packaging publishes:

- `dist/createpropulsion-forge-1.20.1-1.1.5-port.1.jar` for a normal game
  instance;
- `release/1.1.5-port.1/` containing the production JAR, sources JAR,
  tracked-source ZIP, minimal-workspace ZIP, build log, checkpoint record,
  dependency manifest, release notes and SHA-256 sums.

The production release does not bundle Sable, Simulated, Aeronautics, Offroad,
Veil, CC:Tweaked, KubeJS or Create Big Cannons. Install their production JARs
separately as described in the README. Taffy 1.1.4 is the sole JarJar-embedded
library and its exact embedded SHA-256 is verified.

Publication uses a staged directory and retains/restores the previous release
and `dist` copy if the final publish step fails. Existing release output is not
replaced by a failed build.

## Recovery boundary

The tracked-source ZIP is an exact `git archive` of the tagged commit. It does
not contain the ignored third-party named development JARs and is therefore not
independently buildable.

The minimal-workspace ZIP contains:

- the tagged tracked source snapshot;
- all five ignored, hash-locked named development JARs;
- the annotated tag and branch in a verified Git bundle;
- recovery instructions and a SHA-256 manifest covering every other file in
  the archive.

It can build directly after extraction, or `RESTORE.md` can recreate a clean
Git clone and copy only the ignored build inputs into it. ZIP entries are
written with forward slashes and verified for cross-platform directory
recovery.

## GitHub workflow boundary

The public repository checkout lacks the ignored named development JARs, so its
GitHub workflow performs only honest Java 17 wrapper and committed-diff checks.
It does not report a skipped or impossible Forge build as successful and does
not publish nightly/release JARs. Formal builds use the local verified script or
the recovered minimal workspace.
