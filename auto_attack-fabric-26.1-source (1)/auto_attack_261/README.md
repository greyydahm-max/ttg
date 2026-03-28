# Auto Attack — Ported to Minecraft 26.1

Original mod by **John Adept** (MIT License).  
Ported to Minecraft Java Edition 26.1 ("Tiny Takeover").

---

## What changed in this port

| Area | Old (3.0.1 / MC 1.21.1) | New (3.1.0 / MC 26.1) |
|---|---|---|
| Java version | Java 21 | **Java 25** |
| Fabric Loader | ≥ 0.18.2 | **≥ 0.18.4** |
| Minecraft target | 1.21.1 | **26.1** |
| Mixin compat level | JAVA_21 | **JAVA_25** |
| Class names | Obfuscated (`class_310` etc.) | **Unobfuscated** (Mojang names) |
| Mappings | Intermediary | **Official Mojang** |

---

## Requirements to build

- **Java 25** JDK — download from https://adoptium.net
- **Gradle 9.4+** (the wrapper will download it automatically)

---

## How to build

```bash
# Clone or place this folder somewhere, then:
cd auto_attack_261

# On Linux/Mac:
./gradlew build

# On Windows:
gradlew.bat build
```

The compiled `.jar` will appear in `build/libs/`.  
Use the file **without** `-sources` in its name.

---

## How to install

1. Install [Fabric Loader 0.18.4+](https://fabricmc.net/use/) for Minecraft 26.1
2. Install [Fabric API](https://modrinth.com/mod/fabric-api) for 26.1
3. Drop the built `.jar` into your `.minecraft/mods/` folder
4. *(Optional)* Install [Cloth Config](https://modrinth.com/mod/cloth-config) and [ModMenu](https://modrinth.com/mod/modmenu) for in-game config GUI

---

## Optional dependencies

- **Cloth Config** — enables the in-game config screen
- **ModMenu** — adds the mod to the mods list with a config button

Without these, the mod still works — just configure via `config/auto_attack.json`.

---

## Default keybinds

| Key | Action |
|---|---|
| `R` | Toggle Auto Attack on/off |
| `T` | Toggle Rotation Mode on/off |

Both can be rebound in Controls settings.
