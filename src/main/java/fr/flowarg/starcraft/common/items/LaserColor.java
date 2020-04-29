package fr.flowarg.starcraft.common.items;

public enum LaserColor
{
    RED("red"),
    GREEN("green");

    private final String name;

    LaserColor(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
