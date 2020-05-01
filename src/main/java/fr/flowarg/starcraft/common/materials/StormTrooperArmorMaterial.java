package fr.flowarg.starcraft.common.materials;

import fr.flowarg.starcraft.Main;
import fr.flowarg.starcraft.Main.RegistryHandler;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StormTrooperArmorMaterial implements IArmorMaterial
{
    @Override
    public int getDurability(EquipmentSlotType slotIn)
    {
        return 4800;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slotIn)
    {
        return new int[]{9, 14, 11, 6}[slotIn.getIndex()];
    }

    @Override
    public int getEnchantability()
    {
        return 26;
    }

    @Override
    public SoundEvent getSoundEvent()
    {
        return RegistryHandler.STORM_TROOPER_EQUIP_ARMOR;
    }

    @Override
    public Ingredient getRepairMaterial()
    {
        return Ingredient.fromItems(Items.IRON_INGOT);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getName()
    {
        return Main.MODID + ":storm_trooper";
    }

    @Override
    public float getToughness()
    {
        return 15F;
    }
}
