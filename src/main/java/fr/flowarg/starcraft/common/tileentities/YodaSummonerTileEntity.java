package fr.flowarg.starcraft.common.tileentities;

import fr.flowarg.starcraft.Main;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class YodaSummonerTileEntity extends TileEntity implements ITickableTileEntity
{
    private boolean hasMaster = false;
    private boolean isMaster  = false;
    private int     masterX   = 0;
    private int     masterY   = 0;
    private int     masterZ   = 0;

    public YodaSummonerTileEntity()
    {
        super(Main.RegistryHandler.YODA_SUMMONER_TILE_ENTITY);
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        this.hasMaster = compound.getBoolean("HasMaster");
        this.isMaster  = compound.getBoolean("IsMaster");
        this.masterX   = compound.getInt("MasterX");
        this.masterY   = compound.getInt("MasterY");
        this.masterZ   = compound.getInt("MasterZ");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        compound.putBoolean("HasMaster", hasMaster);
        compound.putBoolean("IsMaster", isMaster);
        compound.putInt("MasterX", masterX);
        compound.putInt("MasterY", masterY);
        compound.putInt("MasterZ", masterZ);
        return compound;
    }

    public boolean isHasMaster()
    {
        return this.hasMaster;
    }

    public boolean isMaster()
    {
        return this.isMaster;
    }

    public int getMasterX()
    {
        return this.masterX;
    }

    public int getMasterY()
    {
        return this.masterY;
    }

    public int getMasterZ()
    {
        return this.masterZ;
    }

    public void setMaster(int x, int y, int z)
    {
        this.hasMaster = true;
        this.masterX   = x;
        this.masterY   = y;
        this.masterZ   = z;
    }

    private boolean checkStructure()
    {
        int n = 0;
        for (int x = this.pos.getX() - 1; x < this.pos.getX() + 2; x++)
        {
            for (int z = this.pos.getZ() - 1; z < this.pos.getZ() + 2; z++)
            {
                final BlockPos   position = new BlockPos(x, this.pos.getY(), z);
                final TileEntity tile     = this.world.getTileEntity(position);
                if (tile instanceof YodaSummonerTileEntity)
                {
                    final YodaSummonerTileEntity t = (YodaSummonerTileEntity)tile;
                    if (t.hasMaster)
                    {
                        if (this.pos.getX() == t.getMasterX() && this.pos.getY() == t.getMasterY() && this.pos.getZ() == t.getMasterZ())
                            n++;
                    }
                    else n++;
                }
            }
        }
        return n == 9;
    }

    private void setupStructure()
    {
        for (int x = this.pos.getX() - 1; x < this.pos.getX() + 2; x++)
        {
            for (int z = this.pos.getZ() - 1; z < this.pos.getZ() + 2; z++)
            {
                final BlockPos   position = new BlockPos(x, this.pos.getY(), z);
                final TileEntity tile     = this.world.getTileEntity(position);
                if (tile instanceof YodaSummonerTileEntity)
                {
                    final YodaSummonerTileEntity t = (YodaSummonerTileEntity)tile;
                    t.setMaster(this.pos.getX(), this.pos.getY(), this.pos.getZ());
                    this.isMaster = true;
                }
            }
        }
    }

    private void resetStructure()
    {
        for (int x = this.pos.getX() - 1; x < this.pos.getX() + 2; x++)
        {
            for (int z = this.pos.getZ() - 1; z < this.pos.getZ() + 2; z++)
            {
                final BlockPos   position = new BlockPos(x, this.pos.getY(), z);
                final TileEntity tile     = this.world.getTileEntity(position);
                if (tile instanceof YodaSummonerTileEntity)
                {
                    final YodaSummonerTileEntity t = (YodaSummonerTileEntity)tile;
                    t.reset();
                }
            }
        }
    }

    private boolean checkForMaster()
    {
        final BlockPos   position = new BlockPos(masterX, masterY, masterZ);
        final TileEntity tile     = this.world.getTileEntity(position);
        if (tile instanceof YodaSummonerTileEntity)
        {
            final YodaSummonerTileEntity t = (YodaSummonerTileEntity)tile;
            return t.isMaster();
        }
        return false;
    }

    public void reset()
    {
        this.isMaster  = false;
        this.hasMaster = false;
        this.masterX   = 0;
        this.masterY   = 0;
        this.masterZ   = 0;
    }

    @Override
    public void tick()
    {
        if (this.isMaster)
        {
            if (!this.checkStructure())
                this.resetStructure();
            if (this.getBlockState().isBurning(this.world, this.pos.up()))
            {
                Main.RegistryHandler.YODA_ENTITY.spawn(this.world, null, new TranslationTextComponent("starcraft.yoda"), null, this.pos.up(), SpawnReason.STRUCTURE, false, false);
                this.remove();
            }
        }
        else if (this.hasMaster)
        {
            if (!this.checkForMaster())
                this.reset();
            else
            {
                final BlockPos pos1 = new BlockPos(this.masterX, this.masterY, this.masterZ).up();
                if (world.getBlockState(pos1).isBurning(this.world, pos1))
                {
                    Main.RegistryHandler.YODA_ENTITY.spawn(this.world, null, new TranslationTextComponent("starcraft.yoda"), null, pos1.up(), SpawnReason.STRUCTURE, false, false);
                    this.remove();
                }
            }
        }
        else
        {
            if (this.checkStructure())
            {
                this.setupStructure();
            }
        }
        this.markDirty();
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        final CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return new SUpdateTileEntityPacket(this.pos, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
        this.read(pkt.getNbtCompound());
        if (this.world != null) this.world.markBlockRangeForRenderUpdate(this.pos, this.getBlockState(), this.getBlockState());
    }

    @Override
    public void remove()
    {
        for (int x = this.pos.getX() - 1; x < this.pos.getX() + 2; x++)
        {
            for (int z = this.pos.getZ() - 1; z < this.pos.getZ() + 2; z++)
            {
                final BlockPos   position = new BlockPos(x, this.pos.getY(), z);
                final TileEntity tile     = this.world.getTileEntity(position);
                if (tile instanceof YodaSummonerTileEntity)
                {
                    final YodaSummonerTileEntity t = (YodaSummonerTileEntity)tile;
                    this.world.removeBlock(t.pos, false);
                }
            }
        }
        this.resetStructure();
        super.remove();
    }
}
