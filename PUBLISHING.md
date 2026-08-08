# Publishing

Releases are fully automated. A push to `main` runs `.github/workflows/workflow.yml`, which runs the
tests and then semantic-release. When the commits since the last tag warrant a release,
semantic-release bumps `gradle.properties`, writes the changelog, tags, and invokes the Gradle
`publishRelease` task.

`publishRelease` publishes every module to two places:

- **Maven Central** (`net.cubizor.cubicolor`) — the canonical, anonymously readable location.
- **GitHub Packages** — kept in sync, but requires a token even for public packages.

## Why not plain `publish`

Gradle's `publish` only *stages* a Central deployment; `publishAndReleaseToMavenCentral` is what
actually releases it. `publishRelease` (defined in the root `build.gradle.kts`) depends on both that
task and the GitHub Packages task for every module. All modules stage into one bundle, so a run
produces a single Central deployment rather than six.

## Central requirements

Maven Central rejects a deployment unless it has all of:

- sources and javadoc jars — configured via `JavaLibrary(JavadocJar.Javadoc(), sourcesJar = true)`
- a PGP signature for every artifact — `signAllPublications()`
- POM `name`, `description`, `url`, `licenses`, `developers`, `scm`

## CI secrets

| Secret | Maps to | Purpose |
| --- | --- | --- |
| `MVN_CENTRAL_USERNAME` | `ORG_GRADLE_PROJECT_mavenCentralUsername` | Central Portal user token |
| `MVN_CENTRAL_PASSWORD` | `ORG_GRADLE_PROJECT_mavenCentralPassword` | Central Portal user token |
| `GPG_PRIVATE_KEY` | `ORG_GRADLE_PROJECT_signingInMemoryKey` | ASCII-armored private key |
| `GPG_PASSPHRASE` | `ORG_GRADLE_PROJECT_signingInMemoryKeyPassword` | Key passphrase |

The secret names are arbitrary; the `ORG_GRADLE_PROJECT_*` names are not — that prefix is how Gradle
turns an environment variable into a project property, and the property names are fixed by the
publish plugin.

Portal tokens are generated at <https://central.sonatype.com> under Account → Generate User Token.

## Signing key

RSA 4096, no expiry, published to `keys.openpgp.org` and `keyserver.ubuntu.com`. It is shared by
every Cubizor repository, not specific to Cubicolor — Central binds a key to nothing, it only
checks that signatures verify against a key published on a keyserver.

```
Cubizor (Maven Central signing key) <vulzen@vulzen.dev>
8E5D 6182 7D96 1D9D 818D  BCC5 38C4 B9CE E555 C362
```

The private key, passphrase, and revocation certificate are **not** in this repo. If they are lost,
generate a new key, publish it to both keyservers, and update the two GPG secrets — Central does not
care that the key changed, only that signatures verify against a published key.

## Publishing locally

```bash
export ORG_GRADLE_PROJECT_signingInMemoryKey="$(cat private-key.asc)"
export ORG_GRADLE_PROJECT_signingInMemoryKeyPassword='...'
./gradlew publishToMavenLocal
```

This exercises signing, sources, and javadoc without touching Central.

## Deprecated: the `maven-repo` branch

Before Central, `publish` also wrote a plain Maven layout that CI mirrored onto the `maven-repo`
branch, served anonymously over `raw.githubusercontent.com`. That existed purely to work around
GitHub Packages requiring a token. Central makes it redundant. The branch stays online for existing
consumers but receives no new versions.
