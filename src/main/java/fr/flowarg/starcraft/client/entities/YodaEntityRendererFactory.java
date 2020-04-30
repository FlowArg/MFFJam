package fr.flowarg.starcraft.client.entities;

import fr.flowarg.starcraft.common.entities.YodaEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class YodaEntityRendererFactory implements IRenderFactory<YodaEntity>
{
    @Override
    public EntityRenderer<? super YodaEntity> createRenderFor(EntityRendererManager manager)
    {
        return new YodaEntityRenderer(manager);
    }
}
