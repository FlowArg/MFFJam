package fr.flowarg.starcraft.common.entities;

import fr.flowarg.starcraft.Main;
import fr.flowarg.starcraft.Main.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
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
    public SoundCategory getSoundCategory()
    {
        return SoundCategory.AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return RegistryHandler.YODA_SONGS;
    }

    protected SoundEvent getStepSound()
    {
        return SoundEvents.ENTITY_PANDA_STEP;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound()
    {
        return RegistryHandler.YODA_SONGS;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn)
    {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }


    public void playYodaLessons(Entity entityIn)
    {
        entityIn.playSound(Main.RegistryHandler.YODA_SONGS, 10f, 1f);
        System.out.println("Playing to : " + entityIn.toString());
    }
}
