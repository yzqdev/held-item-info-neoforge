package io.github.a5b84.helditeminfo.mixin.item;

import io.github.a5b84.helditeminfo.GenericTooltipAppender;
import io.github.a5b84.helditeminfo.HeldItemInfoClient;
import net.minecraft.world.item.InstrumentItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({InstrumentItem.class})
public abstract class GoatHornItemMixin implements GenericTooltipAppender {
   public boolean heldItemInfo_shouldAppendTooltip() {
      return HeldItemInfoClient.config.showGoatHornInstrument();
   }
}
