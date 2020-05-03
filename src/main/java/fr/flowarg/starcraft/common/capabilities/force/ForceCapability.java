package fr.flowarg.starcraft.common.capabilities.force;

import fr.flowarg.starcraft.Main;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.WeakHashMap;

@EventBusSubscriber(modid = Main.MODID, bus = Bus.FORGE)
public class ForceCapability
{
    @CapabilityInject(IForce.class)
    public static final  Capability<IForce>  FORCE_CAPABILITY = null;

    public static final ResourceLocation FORCE_CAP_KEY = new ResourceLocation(Main.MODID, "force");
    private static final Map<PlayerEntity, IForce> INVALIDATED_CAPS = new WeakHashMap<>();

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IForce.class, new DefaultForceStorage(), DefaultForceHolder::new);
    }

    @SubscribeEvent
    public static void attachToPlayers(AttachCapabilitiesEvent<Entity> event)
    {
        IForce holder;
        if (event.getObject() instanceof PlayerEntity)
        {
            if (event.getObject() instanceof ServerPlayerEntity)
            {
                holder = new PlayerForceHolder((ServerPlayerEntity)event.getObject());
            }
            else
            {
                holder = FORCE_CAPABILITY.getDefaultInstance();
            }

            final PlayerForceWrapper wrapper = new PlayerForceWrapper(holder);
            event.addCapability(FORCE_CAP_KEY, wrapper);
            event.addListener(() -> wrapper.getCapability(FORCE_CAPABILITY).ifPresent(cap -> INVALIDATED_CAPS.put((PlayerEntity)event.getObject(), cap)));
        }
    }

    public static class DefaultForceStorage implements Capability.IStorage<IForce>
    {
        @Nullable
        @Override
        public INBT writeNBT(Capability<IForce> capability, IForce instance, Direction side)
        {
            return IntNBT.valueOf(instance.getForce());
        }

        @Override
        public void readNBT(Capability<IForce> capability, IForce instance, Direction side, INBT nbt)
        {
            instance.setForce(((IntNBT)nbt).getInt());
        }
    }
}
