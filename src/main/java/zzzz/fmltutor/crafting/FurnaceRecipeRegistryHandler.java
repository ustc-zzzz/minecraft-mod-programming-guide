package zzzz.fmltutor.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import zzzz.fmltutor.item.ItemRegistryHandler;

@EventBusSubscriber
public class FurnaceRecipeRegistryHandler
{
    public static void register()
    {
        GameRegistry.addSmelting(ItemRegistryHandler.DIRT_PICKAXE,
                new ItemStack(ItemRegistryHandler.DIRT_BALL), 0.1F);
        GameRegistry.addSmelting(ItemRegistryHandler.DIRT_HELMET,
                new ItemStack(ItemRegistryHandler.DIRT_BALL), 0.1F);
        GameRegistry.addSmelting(ItemRegistryHandler.DIRT_CHESTPLATE,
                new ItemStack(ItemRegistryHandler.DIRT_BALL), 0.1F);
        GameRegistry.addSmelting(ItemRegistryHandler.DIRT_LEGGINGS,
                new ItemStack(ItemRegistryHandler.DIRT_BALL), 0.1F);
        GameRegistry.addSmelting(ItemRegistryHandler.DIRT_BOOTS,
                new ItemStack(ItemRegistryHandler.DIRT_BALL), 0.1F);
    }

    @SubscribeEvent
    public static void onFurnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event)
    {
        ResourceLocation registryName = event.getItemStack().getItem().getRegistryName();
        String registryNameResourceDomain = registryName.getResourceDomain();
        String registryNameResourcePath = registryName.getResourcePath();
        if ("minecraft".equals(registryNameResourceDomain) && "dirt".equals(registryNameResourcePath))
        {
            event.setBurnTime(400);
        }
    }
}
