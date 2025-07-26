package io.github.a5b84.helditeminfo;

import io.github.a5b84.helditeminfo.config.ConfigChangeListener;
import io.github.a5b84.helditeminfo.config.HeldItemInfoConfig;
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

import java.util.List;

@Mod(value = HeldItemInfo.MOD_ID )
public class HeldItemInfo {
    public static final String MOD_ID = "held_item_info";
    public static final Logger LOGGER = LoggerFactory.getLogger("held-item-info");
 

    public HeldItemInfo(IEventBus modEventBus, ModContainer modContainer) {

    }
}
