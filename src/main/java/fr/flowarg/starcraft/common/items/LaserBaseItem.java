package fr.flowarg.starcraft.common.items;

import fr.flowarg.starcraft.Main;
import fr.flowarg.starcraft.Main.RegistryHandler;
import fr.flowarg.starcraft.common.utils.IHasLaserColor;
import fr.flowarg.starcraft.common.utils.IHasLocation;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
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
        super(RegistryHandler.LASER_TIER, new Properties().rarity(Rarity.UNCOMMON).group(Main.ITEM_GROUP).maxStackSize(1).maxDamage(150));
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
                if (this.isRed())
                {
                    final ItemStack thisItemStack = playerIn.getHeldItem(handIn);
                    final ItemStack redLaser = new ItemStack(RegistryHandler.RED_LASER);
                    redLaser.setDamage(thisItemStack.getDamage());
                    playerIn.setHeldItem(handIn, redLaser);
                }
                else
                {
                    final ItemStack thisItemStack = playerIn.getHeldItem(handIn);
                    final ItemStack greenLaser = new ItemStack(RegistryHandler.GREEN_LASER);
                    greenLaser.setDamage(thisItemStack.getDamage());
                    playerIn.setHeldItem(handIn, greenLaser);
                }
                return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
            }
        }
        return ActionResult.resultPass(playerIn.getHeldItem(handIn));
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving)
    {
        if (!worldIn.isRemote)
            if (state.getBlockHardness(worldIn, pos) != 0.0F)
                stack.damageItem(2, entityLiving, (player) -> player.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        return true;
    }

    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        if(!attacker.world.isRemote)
            stack.damageItem(1, attacker, (player) -> player.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        return true;
    }

    @Override
    public LaserColor getLaserColor()
    {
        return this.laserColor;
    }
}
