package fr.flowarg.starcraft.common.items.armors;

import fr.flowarg.starcraft.Main;
import fr.flowarg.starcraft.Main.RegistryHandler;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Rarity;

public class StormTrooperLeggings extends AbstractRepairableArmorItem
{
    public StormTrooperLeggings()
    {
        super("storm_trooper_leggings", RegistryHandler.STORM_TROOPER_ARMOR_MATERIAL,
              EquipmentSlotType.LEGS,
              new Properties()
                      .group(Main.ITEM_GROUP)
                      .rarity(Rarity.UNCOMMON));
    }
}
