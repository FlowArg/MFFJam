package fr.flowarg.starcraft.common.items.armors;

import fr.flowarg.starcraft.Main;
import fr.flowarg.starcraft.Main.RegistryHandler;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Rarity;

public class StormTrooperBoots extends AbstractRepairableArmorItem
{
    public StormTrooperBoots()
    {
        super("storm_trooper_boots", RegistryHandler.STORM_TROOPER_ARMOR_MATERIAL,
              EquipmentSlotType.FEET,
              new Properties()
                      .group(Main.STAR_CRAFT_GROUP)
                      .rarity(Rarity.UNCOMMON));
    }


}
