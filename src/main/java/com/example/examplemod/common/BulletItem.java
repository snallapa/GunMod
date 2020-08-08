package com.example.examplemod.common;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BulletItem extends Item {
    public BulletItem(Properties properties) {
        super(properties);
        setRegistryName("fal_bullet");
    }

    public AbstractArrowEntity createBullet(World worldIn, ItemStack stack, LivingEntity shooter) {
        return new BulletEntity(worldIn, shooter);
    }

    public boolean isInfinite(ItemStack stack, ItemStack gun, net.minecraft.entity.player.PlayerEntity player) {
        int enchant = net.minecraft.enchantment.EnchantmentHelper.getEnchantmentLevel(net.minecraft.enchantment.Enchantments.INFINITY, gun);
        return enchant > 0 && this.getClass() == BulletItem.class;
    }
}
