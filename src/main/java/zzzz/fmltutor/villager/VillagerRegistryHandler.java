package zzzz.fmltutor.villager;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.registries.IForgeRegistry;
import zzzz.fmltutor.FMLTutor;
import zzzz.fmltutor.item.ItemRegistryHandler;

@EventBusSubscriber
public class VillagerRegistryHandler
{
    public static final VillagerProfession DIRT_WORKER =
            new VillagerProfession(FMLTutor.MODID + ":dirt_worker",
                    FMLTutor.MODID + ":textures/entity/villager/dirt_worker.png",
                    FMLTutor.MODID + ":textures/entity/zombie_villager/zombie_dirt_worker.png");
    public static final VillagerCareer DIRT_WORKER_CAREER =
            new VillagerCareer(DIRT_WORKER, FMLTutor.MODID + ".dirtWorker");

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<VillagerProfession> event)
    {
        IForgeRegistry<VillagerProfession> registry = event.getRegistry();
        DIRT_WORKER_CAREER.addTrade(1,
                new EntityVillager.EmeraldForItems(ItemRegistryHandler.DIRT_BALL,
                        new EntityVillager.PriceInfo(8, 10)),
                new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(Blocks.DIRT),
                        new EntityVillager.PriceInfo(1, 2)));
        EntityVillager.PriceInfo dirtPickaxePriceInfo =
                new EntityVillager.PriceInfo(3, 6);
        DIRT_WORKER_CAREER.addTrade(2, (merchant, recipeList, random) ->
        {
            int emeraldAmount = dirtPickaxePriceInfo.getPrice(random);
            recipeList.add(new MerchantRecipe(
                    new ItemStack(ItemRegistryHandler.DIRT_BALL, 3, 0),
                    new ItemStack(Items.EMERALD, emeraldAmount, 0),
                    new ItemStack(ItemRegistryHandler.DIRT_PICKAXE)));
        });
        registry.register(DIRT_WORKER);
    }
}
