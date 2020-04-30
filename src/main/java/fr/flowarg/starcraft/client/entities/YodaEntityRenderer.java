package fr.flowarg.starcraft.client.entities;

import fr.flowarg.starcraft.Main;
import fr.flowarg.starcraft.common.entities.YodaEntity;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class YodaEntityRenderer extends BipedRenderer<YodaEntity, YodaEntityModel>
{
    public static final ResourceLocation YODA_TEXTURE = new ResourceLocation(Main.MODID, "textures/entity/yoda.png");

    public YodaEntityRenderer(EntityRendererManager renderManagerIn)
    {
        super(renderManagerIn, new YodaEntityModel(), 0.5f);
    }

    @Override
    public ResourceLocation getEntityTexture(YodaEntity entity)
    {
        return YODA_TEXTURE;
    }
}
