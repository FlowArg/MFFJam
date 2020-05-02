package fr.flowarg.starcraft.common.blocks;

import fr.flowarg.starcraft.common.tileentities.YodaSummonerTileEntity;
import fr.flowarg.starcraft.common.utils.IHasLocation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class YodaSummonerBlock extends Block implements IHasLocation
{
    public YodaSummonerBlock()
    {
        super(Properties.create(Material.ROCK)
                        .harvestLevel(0)
                        .harvestTool(ToolType.PICKAXE)
                        .hardnessAndResistance(1f, 2f));
        this.setRegistryName(this.getLocation("yoda_summoner"));
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if (!(placer instanceof PlayerEntity))
            worldIn.removeBlock(pos, false);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new YodaSummonerTileEntity();
    }
}
