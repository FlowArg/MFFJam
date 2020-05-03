package fr.flowarg.starcraft.common.capabilities.force;

public class DefaultForceHolder implements IForce
{
    private int force = 0;

    @Override
    public int getForce()
    {
        return this.force;
    }

    @Override
    public void setForce(int force)
    {
        this.force = this.clamp(force);
    }
}