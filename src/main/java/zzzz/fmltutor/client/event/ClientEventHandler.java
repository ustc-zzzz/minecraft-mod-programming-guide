package zzzz.fmltutor.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zzzz.fmltutor.FMLTutor;
import zzzz.fmltutor.capability.CapabilityRegistryHandler;
import zzzz.fmltutor.capability.DirtBallPower;
import zzzz.fmltutor.item.ItemRegistryHandler;

@EventBusSubscriber
public class ClientEventHandler
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(FMLTutor.MODID + ":textures/gui/overlay.png");

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onRenderGameOverlayPost(RenderGameOverlayEvent.Post event)
    {
        if (RenderGameOverlayEvent.ElementType.ALL.equals(event.getType()))
        {
            Minecraft mc = Minecraft.getMinecraft();
            Entity entity = mc.getRenderViewEntity();
            if (entity instanceof EntityPlayer)
            {
                ItemStack currentItem = ((EntityPlayer) entity).inventory.getCurrentItem();
                if (ItemRegistryHandler.DIRT_BALL.equals(currentItem.getItem()))
                {
                    DirtBallPower power = entity.getCapability(CapabilityRegistryHandler.DIRT_BALL_POWER, null);
                    float orange = power.getOrangePower(), green = power.getGreenPower(), blue = power.getBluePower();

                    ScaledResolution resolution = event.getResolution();
                    int width = resolution.getScaledWidth(), height = resolution.getScaledHeight();

                    GlStateManager.enableBlend();
                    mc.getTextureManager().bindTexture(TEXTURE);

                    mc.ingameGUI.drawTexturedModalRect(width / 2 - 175, height - 40, 0, 9, 80, 40);

                    mc.ingameGUI.drawTexturedModalRect(width / 2 - 170, height - 35, orange < 4 ? 0 : 9, 0, 9, 9);
                    mc.ingameGUI.drawTexturedModalRect(width / 2 - 170, height - 24, green < 4 ? 0 : 9, 0, 9, 9);
                    mc.ingameGUI.drawTexturedModalRect(width / 2 - 170, height - 13, blue < 4 ? 0 : 9, 0, 9, 9);

                    mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("tooltip.fmltutor.power.orange", orange), width / 2 - 158, height - 35, 0xFFFFFF);
                    mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("tooltip.fmltutor.power.green", green), width / 2 - 158, height - 24, 0xFFFFFF);
                    mc.ingameGUI.drawString(mc.fontRenderer, I18n.format("tooltip.fmltutor.power.blue", blue), width / 2 - 158, height - 13, 0xFFFFFF);
                }
            }
        }
    }
}
