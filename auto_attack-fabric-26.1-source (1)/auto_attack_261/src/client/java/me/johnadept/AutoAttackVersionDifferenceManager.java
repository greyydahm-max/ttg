package me.johnadept;

import net.minecraft.client.gui.components.EditBox;

public abstract class AutoAttackVersionDifferenceManager {

    private static final AutoAttackVersionDifferenceManager INSTANCE =
            new me.johnadept.fabric.AutoAttackVersionDifferenceManagerImpl();

    public static AutoAttackVersionDifferenceManager getInstance() {
        return INSTANCE;
    }

    public abstract String getSuggestion(EditBox editBox);

    public abstract boolean isModLoaded(String modId);

    public abstract String getModJarFileName(String modId);
}
