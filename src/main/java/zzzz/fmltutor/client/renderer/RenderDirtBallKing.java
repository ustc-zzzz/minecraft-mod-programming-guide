package zzzz.fmltutor.client.renderer;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import zzzz.fmltutor.FMLTutor;
import zzzz.fmltutor.client.model.ModelDirtBallKing;
import zzzz.fmltutor.entity.EntityDirtBallKing;

public class RenderDirtBallKing extends RenderLiving<EntityDirtBallKing>
{
    private static final ResourceLocation ENTITY_DIRT_BALL_KING_TEXTURE = new ResourceLocation(
            FMLTutor.MODID + ":textures/entity/" + EntityDirtBallKing.ID + "/" + EntityDirtBallKing.ID + ".png");
    private static final ResourceLocation ENTITY_DIRT_BALL_KING_BLUE_TEXTURE = new ResourceLocation(
            FMLTutor.MODID + ":textures/entity/" + EntityDirtBallKing.ID + "/" + EntityDirtBallKing.ID + "_blue.png");
    private static final ResourceLocation ENTITY_DIRT_BALL_KING_GREEN_TEXTURE = new ResourceLocation(
            FMLTutor.MODID + ":textures/entity/" + EntityDirtBallKing.ID + "/" + EntityDirtBallKing.ID + "_green.png");

    public RenderDirtBallKing(RenderManager manager)
    {
        super(manager, new ModelDirtBallKing(), 0.8F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityDirtBallKing entity)
    {
        byte color = entity.getColor();
        if (color == 2)
        {
            return ENTITY_DIRT_BALL_KING_GREEN_TEXTURE;
        }
        if (color == 1)
        {
            return ENTITY_DIRT_BALL_KING_BLUE_TEXTURE;
        }
        return ENTITY_DIRT_BALL_KING_TEXTURE;
    }
}
