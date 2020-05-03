package fr.flowarg.starcraft.common.capabilities.force;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerForceWrapper implements ICapabilitySerializable<INBT>
{
    private IForce holder;

    private final LazyOptional<IForce> lazyOptional = LazyOptional.of(() -> this.holder);

    public PlayerForceWrapper(IForce holder)
    {
        this.holder = holder;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        return ForceCapability.FORCE_CAPABILITY.orEmpty(cap, this.lazyOptional);
    }

    @Override
    public INBT serializeNBT()
    {
        return ForceCapability.FORCE_CAPABILITY.writeNBT(this.holder, null);
    }

    @Override
    public void deserializeNBT(INBT nbt)
    {
        ForceCapability.FORCE_CAPABILITY.readNBT(this.holder, null, nbt);
    }
}
