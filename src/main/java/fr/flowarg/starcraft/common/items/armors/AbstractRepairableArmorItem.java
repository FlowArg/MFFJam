package fr.flowarg.starcraft.common.items.armors;

import fr.flowarg.starcraft.common.utils.IHasLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class AbstractRepairableArmorItem extends ArmorItem implements IHasLocation
{
    private static final int DELAY = 40;
    private              int ticks = DELAY;

    public AbstractRepairableArmorItem(String name, IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder)
    {
        super(materialIn, slot, builder);
        this.setRegistryName(this.getLocation(name));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player)
    {
        if (this.ticks == 0)
        {
            this.onTimerTick(stack, world, player);
            this.ticks = DELAY;
        }
        else this.ticks--;
    }

    protected void onTimerTick(ItemStack stack, World world, PlayerEntity player)
    {
        if (stack.getDamage() > 0)
            stack.setDamage(stack.getDamage() - 1);
    }
}
