# Cubicolor

A modern, type-safe color management system for Java applications with first-class support for Minecraft/Bukkit plugins.

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

Modular color and typography theming library for Java and Minecraft.

## Features

- **Semantic color schemes** - Use meaningful color roles (PRIMARY, SECONDARY, ERROR, SUCCESS, etc.) instead of hardcoded hex values
- **Typography system** - Material Design-inspired text styling with built-in scales
- **Bukkit/Paper integration** - First-class MiniMessage support with automatic ColorScheme tag resolution
- **JSON theme loading** - Load and hot-reload themes from JSON files
- **Multi-plugin management** - Share themes across plugins with namespace isolation
- **Type-safe** - Compile-time safety with full Java type checking
- **Dark mode aware** - Built-in support for automatic dark/light theme switching

## Modules

- **cubicolor-api** - Core interfaces
- **cubicolor-core** - Default implementations
- **cubicolor-text** - Typography and text styling
- **cubicolor-bukkit** - Minecraft/Bukkit integration
- **cubicolor-exporter** - JSON theme loading
- **cubicolor-manager** - Multi-plugin scheme management

## Installation

### Gradle

```gradle
repositories {
    maven { url 'https://raw.githubusercontent.com/cubizor/Cubicolor/maven-repo/' }
}

dependencies {
    implementation 'net.cubizor.cubicolor:cubicolor-core:1.0.0'
    implementation 'net.cubizor.cubicolor:cubicolor-text:1.0.0'      // Optional
    implementation 'net.cubizor.cubicolor:cubicolor-bukkit:1.0.0'    // Optional
    implementation 'net.cubizor.cubicolor:cubicolor-exporter:1.0.0'  // Optional
    implementation 'net.cubizor.cubicolor:cubicolor-manager:1.0.0'   // Optional
}
```

Requires no credentials. Use this repository for anything that resolves Cubicolor **at runtime**
(for example a Paper plugin declaring it through `MavenLibraryResolver`) — GitHub Packages demands a
token even for public packages, so a game server cannot read from it.

The GitHub Packages coordinates still exist and stay in sync:

```gradle
repositories {
    maven {
        url 'https://maven.pkg.github.com/cubizor/Cubicolor'
        credentials {
            username = project.findProperty("gpr.user") ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.key") ?: System.getenv("GITHUB_TOKEN")
        }
    }
}
```

## Documentation

- **[Getting Started](docs/getting-started.md)** - Installation and basic usage
- **[Modules](docs/modules.md)** - Module descriptions and architecture
- **[Bukkit MiniMessage Integration](docs/bukkit-minimessage.md)** - Complete guide for Bukkit/Paper plugins
- **[JSON Themes](docs/json-themes.md)** - Loading themes from JSON files
- **[Manager](docs/manager.md)** - Multi-plugin ColorScheme management

## Requirements

- Java 21
- Gradle 7.0+

## License

MIT License
