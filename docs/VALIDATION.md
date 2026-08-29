# Validation record

## Baseline checks (2026-08-30)

- PASS: user JAR exists and SHA-256 is locked.
- PASS: fork and official upstream histories contain baseline `cb56760`.
- PASS: original metadata identifies version 1.1.5 and NeoForge 1.21.1.
- PASS: 599 resources match byte-for-byte.
- PASS: ten text resources match after line-ending/final-newline normalisation.
- PASS: all 251 effective top-level source classes are present in the JAR.
- PASS: the two source paths without class output contain only commented class
  bodies.
- PASS: atmospheric pressure bytecode matches the `cb56760` source behaviour.
- PASS: Sable and Simulated formal repositories were inspected read-only and
  their exact candidate artifacts were hashed.
- REJECTED: clean candidate NeoForge build. Dependency resolution stopped at a
  CurseMaven TLS handshake for optional Create Industry compile input before
  Java compilation. This is not recorded as a build pass or source failure.

## Port validation

No target-platform compilation or runtime validation has been completed yet.
Future entries must distinguish compile, automated tests, client launch,
dedicated-server launch, gameplay parity, persistence and compatibility matrix
results.
