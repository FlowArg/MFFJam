package fr.flowarg.starcraft.common.utils;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.apache.commons.lang3.tuple.Pair;

public class StarCraftConfig
{
    public static final ForgeConfigSpec CLIENT_SPECS;
    public static final Client CLIENT;

    static
    {
        final Pair<Client, ForgeConfigSpec> clientPair = new Builder().configure(Client::new);
        CLIENT_SPECS = clientPair.getRight();
        CLIENT = clientPair.getLeft();
    }

    public static class Client
    {
        private final BooleanValue showForceOnOverlay;
        private final BooleanValue showRealms;

        public Client(Builder builder)
        {
            builder.comment(" Welcome to the StarCraft Configuration !")
                   .push("client");

            this.showForceOnOverlay = builder.comment(" Define if the Force is displayed on the in-game overlay.")
            .define("showForceOnOverlay", true);

            this.showRealms = builder.comment(" Define if the Realms button is showed or not in the MainMenu.")
                                     .define("showRealms", false);

            builder.pop();
        }

        public BooleanValue canShowForceOnOverlay()
        {
            return this.showForceOnOverlay;
        }

        public BooleanValue canShowRealms()
        {
            return this.showRealms;
        }
    }
}
