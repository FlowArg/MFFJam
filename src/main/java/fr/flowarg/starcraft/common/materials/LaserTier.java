package fr.flowarg.starcraft.common.materials;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public class LaserTier implements IItemTier
{
    @Override
    public int getMaxUses()
    {
        return 150;
    }

    @Override
    public float getEfficiency()
    {
        return 6.42f;
    }

    @Override
    public float getAttackDamage()
    {
        return 14.9f;
    }

    @Override
    public int getHarvestLevel()
    {
        return 2;
    }

    @Override
    public int getEnchantability()
    {
        return 26;
    }

    @Override
    public Ingredient getRepairMaterial()
    {
        return Ingredient.fromItems(Items.IRON_INGOT);
    }
}
