package fr.flowarg.starcraft.common.items;

import fr.flowarg.starcraft.Main;
import fr.flowarg.starcraft.Main.RegistryHandler;
import fr.flowarg.starcraft.common.capabilities.force.ForceCapability;
import fr.flowarg.starcraft.common.utils.IHasLaserColor;
import fr.flowarg.starcraft.common.utils.IHasLocation;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.TieredItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LaserBaseItem extends TieredItem implements IHasLocation, IHasLaserColor
{
    private final LaserColor laserColor;

    public LaserBaseItem(LaserColor laserColor)
    {
        super(RegistryHandler.LASER_TIER, new Properties().rarity(Rarity.UNCOMMON).group(Main.STAR_CRAFT_GROUP).maxStackSize(1));
        this.laserColor = laserColor;
        this.setRegistryName(this.getLocation(this.laserColor.getName() + "_laser_base"));
    }

    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player)
    {
        return !player.isCreative();
    }

    @Override
    public boolean canHarvestBlock(BlockState blockIn)
    {
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        if (!worldIn.isRemote)
        {
            if (handIn == Hand.MAIN_HAND)
            {
                ItemStack newLaser;
                if (this.isRed())
                    newLaser = new ItemStack(RegistryHandler.RED_LASER);
                else newLaser = new ItemStack(RegistryHandler.GREEN_LASER);
                playerIn.setHeldItem(handIn, newLaser);
                return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
            }
        }
        return ActionResult.resultPass(playerIn.getHeldItem(handIn));
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving)
    {
        if (!worldIn.isRemote)
        {
            if (entityLiving instanceof PlayerEntity)
            {
                final PlayerEntity player = (PlayerEntity)entityLiving;
                player.getCapability(ForceCapability.FORCE_CAPABILITY).ifPresent(force ->
                                                                                 {
                                                                                     force.decreaseForce(2);
                                                                                     if (force.getForce() == 0)
                                                                                         player.setHealth(player.getHealth() - 3f);
                                                                                 });
            }
        }
        return true;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        if (!target.world.isRemote)
        {
            if (attacker instanceof PlayerEntity)
            {
                final PlayerEntity player = (PlayerEntity)attacker;
                player.getCapability(ForceCapability.FORCE_CAPABILITY).ifPresent(iForce ->
                                                                                 {
                                                                                     iForce.decreaseForce(1);
                                                                                     if (iForce.getForce() == 0)
                                                                                         player.setHealth(player.getHealth() - 3f);
                                                                                 });

            }
        }
        return true;
    }

    @Override
    public LaserColor getLaserColor()
    {
        return this.laserColor;
    }
}
