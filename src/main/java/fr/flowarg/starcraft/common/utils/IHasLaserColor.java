package fr.flowarg.starcraft.common.utils;

import fr.flowarg.starcraft.common.items.LaserColor;

public interface IHasLaserColor
{
    LaserColor getLaserColor();

    default boolean isRed()
    {
        return this.getLaserColor() == LaserColor.RED;
    }
}
