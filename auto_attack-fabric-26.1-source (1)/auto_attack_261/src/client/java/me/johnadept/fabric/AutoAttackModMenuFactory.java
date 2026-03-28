package me.johnadept.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.johnadept.AutoAttackVersionDifferenceManager;
import me.johnadept.config.ConfigScreen;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.Screen;

public class AutoAttackModMenuFactory implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricLoader.getInstance().isModLoaded("cloth-config")) {
            ConfigScreen configScreen = new ConfigScreen();
            return configScreen::createScreen;
        }
        return parent -> null;
    }
}
