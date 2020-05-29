package zzzz.fmltutor.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zzzz.fmltutor.FMLTutor;
import zzzz.fmltutor.capability.CapabilityRegistryHandler;
import zzzz.fmltutor.capability.DirtBallPower;

public class NetworkRegistryHandler
{
    public static void register()
    {
        Power.CHANNEL.register(Power.class);
        NetworkRegistry.INSTANCE.registerGuiHandler(FMLTutor.MODID, new FMLTutorGuiHandler());
    }

    public static class Power
    {
        private static final String NAME = "DIRTBALLPOWER";
        private static final FMLEventChannel CHANNEL = NetworkRegistry.INSTANCE.newEventDrivenChannel(NAME);

        public static void sendClientCustomPacket(EntityPlayer player)
        {
            PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
            DirtBallPower power = player.getCapability(CapabilityRegistryHandler.DIRT_BALL_POWER, null);

            buf.writeFloat(power.getBluePower());
            buf.writeFloat(power.getGreenPower());
            buf.writeFloat(power.getOrangePower());

            CHANNEL.sendTo(new FMLProxyPacket(buf, NAME), (EntityPlayerMP) player);
        }

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void onClientCustomPacket(FMLNetworkEvent.ClientCustomPacketEvent event)
        {
            ByteBuf buf = event.getPacket().payload();
            float blue = buf.readFloat(), green = buf.readFloat(), orange = buf.readFloat();

            Minecraft mc = Minecraft.getMinecraft();
            mc.addScheduledTask(() ->
            {
                EntityPlayer player = mc.player;
                DirtBallPower power = player.getCapability(CapabilityRegistryHandler.DIRT_BALL_POWER, null);

                power.setBluePower(blue);
                power.setGreenPower(green);
                power.setOrangePower(orange);
            });
        }
    }
}
