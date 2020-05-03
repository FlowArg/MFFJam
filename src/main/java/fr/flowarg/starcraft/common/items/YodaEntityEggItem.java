package fr.flowarg.starcraft.common.items;

import fr.flowarg.starcraft.Main;
import fr.flowarg.starcraft.common.utils.IHasLocation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.spawner.AbstractSpawner;

import java.util.Objects;

public class YodaEntityEggItem extends Item implements IHasLocation
{
    public YodaEntityEggItem()
    {
        super(new Properties().group(Main.STAR_CRAFT_GROUP).maxStackSize(1));
        this.setRegistryName(this.getLocation("yoda_egg"));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        final World world = context.getWorld();
        if (!world.isRemote)
        {
            final ItemStack  itemstack  = context.getItem();
            final BlockPos   blockpos   = context.getPos();
            final Direction  direction  = context.getFace();
            final BlockState blockstate = world.getBlockState(blockpos);
            final Block      block      = blockstate.getBlock();

            itemstack.setDisplayName(new TranslationTextComponent("starcraft.yoda"));

            if (block == Blocks.SPAWNER)
            {
                final TileEntity tileentity = world.getTileEntity(blockpos);
                if (tileentity instanceof MobSpawnerTileEntity)
                {
                    final AbstractSpawner abstractspawner = ((MobSpawnerTileEntity)tileentity).getSpawnerBaseLogic();
                    abstractspawner.setEntityType(Main.RegistryHandler.YODA_ENTITY);
                    tileentity.markDirty();
                    world.notifyBlockUpdate(blockpos, blockstate, blockstate, 3);
                    itemstack.shrink(1);
                    return ActionResultType.SUCCESS;
                }
            }

            BlockPos blockpos1;
            if (blockstate.getCollisionShape(world, blockpos).isEmpty()) blockpos1 = blockpos;
            else blockpos1 = blockpos.offset(direction);

            if (Main.RegistryHandler.YODA_ENTITY.spawn(world, itemstack, context.getPlayer(), blockpos1, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP) != null)
                itemstack.shrink(1);
        }
        return ActionResultType.SUCCESS;
    }
}
