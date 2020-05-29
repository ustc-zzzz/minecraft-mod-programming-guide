package zzzz.fmltutor.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import zzzz.fmltutor.entity.EntityDirtBall;
import zzzz.fmltutor.entity.EntityDirtBallKing;
import zzzz.fmltutor.item.ItemRegistryHandler;

public class RenderRegistryHandler
{
    public static void register()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityDirtBallKing.class, RenderDirtBallKing::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityDirtBall.class, manager ->
        {
            RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
            return new RenderSnowball<EntityDirtBall>(manager, ItemRegistryHandler.DIRT_BALL, renderItem);
        });
    }
}
