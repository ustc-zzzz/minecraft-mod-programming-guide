package zzzz.fmltutor.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import zzzz.fmltutor.FMLTutor;
import zzzz.fmltutor.block.BlockRegistryHandler;

@EventBusSubscriber
public class ItemRegistryHandler
{
    public static final Item.ToolMaterial DIRT_TOOL_MATERIAL = EnumHelper.addToolMaterial(
            "DIRT", 1, 44, 3.0F, 1.0F, 5);
    public static final ItemArmor.ArmorMaterial DIRT_ARMOR_MATERIAL = EnumHelper.addArmorMaterial(
            "DIRT", FMLTutor.MODID + ":dirt", 5, new int[] {1, 2, 2, 1}, 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);

    public static final ItemDirtBall DIRT_BALL = new ItemDirtBall();
    public static final ItemDirtPickaxe DIRT_PICKAXE = new ItemDirtPickaxe();
    public static final ItemDirtArmor DIRT_BOOTS = new ItemDirtArmor(EntityEquipmentSlot.FEET);
    public static final ItemDirtArmor DIRT_LEGGINGS = new ItemDirtArmor(EntityEquipmentSlot.LEGS);
    public static final ItemDirtArmor DIRT_CHESTPLATE = new ItemDirtArmor(EntityEquipmentSlot.CHEST);
    public static final ItemDirtArmor DIRT_HELMET = new ItemDirtArmor(EntityEquipmentSlot.HEAD);

    public static final ItemBlock ITEM_COMPRESSED_DIRT = withRegistryName(new ItemBlock(BlockRegistryHandler.BLOCK_COMPRESSED_DIRT));
    public static final ItemBlock ITEM_DIRT_COMPRESSOR = withRegistryName(new ItemBlock(BlockRegistryHandler.BLOCK_DIRT_COMPRESSOR));

    private static ItemBlock withRegistryName(ItemBlock item)
    {
        item.setRegistryName(item.getBlock().getRegistryName());
        return item;
    }

    @SubscribeEvent
    public static void onRegistry(Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(DIRT_BALL);
        registry.register(DIRT_PICKAXE);
        registry.register(DIRT_BOOTS);
        registry.register(DIRT_LEGGINGS);
        registry.register(DIRT_CHESTPLATE);
        registry.register(DIRT_HELMET);
        registry.register(ITEM_COMPRESSED_DIRT);
        registry.register(ITEM_DIRT_COMPRESSOR);
    }

    @SideOnly(Side.CLIENT)
    private static void registerModel(Item item)
    {
        ModelResourceLocation modelResourceLocation = new ModelResourceLocation(item.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, modelResourceLocation);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistry(ModelRegistryEvent event)
    {
        registerModel(DIRT_BALL);
        registerModel(DIRT_PICKAXE);
        registerModel(DIRT_BOOTS);
        registerModel(DIRT_LEGGINGS);
        registerModel(DIRT_CHESTPLATE);
        registerModel(DIRT_HELMET);
        registerModel(ITEM_COMPRESSED_DIRT);
        registerModel(ITEM_DIRT_COMPRESSOR);
    }
}
