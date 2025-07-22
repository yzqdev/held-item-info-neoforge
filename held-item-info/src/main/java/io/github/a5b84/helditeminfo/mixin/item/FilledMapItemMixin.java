package io.github.a5b84.helditeminfo.mixin.item;

import io.github.a5b84.helditeminfo.HeldItemInfoClient;
import io.github.a5b84.helditeminfo.TooltipAppender;
import io.github.a5b84.helditeminfo.TooltipBuilder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapId;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({MapItem.class})
public abstract class FilledMapItemMixin implements TooltipAppender {
   public boolean heldItemInfo_shouldAppendTooltip() {
      return HeldItemInfoClient.config.showFilledMapId();
   }

   public void heldItemInfo_appendTooltip(TooltipBuilder builder) {
      builder.append( (() -> {
         MapId mapIdComponent = (MapId)builder.stack.get(DataComponents.MAP_ID);
         MutableComponent text;
         if (mapIdComponent != null) {
            text = MapItem.getTooltipForId(mapIdComponent).copy();
         } else {
            text = Component.translatable("filled_map.unknown");
         }

         return text.withStyle(TooltipBuilder.DEFAULT_COLOR);
      }));
   }
}
