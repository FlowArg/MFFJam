package fr.flowarg.starcraft.common.items.armors;

import fr.flowarg.starcraft.Main;
import fr.flowarg.starcraft.Main.RegistryHandler;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Rarity;

public class StormTrooperChestplate extends AbstractRepairableArmorItem
{
    public StormTrooperChestplate()
    {
        super("storm_trooper_chestplate", RegistryHandler.STORM_TROOPER_ARMOR_MATERIAL,
              EquipmentSlotType.CHEST,
              new Properties()
                      .group(Main.ITEM_GROUP)
                      .rarity(Rarity.UNCOMMON));
    }
}
