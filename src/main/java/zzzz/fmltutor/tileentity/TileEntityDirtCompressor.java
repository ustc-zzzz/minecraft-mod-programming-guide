package zzzz.fmltutor.tileentity;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import zzzz.fmltutor.FMLTutor;
import zzzz.fmltutor.item.ItemRegistryHandler;

import javax.annotation.Nullable;

public class TileEntityDirtCompressor extends TileEntity implements ITickable
{
    public static final String ID = FMLTutor.MODID + ":dirt_compressor";

    private final ItemStackHandler up = new ItemStackHandler(1)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            TileEntityDirtCompressor.this.markDirty();
        }
    };
    private final ItemStackHandler side = new ItemStackHandler(1)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            TileEntityDirtCompressor.this.markDirty();
        }
    };
    private final ItemStackHandler down = new ItemStackHandler(1)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            TileEntityDirtCompressor.this.markDirty();
        }
    };

    private int compressorProgress = 0;

    public int getCompressorProgress()
    {
        return this.compressorProgress;
    }

    @Override
    public void update()
    {
        Item dirt = Item.getItemFromBlock(Blocks.DIRT);
        boolean canExtractInput = dirt.equals(this.side.extractItem(0, 1, true).getItem());
        if (canExtractInput)
        {
            if (this.compressorProgress % 20 == 0)
            {
                Item dirtBall = ItemRegistryHandler.DIRT_BALL;
                boolean canExtractDirtBall = dirtBall.equals(this.up.extractItem(0, 1, true).getItem());
                if (canExtractDirtBall)
                {
                    this.up.extractItem(0, 1, false);
                    this.compressorProgress += 1;
                }
            }
            else
            {
                this.compressorProgress += 1;
                if (this.compressorProgress >= 240)
                {
                    ItemStack compressedDirt = new ItemStack(ItemRegistryHandler.ITEM_COMPRESSED_DIRT);
                    boolean canInsertCompressedDirt = this.down.insertItem(0, compressedDirt, true).isEmpty();
                    if (canInsertCompressedDirt)
                    {
                        this.down.insertItem(0, compressedDirt, false);
                        this.side.extractItem(0, 1, false);
                        this.compressorProgress = 0;
                    }
                    else
                    {
                        this.compressorProgress -= 1;
                    }
                }
                else
                {
                    this.markDirty();
                }
            }
        }
        else if (this.compressorProgress > 0)
        {
            this.compressorProgress = 0;
            this.markDirty();
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        Capability<IItemHandler> itemHandlerCapability = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        return itemHandlerCapability.equals(capability) || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        Capability<IItemHandler> itemHandlerCapability = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        if (itemHandlerCapability.equals(capability))
        {
            if (EnumFacing.UP.equals(facing))
            {
                return itemHandlerCapability.cast(this.up);
            }
            if (EnumFacing.DOWN.equals(facing))
            {
                return itemHandlerCapability.cast(this.down);
            }
            return itemHandlerCapability.cast(this.side);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        this.compressorProgress = compound.getInteger("Progress");
        this.down.deserializeNBT(compound.getCompoundTag("Down"));
        this.side.deserializeNBT(compound.getCompoundTag("Side"));
        this.up.deserializeNBT(compound.getCompoundTag("Up"));
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("Progress", this.compressorProgress);
        compound.setTag("Down", this.down.serializeNBT());
        compound.setTag("Side", this.side.serializeNBT());
        compound.setTag("Up", this.up.serializeNBT());
        return super.writeToNBT(compound);
    }
}
