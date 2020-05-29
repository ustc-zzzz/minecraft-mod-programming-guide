package zzzz.fmltutor.item;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import zzzz.fmltutor.FMLTutor;
import zzzz.fmltutor.creativetab.TabFMLTutor;

public class ItemDirtArmor extends ItemArmor
{
    public ItemDirtArmor(EntityEquipmentSlot equipmentSlot)
    {
        super(ItemRegistryHandler.DIRT_ARMOR_MATERIAL, 0, equipmentSlot);
        this.setUnlocalizedName(FMLTutor.MODID + ".dirtArmor." + equipmentSlot.getName());
        this.setRegistryName("dirt_armor_" + equipmentSlot.getName());
        this.setCreativeTab(TabFMLTutor.TAB_FMLTUTOR);
    }
}
