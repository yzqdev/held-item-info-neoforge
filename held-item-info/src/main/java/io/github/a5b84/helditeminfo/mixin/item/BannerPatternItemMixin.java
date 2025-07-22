package io.github.a5b84.helditeminfo.mixin.item;

import io.github.a5b84.helditeminfo.GenericTooltipAppender;
import io.github.a5b84.helditeminfo.HeldItemInfoClient;
import net.minecraft.world.item.BannerPatternItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({BannerPatternItem.class})
public abstract class BannerPatternItemMixin implements GenericTooltipAppender {
   public boolean heldItemInfo_shouldAppendTooltip() {
      return HeldItemInfoClient.config.showBannerPatternName();
   }
}
