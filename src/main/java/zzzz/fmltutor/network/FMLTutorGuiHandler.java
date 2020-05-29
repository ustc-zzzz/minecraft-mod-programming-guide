package zzzz.fmltutor.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import zzzz.fmltutor.client.network.GuiDirtCompressor;

public class FMLTutorGuiHandler implements IGuiHandler
{
    public static final int DIRT_COMPRESSOR = 1;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        if (id == DIRT_COMPRESSOR)
        {
            return new ContainerDirtCompressor(player, world, x, y, z);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        if (id == DIRT_COMPRESSOR)
        {
            return new GuiDirtCompressor(player, world, x, y, z);
        }
        return null;
    }
}
