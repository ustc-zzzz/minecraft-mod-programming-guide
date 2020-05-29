package zzzz.fmltutor.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import zzzz.fmltutor.FMLTutor;
import zzzz.fmltutor.creativetab.TabFMLTutor;

public class BlockCompressedDirt extends Block
{
    public BlockCompressedDirt()
    {
        this(Material.GROUND);
        this.setUnlocalizedName(FMLTutor.MODID + ".compressedDirt");
        this.setCreativeTab(TabFMLTutor.TAB_FMLTUTOR);
        this.setRegistryName("compressed_dirt");
        this.setHarvestLevel("shovel", 0);
        this.setHardness(0.5F);
    }

    private BlockCompressedDirt(Material material)
    {
        super(Material.GROUND);
    }
}
