package fr.flowarg.starcraft.common.network;

import fr.flowarg.starcraft.Main;
import fr.flowarg.starcraft.common.network.packets.SyncForcePacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class StarCraftNetwork
{
    public static final String PROTOCOL_VERSION = String.valueOf(1);

    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(Main.MODID, "starcraft_channel"))
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .simpleChannel();

    public static void registerPackets()
    {
        CHANNEL.messageBuilder(SyncForcePacket.class, 0)
               .encoder(SyncForcePacket::encode)
               .decoder(SyncForcePacket::decode)
               .consumer(SyncForcePacket::handle)
               .add();
    }
}
