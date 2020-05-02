package fr.flowarg.starcraft.common.items.armors;

import fr.flowarg.starcraft.Main;
import fr.flowarg.starcraft.Main.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.world.World;

public class StormTrooperHelmet extends AbstractRepairableArmorItem
{
    private static final int DELAY = 40;
    private int ticks = DELAY;

    public StormTrooperHelmet()
    {
        super("storm_trooper_helmet", RegistryHandler.STORM_TROOPER_ARMOR_MATERIAL,
              EquipmentSlotType.HEAD,
              new Properties()
                      .group(Main.STAR_CRAFT_GROUP)
                      .rarity(Rarity.UNCOMMON));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player)
    {
        if (this.ticks == 0)
        {
            if (stack.getDamage() > 0)
                stack.setDamage(stack.getDamage() - 1);
            this.ticks = DELAY;
        }
        else this.ticks--;
    }
}
