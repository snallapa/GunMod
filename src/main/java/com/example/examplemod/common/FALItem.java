package com.example.examplemod.common;

import com.example.examplemod.ShootersMod;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class FALItem extends ShootableItem {
    public static final float INACCURACY = 6;

    public FALItem(Properties props) {
        super(props);
        setRegistryName("fal");
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    public void shoot(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            boolean flag = playerentity.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemstack = playerentity.findAmmo(stack);
            int charge = 20;
            charge = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, charge, !itemstack.isEmpty() || flag);
            if (charge < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(ShootersMod.BULLET);
                }

                float bulletVelocity = getBulletVelocity(charge);
                if (!((double)bulletVelocity < 0.1D)) {
                    boolean isInfinite = playerentity.abilities.isCreativeMode || (itemstack.getItem() instanceof BulletItem && ((BulletItem)itemstack.getItem()).isInfinite(itemstack, stack, playerentity));
                    if (!worldIn.isRemote) {
                        BulletItem arrowitem = (BulletItem)(itemstack.getItem() instanceof BulletItem ? itemstack.getItem() : ShootersMod.BULLET);
                        AbstractArrowEntity abstractarrowentity = arrowitem.createBullet(worldIn, itemstack, playerentity);
                        abstractarrowentity.func_234612_a_(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, bulletVelocity * 3.0F, INACCURACY);
                        abstractarrowentity.setDamage(1.0D);
                        abstractarrowentity.setKnockbackStrength(2);
                        //repairing and stuff
                        stack.damageItem(1, playerentity, (player) -> player.sendBreakAnimation(playerentity.getActiveHand()));

                        worldIn.addEntity(abstractarrowentity);
                    }

                    worldIn.playSound((PlayerEntity)null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + bulletVelocity * 0.5F);
                    if (!isInfinite && !playerentity.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            playerentity.inventory.deleteStack(itemstack);
                        }
                    }

                    playerentity.addStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    /**
     * Gets the velocity of the arrow entity from the bow's charge
     */
    public static float getBulletVelocity(int charge) {
        float f = (float)charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return (stack) -> stack.getItem().equals(ShootersMod.BULLET);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        boolean hasArrows = !playerIn.findAmmo(itemstack).isEmpty();

        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, hasArrows);
        if (ret != null) return ret;

        if (!playerIn.abilities.isCreativeMode && !hasArrows) {
            return ActionResult.resultFail(itemstack);
        } else {
            playerIn.setActiveHand(handIn);
            shoot(itemstack, worldIn, playerIn);
            return ActionResult.resultConsume(itemstack);
        }
    }

    @Override
    public int func_230305_d_() {
        return 15;
    }
}
