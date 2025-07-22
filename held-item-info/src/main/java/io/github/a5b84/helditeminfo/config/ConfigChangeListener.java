package io.github.a5b84.helditeminfo.config;

import io.github.a5b84.helditeminfo.HeldItemInfoClient;
import java.util.ArrayList;
import java.util.List;

import me.shedaniel.autoconfig.ConfigHolder;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;

public class ConfigChangeListener {
   public void listen(ConfigHolder<HeldItemInfoConfig.HeldItemInfoAutoConfig> holder) {
      holder.registerSaveListener(this::onChange);
      holder.registerLoadListener(this::onChange);
   }

   public InteractionResult onChange(ConfigHolder<HeldItemInfoConfig.HeldItemInfoAutoConfig> holder, HeldItemInfoConfig.HeldItemInfoAutoConfig config) {
      List<String> idStrings = config.filteredEnchantments();
      List<ResourceLocation> ids = new ArrayList(idStrings.size());

      for(String idString : idStrings) {
         try {
            ids.add(ResourceLocation.parse(idString));
         } catch (ResourceLocationException e) {
            HeldItemInfoClient.LOGGER.error("[Held Item Info] Invalid enchantment identifier '" + idString + "': " + e.getMessage());
         }
      }

      HeldItemInfoClient.filteredEnchantments = ids;
      return InteractionResult.SUCCESS;
   }
}
