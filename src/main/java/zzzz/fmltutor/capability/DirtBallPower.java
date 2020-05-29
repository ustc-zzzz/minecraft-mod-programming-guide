package zzzz.fmltutor.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class DirtBallPower implements INBTSerializable<NBTTagCompound>
{
    private float orangePower;
    private float greenPower;
    private float bluePower;

    public DirtBallPower()
    {
        this.orangePower = 0.0F;
        this.greenPower = 0.0F;
        this.bluePower = 0.0F;
    }

    public float getOrangePower()
    {
        return this.orangePower;
    }

    public void setOrangePower(float orangePower)
    {
        this.orangePower = orangePower;
    }

    public float getGreenPower()
    {
        return this.greenPower;
    }

    public void setGreenPower(float greenPower)
    {
        this.greenPower = greenPower;
    }

    public float getBluePower()
    {
        return this.bluePower;
    }

    public void setBluePower(float bluePower)
    {
        this.bluePower = bluePower;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setFloat("Orange", this.orangePower);
        nbt.setFloat("Green", this.greenPower);
        nbt.setFloat("Blue", this.bluePower);

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        this.orangePower = nbt.getFloat("Orange");
        this.greenPower = nbt.getFloat("Green");
        this.bluePower = nbt.getFloat("Blue");
    }
}
