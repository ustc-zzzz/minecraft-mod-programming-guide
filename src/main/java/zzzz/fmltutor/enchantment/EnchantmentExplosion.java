package zzzz.fmltutor.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import zzzz.fmltutor.FMLTutor;

public class EnchantmentExplosion extends Enchantment
{
    public EnchantmentExplosion()
    {
        super(Rarity.RARE, EnumEnchantmentType.WEAPON,
                new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
        this.setName(FMLTutor.MODID + ".explosion");
        this.setRegistryName("explosion");
    }

    @Override
    public int getMaxLevel()
    {
        return 3;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 16 + enchantmentLevel * 5;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return 21 + enchantmentLevel * 5;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench)
    {
        return super.canApplyTogether(ench) && Enchantments.SWEEPING != ench;
    }
}
