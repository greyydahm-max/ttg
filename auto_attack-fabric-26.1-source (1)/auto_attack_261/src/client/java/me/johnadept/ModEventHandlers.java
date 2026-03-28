package me.johnadept;

import me.johnadept.config.AutoAttackConfig;
import me.johnadept.config.MessageDisplayMode;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ModEventHandlers {

    private static final Rotater rotater = new Rotater();

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(ModEventHandlers::onClientTick);
    }

    private static void onClientTick(Minecraft mc) {
        LocalPlayer player = mc.player;
        AutoAttackConfig config = AutoAttackConfig.get();

        // Handle toggle attack key
        while (ModKeyBindings.toggleAttack.consumeClick()) {
            if (!config.enableMod) return;
            AutoAttackClient.autoAttackEnabled = !AutoAttackClient.autoAttackEnabled;

            Component status = AutoAttackClient.autoAttackEnabled
                    ? Component.translatable("gui.auto_attack.enabled")
                    : Component.translatable("gui.auto_attack.disabled");

            Component message = Component.translatable("gui.auto_attack.autoAttackPrefix", status);
            sendMessage(mc, config, message);
        }

        // Handle toggle rotation key
        while (ModKeyBindings.toggleRotation.consumeClick()) {
            rotater.handleKeyPress(mc);
        }

        if (player == null || !config.enableMod || !AutoAttackClient.autoAttackEnabled) return;

        // Don't attack in spectator mode
        if (player.isSpectator()) {
            if (AutoAttackClient.autoAttackEnabled) {
                AutoAttackClient.autoAttackEnabled = false;
                sendMessage(mc, config,
                        Component.translatable("gui.auto_attack.autoAttackPrefix",
                                Component.translatable("gui.auto_attack.disabledDueSpectator")));
            }
            return;
        }

        // Tick rotation
        rotater.tick(player, config);

        // Attempt auto attack
        Attacker.tryAttack(mc, config);
    }

    private static void sendMessage(Minecraft mc, AutoAttackConfig config, Component message) {
        if (mc.player == null) return;
        if (config.displayMode == MessageDisplayMode.ACTION_BAR) {
            mc.player.displayClientMessage(message, true);
        } else {
            mc.player.sendSystemMessage(message);
        }
    }
}
