package fr.flowarg.starcraft.common.capabilities.force;

public interface IForce
{
    int MAX = 100;
    int MIN = 0;

    int getForce();

    void setForce(int force);

    default void increaseForce(int value)
    {
        final int result = this.clamp(this.getForce() + value);
        this.setForce(result);
    }

    default void decreaseForce(int value)
    {
        final int result = this.clamp(this.getForce() - value);
        this.setForce(result);
    }

    default int clamp(int value)
    {
        if (value > MAX)
            return MAX;
        else return Math.max(value, MIN);
    }
}
