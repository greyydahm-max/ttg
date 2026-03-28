package me.johnadept.fabric;

import me.johnadept.ModEventHandlers;
import me.johnadept.ModKeyBindings;
import me.johnadept.config.AutoAttackConfig;
import net.fabricmc.api.ClientModInitializer;

public class AutoAttackFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AutoAttackConfig.load();
        ModKeyBindings.register();
        ModEventHandlers.register();
    }
}
