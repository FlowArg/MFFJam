package fr.flowarg.starcraft.common.items;

import fr.flowarg.starcraft.Main;
import fr.flowarg.starcraft.Main.RegistryHandler;
import fr.flowarg.starcraft.common.capabilities.force.ForceCapability;
import fr.flowarg.starcraft.common.utils.IHasLaserColor;
import fr.flowarg.starcraft.common.utils.IHasLocation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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
        super(RegistryHandler.LASER_TIER, 0, 24, new Properties().group(Main.STAR_CRAFT_GROUP).maxStackSize(1).rarity(Rarity.UNCOMMON));
        this.laserColor = laserColor;
        this.setRegistryName(this.getLocation(laserColor.getName() + "_laser"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        if (!worldIn.isRemote)
        {
            if (handIn == Hand.MAIN_HAND)
            {
                final boolean flag = this.isRed();
                ItemStack     newLaser;

                if (playerIn.isSneaking())
                {
                    if (flag)
                        newLaser = new ItemStack(RegistryHandler.GREEN_LASER);
                    else newLaser = new ItemStack(RegistryHandler.RED_LASER);
                }
                else
                {
                    if (flag)
                        newLaser = new ItemStack(RegistryHandler.RED_LASER_BASE);
                    else newLaser = new ItemStack(RegistryHandler.GREEN_LASER_BASE);
                }

                playerIn.setHeldItem(handIn, newLaser);
                return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
            }
        }
        return ActionResult.resultPass(playerIn.getHeldItem(handIn));
    }

    @Override
    public boolean canHarvestBlock(BlockState blockIn)
    {
        return blockIn.getMaterial() == Material.ROCK;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state)
    {
        final Material material = state.getMaterial();
        return material != Material.WEB && material != Material.PLANTS && material != Material.TALL_PLANTS && material != Material.CORAL && !state.isIn(BlockTags.LEAVES) && material != Material.GOURD && material != Material.ROCK && material != Material.WOOD ? 2.1F : 19.6F;
    }

    @Override
    public LaserColor getLaserColor()
    {
        return this.laserColor;
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
                                                                                     iForce.decreaseForce(2);
                                                                                     if (iForce.getForce() == 0)
                                                                                         player.setHealth(player.getHealth() - 4f);
                                                                                 });

            }
        }
        return true;
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
                                                                                     force.decreaseForce(3);
                                                                                     if (force.getForce() == 0)
                                                                                         player.setHealth(player.getHealth() - 2f);
                                                                                 });
            }
        }

        if (state.getBlock() instanceof LogBlock)
        {
            final int x = pos.getX();
            final int y = pos.getY();
            final int z = pos.getZ();
            this.lookY(x, y, z, worldIn);
        }
        return true;
    }

    private void lookY(final int x, int y, final int z, World world)
    {
        BlockPos pos = this.convertXYZToBlockPos(x, y, z);
        while (this.deleteIfBlockIsALog(this.convertBlockPosToBlock(world, pos = pos.up()), world, pos))
        {
            this.lookX(pos.getX(), pos.getY(), pos.getZ(), world);
            this.lookZ(pos.getX(), pos.getY(), pos.getZ(), world);
        }
        this.lookX(pos.getX(), pos.getY(), pos.getZ(), world);
        this.lookZ(pos.getX(), pos.getY(), pos.getZ(), world);
    }

    private void lookX(int x, int y, int z, World world)
    {
        BlockPos pos = this.convertXYZToBlockPos(x, y, z);
        while (this.deleteIfBlockIsALog(this.convertBlockPosToBlock(world, pos = pos.north()), world, pos))
        {
            this.lookY(x, y, z, world);
        }
        BlockPos pos1 = this.convertXYZToBlockPos(x, y, z);
        while (this.deleteIfBlockIsALog(this.convertBlockPosToBlock(world, pos1 = pos1.south()), world, pos1))
        {
            this.lookY(x, y, z, world);
        }
    }

    private void lookZ(int x, int y, int z, World world)
    {
        BlockPos pos = this.convertXYZToBlockPos(x, y, z);
        while (this.deleteIfBlockIsALog(this.convertBlockPosToBlock(world, pos = pos.west()), world, pos))
        {
            this.lookY(x, y, z, world);
        }
        BlockPos pos1 = this.convertXYZToBlockPos(x, y, z);
        while (this.deleteIfBlockIsALog(this.convertBlockPosToBlock(world, pos1 = pos1.east()), world, pos1))
        {
            this.lookY(x, y, z, world);
        }
    }

    private boolean deleteIfBlockIsALog(Block block, World world, BlockPos pos)
    {
        if (block instanceof LogBlock)
        {
            world.removeBlock(pos, false);
            Block.spawnDrops(block.getDefaultState(), world, pos);
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
