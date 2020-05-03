package fr.flowarg.starcraft.common.capabilities.force;

import fr.flowarg.starcraft.common.network.StarCraftNetwork;
import fr.flowarg.starcraft.common.network.packets.SyncForcePacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.PacketDistributor;

public class PlayerForceHolder extends DefaultForceHolder
{
    private final ServerPlayerEntity player;

    public PlayerForceHolder(ServerPlayerEntity player)
    {
        this.player = player;
    }

    @Override
    public void setForce(int force)
    {
        super.setForce(force);
        if (this.player.connection != null)
            this.player.getCapability(ForceCapability.FORCE_CAPABILITY).ifPresent(iForce -> StarCraftNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> this.player), new SyncForcePacket(iForce)));
    }
}
