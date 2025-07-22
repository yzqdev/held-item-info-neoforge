package io.github.a5b84.helditeminfo;

import io.github.a5b84.helditeminfo.config.ConfigChangeListener;
import io.github.a5b84.helditeminfo.config.HeldItemInfoConfig;

import java.util.List;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;

import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(value = HeldItemInfoClient.MOD_ID,dist = Dist.CLIENT)
public class HeldItemInfoClient {
    public static final String MOD_ID = "held_item_info";
    public static final Logger LOGGER = LoggerFactory.getLogger("held-item-info");

    public static HeldItemInfoConfig config;
    public static List<ResourceLocation> filteredEnchantments;

    public HeldItemInfoClient(IEventBus modEventBus, ModContainer modContainer) {


        modContainer.registerExtensionPoint(
                IConfigScreenFactory.class,
                (client, parent) -> AutoConfig.getConfigScreen(HeldItemInfoConfig.HeldItemInfoAutoConfig.class, parent).get()
        );
        ConfigHolder<HeldItemInfoConfig.HeldItemInfoAutoConfig> holder = AutoConfig.register(HeldItemInfoConfig.HeldItemInfoAutoConfig.class, Toml4jConfigSerializer::new);
        config = holder.getConfig();
        ConfigChangeListener changeListener = new ConfigChangeListener();
        changeListener.listen(holder);
        changeListener.onChange(holder, holder.getConfig());




    }
}
