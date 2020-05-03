package fr.flowarg.starcraft.common.network.packets;

import fr.flowarg.starcraft.common.capabilities.force.ForceCapability;
import fr.flowarg.starcraft.common.capabilities.force.IForce;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncForcePacket
{
    private final int force;

    public SyncForcePacket(IForce instance)
    {
        this.force = instance.getForce();
    }

    public SyncForcePacket(int forceIn)
    {
        this.force = forceIn;
    }

    public static void encode(SyncForcePacket pck, PacketBuffer buf)
    {
        buf.writeInt(pck.force);
    }

    public static SyncForcePacket decode(PacketBuffer buf)
    {
        return new SyncForcePacket(buf.readInt());
    }

    public static void handle(SyncForcePacket pck, Supplier<NetworkEvent.Context> ctxSupplier)
    {
        if (ctxSupplier.get().getDirection().getReceptionSide() == LogicalSide.CLIENT)
            ctxSupplier.get().enqueueWork(() -> handleClientUpdate(pck));
        ctxSupplier.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void handleClientUpdate(SyncForcePacket pck)
    {
        final ClientPlayerEntity player = Minecraft.getInstance().player;
        player.getCapability(ForceCapability.FORCE_CAPABILITY).ifPresent(iForce -> iForce.setForce(pck.force));
    }
}
