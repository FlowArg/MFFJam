package fr.flowarg.starcraft.common.utils;

import fr.flowarg.starcraft.Main;
import net.minecraft.util.ResourceLocation;

public interface IHasLocation
{
    default ResourceLocation getLocation(String name) { return new ResourceLocation(Main.MODID, name); }
}
