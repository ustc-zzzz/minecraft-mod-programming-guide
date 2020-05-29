package zzzz.fmltutor.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import zzzz.fmltutor.FMLTutor;
import zzzz.fmltutor.capability.CapabilityRegistryHandler;
import zzzz.fmltutor.capability.DirtBallPower;
import zzzz.fmltutor.creativetab.TabFMLTutor;
import zzzz.fmltutor.entity.EntityDirtBall;
import zzzz.fmltutor.network.NetworkRegistryHandler;

public class ItemDirtBall extends Item
{
    public ItemDirtBall()
    {
        this.setUnlocalizedName(FMLTutor.MODID + ".dirtBall");
        this.setCreativeTab(TabFMLTutor.TAB_FMLTUTOR);
        this.setRegistryName("dirt_ball");
        this.setMaxStackSize(16);
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack)
    {
        return 100;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack item = playerIn.getHeldItem(handIn);

        if (!playerIn.capabilities.isCreativeMode)
        {
            item.shrink(1);
        }

        if (!worldIn.isRemote)
        {
            DirtBallPower power = playerIn.getCapability(CapabilityRegistryHandler.DIRT_BALL_POWER, null);
            float orange = power.getOrangePower(), green = power.getGreenPower(), blue = power.getBluePower();

            if (orange < 4 || green < 4 || blue < 4)
            {
                playerIn.sendMessage(new TextComponentTranslation("message.fmltutor.power.insufficient"));
                return ActionResult.newResult(EnumActionResult.PASS, item);
            }

            power.setOrangePower(orange - 4);
            power.setGreenPower(green - 4);
            power.setBluePower(blue - 4);

            // Synchronize data
            NetworkRegistryHandler.Power.sendClientCustomPacket(playerIn);

            EntityDirtBall entityDirtBall = new EntityDirtBall(worldIn, playerIn);
            float pitch = playerIn.rotationPitch, yaw = playerIn.rotationYaw;
            entityDirtBall.shoot(playerIn, pitch, yaw, 0.0F, 1.5F, 1.0F);
            worldIn.spawnEntity(entityDirtBall);
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, item);
    }
}
