package fr.flowarg.starcraft.common.items;

import fr.flowarg.starcraft.Main;
import fr.flowarg.starcraft.Main.RegistryHandler;
import fr.flowarg.starcraft.common.utils.IHasLaserColor;
import fr.flowarg.starcraft.common.utils.IHasLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class LaserBaseItem extends Item implements IHasLocation, IHasLaserColor
{
    private final LaserColor laserColor;

    public LaserBaseItem(LaserColor laserColor)
    {
        super(new Properties().setNoRepair().rarity(Rarity.UNCOMMON).group(Main.ITEM_GROUP));
        this.laserColor = laserColor;
        this.setRegistryName(this.getLocation(this.laserColor.getName() + "_laser_base"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        if (handIn == Hand.MAIN_HAND)
        {
            if (this.isRed())
            {
                final ItemStack redLaser = new ItemStack(RegistryHandler.RED_LASER);
                redLaser.setDamage(this.getDamage(new ItemStack(this)));
                playerIn.setHeldItem(handIn, redLaser);
            }
            else
            {
                final ItemStack greenLaser = new ItemStack(RegistryHandler.GREEN_LASER);
                greenLaser.setDamage(this.getDamage(new ItemStack(this)));
                playerIn.setHeldItem(handIn, greenLaser);
            }
            return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
        }
        return ActionResult.resultPass(playerIn.getHeldItem(handIn));
    }

    @Override
    public LaserColor getLaserColor()
    {
        return this.laserColor;
    }
}
