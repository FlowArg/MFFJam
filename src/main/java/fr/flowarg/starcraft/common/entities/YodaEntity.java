package fr.flowarg.starcraft.common.entities;

import fr.flowarg.starcraft.Main.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class YodaEntity extends AgeableEntity
{
    public YodaEntity(EntityType<YodaEntity> type, World worldIn)
    {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 20.0F));
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

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(RegistryHandler.GREEN_LASER));
    }
}
