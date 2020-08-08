package com.example.examplemod.client;

import com.example.examplemod.common.BulletEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class BulletRenderer extends ArrowRenderer<BulletEntity> {
    public static final ResourceLocation RES_FAL_BULLET = new ResourceLocation("shooters", "textures/entity/projectiles/fal_bullet.png");
    private static final Logger LOGGER = LogManager.getLogger();

    public BulletRenderer(EntityRendererManager manager) {
        super(manager);
    }

    /**
     * Returns the location of an entity's texture.
     */
    @Override
    public ResourceLocation getEntityTexture(BulletEntity entity) {
        return RES_FAL_BULLET;
    }
}
