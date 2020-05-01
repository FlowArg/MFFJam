package fr.flowarg.starcraft;

import fr.flowarg.starcraft.client.creativetabs.StarCraftItemGroup;
import fr.flowarg.starcraft.client.entities.YodaEntityRendererFactory;
import fr.flowarg.starcraft.client.guis.CustomInGameMenuScreen;
import fr.flowarg.starcraft.client.guis.CustomMainMenuScreen;
import fr.flowarg.starcraft.common.entities.YodaEntity;
import fr.flowarg.starcraft.common.items.LaserBaseItem;
import fr.flowarg.starcraft.common.items.LaserColor;
import fr.flowarg.starcraft.common.items.LaserItem;
import fr.flowarg.starcraft.common.items.YodaEntityEggItem;
import fr.flowarg.starcraft.common.items.armors.*;
import fr.flowarg.starcraft.common.materials.LaserTier;
import fr.flowarg.starcraft.common.materials.StormTrooperArmorMaterial;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

@Mod(Main.MODID)
public class Main
{
    public static final String MODID  = "starcraft";
    public static final Logger LOGGER = LogManager.getLogger("StarCraft");

    @OnlyIn(Dist.CLIENT)
    public static final ItemGroup ITEM_GROUP = new StarCraftItemGroup();

    public Main()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    }

    public void setupCommon(final FMLCommonSetupEvent event) { LOGGER.info("FML is loading StarCraft Mod !"); }

    @OnlyIn(Dist.CLIENT)
    public void setupClient(final FMLClientSetupEvent event)
    {
        LOGGER.info("FML is loading client part of StarCraft Mod !");
        RenderingRegistry.registerEntityRenderingHandler(RegistryHandler.YODA_ENTITY, new YodaEntityRendererFactory());
    }

    @EventBusSubscriber(modid = MODID, bus = Bus.MOD)
    public static class RegistryHandler
    {
        public static final IItemTier      LASER_TIER                   = new LaserTier();
        public static final IArmorMaterial STORM_TROOPER_ARMOR_MATERIAL = new StormTrooperArmorMaterial();
        public static final Item           GREEN_LASER                  = new LaserItem(LaserColor.GREEN);
        public static final Item           RED_LASER                    = new LaserItem(LaserColor.RED);
        public static final Item           GREEN_LASER_BASE             = new LaserBaseItem(LaserColor.GREEN);
        public static final Item           RED_LASER_BASE               = new LaserBaseItem(LaserColor.RED);
        public static final Item           YODA_SPAWN_EGG               = new YodaEntityEggItem();

        public static final AbstractRepairableArmorItem STORM_TROOPER_HELMET     = new StormTrooperHelmet();
        public static final AbstractRepairableArmorItem STORM_TROOPER_CHESTPLATE = new StormTrooperChestplate();
        public static final AbstractRepairableArmorItem STORM_TROOPER_LEGGINGS   = new StormTrooperLeggings();
        public static final AbstractRepairableArmorItem STORM_TROOPER_BOOTS      = new StormTrooperBoots();

        public static final SoundEvent YODA_SONGS                = new SoundEvent(new ResourceLocation(MODID, "yoda_songs"));
        public static final SoundEvent STORM_TROOPER_EQUIP_ARMOR = new SoundEvent(new ResourceLocation(MODID, "storm_trooper_equip_armor"));

        public static final EntityType<YodaEntity> YODA_ENTITY = EntityType.Builder.create(YodaEntity::new, EntityClassification.AMBIENT)
                                                                                   .size(0.6F, 1.8F)
                                                                                   .setShouldReceiveVelocityUpdates(false)
                                                                                   .immuneToFire()
                                                                                   .disableSerialization()
                                                                                   .build("yoda");

        @SubscribeEvent
        public static void onItemRegister(final RegistryEvent.Register<Item> event)
        {
            event.getRegistry().registerAll(
                    GREEN_LASER,
                    RED_LASER,
                    GREEN_LASER_BASE,
                    RED_LASER_BASE,
                    YODA_SPAWN_EGG,
                    STORM_TROOPER_HELMET,
                    STORM_TROOPER_CHESTPLATE,
                    STORM_TROOPER_LEGGINGS,
                    STORM_TROOPER_BOOTS
            );
        }

        @SubscribeEvent
        public static void onSoundEventRegister(final RegistryEvent.Register<SoundEvent> event)
        {
            event.getRegistry().registerAll(
                    YODA_SONGS.setRegistryName("yoda_songs"),
                    STORM_TROOPER_EQUIP_ARMOR.setRegistryName("storm_trooper_equip_armor")
            );
        }

        @SubscribeEvent
        public static void onEntityRegister(final RegistryEvent.Register<EntityType<?>> event)
        {
            event.getRegistry().register(YODA_ENTITY.setRegistryName(MODID, "yoda"));
        }
    }

    @EventBusSubscriber(modid = MODID, bus = Bus.MOD, value = Dist.CLIENT)
    public static class ClientEventHandler
    {
        @SubscribeEvent
        public static void onHandleItemColor(ColorHandlerEvent.Item event)
        {
            event.getItemColors().register((stack, i) -> 0x206e0e, RegistryHandler.YODA_SPAWN_EGG);
        }
    }

    @EventBusSubscriber(modid = MODID, bus = Bus.FORGE)
    public static class EventHandler
    {
        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void onPlayerInteractWithEntity(PlayerInteractEvent.EntityInteractSpecific event)
        {
            final World world = event.getWorld();
            if (event.getTarget() instanceof YodaEntity)
            {
                if (world.isRemote)
                {
                    final YodaEntity yoda = (YodaEntity)event.getTarget();
                    final double posX = yoda.getPosX();
                    final double posY = yoda.getPosY();
                    final double posZ = yoda.getPosZ();

                    world.playSound(posX, posY, posZ, RegistryHandler.YODA_SONGS, SoundCategory.AMBIENT, 10f, 1f, true);
                }
            }
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void onGuiOpenedEvent(GuiOpenEvent event)
        {
            if (event.getGui() != null)
            {
                if (event.getGui().getClass() == MainMenuScreen.class)
                {
                    event.setGui(new CustomMainMenuScreen(true));
                }
                else if (event.getGui().getClass() == IngameMenuScreen.class)
                {
                    event.setGui(new CustomInGameMenuScreen(!Minecraft.getInstance().isIntegratedServerRunning()));
                }
            }
        }
    }
}
