package fr.flowarg.starcraft.client.entities;

import fr.flowarg.starcraft.common.entities.YodaEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class YodaEntityModel extends PlayerModel<YodaEntity>
{
    public YodaEntityModel()
    {
        super(0, false);
    }
}
