package fr.flowarg.starcraft;

import fr.flowarg.starcraft.client.creativetabs.StarCraftItemGroup;
import fr.flowarg.starcraft.common.items.LaserBaseItem;
import fr.flowarg.starcraft.common.items.LaserColor;
import fr.flowarg.starcraft.common.items.LaserItem;
import fr.flowarg.starcraft.common.materials.LaserTier;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Main.MODID)
public class Main
{
    public static final String    MODID      = "starcraft";
    public static final Logger    LOGGER     = LogManager.getLogger("StarCraft");
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
    }

    @EventBusSubscriber(modid = MODID, bus = Bus.MOD)
    public static class RegistryHandler
    {
        public static final IItemTier LASER_TIER       = new LaserTier();
        public static final Item      GREEN_LASER      = new LaserItem(LaserColor.GREEN);
        public static final Item      RED_LASER        = new LaserItem(LaserColor.RED);
        public static final Item      GREEN_LASER_BASE = new LaserBaseItem(LaserColor.GREEN);
        public static final Item      RED_LASER_BASE   = new LaserBaseItem(LaserColor.RED);

        @SubscribeEvent
        public static void onItemRegister(final RegistryEvent.Register<Item> event)
        {
            event.getRegistry().registerAll(
                    GREEN_LASER,
                    RED_LASER,
                    GREEN_LASER_BASE,
                    RED_LASER_BASE
            );
        }
    }
}
