package me.johnadept;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class ModKeyBindings {
    public static final String CATEGORY_AUTO_ATTACK = "key.category.auto_attack.auto_attack";

    public static KeyMapping toggleAttack;
    public static KeyMapping toggleRotation;

    public static void register() {
        toggleAttack = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.auto_attack.toggleAttack",
                GLFW.GLFW_KEY_R,
                CATEGORY_AUTO_ATTACK
        ));
        toggleRotation = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.auto_attack.toggleRotation",
                GLFW.GLFW_KEY_T,
                CATEGORY_AUTO_ATTACK
        ));
    }
}
