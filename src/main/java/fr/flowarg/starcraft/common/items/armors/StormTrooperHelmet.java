package fr.flowarg.starcraft.common.items.armors;

import fr.flowarg.starcraft.Main;
import fr.flowarg.starcraft.Main.RegistryHandler;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Rarity;

public class StormTrooperHelmet extends AbstractRepairableArmorItem
{
    public StormTrooperHelmet()
    {
        super("storm_trooper_helmet", RegistryHandler.STORM_TROOPER_ARMOR_MATERIAL,
              EquipmentSlotType.HEAD,
              new Properties()
                      .group(Main.STAR_CRAFT_GROUP)
                      .rarity(Rarity.UNCOMMON));
    }
}
