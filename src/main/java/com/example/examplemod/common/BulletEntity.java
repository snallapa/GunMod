package com.example.examplemod.common;

import com.example.examplemod.ShootersMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class BulletEntity extends AbstractArrowEntity {

    public BulletEntity(EntityType<? extends BulletEntity> type, World worldIn) {
        super(type, worldIn);
        this.pickupStatus = PickupStatus.DISALLOWED;
    }

    public BulletEntity(FMLPlayMessages.SpawnEntity packet, World worldIn) {
        super(ShootersMod.BULLET_ENTITY, worldIn);
        this.pickupStatus = PickupStatus.DISALLOWED;
    }

    public BulletEntity(World worldIn, LivingEntity shooter) {
        super(ShootersMod.BULLET_ENTITY, shooter, worldIn);
        this.pickupStatus = PickupStatus.DISALLOWED;
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ShootersMod.BULLET);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
