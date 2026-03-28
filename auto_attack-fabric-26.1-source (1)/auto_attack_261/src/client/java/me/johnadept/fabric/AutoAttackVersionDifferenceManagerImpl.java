package me.johnadept.fabric;

import me.johnadept.AutoAttackVersionDifferenceManager;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.components.EditBox;

import java.nio.file.Path;

public class AutoAttackVersionDifferenceManagerImpl extends AutoAttackVersionDifferenceManager {

    @Override
    public String getSuggestion(EditBox editBox) {
        return editBox.suggestion;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public String getModJarFileName(String modId) {
        return FabricLoader.getInstance().getModContainer(modId)
                .map(container -> {
                    Path path = container.getOrigin().getPaths().get(0);
                    return path.getFileName().toString();
                })
                .orElse("");
    }
}
