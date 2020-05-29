package zzzz.fmltutor.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class EnchantmentRegistryHandler
{
    public static final EnchantmentExplosion EXPLOSION = new EnchantmentExplosion();

    @SubscribeEvent
    public static void onRegistry(Register<Enchantment> event)
    {
        IForgeRegistry<Enchantment> registry = event.getRegistry();
        registry.register(EXPLOSION);
    }
}
