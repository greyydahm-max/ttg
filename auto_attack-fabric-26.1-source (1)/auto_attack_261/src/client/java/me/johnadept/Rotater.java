package me.johnadept;

import me.johnadept.config.AutoAttackConfig;
import me.johnadept.config.MessageDisplayMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;

public class Rotater {

    private float currentYaw = 0.0f;
    private float rotationProgress = 0.0f;
    private boolean rotatingRight = true;

    public void handleKeyPress(Minecraft mc) {
        LocalPlayer player = mc.player;
        if (player == null) return;

        AutoAttackConfig config = AutoAttackConfig.get();
        Direction facing = player.getDirection();

        // Only allow horizontal directions
        if (facing == Direction.UP || facing == Direction.DOWN) {
            sendMessage(mc, config,
                    Component.translatable("gui.auto_attack.rotationModePrefix",
                            Component.translatable("gui.auto_attack.invalidFacing")));
            return;
        }

        AutoAttackClient.rotationModeEnabled = !AutoAttackClient.rotationModeEnabled;

        if (AutoAttackClient.rotationModeEnabled) {
            // Auto-align yaw to nearest cardinal direction if configured
            if (config.autoAlignYaw) {
                float yaw = snapToCardinal(facing, config.autoAlignYawOffset);
                player.setYRot(yaw);
                currentYaw = yaw;
            } else {
                currentYaw = player.getYRot();
            }
            rotationProgress = 0.0f;

            String directionKey = "gui.auto_attack.direction." + facing.getName();
            sendMessage(mc, config,
                    Component.translatable("gui.auto_attack.rotationModePrefix",
                            Component.translatable(directionKey)));
        } else {
            sendMessage(mc, config,
                    Component.translatable("gui.auto_attack.rotationModePrefix",
                            Component.translatable("gui.auto_attack.disabled")));
        }
    }

    public void tick(LocalPlayer player, AutoAttackConfig config) {
        if (!AutoAttackClient.rotationModeEnabled || player == null) return;

        float angle = config.rotationAngle;
        float speed = config.rotationSpeed;

        float targetYaw = rotatingRight ? currentYaw + angle : currentYaw - angle;
        float newYaw = lerp(player.getYRot(), targetYaw, speed * 0.1f);
        player.setYRot(newYaw);

        rotationProgress += speed * 0.1f;
        if (rotationProgress >= 1.0f) {
            rotationProgress = 0.0f;
            currentYaw = rotatingRight ? currentYaw + angle : currentYaw - angle;
            rotatingRight = !rotatingRight;
        }
    }

    private static float snapToCardinal(Direction facing, int offset) {
        return switch (facing) {
            case NORTH -> -180.0f + offset;
            case SOUTH -> 0.0f + offset;
            case EAST -> -90.0f + offset;
            case WEST -> 90.0f + offset;
            default -> 0.0f;
        };
    }

    private static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
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
