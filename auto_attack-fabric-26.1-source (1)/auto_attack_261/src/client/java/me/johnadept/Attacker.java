package me.johnadept;

import me.johnadept.config.AutoAttackConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.MaceItem;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class Attacker {

    public static void tryAttack(Minecraft mc, AutoAttackConfig config) {
        LocalPlayer player = mc.player;
        if (player == null) return;

        // Check attack cooldown - only attack when indicator is full (1.0)
        if (player.getAttackStrengthScale(0.0f) < 1.0f) return;

        HitResult hit = mc.hitResult;
        if (hit == null || hit.getType() != HitResult.Type.ENTITY) return;

        EntityHitResult entityHit = (EntityHitResult) hit;
        Entity entity = entityHit.getEntity();

        // Get the entity's resource location ID
        ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        String idStr = id.toString();

        // Blacklist takes priority over everything except whitelist
        if (config.entityBlacklist.contains(idStr)) return;

        // Whitelist overrides all other restrictions
        boolean whitelisted = config.entityWhitelist.contains(idStr);

        if (!whitelisted) {
            // Don't attack non-living entities unless configured
            if (!(entity instanceof LivingEntity) && !config.attackNonLiving) return;

            // Don't attack players
            if (entity instanceof Player) return;

            // Don't attack tamed animals belonging to the player
            if (config.protectTamedMobs && entity instanceof TamableAnimal tamable) {
                if (player.getUUID().equals(tamable.getOwnerUUID())) return;
            }

            // Don't attack non-hostile mobs unless configured
            if (!(entity instanceof Enemy) && !config.attackNonHostile) return;
        }

        // Check if player is shielding
        if (isShielding(player)) return;

        // Check weapon durability
        ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (config.disableOnLowDurability && isWeapon(mainHand)) {
            int durability = mainHand.getMaxDamage() - mainHand.getDamageValue();
            if (durability <= config.durabilityThreshold) return;
        }

        mc.gameMode.attack(player, entity);
        player.swing(InteractionHand.MAIN_HAND);
    }

    private static boolean isWeapon(ItemStack stack) {
        return stack.getItem() instanceof SwordItem
                || stack.getItem() instanceof AxeItem
                || stack.getItem() instanceof TridentItem
                || stack.getItem() instanceof MaceItem;
    }

    private static boolean isShielding(LocalPlayer player) {
        return player.isUsingItem()
                && player.getUseItem().getItem() instanceof net.minecraft.world.item.ShieldItem;
    }
}
