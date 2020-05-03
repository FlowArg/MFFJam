package fr.flowarg.starcraft.common.items.armors;

import fr.flowarg.starcraft.Main;
import fr.flowarg.starcraft.Main.RegistryHandler;
import fr.flowarg.starcraft.common.capabilities.force.ForceCapability;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.world.World;

public class StormTrooperChestplate extends AbstractRepairableArmorItem
{
    public StormTrooperChestplate()
    {
        super("storm_trooper_chestplate", RegistryHandler.STORM_TROOPER_ARMOR_MATERIAL,
              EquipmentSlotType.CHEST,
              new Properties()
                      .group(Main.STAR_CRAFT_GROUP)
                      .rarity(Rarity.UNCOMMON));
    }

    @Override
    protected void onTimerTick(ItemStack stack, World world, PlayerEntity player)
    {
        super.onTimerTick(stack, world, player);
        if (!world.isRemote)
        {
            int forceToGive = 1;
            if (player.getMaxHealth() == player.getHealth())
                forceToGive += 1;
            if (!player.getFoodStats().needFood())
                forceToGive += 3;
            if (player.abilities.isCreativeMode)
                forceToGive = 100;

            int finalForceToGive = forceToGive;
            player.getCapability(ForceCapability.FORCE_CAPABILITY).ifPresent(iForce -> iForce.increaseForce(finalForceToGive));
        }
    }
}
