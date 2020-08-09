package com.nallapareddy.shootersmod;

import com.nallapareddy.shootersmod.client.BulletRenderer;
import com.nallapareddy.shootersmod.common.BulletEntity;
import com.nallapareddy.shootersmod.common.BulletItem;
import com.nallapareddy.shootersmod.common.FALItem;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("shooters")
public class ShootersMod
{
    public static Item BULLET;

    public static Item FAL;

    public static EntityType<BulletEntity> BULLET_ENTITY = EntityType.Builder.<BulletEntity>create(BulletEntity::new, EntityClassification.MISC)
            .setCustomClientFactory(BulletEntity::new)
            .size(0.24F, 0.25F)
            .func_233606_a_(4)
            .func_233608_b_(20)
            .build("bullet");

    public ShootersMod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        RenderingRegistry.registerEntityRenderingHandler(BULLET_ENTITY, BulletRenderer::new);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
    }

    private void processIMC(final InterModProcessEvent event)
    {
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onItemRegister(final RegistryEvent.Register<Item> itemRegistryEvent) {
            // register a new block here
            BULLET = new BulletItem(new Item.Properties()
                    .group(ItemGroup.COMBAT)
                    .maxStackSize(64));
            FAL = new FALItem(new Item.Properties()
                    .group(ItemGroup.COMBAT)
                    .maxStackSize(1));
            itemRegistryEvent.getRegistry().register(FAL);
            itemRegistryEvent.getRegistry().register(BULLET);
        }

        @SubscribeEvent
        public static void onEntityTypeRegister(final RegistryEvent.Register<EntityType<?>> event) {
            EntityType<?> bullet_entity = BULLET_ENTITY.setRegistryName("bullet_entity");
            event.getRegistry().register(bullet_entity);
        }
    }
}
