package zzzz.fmltutor;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;
import zzzz.fmltutor.capability.CapabilityRegistryHandler;
import zzzz.fmltutor.client.renderer.RenderRegistryHandler;
import zzzz.fmltutor.crafting.FurnaceRecipeRegistryHandler;
import zzzz.fmltutor.network.NetworkRegistryHandler;
import zzzz.fmltutor.potion.PotionRegistryHandler;

@Mod(modid = FMLTutor.MODID, name = FMLTutor.NAME, version = FMLTutor.VERSION, acceptedMinecraftVersions = "[1.12,1.13)")
public class FMLTutor
{
    public static final String MODID = "fmltutor";
    public static final String NAME = "FMLTutor";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        NetworkRegistryHandler.register();
        CapabilityRegistryHandler.register();
    }

    @EventHandler
    @SideOnly(Side.CLIENT)
    public void preInitClient(FMLPreInitializationEvent event)
    {
        RenderRegistryHandler.register();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        FurnaceRecipeRegistryHandler.register();
        PotionRegistryHandler.register();
    }
}
