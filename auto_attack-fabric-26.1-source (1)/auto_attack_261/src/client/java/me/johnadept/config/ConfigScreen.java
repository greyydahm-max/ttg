package me.johnadept.config;

import me.johnadept.AutoAttackVersionDifferenceManager;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;

public class ConfigScreen implements ConfigScreenProvider {

    @Override
    public Screen createScreen(Screen parent) {
        AutoAttackConfig config = AutoAttackConfig.get();
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("menu.auto_attack.config.title"))
                .setSavingRunnable(AutoAttackConfig::save);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory general = builder.getOrCreateCategory(
                Component.translatable("menu.auto_attack.config.category.general"));

        general.addEntry(entryBuilder.startBooleanToggle(
                Component.translatable("menu.auto_attack.config.enableMod"), config.enableMod)
                .setDefaultValue(true)
                .setSaveConsumer(val -> config.enableMod = val)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(
                Component.translatable("menu.auto_attack.config.disableOnLowDurability"), config.disableOnLowDurability)
                .setDefaultValue(false)
                .setSaveConsumer(val -> config.disableOnLowDurability = val)
                .build());

        general.addEntry(entryBuilder.startIntField(
                Component.translatable("menu.auto_attack.config.durabilityThreshold"), config.durabilityThreshold)
                .setDefaultValue(5)
                .setTooltip(Component.translatable("menu.auto_attack.config.durabilityThreshold.tooltip"))
                .setSaveConsumer(val -> config.durabilityThreshold = val)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(
                Component.translatable("menu.auto_attack.config.attackNonHostile"), config.attackNonHostile)
                .setDefaultValue(false)
                .setSaveConsumer(val -> config.attackNonHostile = val)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(
                Component.translatable("menu.auto_attack.config.protectTamedMobs"), config.protectTamedMobs)
                .setDefaultValue(true)
                .setSaveConsumer(val -> config.protectTamedMobs = val)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(
                Component.translatable("menu.auto_attack.config.attackNonLiving"), config.attackNonLiving)
                .setDefaultValue(false)
                .setSaveConsumer(val -> config.attackNonLiving = val)
                .build());

        general.addEntry(entryBuilder.startIntField(
                Component.translatable("menu.auto_attack.config.rotationAngle"), config.rotationAngle)
                .setDefaultValue(90)
                .setSaveConsumer(val -> config.rotationAngle = val)
                .build());

        general.addEntry(entryBuilder.startFloatField(
                Component.translatable("menu.auto_attack.config.rotationSpeed"), config.rotationSpeed)
                .setDefaultValue(1.0f)
                .setSaveConsumer(val -> config.rotationSpeed = val)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(
                Component.translatable("menu.auto_attack.config.autoAlignYaw"), config.autoAlignYaw)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("menu.auto_attack.config.autoAlignYaw.tooltip"))
                .setSaveConsumer(val -> config.autoAlignYaw = val)
                .build());

        general.addEntry(entryBuilder.startIntField(
                Component.translatable("menu.auto_attack.config.autoAlignYawOffset"), config.autoAlignYawOffset)
                .setDefaultValue(0)
                .setTooltip(Component.translatable("menu.auto_attack.config.autoAlignYawOffset.tooltip"))
                .setSaveConsumer(val -> config.autoAlignYawOffset = val)
                .build());

        general.addEntry(entryBuilder.startEnumSelector(
                Component.translatable("menu.auto_attack.config.display_mode"),
                MessageDisplayMode.class, config.displayMode)
                .setDefaultValue(MessageDisplayMode.ACTION_BAR)
                .setTooltip(Component.translatable("menu.auto_attack.config.display_mode.tooltip"))
                .setSaveConsumer(val -> config.displayMode = val)
                .build());

        general.addEntry(entryBuilder.startStrList(
                Component.translatable("menu.auto_attack.config.whitelist"),
                config.entityWhitelist)
                .setDefaultValue(new ArrayList<>())
                .setTooltip(Component.translatable("menu.auto_attack.config.whitelist.tooltip"))
                .setSaveConsumer(val -> config.entityWhitelist = val)
                .build());

        general.addEntry(entryBuilder.startStrList(
                Component.translatable("menu.auto_attack.config.blacklist"),
                config.entityBlacklist)
                .setDefaultValue(new ArrayList<>())
                .setTooltip(Component.translatable("menu.auto_attack.config.blacklist.tooltip"))
                .setSaveConsumer(val -> config.entityBlacklist = val)
                .build());

        return builder.build();
    }
}
