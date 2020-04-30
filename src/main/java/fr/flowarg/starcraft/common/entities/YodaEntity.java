package fr.flowarg.starcraft.common.entities;

import fr.flowarg.starcraft.Main;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class YodaEntity extends AgeableEntity
{
    public YodaEntity(EntityType<YodaEntity> type, World worldIn)
    {
        super(type, worldIn);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable)
    {
        return null;
    }

    @Override
    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(800F);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.13F);
    }

    @Override
    public boolean isAggressive()
    {
        return false;
    }

    @Override
    public void onDeath(DamageSource cause)
    {
        if (cause.getImmediateSource() != null)
            this.playYodaLessons(cause.getImmediateSource());
    }

    @Override
    public boolean hitByEntity(Entity entityIn)
    {
        this.playYodaLessons(entityIn);
        return false;
    }

    public void playYodaLessons(Entity entityIn)
    {
        entityIn.playSound(Main.RegistryHandler.YODA_SONGS, 10f, 1f);
    }
}
