package zzzz.fmltutor.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import zzzz.fmltutor.FMLTutor;
import zzzz.fmltutor.creativetab.TabFMLTutor;

public class ItemDirtPickaxe extends ItemPickaxe
{
    public ItemDirtPickaxe()
    {
        super(ItemRegistryHandler.DIRT_TOOL_MATERIAL);
        this.setUnlocalizedName(FMLTutor.MODID + ".dirtPickaxe");
        this.setCreativeTab(TabFMLTutor.TAB_FMLTUTOR);
        this.setRegistryName("dirt_pickaxe");
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        Block block = state.getBlock();
        float speed = super.getDestroySpeed(stack, state);
        return (block == Blocks.DIRT || block == Blocks.GRASS) ? speed * 10 : speed;
    }
}
