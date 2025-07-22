package io.github.a5b84.helditeminfo.mixin.item;

import io.github.a5b84.helditeminfo.GenericTooltipAppender;
import io.github.a5b84.helditeminfo.HeldItemInfoClient;
import io.github.a5b84.helditeminfo.TooltipBuilder;
import io.github.a5b84.helditeminfo.Util;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.TippedArrowItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({PotionItem.class, TippedArrowItem.class})
public abstract class ItemsWithPotionEffectsMixin implements GenericTooltipAppender {
   public boolean heldItemInfo_shouldAppendTooltip() {
      return HeldItemInfoClient.config.showPotionEffects();
   }

   public List<Component> heldItemInfo_postProcess(TooltipBuilder builder, List<Component> tooltip) {
      int i = 0;

      for(Component line : tooltip) {
         if (Util.isBlank(line)) {
            tooltip = tooltip.subList(0, i);
            break;
         }

         ++i;
      }

      return tooltip;
   }
}
