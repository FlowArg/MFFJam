package fr.flowarg.starcraft.client.creativetabs;

import fr.flowarg.starcraft.Main.RegistryHandler;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StarCraftItemGroup extends ItemGroup
{
    public StarCraftItemGroup()
    {
        super("starcraft_items");
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(RegistryHandler.GREEN_LASER);
    }
}
