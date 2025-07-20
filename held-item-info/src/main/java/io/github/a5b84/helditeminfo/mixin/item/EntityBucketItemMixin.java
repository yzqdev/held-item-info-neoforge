package io.github.a5b84.helditeminfo.mixin.item;

import io.github.a5b84.helditeminfo.GenericTooltipAppender;
import io.github.a5b84.helditeminfo.HeldItemInfo;
import net.minecraft.world.item.MobBucketItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({MobBucketItem.class})
public abstract class EntityBucketItemMixin implements GenericTooltipAppender {
   public boolean heldItemInfo_shouldAppendTooltip() {
      return HeldItemInfo.config.showEntityBucketContent();
   }
}
