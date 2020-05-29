package zzzz.fmltutor.event;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zzzz.fmltutor.FMLTutor;
import zzzz.fmltutor.capability.CapabilityRegistryHandler;
import zzzz.fmltutor.capability.DirtBallPower;
import zzzz.fmltutor.enchantment.EnchantmentRegistryHandler;
import zzzz.fmltutor.entity.EntityDirtBallKing;
import zzzz.fmltutor.network.NetworkRegistryHandler;
import zzzz.fmltutor.potion.PotionRegistryHandler;

@EventBusSubscriber
public class EventHandler
{
    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinWorldEvent event)
    {
        Entity entity = event.getEntity();
        if (!entity.world.isRemote && entity instanceof EntityPlayer)
        {
            entity.sendMessage(new TextComponentTranslation("message.fmltutor.welcome", FMLTutor.NAME, entity.getName()));

            // Synchronize data
            NetworkRegistryHandler.Power.sendClientCustomPacket((EntityPlayer) entity);
        }
    }

    private static TextComponentTranslation addPower(EntityDirtBallKing entity, DirtBallPower power, float amount)
    {
        byte color = entity.getColor();
        if (color == 2)
        {
            power.setGreenPower(power.getGreenPower() + amount);
            return new TextComponentTranslation("message.fmltutor.power.add.green", amount);
        }
        if (color == 1)
        {
            power.setBluePower(power.getBluePower() + amount);
            return new TextComponentTranslation("message.fmltutor.power.add.blue", amount);
        }
        power.setOrangePower(power.getOrangePower() + amount);
        return new TextComponentTranslation("message.fmltutor.power.add.orange", amount);
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event)
    {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity instanceof EntityDirtBallKing)
        {
            float amount = Math.min(entity.getHealth(), event.getAmount());
            Entity source = event.getSource().getTrueSource();
            if (source instanceof EntityPlayer)
            {
                DirtBallPower power = source.getCapability(CapabilityRegistryHandler.DIRT_BALL_POWER, null);
                TextComponentTranslation text = addPower((EntityDirtBallKing) entity, power, amount);

                // Synchronize data
                NetworkRegistryHandler.Power.sendClientCustomPacket((EntityPlayer) source);

                source.sendMessage(text);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event)
    {
        Entity source = event.getSource().getImmediateSource();
        if (source instanceof EntityPlayer && !source.world.isRemote)
        {
            EntityPlayer player = (EntityPlayer) source;
            ItemStack heldItemMainhand = player.getHeldItemMainhand();
            int level = EnchantmentHelper.getEnchantmentLevel(
                    EnchantmentRegistryHandler.EXPLOSION, heldItemMainhand);
            if (level > 0)
            {
                Entity target = event.getEntity();
                target.world.createExplosion(null,
                        target.posX, target.posY, target.posZ, 2 * level, false);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityStruckByLightning(EntityStruckByLightningEvent event)
    {
        Entity entity = event.getEntity();
        if (entity instanceof EntitySlime && !entity.world.isRemote && !entity.isDead)
        {
            EntityDirtBallKing newEntity = new EntityDirtBallKing(entity.world);

            DifficultyInstance difficulty = entity.world.getDifficultyForLocation(new BlockPos(entity));
            newEntity.setPosition(entity.posX, entity.posY, entity.posZ);
            newEntity.onInitialSpawn(difficulty, null);

            if (entity.hasCustomName())
            {
                newEntity.setAlwaysRenderNameTag(entity.getAlwaysRenderNameTag());
                newEntity.setCustomNameTag(entity.getCustomNameTag());
            }

            entity.world.spawnEntity(newEntity);
            entity.setDead();

            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event)
    {
        DamageSource damageSource = event.getSource();
        if ("fall".equals(damageSource.getDamageType()))
        {
            EntityLivingBase target = event.getEntityLiving();
            Potion potion = PotionRegistryHandler.POTION_DIRT_PROTECTION;
            if (target.isPotionActive(potion))
            {
                PotionEffect effect = target.getActivePotionEffect(potion);
                BlockPos pos = new BlockPos(target.posX, target.posY - 0.2, target.posZ);
                Block block = target.world.getBlockState(pos).getBlock();
                if (block == Blocks.DIRT || block == Blocks.GRASS)
                {
                    event.setAmount(effect.getAmplifier() > 0 ? 0 : event.getAmount() / 2);
                }
            }
        }
    }
}
