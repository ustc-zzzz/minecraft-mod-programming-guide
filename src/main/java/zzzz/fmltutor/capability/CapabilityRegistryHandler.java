package zzzz.fmltutor.capability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zzzz.fmltutor.FMLTutor;

@EventBusSubscriber
public class CapabilityRegistryHandler
{
    @CapabilityInject(DirtBallPower.class)
    public static Capability<DirtBallPower> DIRT_BALL_POWER;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(DirtBallPower.class, new Capability.IStorage<DirtBallPower>()
        {
            @Override
            public NBTBase writeNBT(Capability<DirtBallPower> cap, DirtBallPower instance, EnumFacing side)
            {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<DirtBallPower> cap, DirtBallPower instance, EnumFacing side, NBTBase nbt)
            {
                if (nbt instanceof NBTTagCompound)
                {
                    instance.deserializeNBT((NBTTagCompound) nbt);
                }
            }
        }, DirtBallPower::new);
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof EntityPlayer)
        {
            DirtBallPowerProvider provider = new DirtBallPowerProvider();
            event.addCapability(new ResourceLocation(FMLTutor.MODID + ":dirt_ball_power"), provider);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
        DirtBallPower instance = event.getEntityPlayer().getCapability(DIRT_BALL_POWER, null);
        DirtBallPower original = event.getOriginal().getCapability(DIRT_BALL_POWER, null);

        instance.setOrangePower(original.getOrangePower());
        instance.setGreenPower(original.getGreenPower());
        instance.setBluePower(original.getGreenPower());
    }
}
