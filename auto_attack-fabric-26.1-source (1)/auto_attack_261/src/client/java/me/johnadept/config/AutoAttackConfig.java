package me.johnadept.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AutoAttackConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("auto_attack.json");
    private static AutoAttackConfig INSTANCE;

    // General
    public boolean enableMod = true;
    public boolean disableOnLowDurability = false;
    public int durabilityThreshold = 5;
    public boolean attackNonHostile = false;
    public boolean protectTamedMobs = true;
    public boolean attackNonLiving = false;

    // Rotation
    public int rotationAngle = 90;
    public float rotationSpeed = 1.0f;
    public boolean autoAlignYaw = true;
    public int autoAlignYawOffset = 0;

    // Display
    public MessageDisplayMode displayMode = MessageDisplayMode.ACTION_BAR;

    // Entity lists
    public List<String> entityWhitelist = new ArrayList<>();
    public List<String> entityBlacklist = new ArrayList<>();

    public static AutoAttackConfig get() {
        if (INSTANCE == null) {
            load();
        }
        return INSTANCE;
    }

    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                INSTANCE = GSON.fromJson(reader, AutoAttackConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
                INSTANCE = new AutoAttackConfig();
            }
        } else {
            INSTANCE = new AutoAttackConfig();
            save();
        }
    }

    public static void save() {
        try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
            GSON.toJson(INSTANCE, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
