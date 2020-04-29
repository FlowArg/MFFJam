package fr.flowarg.starcraft.common.items;

import fr.flowarg.starcraft.Main;
import fr.flowarg.starcraft.Main.RegistryHandler;
import fr.flowarg.starcraft.common.utils.IHasLaserColor;
import fr.flowarg.starcraft.common.utils.IHasLocation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LogBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.SwordItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LaserItem extends SwordItem implements IHasLocation, IHasLaserColor
{
    private final LaserColor laserColor;

    public LaserItem(LaserColor laserColor)
    {
        super(RegistryHandler.LASER_TIER, 0, 24, new Properties().group(Main.ITEM_GROUP).maxStackSize(1).rarity(Rarity.UNCOMMON).setNoRepair().maxDamage(200));
        this.laserColor = laserColor;
        this.setRegistryName(this.getLocation(laserColor.getName() + "_laser"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        if (handIn == Hand.MAIN_HAND)
        {
            if (playerIn.isSneaking())
            {
                if (this.isRed())
                {
                    final ItemStack greenLaser = new ItemStack(RegistryHandler.GREEN_LASER);
                    greenLaser.setDamage(this.getDamage(new ItemStack(this)));
                    playerIn.setHeldItem(handIn, greenLaser);
                }
                else
                {
                    final ItemStack redLaser = new ItemStack(RegistryHandler.RED_LASER);
                    redLaser.setDamage(this.getDamage(new ItemStack(this)));
                    playerIn.setHeldItem(handIn, redLaser);
                }
            }
            else
            {
                if (this.isRed())
                {
                    final ItemStack redLaserBase = new ItemStack(RegistryHandler.RED_LASER_BASE);
                    redLaserBase.setDamage(this.getDamage(new ItemStack(this)));
                    playerIn.setHeldItem(handIn, redLaserBase);
                }
                else
                {
                    final ItemStack greenLaserBase = new ItemStack(RegistryHandler.GREEN_LASER_BASE);
                    greenLaserBase.setDamage(this.getDamage(new ItemStack(this)));
                    playerIn.setHeldItem(handIn, greenLaserBase);
                }
            }
            return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
        }
        return ActionResult.resultPass(playerIn.getHeldItem(handIn));
    }

    @Override
    public boolean canHarvestBlock(BlockState blockIn)
    {
        final Block block = blockIn.getBlock();
        return block == Blocks.STONE || block == Blocks.COBBLESTONE;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state)
    {
        final Material material = state.getMaterial();
        return material != Material.PLANTS && material != Material.TALL_PLANTS && material != Material.CORAL && !state.isIn(BlockTags.LEAVES) && material != Material.GOURD && material != Material.ROCK && material != Material.WOOD ? 2.1F : 10.6F;
    }

    @Override
    public LaserColor getLaserColor()
    {
        return this.laserColor;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving)
    {
        if (state.getBlockHardness(worldIn, pos) != 0.0F)
            stack.damageItem(2, entityLiving, (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        if (state.getBlock() instanceof LogBlock && entityLiving instanceof PlayerEntity)
        {
            final int x = pos.getX();
            final int y = pos.getY();
            final int z = pos.getZ();
            final PlayerEntity player = (PlayerEntity)entityLiving;
            this.lookY(x, y, z, worldIn, player);
        }
        return true;
    }

    private void lookY(final int x, int y, final int z, World world, PlayerEntity player)
    {
        final BlockPos pos = this.convertXYZToBlockPos(x, y, z);
        while (this.deleteIfBlockIsALog(this.convertBlockPosToBlock(world, pos.up()), world, pos.up(), player))
        {
            ;
        }
    }

    private boolean deleteIfBlockIsALog(Block block, World world, BlockPos pos, PlayerEntity player)
    {
        if (block instanceof LogBlock)
        {
            world.removeBlock(pos, false);
            block.onBlockHarvested(world, pos, block.getDefaultState(), player);
            return true;
        }
        return false;
    }

    private Block convertBlockPosToBlock(World world, BlockPos pos)
    {
        return world.getBlockState(pos).getBlock();
    }

    private BlockPos convertXYZToBlockPos(int x, int y, int z)
    {
        return new BlockPos(x, y, z);
    }
}
