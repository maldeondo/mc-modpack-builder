# mc-modpack-builder

A CLI-based modpack automation tool built with Java by [@maldeondo](https://github.com/maldeondo).
Designed to streamline the creation and management of custom modpacks for Minecraft server administrators.

## Overview

**mc-modpack-builder** is a Java-based CLI application that automates the creation and organization of Minecraft modpacks.

It is designed primarily for server administrators who need to:

- Separate client-only and server-only mods

- Maintain consistency between environments

- Generate structured modpack directories and compressed files automatically

- Reduce manual errors during modpack preparation

> See the `dev/ROADMAP.md` file to check development status.

---

## Getting Started

This is a **CLI-based** tool intended for local execution.

The easiest way to use it is to download a `.jar` file from the [releases](https://github.com/maldeondo/mc-modpack-builder/releases) page and run it with:
- `java -jar ./mc-modpack-builder.jar`

## Build

Clone the repo:
- `git clone https://github.com/maldeondo/mc-modpack-builder.git`
- `cd mc-modpack-builder`

Build fat-JAR using Gradle:
- `./gradlew jar`

---

## Tech Stack

- Language: Java

- Build Tool & Dependency Management: Gradle

- Interface: Command-Line (CLI)

### Dependencies

- JSON Parsing: [GSON](https://github.com/google/gson)

> Gradle handles all dependencies and build configuration. Project requirements and dependency versions can be found in the build.gradle file.

## License

This project is licensed under the Apache License 2.0.

See the `LICENSE` file for full license text and details.

---

> _“Simplicity is prerequisite for reliability.”_ 
> — Edsger W. Dijkstra
