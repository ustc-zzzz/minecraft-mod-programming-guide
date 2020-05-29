package zzzz.fmltutor.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import zzzz.fmltutor.FMLTutor;
import zzzz.fmltutor.potion.PotionRegistryHandler;

public class EntityDirtBall extends EntityThrowable
{
    public static final String ID = "dirt_ball";
    public static final String NAME = FMLTutor.MODID + ".DirtBall";

    public EntityDirtBall(World worldIn)
    {
        super(worldIn);
    }

    public EntityDirtBall(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        if (!this.world.isRemote)
        {
            if (result.entityHit != null)
            {
                float amount = 6.0F;
                DamageSource source = DamageSource.causeThrownDamage(this, this.getThrower());
                if (result.entityHit instanceof EntityLivingBase)
                {
                    EntityLivingBase target = ((EntityLivingBase) result.entityHit);
                    if (target.isPotionActive(PotionRegistryHandler.POTION_DIRT_PROTECTION))
                    {
                        PotionEffect effect = target.getActivePotionEffect(PotionRegistryHandler.POTION_DIRT_PROTECTION);
                        amount = effect.getAmplifier() > 0 ? 0 : amount / 2;
                    }
                }
                result.entityHit.attackEntityFrom(source, amount);
            }
            this.setDead();
        }
    }
}
